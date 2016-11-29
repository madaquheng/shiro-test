package com.shiroTest;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import org.apache.shiro.config.Ini;
import org.apache.shiro.realm.Realm;

/**
 * Created by quheng on 11/26/16.
 */
class MyShiroModule extends AbstractModule {

    @Provides
    Ini provideShiroIni() {
        return Ini.fromResourcePath("classpath:shiro.ini");
    }

    protected void configure() {
        bind(Realm.class).to(MyRealm.class).in(Scopes.SINGLETON);
    }
}
