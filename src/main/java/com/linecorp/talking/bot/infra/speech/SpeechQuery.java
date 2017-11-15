/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.speech;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpeechQuery {

    @Autowired
    private StringRedisTemplate template;

    public String filename(final String timestamp) {
        return template.<String, String>opsForHash().get(timestamp, SpeechRepository.FILENAME);
    }

    public Set<String> keys() {
        return template.keys("*");
    }

}
