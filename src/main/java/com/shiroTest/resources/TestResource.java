package com.shiroTest.resources;

import com.codahale.metrics.annotation.Timed;

import javax.security.auth.Subject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;

@Path("/tes")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @GET
    @Timed
    @RequiresAuthentication
    public String sayHello(Subject subject) {
        return subject.toString();
    }
}