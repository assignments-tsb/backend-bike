package com.bike.app.driver.datastore;

import io.micronaut.context.annotation.Requires;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Requires(property="feature.persistence", value="ORM", defaultValue="MEMORY")
public @interface FeatureORMPersistence {}