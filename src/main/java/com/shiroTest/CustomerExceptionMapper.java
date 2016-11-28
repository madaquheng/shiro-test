package com.shiroTest;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import io.dropwizard.jersey.errors.ErrorMessage;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.apache.shiro.authz.UnauthenticatedException;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by quheng on 11/28/16.
 */
public class CustomerExceptionMapper implements ExceptionMapper<UnauthenticatedException> {
    private final Meter exceptions;
    public CustomerExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(name(getClass(), "exceptions"));
    }

    public Response toResponse(UnauthenticatedException e) {
        exceptions.mark();
        return Response.status(Response.Status.UNAUTHORIZED)
            .header("X-YOU-SILLY", "true")
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),
                "You passed an illegal argument!"))
            .build();
    }
}