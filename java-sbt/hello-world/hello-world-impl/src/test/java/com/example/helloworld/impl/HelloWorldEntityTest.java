package com.example.helloworld.impl;

import akka.Done;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver;
import com.lightbend.lagom.javadsl.testkit.PersistentEntityTestDriver.Outcome;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import com.example.helloworld.impl.HelloWorldCommand.Hello;
import com.example.helloworld.impl.HelloWorldCommand.UseGreetingMessage;
import com.example.helloworld.impl.HelloWorldEvent.GreetingMessageChanged;

import static org.junit.Assert.assertEquals;

public class HelloWorldEntityTest {
    private static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("HelloWorldEntityTest");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testHelloWorldEntity() {
        PersistentEntityTestDriver<HelloWorldCommand, HelloWorldEvent, HelloWorldState> driver = new PersistentEntityTestDriver<>(system,
                new HelloWorldEntity(), "world-1");

        Outcome<HelloWorldEvent, HelloWorldState> outcome1 = driver.run(new Hello("Alice"));
        assertEquals("Hello, Alice!", outcome1.getReplies().get(0));
        assertEquals(Collections.emptyList(), outcome1.issues());

        Outcome<HelloWorldEvent, HelloWorldState> outcome2 = driver.run(new UseGreetingMessage("Hi"),
                new Hello("Bob"));
        assertEquals(1, outcome2.events().size());
        assertEquals(new GreetingMessageChanged("world-1", "Hi"), outcome2.events().get(0));
        assertEquals("Hi", outcome2.state().message);
        assertEquals(Done.getInstance(), outcome2.getReplies().get(0));
        assertEquals("Hi, Bob!", outcome2.getReplies().get(1));
        assertEquals(2, outcome2.getReplies().size());
        assertEquals(Collections.emptyList(), outcome2.issues());
    }
}
