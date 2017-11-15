/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.speech;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

@Component
public class SpeechRepository {

    static final String FILENAME = "FILENAME";

    @Autowired
    private StringRedisTemplate template;

    public void create(
            final String timestamp, final String filename) {
        final Map<String, String> map = new ImmutableMap.Builder<String, String>()
            .put(FILENAME, filename)
            .build();
        template.<String, String>opsForHash().putAll(timestamp, map);
    }

    public void delete(final String filename){
        template.delete(filename);
    }
}
