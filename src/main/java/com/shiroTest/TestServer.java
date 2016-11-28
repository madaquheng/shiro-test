package com.shiroTest;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.shiroTest.resources.TestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class TestServer extends Application<TestConfiguration> {
    static Injector injector = Guice.createInjector(new MyShiroModule(), new MyShiroAopModule(), new TestResource());

    public static void main(String[] args) throws Exception {
        SecurityManager securityManager = injector.getInstance(SecurityManager.class);
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            //(do you know what movie this is from? ;)
            UsernamePasswordToken token = new UsernamePasswordToken("exampleuser", "examplepasswor");
            //this is all you have to do to support 'remember me' (no config - built in!):

            try {
                currentUser.login(token);
                System.out.println("!!!!" + "succ");
            } catch (UnknownAccountException uae) {
                System.out.println("!!!!" + "UnknownAccountException");
            } catch (IncorrectCredentialsException ice) {
                System.out.println("!!!!" + "IncorrectCredentialsException");
            } catch (LockedAccountException lae) {
                System.out.println("!!!!" + "LockedAccountException");
            } catch (AuthenticationException ae) {
                System.out.println("!!!!" + "AuthenticationException");
            }
        }

        new TestServer().run(args);
    }

    @Override
    public String getName() {
        return "TestServer";
    }

    @Override
    public void initialize(Bootstrap<TestConfiguration> bootstrap) {

    }

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(injector.getInstance(TestResource.class));
        environment.jersey().register(new CustomerExceptionMapper(environment.metrics()));
    }
}
