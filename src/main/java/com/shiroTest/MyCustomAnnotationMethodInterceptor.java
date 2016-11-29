package com.shiroTest;

import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.aop.AuthenticatedAnnotationHandler;

/**
 * Created by quheng on 11/28/16.
 */
public class MyCustomAnnotationMethodInterceptor extends AnnotationMethodInterceptor {

    public MyCustomAnnotationMethodInterceptor(AuthenticatedAnnotationHandler handler, AnnotationResolver resolver) {
        super(handler, resolver);
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("******************invoke*******************");
        return methodInvocation.proceed();
    }
}