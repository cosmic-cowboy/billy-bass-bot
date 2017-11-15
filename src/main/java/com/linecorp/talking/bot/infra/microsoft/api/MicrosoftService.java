/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.linecorp.talking.bot.infra.http.Client;

import retrofit2.Call;

/**
 *
 */
@Component
public class MicrosoftService {

    @Value("${microsoft.cognitive.apiDomain}")
    private String apiDomain;

    @Value("${microsoft.key}")
    private String key;

    public LanguagesResponse language(final String message) {
        return getClient(t -> t.languages(key, new LanguagesRequest(Collections.singletonList(new LanguagesRequest.Document("1", message)))));
    }

    public KeyPhrasesResponse keyPhrases(final String message, final String language) {
        return getClient(t -> t.keyPhrases(key, new KeyPhrasesRequest(Collections.singletonList(new KeyPhrasesRequest.Document(language, "1", message)))));
    }

    ////////////
    //private
    ////////////
    private <R> R getClient(final Function<MicrosoftAPI, Call<R>> function) {

    Gson gson = new GsonBuilder().create();

    return Client.getClient(apiDomain, MicrosoftAPI.class, function, Optional.of(gson));
    }

}
