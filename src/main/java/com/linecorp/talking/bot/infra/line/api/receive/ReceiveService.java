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
package com.linecorp.talking.bot.infra.line.api.receive;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.linecorp.talking.bot.infra.line.api.common.EventType;
import com.linecorp.talking.bot.infra.line.api.common.MessageType;
import com.linecorp.talking.bot.infra.line.api.common.SourceType;
import com.linecorp.talking.bot.infra.line.api.receive.response.Result;
import com.linecorp.talking.bot.infra.line.api.receive.response.Results;

@Component
public class ReceiveService {

    @Autowired
    ReceiveEvent receiveEvent;

    private static final Logger logger = Logger.getLogger(ReceiveService.class);

    public void receive(final String requestBody) {

        logger.debug("result : " + requestBody);
        // convert a JSON string into an equivalent Java object
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EventType.class, new EventType.Deserializer())
                .registerTypeAdapter(MessageType.class, new MessageType.Deserializer())
                .registerTypeAdapter(SourceType.class, new SourceType.Deserializer())
                .create();
        Results results = gson.fromJson(requestBody, Results.class);

        // Provides the Observer with a new receive item to observe
        if (logger.isDebugEnabled()) {
            logger.debug("results:" + results);
            for(Result result : results.events) {
                logger.debug("result : " + result);
                receiveEvent.received.onNext(result);
            }
        }
    }

}
