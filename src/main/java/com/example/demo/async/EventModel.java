package com.example.demo.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private  EventType eventType;
    private int actionId; //该事件的发起者
    private String content;
    private int entityType;
    private int entityId;
    private int ownId; //该事件的对象

    public int getOwnId() {
        return ownId;
    }

    public EventModel setOwnId(int ownId) {
        this.ownId = ownId;
        return this;
    }

    private Map<String,String> map=new HashMap<>();

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public EventModel setContent(String content) {
        this.content = content;
        return this;
    }

    public int getActionId() {
        return actionId;
    }

    public EventModel setActionId(int actionId) {
        this.actionId = actionId;
        return this;
    }
    public EventModel setMap(String key,String value){
        map.put(key,value);
        return this;
    }
    public String getMap(String key){
        return map.get(key);
    }
}
