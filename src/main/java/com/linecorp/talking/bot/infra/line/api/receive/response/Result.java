/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.receive.response;

import com.linecorp.talking.bot.infra.line.api.common.EventType;

/**
 *
 */
public class Result extends JsonObjectInterface {

    public final String replyToken;
    public final EventType type;
    public final String timestamp;
    public final Source source;
    public final Message message;
    public final Postback postback;
    public final Beacon beacon;

    public Result(String replyToken, EventType type, String timestamp, Source source, Message message,
            Postback postback, Beacon beacon) {
        this.replyToken = replyToken;
        this.type = type;
        this.timestamp = timestamp;
        this.source = source;
        this.message = message;
        this.postback = postback;
        this.beacon = beacon;
    }
}
