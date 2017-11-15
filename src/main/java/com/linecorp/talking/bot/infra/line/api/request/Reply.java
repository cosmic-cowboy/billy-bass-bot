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
public class Reply {

    public final String replyToken;
    public final List<Message> messages;

    public Reply(String replyToken, List<Message> messages) {
        this.replyToken = replyToken;
        this.messages = messages;
    }
}
