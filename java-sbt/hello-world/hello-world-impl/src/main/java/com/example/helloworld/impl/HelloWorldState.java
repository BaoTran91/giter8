package com.example.helloworld.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.CompressedJsonable;
import lombok.Value;

/**
 * The state for the {@link HelloWorldEntity} entity.
 */
@SuppressWarnings("serial")
@Value
@JsonDeserialize
public final class HelloWorldState implements CompressedJsonable {
    public final String message;
    public final String timestamp;

    @JsonCreator
    HelloWorldState(String message, String timestamp) {
        this.message = Preconditions.checkNotNull(message, "message");
        this.timestamp = Preconditions.checkNotNull(timestamp, "timestamp");
    }
}
