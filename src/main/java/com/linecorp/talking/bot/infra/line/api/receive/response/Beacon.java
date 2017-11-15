/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.receive.response;

/**
 *
 */
public class Beacon extends JsonObjectInterface {
    public final String hwid;
    public final String type;

    public Beacon(String hwid, String type) {
        this.hwid = hwid;
        this.type = type;
    }
}
