package com.shiroTest;

import java.lang.annotation.Annotation;
import org.apache.shiro.aop.AnnotationHandler;
import org.apache.shiro.aop.AnnotationMethodInterceptor;
import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.guice.aop.ShiroAopModule;

/**
 * Created by quheng on 11/26/16.
 */
class MyShiroAopModule extends ShiroAopModule {
    protected void configureInterceptors(AnnotationResolver resolver)
    {
    }

}

