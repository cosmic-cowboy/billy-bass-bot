/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class TextMessage extends Message {

    public final String text;

    public TextMessage(String text) {
        super(MessageType.Text);
        this.text = text;
    }
}
