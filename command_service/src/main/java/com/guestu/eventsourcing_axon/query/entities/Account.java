package com.guestu.eventsourcing_axon.query.entities;

import com.guestu.eventsourcing_axon.common_api.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Operation> operations;
}
