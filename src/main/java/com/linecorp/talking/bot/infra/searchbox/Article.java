/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.searchbox;

import com.linecorp.talking.bot.infra.line.api.receive.response.JsonObjectInterface;

import io.searchbox.annotations.JestId;

/**
 *
 */
public class Article extends JsonObjectInterface {

    @JestId
    private String documentId;
    private String question;
    private String answer;
    private String category;

    public String getDocumentId() {
        return documentId;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public String getCategory() {
        return category;
    }
}
