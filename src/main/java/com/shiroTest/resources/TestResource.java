package com.shiroTest.resources;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/tes")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @GET
    @Timed
    public String sayHello() {
        return "success";
    }
}