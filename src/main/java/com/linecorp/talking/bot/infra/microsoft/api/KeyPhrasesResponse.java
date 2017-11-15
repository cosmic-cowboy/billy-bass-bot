/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api;

import java.util.List;

public class KeyPhrasesResponse {

    public final List<Document> documents;
    public KeyPhrasesResponse(List<Document> documents) {
        this.documents = documents;
    }

    public static class Document {
        public final List<String> keyPhrases;
        public final String id;

        public Document(List<String> keyPhrases, String id) {
            this.keyPhrases = keyPhrases;
            this.id = id;
        }

    }
}
