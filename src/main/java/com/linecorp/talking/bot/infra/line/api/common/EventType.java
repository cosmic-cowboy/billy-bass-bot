/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.common;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public enum EventType {

    Beacon("beacon"),
    Message("message"),
    Postback("postback"),
    Unfollow("unfollow"),
    Follow("follow"),
    Leave("leave"),
    Join("join");

    public final String value;

    private EventType(String value) {
        this.value = value;
    }

    public static EventType find(final String eventType) {
        Optional<EventType> optEventType =
                Arrays.stream(values()).filter(e -> e.value.equals(eventType)).findFirst();
        if (optEventType.isPresent()){
            return optEventType.get();
        } else {
            throw new IllegalArgumentException();
        }
    }


    public static class Deserializer implements JsonDeserializer<EventType> {

        @Override
        public EventType deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {

            String eventType = arg0.getAsJsonPrimitive().getAsString();
            return EventType.find(eventType);
        }

    }

    public static class Serializer implements JsonSerializer<EventType> {

        @Override
        public JsonElement serialize(EventType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.value);
        }
    }
}
