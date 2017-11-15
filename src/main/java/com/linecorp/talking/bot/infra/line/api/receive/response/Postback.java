/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.receive.response;

/**
 *
 */
public class Postback extends JsonObjectInterface {

    public final String data;
    public final String params;
    public Postback(String data, String params) {
        this.data = data;
        this.params = params;
    }

}
