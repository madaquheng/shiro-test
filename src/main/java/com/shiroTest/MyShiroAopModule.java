package com.shiroTest;

import com.google.inject.Provides;
import java.lang.annotation.Annotation;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.config.Ini;
import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.realm.text.IniRealm;

/**
 * Created by quheng on 11/26/16.
 */
class MyShiroAopModule extends ShiroAopModule {
    protected void configureInterceptors(AnnotationResolver resolver)
    {
        AuthenticatedAnnotationHandler annotationHandler = new AuthenticatedAnnotationHandler();
        bindShiroInterceptor(new MyCustomAnnotationMethodInterceptor(annotationHandler, resolver));
    }
}
