/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api;

import java.util.List;

public class LanguagesRequest {

    public final List<Document> documents;
    public LanguagesRequest(List<Document> documents) {
        this.documents = documents;
    }

    public static class Document {
        public final String id;
        public final String text;

        public Document(String id, String text) {
            this.id = id;
            this.text = text;
        }
    }
}
