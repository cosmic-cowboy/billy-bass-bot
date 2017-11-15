/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import java.lang.reflect.Field;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

/**
 *
 */
public abstract class Message {
    public final String type;
    public Message(MessageType type) {
        this.type = type.value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Class: " + this.getClass().getCanonicalName() + "\n");
        sb.append("Settings:\n");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                sb.append(field.getName() + " = " + field.get(this) + "\n");
            } catch (IllegalAccessException e) {
                sb.append(field.getName() + " = " + "access denied\n");
            }
        }
        return sb.toString();
    }

}
