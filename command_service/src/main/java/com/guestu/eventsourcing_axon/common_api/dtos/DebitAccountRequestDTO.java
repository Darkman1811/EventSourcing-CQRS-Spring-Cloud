package com.guestu.eventsourcing_axon.common_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DebitAccountRequestDTO {
    private String AccountId;
    private double amount;
    private String currency;
}
