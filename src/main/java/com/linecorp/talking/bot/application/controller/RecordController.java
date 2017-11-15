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

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.talking.bot.domain.message.RecordService;


@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/audio", method = RequestMethod.POST)
    public void uploadAudio(InputStream input) throws ProtocolException, IOException, Exception {
        recordService.sendTextFromVoice(input);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String token() {
        return recordService.getToken();
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST)
    public void post(@RequestBody String requestBody) throws ProtocolException, IOException, Exception {
        recordService.sendMessageToGroup(requestBody);
    }

}
