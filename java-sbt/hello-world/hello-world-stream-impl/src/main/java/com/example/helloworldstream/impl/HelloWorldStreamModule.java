package com.example.helloworldstream.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

import com.example.helloworld.api.HelloWorldService;
import com.example.helloworldstream.api.HelloWorldStreamService;

/**
 * The module that binds the HelloWorldStreamService so that it can be served.
 */
public class HelloWorldStreamModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        // Bind the HelloWorldStreamService service
        bindService(HelloWorldStreamService.class, HelloWorldStreamServiceImpl.class);
        // Bind the HelloWorldService client
        bindClient(HelloWorldService.class);
        // Bind the subscriber eagerly to ensure it starts up
        bind(HelloWorldStreamSubscriber.class).asEagerSingleton();
    }
}
