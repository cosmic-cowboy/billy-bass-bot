/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SpeechAPI {

    @Headers({
        "Content-type: audio/wav",
        "codec: audio/pcm",
        "samplerate: 16000",
        "Transfer-Encoding: chunked"
    })
    @POST("speech/recognition/conversation/cognitiveservices/v1?language=ja-JP")
    Call<Void> speechToText(
            @Header("Authorization") String authorization,
            @Body RequestBody body);

}
