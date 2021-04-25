package com.example.helloworldstream.impl;

import akka.Done;
import akka.stream.javadsl.Flow;

import com.example.helloworld.api.HelloWorldEvent;
import com.example.helloworld.api.HelloWorldService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

/**
 * This subscribes to the HelloWorldService event stream.
 */
public class HelloWorldStreamSubscriber {
    @Inject
    public HelloWorldStreamSubscriber(HelloWorldService helloWorldService, HelloWorldStreamRepository repository) {
        // Create a subscriber
        helloWorldService.helloEvents().subscribe()
                // And subscribe to it with at least once processing semantics.
                .atLeastOnce(
                        // Create a flow that emits a Done for each message it processes
                        Flow.<HelloWorldEvent>create().mapAsync(1, event -> {
                            if (event instanceof HelloWorldEvent.GreetingMessageChanged) {
                                HelloWorldEvent.GreetingMessageChanged messageChanged = (HelloWorldEvent.GreetingMessageChanged) event;
                                // Update the message
                                return repository.updateMessage(messageChanged.getName(), messageChanged.getMessage());
                            } else {
                                // Ignore all other events
                                return CompletableFuture.completedFuture(Done.getInstance());
                            }
                        })
                );
    }
}
