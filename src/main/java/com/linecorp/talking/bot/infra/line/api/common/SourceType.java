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

public enum SourceType {

    User("user"),
    Room("room"),
    Group("group");

    public final String value;

    private SourceType(String value) {
        this.value = value;
    }

    public static SourceType find(final String eventType) {
        Optional<SourceType> optEventType =
                Arrays.stream(values()).filter(e -> e.value.equals(eventType)).findFirst();
        if (optEventType.isPresent()){
            return optEventType.get();
        } else {
            throw new IllegalArgumentException();
        }
    }


    public static class Deserializer implements JsonDeserializer<SourceType> {

        @Override
        public SourceType deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {

            String eventType = arg0.getAsJsonPrimitive().getAsString();
            return SourceType.find(eventType);
        }

    }

    public static class Serializer implements JsonSerializer<SourceType> {

        @Override
        public JsonElement serialize(SourceType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.value);
        }
    }
}
