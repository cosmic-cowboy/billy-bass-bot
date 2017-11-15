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

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linecorp.talking.bot.infra.microsoft.api.speech.TTSService;


@RestController
@RequestMapping("/voice")
public class VoiceController {

    @Autowired
    private TTSService ttsService;

    @RequestMapping(value = "/retrive")
    public String retrive() {
        Set<String> files = ttsService.voices();
        if (files.isEmpty()) {
            return "";
        } else {
            return String.join(",", files);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam Map<String, String> queryParameters) {

        String filename = queryParameters.get("filename");
        if (filename == null) {
            return;
        }
        ttsService.delete(filename);
    }

}
