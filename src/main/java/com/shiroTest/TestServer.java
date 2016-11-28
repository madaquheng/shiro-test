package com.shiroTest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.shiroTest.resources.TestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.net.URL;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class TestServer extends Application<TestConfiguration> {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new MyShiroModule(), new MyShiroAopModule());
        SecurityManager securityManager = injector.getInstance(SecurityManager.class);
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()) {
            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            //(do you know what movie this is from? ;)
            UsernamePasswordToken token = new UsernamePasswordToken("exampleuser", "examplepord");
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
        final TestResource resource = new TestResource();
        environment.jersey().register(resource);
    }
}
