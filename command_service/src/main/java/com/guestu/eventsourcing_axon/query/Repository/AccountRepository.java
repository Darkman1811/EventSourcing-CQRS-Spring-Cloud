package com.guestu.eventsourcing_axon.query.Repository;

import com.guestu.eventsourcing_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
