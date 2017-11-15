/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.microsoft.api.speech;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsConnection {

    public static HttpsURLConnection getHttpsConnection (String connectingUrl) throws Exception {

        URL url = new URL(connectingUrl);
        HttpsURLConnection webRequest = (HttpsURLConnection) url.openConnection();
        return webRequest;
    }
}
