/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.domain.message;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;

import com.linecorp.talking.bot.domain.message.creator.MessageCreator;
import com.linecorp.talking.bot.infra.line.api.LineAPIService;
import com.linecorp.talking.bot.infra.line.api.receive.response.Profile;
import com.linecorp.talking.bot.infra.line.api.receive.response.Result;
import com.linecorp.talking.bot.infra.line.api.receive.response.Source;
import com.linecorp.talking.bot.infra.line.api.request.Push;
import com.linecorp.talking.bot.infra.line.api.request.message.Message;
import com.linecorp.talking.bot.infra.microsoft.api.speech.TTSService;
import com.linecorp.talking.bot.infra.microsoft.api.speech.request.VoiceFont;
import com.linecorp.talking.bot.infra.searchbox.Article;
import com.linecorp.talking.bot.infra.searchbox.SearchBoxService;

@Component
public class MessageService {

    private static final Logger logger = Logger.getLogger(MessageService.class);

    private static final Pattern p = Pattern.compile("^[a-z]");

    @Value("${linecorp.platform.cpDomain}")
    private String cpDomain;

    @Autowired
    private LineAPIService lineAPIService;

    @Autowired
    private MessageCreator messageCreator;

    @Autowired
    private TTSService ttsService;

    @Autowired
    private SearchBoxService searchBoxService;

    // ----------------------------------------------------------------
    //     answer message for receive message (Operation Event Type)
    // ----------------------------------------------------------------
    void dealWithBeacon(Result result) {
        if (logger.isDebugEnabled()) {
            logger.debug("result type is beacon");
            logger.debug("beacon hwid is beacon : " + result.beacon.hwid);
            logger.debug("beacon type is beacon : " + result.beacon.type);
        }
    }

    void dealWithFollow(Result result) {
    }

    void dealWithUnfollow(Result result) {
        if (logger.isDebugEnabled()) {
            logger.debug("result type is Unfollow");
        }
    }

    void dealWithJoin(Result result) {
    }

    void dealWithLeave(Result result) {
        if (logger.isDebugEnabled()) {
            logger.debug("result type is Leave");
        }
    }


    // ----------------------------------------------------------------
    //     answer message for receive message (Message Event Type)
    // ----------------------------------------------------------------
    void dealWithPostback(Result result) {

        Map<String, String> map = Splitter.on(",")
                .trimResults()
                .withKeyValueSeparator(":")
                .split(result.postback.data);
        String documentId = map.get("documentId");
        String language = map.get("language");
        final Article article = searchBoxService.documentId(documentId, language);
        push(result, createAnswerMessage(article, language));
    }

    void dealWithMessage(Result result) {
        switch (result.message.type) {
            case Text:
                createTextForVoice(result);
                break;
            case Sticker:
                break;
            case Image:
                break;
            case Video:
                break;
            case Audio:
                break;
            default:
        }
    }

    void dealWithMessageForWebsocket(Result result) {
        switch (result.message.type) {
            case Text:
            		createText(result);
                break;
            case Sticker:
                break;
            case Image:
                break;
            case Video:
                break;
            case Audio:
                break;
            default:
        }
    }

    // ----------------------------------------------------------------
    //     extract text matching
    // ----------------------------------------------------------------
    public String createText(Result result) {
        final String text   = result.message.text;
        final String userId = result.source.userId;
        String voiceText = "";
        if (text == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("text is null");
            }
        } else {
            final String switchText = text.toLowerCase();
            if (logger.isDebugEnabled()) {
                logger.debug("text is : " + switchText);
            }

            VoiceFont voiceFont = selectVoiceFont(switchText, userId);
            Profile profile = lineAPIService.profile(userId);
            
            if (voiceFont.locale.indexOf("JP") > 0 ) {
                voiceText = "あああああ。" + profile.displayName + "さんからのメッセージです。" + switchText;
            } else {
                voiceText = "hey, hey" + "Here is a message from " + profile.displayName + switchText;
            }
        }
        return voiceText;
    }

    private void createTextForVoice(Result result) {
        final String text   = result.message.text;
        final String userId = result.source.userId;

        if (text == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("text is null");
            }
        } else {
            final String switchText = text.toLowerCase();
            if (logger.isDebugEnabled()) {
                logger.debug("text is : " + switchText);
            }

            VoiceFont voiceFont = selectVoiceFont(switchText, userId);

            Profile profile = lineAPIService.profile(userId);
            String voiceText;
            if (voiceFont.locale.indexOf("JP") > 0 ) {
                voiceText = "あああああ。" + profile.displayName + "さんからのメッセージです。" + switchText;
            } else {
                voiceText = "hey, hey" + "Here is a message from " + profile.displayName + switchText;
            }

            try {
                ttsService.synthesize(voiceText, voiceFont, result.timestamp);
            } catch (Exception e) {
                logger.warn("Unexpected exception:", e);
            }

        }
    }

    private VoiceFont selectVoiceFont(final String switchText, final String userId) {
        Matcher m = p.matcher(switchText);
        boolean isMale = true;
        // Uc30d8a8b6be3500ccf8c633f2852313d
        //
        // userId matching
        if(userId != null && userId.equals("U6cc67601e81219ef8ac286a6a6a384cb")){
            isMale = false;
        }

        VoiceFont voiceFont;
        if (m.find()) {
            if (isMale) {
                voiceFont = VoiceFont.find("Shaun");
            } else {
                voiceFont = VoiceFont.find("Linda");
            }
        } else {
            if (isMale) {
                voiceFont = VoiceFont.find("Ichiro");
            } else {
                voiceFont = VoiceFont.find("ayumi");
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("this voice is : " + voiceFont.serviceName);
        }
        return voiceFont;
    }

//    private Message createQuestionOptionMessage(final List<Article> list, final String inputLanguage) {
//        final String seeAnswerToThisQuestion;
//        final String faqRelatedToQuestion;
//        if (inputLanguage.equals("en")) {
//            seeAnswerToThisQuestion = "See answer";
//            faqRelatedToQuestion = "This is a FAQ related to questions";
//        } else {
//            seeAnswerToThisQuestion = "この質問の回答を見る";
//            faqRelatedToQuestion = "質問に関連するFAQです";
//        }
//
//        final ImmutableList.Builder<TemplateMessage.Column> columnBuilder = new ImmutableList.Builder<>();
//        for (Article article : list) {
//            final String postbackData = "documentId:"+ article.getDocumentId() + ", language:" + inputLanguage;
//            ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<>();
//            actionBuilder.add(new TemplateMessage.Action("postback", seeAnswerToThisQuestion, postbackData, null, null));
//            columnBuilder.add(new TemplateMessage.Column(null, null, article.getQuestion(), actionBuilder.build()));
//        }
//        TemplateMessage.Template template = new TemplateMessage.CarouselTemplate("alt", columnBuilder.build());
//        return new TemplateMessage(faqRelatedToQuestion, template);
//    }

    private Message createAnswerMessage(final Article article, final String inputLanguage) {
        final String noAnswer;
        if (inputLanguage.equals("en")) {
            noAnswer = "we can not find out corresponding answer";
        } else {
            noAnswer = "対応する回答が見つかりませんでした";
        }

        if (article != null) {
            return messageCreator.createTextMessage(Optional.of(article.getAnswer()));
        } else {
            return messageCreator.createTextMessage(Optional.of(noAnswer));
        }
    }

    // push
    private void push(final Result result, final Message message) {
        push(result, sendTo(result.source), message);
    }

    private void push(final Result result, final String sendTo, final Message message) {
        lineAPIService.push(new Push(sendTo, Collections.singletonList(message)));
    }

    private String sendTo(Source source) {
        final String sendTo;
        switch (source.type) {
            case Room:
                sendTo = source.roomId;
                break;
            case Group:
                sendTo = source.groupId;
                break;
            case User:
                sendTo = source.userId;
                break;
            default:
                sendTo = null;
                break;
        }
        return sendTo;
    }
}
