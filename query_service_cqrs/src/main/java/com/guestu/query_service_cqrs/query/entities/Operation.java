package com.guestu.query_service_cqrs.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guestu.query_service_cqrs.query.utils.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createdAt;
    private double amout;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
