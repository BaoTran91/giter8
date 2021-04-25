package com.example.helloworld.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import com.example.helloworld.api.HelloWorldService;

/**
 * The module that binds the HelloWorldService so that it can be served.
 */
public class HelloWorldModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(HelloWorldService.class, HelloWorldServiceImpl.class);
    }
}
