/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api;

import java.util.List;

public class LanguagesResponse {

    public final List<Document> documents;
    public LanguagesResponse(List<Document> documents) {
        this.documents = documents;
    }

    public static class Document {
        public final List<DetectedLanguage> detectedLanguages;
        public final String id;

        public Document(List<DetectedLanguage> detectedLanguages, String id) {
            this.detectedLanguages = detectedLanguages;
            this.id = id;
        }
    }

    public static class DetectedLanguage{
        public final String name;
        public final String iso6391Name;
        public final double score;

        public DetectedLanguage(String name, String iso6391Name, double score) {
            this.name = name;
            this.iso6391Name = iso6391Name;
            this.score = score;
        }
    }
}
