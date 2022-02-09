package com.guestu.eventsourcing_axon.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.guestu.eventsourcing_axon.query.utils.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
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
