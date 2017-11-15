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
package com.linecorp.talking.bot.application.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.talking.bot.infra.line.api.LineAPIService;


/**
 * <p>Receiving Messages/Operations Controller</p>
 * <p>
 * This controller receives the requests sent to your Business Connect Server from the LINE Platform.<br/>
 */
@RestController
public class ReceiveController {

    private static final String REQUEST_HEADER_X_LINE_SIGNATURE = "X-LINE-Signature";

    @Autowired
    private LineAPIService lineAPIService;


    /**
     * <p>This method receives requests sent to your Business Connect Server from the LINE Platform.</p>
     */

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public void channelReceive(@RequestBody String requestBody, HttpServletRequest request) {

        // Signature Validation
        final String channelSignatureComputedByTheLinePlatform = request.getHeader(REQUEST_HEADER_X_LINE_SIGNATURE);
        lineAPIService.validateSignature(channelSignatureComputedByTheLinePlatform, requestBody);

        // transform request into Observable
        lineAPIService.receiveData(requestBody);

    }

}
