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
package com.linecorp.talking.bot.infra.line.api;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.linecorp.talking.bot.infra.http.Client;
import com.linecorp.talking.bot.infra.line.api.common.EventType;
import com.linecorp.talking.bot.infra.line.api.common.MessageType;
import com.linecorp.talking.bot.infra.line.api.common.SourceType;
import com.linecorp.talking.bot.infra.line.api.receive.ReceiveService;
import com.linecorp.talking.bot.infra.line.api.receive.response.AccessToken;
import com.linecorp.talking.bot.infra.line.api.receive.response.Profile;
import com.linecorp.talking.bot.infra.line.api.request.AdMulticast;
import com.linecorp.talking.bot.infra.line.api.request.Multicast;
import com.linecorp.talking.bot.infra.line.api.request.Push;
import com.linecorp.talking.bot.infra.line.api.request.Reply;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 *
 */
@Component
public class LineAPIService {

    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    @Value("${linecorp.platform.apiDomain}")
    private String apiDomain;

    @Value("${linecorp.platform.cpDomain}")
    private String cpDomain;

    @Value("${linecorp.platform.loginDomain}")
    private String loginDomain;

    @Value("${linecorp.platform.channelId}")
    private String channelId;

    @Value("${linecorp.platform.channelSecret}")
    private String channelSecret;

    @Value("${linecorp.platform.channelAccessToken}")
    private String channelAccessToken;

    @Autowired
    private ReceiveService receiveService;

    @Autowired
    private LineAuth lineAuth;

    // ----------------------------------------------------------------
    //     Receive
    // ----------------------------------------------------------------
    public void receiveData(final String requestBody) {
        receiveService.receive(requestBody);
    }

    // ----------------------------------------------------------------
    //     Send
    // ----------------------------------------------------------------
    public void multicast(final Multicast multicast) {
        getClient(t -> t.multicast(addBearer(channelAccessToken), multicast));
    }

    public void push(final Push push) {
        getClient(t -> t.push(addBearer(channelAccessToken), push));
    }

    public void reply(final Reply reply) {
        getClient(t -> t.reply(addBearer(channelAccessToken), reply));
    }

    // ----------------------------------------------------------------
    //     Send
    // ----------------------------------------------------------------
    public void admulticast(final AdMulticast adMulticast) {
        getClient(t -> t.admulticast(addBearer(channelAccessToken), adMulticast));
    }

    // ----------------------------------------------------------------
    //  Content
    //----------------------------------------------------------------
    public ResponseBody content(String messageId) {
        return getClient(t -> t.content(addBearer(channelAccessToken), messageId));
    }

    public Profile profile(String userId) {
        return getClient(t -> t.profile(addBearer(channelAccessToken), userId));
    }

    // ----------------------------------------------------------------
    //     Business Connect V2
    // ----------------------------------------------------------------
    public void convertMid(String mid) {
        getClient(t -> t.convertMid(addBearer(channelAccessToken), mid));
    }
    public void convertMids(String mids) {
        getClient(t -> t.convertMids(addBearer(channelAccessToken), mids));
    }

    // ----------------------------------------------------------------
    //  leave
    //----------------------------------------------------------------
    public void leaveGroup(String groupId) {
        getClient(t -> t.leaveGroup(addBearer(channelAccessToken), groupId));
    }
    public void leaveRoom(String roomId) {
        getClient(t -> t.leaveRoom(addBearer(channelAccessToken), roomId));
    }


    // ----------------------------------------------------------------
    //     Auth
    // ----------------------------------------------------------------
    public String getLineWebLoginUrl(final String state) {
        return lineAuth.getLineWebLoginUrl(state, channelId, loginDomain, cpDomain);
    }

    public void validateSignature(
            final String channelSignatureComputedByTheLinePlatform,
            final String httpRequestBody) {

        lineAuth.validateSignature(
                channelSecret,
                channelSignatureComputedByTheLinePlatform,
                httpRequestBody);
    }


    // ----------------------------------------------------------------
    //     Use user access token V2
    // ----------------------------------------------------------------
    public AccessToken accessToken(final String code) {
        return getClient(t -> t.accessToken(
                GRANT_TYPE_AUTHORIZATION_CODE,
                code,
                channelId,
                channelSecret,
                lineAuth.getRedirectUrl(cpDomain)));
    }

    public AccessToken refreshToken(final String refreshToken) {
        return getClient(t -> t.refreshToken(
                GRANT_TYPE_REFRESH_TOKEN,
                refreshToken,
                channelId,
                channelSecret));
    }

    public AccessToken issueToken() {
        return getClient(t -> t.issueToken(
                GRANT_TYPE_CLIENT_CREDENTIALS,
                channelId,
                channelSecret));
    }

    public void revokeChannelAccessToken(final String accessToken) {
        getClient(t -> t.revokeChannelAccessToken(accessToken));
    }

    public void verify(final String accessToken) {
        getClient(t -> t.verify(accessToken));
    }

    public void revoke(final String refreshToken) {
        getClient(t -> t.revoke(refreshToken));
    }

    public void profileWithUserAccessToken(final String accessToken) {
        getClient(t -> t.profile(addBearer(accessToken)));
    }

    // ----------------------------------------------------------------
    //     Use user access token
    // ----------------------------------------------------------------
    public AccessToken accessToken_v1(final String code) {
        return getClient(t -> t.accessToken_v1(
                GRANT_TYPE_AUTHORIZATION_CODE,
                channelId,
                channelSecret,
                lineAuth.getRedirectUrl(cpDomain),
                code));
    }

    public AccessToken refreshToken_v1(final String refreshToken) {
        return getClient(t -> t.refreshToken_v1(
                GRANT_TYPE_REFRESH_TOKEN,
                refreshToken,
                channelId,
                channelSecret));
    }

    public void permissions_v1(final String accessToken) {
        getClient(t -> t.permissions_v1(addBearer(accessToken)));
    }

    public void profile_v1(final String accessToken) {
        getClient(t -> t.profile_v1(addBearer(accessToken)));
    }

    public void email_v1(final String accessToken) {
        getClient(t -> t.email_v1(addBearer(accessToken)));
    }

    public void phone_v1(final String accessToken) {
        getClient(t -> t.phone_v1(addBearer(accessToken)));
    }

    ////////////
    // private
    ////////////
    private String addBearer(final String accessToken){
        return "Bearer " + accessToken;
    }

    private <R> R getClient(final Function<LineAPI, Call<R>> function) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EventType.class, new EventType.Deserializer())
                .registerTypeAdapter(MessageType.class, new MessageType.Deserializer())
                .registerTypeAdapter(SourceType.class, new SourceType.Deserializer())
                .create();

        return Client.getClient(apiDomain, LineAPI.class, function, Optional.of(gson));
    }

}
