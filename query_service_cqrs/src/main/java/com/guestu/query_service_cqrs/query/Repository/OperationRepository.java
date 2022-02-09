package com.guestu.query_service_cqrs.query.Repository;

import com.guestu.query_service_cqrs.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
