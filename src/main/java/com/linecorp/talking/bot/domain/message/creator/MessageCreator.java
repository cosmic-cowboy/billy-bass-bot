/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.domain.message.creator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import com.linecorp.talking.bot.infra.line.api.request.message.AudioMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.ImageMapMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.ImageMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.LocationMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.Message;
import com.linecorp.talking.bot.infra.line.api.request.message.StickerMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.TemplateMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.TextMessage;
import com.linecorp.talking.bot.infra.line.api.request.message.VideoMessage;

@Component
public class MessageCreator {

    @Value("${linecorp.platform.cpDomain}")
    private String cpDomain;

    public List<Message> createMessage() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createLocationMessage());
        builder.add(createStickerMessage());
        builder.add(createImageMapMessage());
        builder.add(createCarousel(5));
        builder.add(createConfirm());
        return builder.build();
    }

    public List<Message> createMessage2() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.empty()));
        builder.add(createImageMessage());
        builder.add(createVideoMessage());
        builder.add(createAudioMessage());
        return builder.build();
    }

    public List<Message> createMessageWithTypes(List<String> types) {

        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();

        for (String type : types) {

            switch (type.toLowerCase()) {
                case "text":
                    builder.add(createTextMessage());
                    break;
                case "image":
                    builder.add(createImageMessage());
                    break;
                case "video":
                    builder.add(createVideoMessage());
                    break;
                case "audio":
                    builder.add(createAudioMessage());
                    break;
                case "location":
                    builder.add(createLocationMessage());
                    break;
                case "sticker":
                    builder.add(createStickerMessage());
                    break;
                case "carousel":
                    builder.add(createCarouselWithoutPostback(5));
                    break;
                case "confirm":
                    builder.add(createConfirmWithoutPostback());
                    break;
                case "imagemap":
                    builder.add(createImageMapMessage());
                    break;
                case "carousel-postback":
                    builder.add(createCarouselWithPostback(5));
                    break;
                case "carousel-message":
                    builder.add(createCarouselWithMessage(5));
                    break;
                case "confirm-postback":
                    builder.add(createConfirmWithPostback());
                    break;
                case "confirm-message":
                    builder.add(createConfirmWithMessage());
                    break;
                case "ad-image":
                    builder.add(createAdImage());
                    break;
                case "ad-overview":
                    builder.add(createAdOverview());
                    break;
                case "ad-image-carousel":
                    builder.add(createAdImageCarousel(5));
                    break;
                case "ad-overview-carousel":
                    builder.add(createAdOverviewCarousel(5));
                    break;
            }
        }

        return builder.build();
    }

    public List<Message> createMessageForIFA1() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("THERE SHOULD BE 4 MESSAGES AFTER THIS MESSAGE: Text, Image, Video, Audio")));
        builder.add(createTextMessage(Optional.empty()));
        builder.add(createImageMessage());
        builder.add(createVideoMessage());
        builder.add(createAudioMessage());
        return builder.build();
    }

    public List<Message> createMessageForIFA2() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("THERE SHOULD BE 2 MESSAGES AFTER THIS MESSAGE: Location, Sticker")));
        builder.add(createLocationMessage());
        builder.add(createStickerMessage());
        return builder.build();
    }

    public List<Message> createMessageForIFA3() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("THERE SHOULD BE 2 MESSAGES AFTER THIS MESSAGE: Carousel, Confirm")));
        builder.add(createCarouselWithoutPostback(5));
        builder.add(createConfirmWithoutPostback());
        return builder.build();
    }

    public List<Message> createMessageForIFA_New() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("THERE SHOULD BE 4 MESSAGES AFTER THIS MESSAGE: (New Ad Templates) Ad Image, Ad Overview, Ad Image Carousel, Ad Overview Carousel")));
        builder.add(createAdImage());
        builder.add(createAdOverview());
        builder.add(createAdImageCarousel(5));
        builder.add(createAdOverviewCarousel(5));
        return builder.build();
    }

    public List<Message> createMessageForIFA_ImageMap() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("IF THIS APPEARS, THE TEST HAS FAILED (ImageMap is not supported)")));
        builder.add(createImageMapMessage());
        return builder.build();
    }

    public List<Message> createMessageForIFA_CarouselWithPostback() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("IF THIS APPEARS, THE TEST HAS FAILED (Postback Action is not supported in Carousel Template)")));
        builder.add(createCarousel(5));
        return builder.build();
    }

    public List<Message> createMessageForIFA_CarouselWithMessage() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("IF THIS APPEARS, THE TEST HAS FAILED (Message Action is not supported in Carousel Template)")));
        builder.add(createCarousel(5));
        return builder.build();
    }

    public List<Message> createMessageForIFA_ConfirmWithPostback() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("IF THIS APPEARS, THE TEST HAS FAILED (Postback Action is not supported in Carousel Template)")));
        builder.add(createConfirm());
        return builder.build();
    }

    public List<Message> createMessageForIFA_ConfirmWithMessage() {
        ImmutableList.Builder<Message> builder = new ImmutableList.Builder<Message>();
        builder.add(createTextMessage(Optional.of("IF THIS APPEARS, THE TEST HAS FAILED (Message Action is not supported in Carousel Template)")));
        builder.add(createConfirm());
        return builder.build();
    }

    public Message createTextMessage() {
        return createTextMessage(Optional.empty());
    }

    public Message createTextMessage(Optional<String> optText) {
        String message;
        if (!optText.isPresent()) {
            char[] charArray = Character.toChars(0x1000B2);
            String emoticon = new String(charArray);
            message = "Hello, World " + emoticon;
        } else {
            message = optText.get();
        }
        return new TextMessage(message);
    }

    public Message createImageMessage() {
        return new ImageMessage(
                cpDomain + "img/image/1057829_1280.jpg",
                cpDomain + "img/image/1057829_240.jpg"
        );
    }

    public Message createAudioMessage() {
        return new AudioMessage(
                cpDomain + "audio/parots_talking.mp3",
                "2000"
        );
    }

    public Message createVideoMessage() {
        return new VideoMessage(
                cpDomain + "movie/movie.mp4",
                cpDomain + "movie/movie.jpg"
        );
    }

    public Message createLocationMessage() {
        return new LocationMessage(
                "hikarie",
                "〒150-8510 東京都渋谷区渋谷２丁目２１−１",
                new BigDecimal(35.6591041),
                new BigDecimal(139.70374200000003)
        );
    }

    public Message createStickerMessage() {
        return new StickerMessage("2", "141");
    }


    public ImageMapMessage createImageMapMessage() {

        ImmutableList.Builder<ImageMapMessage.Action> builder = new ImmutableList.Builder<ImageMapMessage.Action>();

        // Rectangular area where tap event is received.
        // ================================================================
        // ||    lineq    || line play  || line fortune ||
        // ||  Message1  || line news  || line live    ||
        // || line camera || line manga || line here    ||
        // ================================================================
        builder.add(new ImageMapMessage.Action("uri", null, "http://lineq.jp/", new ImageMapMessage.Area(0, 0, 350, 350)));
        builder.add(new ImageMapMessage.Action("message", "Message1", null, new ImageMapMessage.Area(0, 350, 350, 350)));
        builder.add(new ImageMapMessage.Action("message", "Message2", null, new ImageMapMessage.Area(0, 700, 340, 350)));
        builder.add(new ImageMapMessage.Action("uri", null, "http://lp.play.line.me/ja.html", new ImageMapMessage.Area(350, 0, 350, 350)));
        builder.add(new ImageMapMessage.Action("uri", null, "http://news.line.me/about/", new ImageMapMessage.Area(350, 350, 350, 350)));
        builder.add(new ImageMapMessage.Action("message", "Message3", null, new ImageMapMessage.Area(350, 700, 340, 350)));
        builder.add(new ImageMapMessage.Action("uri", null, "https://fortune.line.me/about/", new ImageMapMessage.Area(700, 0, 350, 340)));
        builder.add(new ImageMapMessage.Action("uri", null, "https://live.line.me/landing", new ImageMapMessage.Area(700, 350, 350, 340)));
        builder.add(new ImageMapMessage.Action("message", "Message4", null, new ImageMapMessage.Area(700, 700, 340, 340)));

        return new ImageMapMessage(
                cpDomain + "img/imagemap",
                "Please visit site",
                new ImageMapMessage.BaseSize(1040, 1040),
                builder.build()
        );

    }


    public TemplateMessage createConfirm() {
        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "postback",
                "はい",
                "postback.data",
                "postback.text",
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "message",
                "いいえ",
                null,
                "sendMessage.text",
                null));

        TemplateMessage.Template template = new TemplateMessage.ConfirmTemplate(
                "alt",
                "ほんとうにいいんですか？",
                actionBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);

    }

    public TemplateMessage createConfirmWithPostback() {
        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "postback",
                "はい",
                "postback.data",
                "postback.text",
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        TemplateMessage.Template template = new TemplateMessage.ConfirmTemplate(
                "alt",
                "ほんとうにいいんですか？",
                actionBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);

    }

    public TemplateMessage createConfirmWithMessage() {
        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "message",
                "いいえ",
                null,
                "sendMessage.text",
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        TemplateMessage.Template template = new TemplateMessage.ConfirmTemplate(
                "alt",
                "ほんとうにいいんですか？",
                actionBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);

    }

    public TemplateMessage createConfirmWithoutPostback() {
        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        TemplateMessage.Template template = new TemplateMessage.ConfirmTemplate(
                "alt",
                "ほんとうにいいんですか？",
                actionBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);

    }

    public TemplateMessage createCarousel(int carouselNum) {

        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "postback",
                "ポストバック",
                "postback.data",
                null,
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "message",
                "メッセージ",
                null,
                "sendMessage.text",
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        ImmutableList.Builder<TemplateMessage.Column> columnBuilder = new ImmutableList.Builder<TemplateMessage.Column>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.Column(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "【かわいすぎ…】「カービィカフェ」が期間限定オープン！",
                    "8月5日から大阪駅に、8月中に東京スカイツリータウン・ソラマチにオープン予定。",
                    actionBuilder.build()));
        }

        TemplateMessage.Template template = new TemplateMessage.CarouselTemplate("alt", columnBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createCarouselWithPostback(int carouselNum) {

        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "message",
                "メッセージ",
                null,
                "sendMessage.text",
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        ImmutableList.Builder<TemplateMessage.Column> columnBuilder = new ImmutableList.Builder<TemplateMessage.Column>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.Column(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "【かわいすぎ…】「カービィカフェ」が期間限定オープン！",
                    "8月5日から大阪駅に、8月中に東京スカイツリータウン・ソラマチにオープン予定。",
                    actionBuilder.build()));
        }

        TemplateMessage.Template template = new TemplateMessage.CarouselTemplate("alt", columnBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createCarouselWithMessage(int carouselNum) {

        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "postback",
                "ポストバック",
                "postback.data",
                null,
                null));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        ImmutableList.Builder<TemplateMessage.Column> columnBuilder = new ImmutableList.Builder<TemplateMessage.Column>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.Column(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "【かわいすぎ…】「カービィカフェ」が期間限定オープン！",
                    "8月5日から大阪駅に、8月中に東京スカイツリータウン・ソラマチにオープン予定。",
                    actionBuilder.build()));
        }

        TemplateMessage.Template template = new TemplateMessage.CarouselTemplate("alt", columnBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createCarouselWithoutPostback(int carouselNum) {

        ImmutableList.Builder<TemplateMessage.Action> actionBuilder = new ImmutableList.Builder<TemplateMessage.Action>();
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));
        actionBuilder.add(new TemplateMessage.Action(
                "uri",
                "電話する",
                null,
                null,
                "tel:09012345678"));

        ImmutableList.Builder<TemplateMessage.Column> columnBuilder = new ImmutableList.Builder<TemplateMessage.Column>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.Column(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "【かわいすぎ…】「カービィカフェ」が期間限定オープン！",
                    "8月5日から大阪駅に、8月中に東京スカイツリータウン・ソラマチにオープン予定。",
                    actionBuilder.build()));
        }

        TemplateMessage.Template template = new TemplateMessage.CarouselTemplate("alt", columnBuilder.build());
        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createAdImage() {

        TemplateMessage.Action action = new TemplateMessage.Action(
                "uri",
                "uri action",
                null,
                null,
                "http://linecorp.com"
        );

        TemplateMessage.Advertiser advertiser = new TemplateMessage.Advertiser("LINE Corp");

        TemplateMessage.Template template = new TemplateMessage.AdImageTemplate(
                "alt",
                cpDomain + "img/template/pexels-photo-30469.jpg",
                "time=1479890402625&tmId=ub3c6b041baaaf9aec9c73f28dcdba057&serviceName=ADOA",
                "statsUrl=https%3A%2F%2Fadp.line-apps-beta.com%3A20443%2Fevent%2Ftestpost.php",
                "bucketId=A&siteKey=zdUkJXcS2XE&inventoryKey=1VXc2HBijMg&languageCreativeKey=hm-67396&carouselSlotId=1234&sdata=2wAzQuzTYxj_j_ZWuz5fJKg%3BLanguageMaterial_1478225448731t055893a8%2C%2C0gFIns_-5Bg",
                action,
                advertiser
        );

        return new TemplateMessage("テンプレート直下のalt", template);
    }


    public TemplateMessage createAdOverview() {

        TemplateMessage.Action action = new TemplateMessage.Action(
                "uri",
                "uri action",
                null,
                null,
                "http://linecorp.com"
        );

        TemplateMessage.Advertiser advertiser = new TemplateMessage.Advertiser("LINE Corp");

        TemplateMessage.Template template = new TemplateMessage.AdOverviewTemplate(
                "alt",
                cpDomain + "img/template/pexels-photo-30469.jpg",
                "time=1479890402625&tmId=ub3c6b041baaaf9aec9c73f28dcdba057&serviceName=ADOA",
                "statsUrl=https%3A%2F%2Fadp.line-apps-beta.com%3A20443%2Fevent%2Ftestpost.php",
                "bucketId=A&siteKey=zdUkJXcS2XE&inventoryKey=1VXc2HBijMg&languageCreativeKey=hm-67396&carouselSlotId=1234&sdata=2wAzQuzTYxj_j_ZWuz5fJKg%3BLanguageMaterial_1478225448731t055893a8%2C%2C0gFIns_-5Bg",
                action,
                advertiser,
                "Ad Title",
                "Ad Text"
        );

        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createAdImageCarousel(int carouselNum) {

        TemplateMessage.Action action = new TemplateMessage.Action(
                "uri",
                "uri action",
                null,
                null,
                "http://linecorp.com"
        );

        TemplateMessage.Advertiser advertiser = new TemplateMessage.Advertiser("LINE Corp");

        ImmutableList.Builder<TemplateMessage.AdColumn> columnBuilder = new ImmutableList.Builder<>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.AdColumn(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "bucketId=A&siteKey=zdUkJXcS2XE&inventoryKey=1VXc2HBijMg&languageCreativeKey=hm-67396&carouselSlotId=1234&sdata=2wAzQuzTYxj_j_ZWuz5fJKg%3BLanguageMaterial_1478225448731t055893a8%2C%2C0gFIns_-5Bg",
                    "null",
                    "null",
                    action));
        }

        TemplateMessage.Template template = new TemplateMessage.AdCarouselTemplate(
                "alt",
                "statsUrl=https%3A%2F%2Fadp.line-apps-beta.com%3A20443%2Fevent%2Ftestpost.php",
                "time=1479890402625&tmId=ub3c6b041baaaf9aec9c73f28dcdba057&serviceName=ADOA",
                advertiser,
                columnBuilder.build()
        );

        return new TemplateMessage("テンプレート直下のalt", template);
    }

    public TemplateMessage createAdOverviewCarousel(int carouselNum) {

        TemplateMessage.Action action = new TemplateMessage.Action(
                "uri",
                "ad label",
                null,
                "uri action",
                "http://linecorp.com"
        );

        TemplateMessage.Advertiser advertiser = new TemplateMessage.Advertiser("LINE Corp");

        ImmutableList.Builder<TemplateMessage.AdColumn> columnBuilder = new ImmutableList.Builder<>();

        for (int i = 0; i < carouselNum; i++) {
            columnBuilder.add(new TemplateMessage.AdColumn(
                    cpDomain + "img/template/pexels-photo-30469.jpg",
                    "ad title",
                    "ad text",
                    "bucketId=A&siteKey=zdUkJXcS2XE&inventoryKey=1VXc2HBijMg&languageCreativeKey=hm-67396&carouselSlotId=1234&sdata=2wAzQuzTYxj_j_ZWuz5fJKg%3BLanguageMaterial_1478225448731t055893a8%2C%2C0gFIns_-5Bg",
                    action));
        }

        TemplateMessage.Template template = new TemplateMessage.AdCarouselTemplate(
                "alt",
                "statsUrl=https%3A%2F%2Fadp.line-apps-beta.com%3A20443%2Fevent%2Ftestpost.php",
                "time=1479890402625&tmId=ub3c6b041baaaf9aec9c73f28dcdba057&serviceName=ADOA",
                advertiser,
                columnBuilder.build()
        );

        return new TemplateMessage("テンプレート直下のalt", template);
    }
}
