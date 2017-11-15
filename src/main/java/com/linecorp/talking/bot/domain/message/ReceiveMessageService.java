/*
 * Copyright (c) 2016 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linecorp.talking.bot.domain.message;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linecorp.talking.bot.infra.line.api.receive.ReceiveEvent;
import com.linecorp.talking.bot.infra.line.api.receive.response.Result;
import com.linecorp.talking.bot.infra.util.SubscribeUtils;

/**
 *
 */
@Component
public class ReceiveMessageService {

    @Autowired
    private ReceiveEvent receiveEvent;

    @Autowired
    private MessageService messageService;

    /**
     * to subscribe receive
     */
    @PostConstruct
    public void init() {
        SubscribeUtils.subscribe(receiveEvent.received(), result -> replyToUser(result));
    }

    private void replyToUser(Result result) {

        switch(result.type){
            case Beacon :
                messageService.dealWithBeacon(result);
                break;
            case Follow :
                messageService.dealWithFollow(result);
                break;
            case Unfollow :
                messageService.dealWithUnfollow(result);
                break;
            case Join :
                messageService.dealWithJoin(result);
                break;
            case Leave :
                messageService.dealWithLeave(result);
                break;
            case Message :
                messageService.dealWithMessage(result);
                break;
            case Postback :
                messageService.dealWithPostback(result);
                break;
            default:
        }
    }

}
