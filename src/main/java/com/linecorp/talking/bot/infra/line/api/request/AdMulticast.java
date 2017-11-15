/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request;

import java.util.List;

import com.linecorp.talking.bot.infra.line.api.request.message.Message;

/**
 *
 */
public class AdMulticast {

    public final To to;
    public final Integer tagno;
    public final List<Message> messages;


    public AdMulticast(List<String> ids, Integer tagno, List<Message> messages) {
        super();
        to = new To(ids);
        this.tagno = tagno;
        this.messages = messages;
    }

    static public class To {
        public final String type;
        public final List<String> ids;

        public To(List<String> ids) {
            type = "ifa";
            this.ids = ids;
        }
    }
}
