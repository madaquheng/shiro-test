package com.shiroTest;

import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;

/**
 * Created by quheng on 11/28/16.
 */
public class MyCustomAnnotationMethodInterceptor extends AnnotationMethodInterceptor {
    private final AuthenticatedAnnotationHandler handler;


    public MyCustomAnnotationMethodInterceptor(AuthenticatedAnnotationHandler handler, AnnotationResolver resolver) {
        super(handler, resolver);
        this.handler = handler;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("******************invoke*******************");
        handler.assertAuthorized(getAnnotation(methodInvocation));
        return methodInvocation.proceed();
    }
}