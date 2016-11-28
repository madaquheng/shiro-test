package com.shiroTest;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;
import org.apache.shiro.guice.aop.ShiroAopModule;

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
