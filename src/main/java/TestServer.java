package com.madadata.turbine.metadata.api;

import com.google.inject.Guice;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.madadata.turbine.auth.leancloud.LeancloudGuiceModule;
import com.madadata.turbine.auth.leancloud.health.LeancloudHealthcheck;
import com.madadata.turbine.common.dynamo.DynamoDbTableTask;
import com.madadata.turbine.common.resource.VersionResource;
import com.madadata.turbine.metadata.api.conf.MetadataApiConfiguration;
import com.madadata.turbine.metadata.api.exec.GraphQlExceptionMapper;
import com.madadata.turbine.metadata.api.module.GraphQlSchemaModule;
import com.madadata.turbine.metadata.api.resource.MetadataApiResource;
import com.madadata.turbine.metadata.guice.MetadataGuiceModule;
import com.madadata.turbine.metadata.health.DynamoDbHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javax.ws.rs.container.DynamicFeature;

/**
 * Created by jiayu on 7/15/16.
 */
public class MetadataApiServer extends Application<MetadataApiConfiguration> {

    private GuiceBundle<MetadataApiConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new MetadataApiServer().run(args);
    }

    @Override
    public String getName() {
        return "MetadataApiServer";
    }

    @Override
    public void initialize(Bootstrap<MetadataApiConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false))
        );
        guiceBundle = GuiceBundle.<MetadataApiConfiguration>newBuilder()
            .setConfigClass(MetadataApiConfiguration.class)
            .addModule(new MetadataGuiceModule())
            .addModule(new MetadataApiGuiceModule())
            .addModule(new GraphQlSchemaModule())
            .addModule(new LeancloudGuiceModule())
            // https://github.com/HubSpot/dropwizard-guice/issues/19
            // because we are using singletons that depend on environment and configuration
            // we must force it to be lazily initialized, and then force initialization on
            // the #run method below
            .setInjectorFactory((state, modules) -> Guice.createInjector(Stage.DEVELOPMENT, modules))
            .build();
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(MetadataApiConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(guiceBundle.getInjector().getInstance(VersionResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(GraphQlExceptionMapper.class));
        // This will force the initialization of this resource and dependent components
        environment.jersey().register(guiceBundle.getInjector().getInstance(MetadataApiResource.class));
        environment.jersey().register(guiceBundle.getInjector().getInstance(DynamicFeature.class));
        environment.admin().addTask(guiceBundle.getInjector().getInstance(DynamoDbTableTask.class));
        environment.healthChecks().register("DynamoDb", guiceBundle.getInjector().getInstance(DynamoDbHealthCheck.class));
        environment.healthChecks().register("Leancloud", guiceBundle.getInjector().getInstance(LeancloudHealthcheck.class));
    }
}
