/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
     * This class demonstrates how to get a valid O-auth token from
     * Azure Data Market.
     */
@Component
public class AuthenticationService
{
    public static final String AccessTokenUri = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";

    @Value("${microsoft.api.key}")
    private String apiKey;

    public String getAccessToken() {
        return HttpPost(AccessTokenUri, apiKey);
    }

    private String HttpPost(String AccessTokenUri, String apiKey){
        InputStream inSt = null;
        HttpsURLConnection webRequest = null;

        //Prepare OAuth request
        try{
            webRequest = HttpsConnection.getHttpsConnection(AccessTokenUri);
            webRequest.setDoInput(true);
            webRequest.setDoOutput(true);
            webRequest.setConnectTimeout(5000);
            webRequest.setReadTimeout(5000);
            webRequest.setRequestMethod("POST");

            byte[] bytes = new byte[0];
            webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
            webRequest.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            webRequest.connect();

            DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
            dop.write(bytes);
            dop.flush();
            dop.close();

            inSt = webRequest.getInputStream();
            InputStreamReader in = new InputStreamReader(inSt);
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }

            bufferedReader.close();
            in.close();
            inSt.close();
            webRequest.disconnect();

            // parse the access token
            String token = strBuffer.toString();

            return token;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
