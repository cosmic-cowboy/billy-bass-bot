/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import java.util.List;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class ImageMapMessage extends Message {

    public final String baseUrl;
    public final String altText;
    public final BaseSize baseSize;
    public final List<Action> actions;

    public ImageMapMessage(String baseUrl, String altText, BaseSize baseSize, List<Action> actions) {
        super(MessageType.Imagemap);
        this.baseUrl = baseUrl;
        this.altText = altText;
        this.baseSize = baseSize;
        this.actions = actions;
    }

    // ----------------------------------------------------------------
    //     Internal class
    // ----------------------------------------------------------------

    public static class BaseSize {
        public final Integer width;
        public final Integer height;

        public BaseSize(Integer width, Integer height) {
            this.width = width;
            this.height = height;
        }
    }

    public static class Action {
        public final String type;
        public final String text;
        public final String linkUri;
        public final Area area;

        public Action(String type, String text, String linkUri, Area area) {
            this.type = type;
            this.text = text;
            this.linkUri = linkUri;
            this.area = area;
        }
    }

    public static class Area {
        public final Integer x;
        public final Integer y;
        public final Integer width;
        public final Integer height;

        public Area(Integer x, Integer y, Integer width, Integer height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }


}
