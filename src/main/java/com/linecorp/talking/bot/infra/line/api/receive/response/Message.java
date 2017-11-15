/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.receive.response;

import java.math.BigDecimal;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

/**
 *
 */
public class Message extends JsonObjectInterface {

    public final String id;
    public final MessageType type;
    public final String text;

    public final String title;
    public final String address;
    public final BigDecimal latitude;
    public final BigDecimal longitude;

    public final String packageId;
    public final String stickerId;

    public final String userId;
    public final String displayName;

    public Message(String id, MessageType type, String text, String title, String address, BigDecimal latitude,
            BigDecimal longitude, String packageId, String stickerId, String userId, String displayName) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.packageId = packageId;
        this.stickerId = stickerId;
        this.userId = userId;
        this.displayName = displayName;
    }
}
