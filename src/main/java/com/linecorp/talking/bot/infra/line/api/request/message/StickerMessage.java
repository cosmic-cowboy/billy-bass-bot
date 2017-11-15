/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class StickerMessage extends Message {

    public final String packageId;
    public final String stickerId;

    public StickerMessage(String packageId, String stickerId) {
        super(MessageType.Sticker);
        this.packageId = packageId;
        this.stickerId = stickerId;
    }
}
