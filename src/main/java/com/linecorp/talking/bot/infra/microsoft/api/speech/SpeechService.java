/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.linecorp.talking.bot.infra.http.Client;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 *
 */
@Component
public class SpeechService {

    public void speechToText(final File audioFile, final String accessToken) {
        RequestBody body = RequestBody.create(MediaType.parse("audio/wav"), audioFile);
        getClient(t -> t.speechToText(addBearer(accessToken), body));
    }

    ////////////
    //private
    ////////////
    private <R> R getClient(final Function<SpeechAPI, Call<R>> function) {

    Gson gson = new GsonBuilder().create();

    return Client.getClient("https://speech.platform.bing.com/", SpeechAPI.class, function, Optional.of(gson));
    }

    ////////////
    // private
    ////////////
    private String addBearer(final String accessToken){
        return "Bearer " + accessToken;
    }

}
