/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.db;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public RedisConnectionFactory jedisConnFactory() {

        try {
            String redistogoUrl = System.getenv("REDIS_URL");
            URI redistogoUri = new URI(redistogoUrl);

            JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();

            jedisConnFactory.setUsePool(true);
            jedisConnFactory.setHostName(redistogoUri.getHost());
            jedisConnFactory.setPort(redistogoUri.getPort());
            jedisConnFactory.setTimeout(300);
            jedisConnFactory.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);

            return jedisConnFactory;

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
