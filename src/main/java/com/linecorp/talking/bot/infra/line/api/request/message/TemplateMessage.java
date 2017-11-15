/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.infra.line.api.request.message;

import java.util.List;

import com.linecorp.talking.bot.infra.line.api.common.MessageType;

public class TemplateMessage extends Message {

    public final String altText;
    public final Template template;

    public TemplateMessage(String altText, Template template) {
        super(MessageType.Template);
        this.altText = altText;
        this.template = template;
    }

    // ----------------------------------------------------------------
    //     Internal class
    // ----------------------------------------------------------------

    public static abstract class Template {
        public final String type;
        public final String altText;

        public Template(String type, String altText) {
            this.type = type;
            this.altText = altText;
        }
    }

    public static class CarouselTemplate extends Template {
        public final List<Column> columns;

        public CarouselTemplate(String altText, List<Column> columns) {
            super("carousel", altText);
            this.columns = columns;
        }
    }

    public static class ConfirmTemplate extends Template {
        public final String text;
        public final List<Action> actions;

        public ConfirmTemplate(String altText, String text, List<Action> actions) {
            super("confirm", altText);
            this.text = text;
            this.actions = actions;
        }
    }

    public static class AdImageTemplate extends Template{

        public final String productImageUrl;
        public final String adInventory;
        public final String adConfig;
        public final String adItem;
        public final Action action;
        public final Advertiser advertiser;

        public AdImageTemplate(String altText, String productImageUrl, String adInventory, String adConfig, String adItem, Action action, Advertiser advertiser){
            super("ad_product_image", altText);
            this.productImageUrl = productImageUrl;
            this.adInventory = adInventory;
            this.adConfig = adConfig;
            this.adItem = adItem;
            this.action = action;
            this.advertiser = advertiser;
        }
    }

    public static class AdOverviewTemplate extends Template{

        public final String productImageUrl;
        public final String adInventory;
        public final String adConfig;
        public final String adItem;
        public final Action action;
        public final Advertiser advertiser;
        public final String title;
        public final String text;

        public AdOverviewTemplate(String altText, String productImageUrl, String adInventory, String adConfig, String adItem, Action action, Advertiser advertiser, String title, String text) {
            super("ad_product_overview", altText);
            this.productImageUrl = productImageUrl;
            this.adInventory = adInventory;
            this.adConfig = adConfig;
            this.adItem = adItem;
            this.action = action;
            this.advertiser = advertiser;
            this.title = title;
            this.text = text;
        }
    }

    public static class AdCarouselTemplate extends Template {

        public final List<AdColumn> columns;
        public final String adConfig;
        public final String adInventory;
        public final Advertiser advertiser;

        public AdCarouselTemplate(String altText, String adConfig, String adInventory, Advertiser advertiser, List<AdColumn> columns) {
            super("ad_product_image_carousel", altText);
            this.adConfig = adConfig;
            this.adInventory = adInventory;
            this.advertiser = advertiser;
            this.columns = columns;
        }
    }

    public static class Column {
        public final String thumbnailImageUrl;
        public final String title;
        public final String text;
        public final List<Action> actions;

        public Column(String thumbnailImageUrl, String title, String text, List<Action> actions) {
            this.thumbnailImageUrl = thumbnailImageUrl;
            this.title = title;
            this.text = text;
            this.actions = actions;
        }
    }

    public static class AdColumn {
        public final String productImageUrl;
        public final Action action;
        public final String adItem;
        public final String title;
        public final String text;


        public AdColumn(String productImageUrl, String title, String text, String adItem, Action action) {
            this.productImageUrl = productImageUrl;
            this.adItem = adItem;
            this.action = action;
            this.title = title;
            this.text = text;
        }
    }

    public static class Action {

        public final String type;
        public final String label;
        public final String data;
        public final String text;
        public final String uri;

        public Action(String type, String label, String data, String text, String uri) {
            this.type = type;
            this.label = label;
            this.data = data;
            this.text = text;
            this.uri = uri;
        }
    }

    public static class Advertiser {
        public final String name;

        public Advertiser(String name) {
            this.name = name;
        }
    }
}
