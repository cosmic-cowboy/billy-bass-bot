/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request;

import java.util.List;

import com.linecorp.talking.bot.infra.line.api.request.message.Message;

/**
 *
 */
public class Push {

    public final String to;
    public final List<Message> messages;

    public Push(String to, List<Message> messages) {
        this.to = to;
        this.messages = messages;
    }
}
