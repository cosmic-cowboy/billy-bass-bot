/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class ImageMessage extends Message {

    public final String originalContentUrl;
    public final String previewImageUrl;

    public ImageMessage(String originalContentUrl, String previewImageUrl) {
        super(MessageType.Image);
        this.originalContentUrl = originalContentUrl;
        this.previewImageUrl = previewImageUrl;
    }
}
