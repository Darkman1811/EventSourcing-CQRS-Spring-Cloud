package com.guestu.query_service_cqrs.common_api.events;

import lombok.Getter;

public abstract class BaseEvent <T>{
    @Getter
    private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
