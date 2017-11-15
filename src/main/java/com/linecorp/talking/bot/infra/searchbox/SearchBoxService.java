/*
 * Copyright (c) 2017 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.searchbox;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Get;
import io.searchbox.core.Search;

/**
 *
 */
@Component
public class SearchBoxService {

    public static final String INDEX_JP = "line_bot_faq_jp";
    public static final String INDEX_EN = "line_bot_faq_en";
    public static final String TYPE = "qa";

    public List<Article> search(List<String> keyPhrases, final String inputLanguage){
// use bool query
//        {
//            "query": {
//              "bool" : {
//                "must" : [
//        "term" : { "tag" : "tech" }
//                  { "range" : { "age" : { "gte": 25 } } },
//                  { "range" : { "salary" : { "gte": 500000  } } }
//                ]
//              }
//            }
//          }
//        String word = keyPhrases.stream().collect(
//                Collectors.joining(
//                        "\"}},{ \"term\" : { \"question\" : \"",
//                        "{ \"term\" : { \"question\" : \"",
//                        "\"}}"));
//        String query = "{ \"query\": { \"bool\" : { \"should\" : [" + word + "] } } }";
//        System.out.println("word : " + query);

        String query = "{ \"query\": { \"match\" : { \"question\" : \"" + String.join(" ", keyPhrases) + "\" } } }";
        System.out.println("query : " + query);
        return query(query, inputLanguage);
    }

    public Article documentId(final String documentId, final String inputLanguage){
        final String language = getLanguage(inputLanguage);

        JestClient client = getClient();
        Get get = new Get.Builder(language, documentId).type(TYPE).build();
        JestResult result;
        try {
            result = client.execute(get);
            return result.getSourceAsObject(Article.class);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }


    private List<Article> query(String query, final String inputLanguage) {
        final String language = getLanguage(inputLanguage);

        JestClient client = getClient();

        Search search = new Search.Builder(query)
                .addIndex(language)
                .addType(TYPE)
                .build();
        try {
            JestResult result = client.execute(search);
            System.out.println(result.getJsonString());
            List<Article> list = result.getSourceAsObjectList(Article.class);
            ImmutableList.Builder<Article> returnListBuilder = new ImmutableList.Builder<>();
            int counter = 0;
            for(Article article : list) {
                if (counter > 4) {
                    break;
                } else {
                    returnListBuilder.add(article);
                    counter++;
                }
            }

            return returnListBuilder.build();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }


    private JestClient getClient() {
        String connectionUrl = System.getenv("SEARCHBOX_URL");

        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .build());
        JestClient client = factory.getObject();
        return client;
    }


    private String getLanguage(final String inputLanguage) {
        final String language;
        if (inputLanguage.equals("ja")) {
            language = INDEX_JP;
        } else if (inputLanguage.equals("en") ) {
            language = INDEX_EN;
        } else {
            language = INDEX_JP;
        }
        return language;
    }

}
