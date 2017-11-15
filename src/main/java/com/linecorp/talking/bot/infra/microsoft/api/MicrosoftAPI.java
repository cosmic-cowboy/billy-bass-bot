/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MicrosoftAPI {

    @POST("text/analytics/v2.0/keyPhrases")
    Call<KeyPhrasesResponse> keyPhrases(
            @Header("Ocp-Apim-Subscription-Key") String key,
            @Body KeyPhrasesRequest keyPhrasesRequest);

    @POST("text/analytics/v2.0/languages")
    Call<LanguagesResponse> languages(
            @Header("Ocp-Apim-Subscription-Key") String key,
            @Body LanguagesRequest languagesRequest);

}
