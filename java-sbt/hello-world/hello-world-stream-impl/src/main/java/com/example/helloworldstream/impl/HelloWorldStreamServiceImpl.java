package com.example.helloworldstream.impl;

import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import com.example.helloworld.api.HelloWorldService;
import com.example.helloworldstream.api.HelloWorldStreamService;

import javax.inject.Inject;

import static java.util.concurrent.CompletableFuture.completedFuture;

/**
 * Implementation of the HelloWorldStreamService.
 */
public class HelloWorldStreamServiceImpl implements HelloWorldStreamService {
    private final HelloWorldService helloWorldService;
    private final HelloWorldStreamRepository repository;

    @Inject
    public HelloWorldStreamServiceImpl(HelloWorldService helloWorldService, HelloWorldStreamRepository repository) {
        this.helloWorldService = helloWorldService;
        this.repository = repository;
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> directStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> helloWorldService.hello(name).invoke()));
    }

    @Override
    public ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> autonomousStream() {
        return hellos -> completedFuture(
                hellos.mapAsync(8, name -> repository.getMessage(name).thenApply(message ->
                        String.format("%s, %s!", message.orElse("Hello"), name)
                ))
        );
    }
}
