package com.shiroTest.resources;

import com.codahale.metrics.annotation.Timed;

import com.google.inject.AbstractModule;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.shiro.authz.annotation.RequiresAuthentication;

@Path("/tes")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource extends AbstractModule {

    @GET
    @Timed
    @RequiresAuthentication
    public String sayHello() {
        return "hello";
    }

    protected void configure() {

    }
}