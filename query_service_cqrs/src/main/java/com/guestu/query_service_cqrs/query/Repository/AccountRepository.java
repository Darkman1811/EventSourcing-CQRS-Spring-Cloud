package com.guestu.query_service_cqrs.query.Repository;

import com.guestu.query_service_cqrs.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
