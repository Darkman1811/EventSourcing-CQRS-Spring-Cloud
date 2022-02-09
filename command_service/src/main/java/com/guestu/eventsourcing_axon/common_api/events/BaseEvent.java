package com.guestu.eventsourcing_axon.common_api.events;

import lombok.Getter;

public abstract class BaseEvent <T>{
    @Getter private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
