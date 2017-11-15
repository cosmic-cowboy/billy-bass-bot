package com.linecorp.talking.bot.application.controller.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.linecorp.talking.bot.domain.message.MessageService;
import com.linecorp.talking.bot.infra.line.api.receive.ReceiveEvent;
import com.linecorp.talking.bot.infra.line.api.receive.response.Result;
import com.linecorp.talking.bot.infra.util.SubscribeUtils;

@Component
public class EventHandler extends TextWebSocketHandler {
	
	private Map<String, WebSocketSession> sessionPool = new ConcurrentHashMap<>();
	
    @Autowired
    private ReceiveEvent receiveEvent;
    
    @Autowired
    private MessageService messageService;
    
    @PostConstruct
    public void init() {
        SubscribeUtils.subscribe(receiveEvent.received(), result -> notifyEvent(result));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    		sessionPool.put(session.getId(), session);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    		sessionPool.remove(session.getId());
    }
    
    private void notifyEvent(Result result) {
        switch(result.type){
            case Beacon :
                break;
            case Follow :
                break;
            case Unfollow :
                break;
            case Join :
                break;
            case Leave :
                break;
            case Message :
	            	switch (result.message.type) {
	                case Text:
	                		sendMessage(result);
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
	            break;
            case Postback :
                break;
            default:
        }
    }
    
    private void sendMessage(Result result) {
    		String text = messageService.createText(result);
    		if(text == null || text.equals("")) {
    			return;
    		}
    		sessionPool.forEach((sessionId, session) -> {
    			try {
				session.sendMessage(new TextMessage(text));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		});
    }
}
