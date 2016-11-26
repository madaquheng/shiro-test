package com.shiroTest;

import com.shiroTest.resources.TestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class TestServer extends Application<TestConfiguration> {

    public static void main(String[] args) throws Exception {
        new TestServer().run(args);
    }

    @Override
    public String getName() {
        return "TestServer";
    }


    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        final TestResource resource = new TestResource();
        environment.jersey().register(resource);
    }
}
