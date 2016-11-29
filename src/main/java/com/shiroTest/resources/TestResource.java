package com.shiroTest.resources;

import com.codahale.metrics.annotation.Timed;

import com.google.inject.AbstractModule;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

@Path("/tes")
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

    @GET
    @Timed
    @RequiresAuthentication
    public String sayHello(@Auth Subject subject) {
        new org.apache.shiro.web.servlet.ShiroFilter();
        return subject.getPrincipal().toString();
    }
}