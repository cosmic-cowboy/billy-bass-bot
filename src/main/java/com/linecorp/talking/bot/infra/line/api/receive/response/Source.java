/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.receive.response;

import com.linecorp.talking.bot.infra.line.api.common.SourceType;

public class Source extends JsonObjectInterface {

    public final SourceType type;
    public final String roomId;
    public final String groupId;
    public final String userId;

    public Source(SourceType type, String roomId, String groupId, String userId) {
        this.type = type;
        this.roomId = roomId;
        this.groupId = groupId;
        this.userId = userId;
    }

}
