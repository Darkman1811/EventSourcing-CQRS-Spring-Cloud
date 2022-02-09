package com.guestu.query_service_cqrs.query.controllers;

import com.guestu.query_service_cqrs.query.entities.Account;
import com.guestu.query_service_cqrs.query.queries.GetAllAccountsQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query/accounts")
@AllArgsConstructor
@Slf4j
public class QueryController {
    private QueryGateway queryGateway;

    @GetMapping("/allAccounts")
    public List<Account> accountList(){
        GetAllAccountsQuery getAllAccountsQuery = new GetAllAccountsQuery();
        List<Account> response = queryGateway.query(getAllAccountsQuery, ResponseTypes.multipleInstancesOf(Account.class)).join();
        return  response;
    }
}
