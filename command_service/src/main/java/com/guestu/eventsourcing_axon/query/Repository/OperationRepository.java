package com.guestu.eventsourcing_axon.query.Repository;

import com.guestu.eventsourcing_axon.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
