/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.linecorp.talking.bot.infra.http;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class Client {

    private static final Logger logger = Logger.getLogger(Client.class);

    public static <T, R> R getClient(
            final String url,
            final Class<T> service,
            final Function<T, Call<R>> function,
            final Optional<Gson> optGson){

        // For debug
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(interceptor);
//        clientBuilder.connectTimeout(100, TimeUnit.SECONDS);
//        clientBuilder.readTimeout(100,TimeUnit.SECONDS);

        // create GsonConverterFactory
        GsonConverterFactory factory;
        if(optGson.isPresent()){
            factory = GsonConverterFactory.create(optGson.get());
        } else {
            factory = GsonConverterFactory.create();
        }

        // Create a adapter which points the API.
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .client(clientBuilder.build())
            .addConverterFactory(factory)
            .build();

        if (logger.isDebugEnabled()) {
            logger.debug("request url : " + retrofit.baseUrl());
        }

        // Create an instance of API interface.
        T t = retrofit.create(service);
        // Create a call instance for looking up method.
        Call<R> call = function.apply(t);
        // call API
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }

    }

}
