/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api;

import com.linecorp.talking.bot.infra.line.api.receive.response.AccessToken;
import com.linecorp.talking.bot.infra.line.api.receive.response.Profile;
import com.linecorp.talking.bot.infra.line.api.request.AdMulticast;
import com.linecorp.talking.bot.infra.line.api.request.Multicast;
import com.linecorp.talking.bot.infra.line.api.request.Push;
import com.linecorp.talking.bot.infra.line.api.request.Reply;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 *
 */
public interface LineAPI {

    // ----------------------------------------------------------------
    //  Send
    //----------------------------------------------------------------

    @POST("v2/bot/message/reply")
    Call<Void> reply(
            @Header("Authorization") String authorization,
            @Body Reply reply);

    @POST("v2/bot/message/push")
    Call<Void> push(
            @Header("Authorization") String authorization,
            @Body Push push);

    @POST("v2/bot/message/multicast")
    Call<Void> multicast(
            @Header("Authorization") String authorization,
            @Body Multicast multicast);

    // ----------------------------------------------------------------
    //  Bot AD APIs
    //----------------------------------------------------------------
    @POST("/v2/bot/ad/message/multicast")
    Call<Void> admulticast(
            @Header("Authorization") String authorization,
            @Body AdMulticast adMulticast);


    // ----------------------------------------------------------------
    //  Content
    //----------------------------------------------------------------

    @GET("v2/bot/message/{messageId}/content")
    @Streaming
    Call<ResponseBody> content(
            @Header("Authorization") String authorization,
            @Path("messageId") String messageId);

    @GET("v2/bot/profile/{userId}")
    Call<Profile> profile(
            @Header("Authorization") String authorization,
            @Path("userId") String userId);

    // ----------------------------------------------------------------
    //  leave
    //----------------------------------------------------------------

    @POST("v2/bot/room/{roomId}/leave")
    Call<Void> leaveRoom(
            @Header("Authorization") String authorization,
            @Path("roomId") String roomId);

    @POST("v2/bot/group/{groupId}/leave")
    Call<Void> leaveGroup(
            @Header("Authorization") String authorization,
            @Path("groupId") String groupId);

    // ----------------------------------------------------------------
    //     Business Connect V2
    // ----------------------------------------------------------------
    @GET("v2/bot/dedisco/migration/userId")
    Call<Void> convertMid(
            @Header("Authorization") String authorization,
            @Query("mid") String mid);

    @Headers("Content-type: text/plain")
    @POST("v2/bot/dedisco/migration/userId/getbulk")
    Call<Void> convertMids(
            @Header("Authorization") String authorization,
            @Body String mids);


    // ----------------------------------------------------------------
    //     LINE web Login V2
    // ----------------------------------------------------------------

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/accessToken")
    Call<AccessToken> accessToken(
            @Field("grant_type") String grant_type,
            @Field("code") String code,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("redirect_uri") String redirect_uri);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/accessToken")
    Call<AccessToken> refreshToken(
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/accessToken")
    Call<AccessToken> issueToken(
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/verify")
    Call<Void> verify(@Field("access_token") String access_token);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/revoke")
    Call<Void> revoke(@Field("refresh_token") String refresh_token);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v2/oauth/revoke")
    Call<Void> revokeChannelAccessToken(@Field("access_token") String access_token);

    @GET("v2/profile")
    Call<Void> profile(@Header("Authorization") String accessToken);



    // ----------------------------------------------------------------
    //     LINE web Login V1
    // ----------------------------------------------------------------
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v1/oauth/accessToken")
    Call<AccessToken> accessToken_v1(
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("redirect_uri") String redirect_uri,
            @Field("code") String code);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("v1/oauth/accessToken ")
    Call<AccessToken> refreshToken_v1(
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret);

    @GET("v1/permissions")
    Call<Void> permissions_v1(@Header("Authorization") String accessToken);

    @GET("v1/profile")
    Call<Void> profile_v1(@Header("Authorization") String accessToken);

    @GET("v1/email")
    Call<Void> email_v1(@Header("Authorization") String accessToken);

    @GET("v1/phone")
    Call<Void> phone_v1(@Header("Authorization") String accessToken);

}
