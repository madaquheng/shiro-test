package com.shiroTest.resources;

import com.codahale.metrics.annotation.Timed;

import javax.security.auth.Subject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;

@Path("/tes")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @GET
    @Timed
    @RequiresRoles("admin")
    public String sayHello(Subject subject) {
        return subject.toString();
    }
}