package com.guestu.eventsourcing_axon.common_api.events;

import com.guestu.eventsourcing_axon.common_api.enums.AccountStatus;
import lombok.Getter;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter private AccountStatus accountStatus;
    public AccountActivatedEvent(String id, AccountStatus accountStatus) {
        super(id);
        this.accountStatus = accountStatus;
    }
}
