package com.example.demo.async;

import java.util.List;

public interface EventHandler {
    public void handle(EventModel model);
    public List<EventType> getHandleType();
}
