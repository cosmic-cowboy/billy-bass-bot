/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class AudioMessage extends Message {

    public final String originalContentUrl;
    public final String duration;

    public AudioMessage(String originalContentUrl, String duration) {
        super(MessageType.Audio);
        this.originalContentUrl = originalContentUrl;
        this.duration = duration;
    }
}
