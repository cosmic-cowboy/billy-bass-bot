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


public enum MessageType {

    Text("text"),
    Image("image"),
    Video("video"),
    Audio("audio"),
    Location("location"),
    Sticker("sticker"),
    Imagemap("imagemap"),
    Template("template"),
    Contact("contact");

    public final String value;

    private MessageType(String value) {
        this.value = value;
    }

    public static MessageType find(final String eventType) {
        Optional<MessageType> optEventType =
                Arrays.stream(values()).filter(e -> e.value.equals(eventType)).findFirst();
        if (optEventType.isPresent()){
            return optEventType.get();
        } else {
            throw new IllegalArgumentException();
        }
    }


    public static class Deserializer implements JsonDeserializer<MessageType> {

        @Override
        public MessageType deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
                throws JsonParseException {

            String eventType = arg0.getAsJsonPrimitive().getAsString();
            return MessageType.find(eventType);
        }

    }

    public static class Serializer implements JsonSerializer<MessageType> {

        @Override
        public JsonElement serialize(MessageType src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.value);
        }
    }
}
