/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import java.math.BigDecimal;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class LocationMessage extends Message {

    public final String title;
    public final String address;
    public final BigDecimal latitude;
    public final BigDecimal longitude;

    public LocationMessage(String title, String address, BigDecimal latitude,
            BigDecimal longitude) {
        super(MessageType.Location);
        this.title = title;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
