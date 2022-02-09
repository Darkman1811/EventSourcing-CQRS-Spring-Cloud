package com.guestu.eventsourcing_axon.query.service;

import com.guestu.eventsourcing_axon.common_api.events.AccountActivatedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountCreatedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountCreditedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountDebitedEvent;
import com.guestu.eventsourcing_axon.query.Repository.AccountRepository;
import com.guestu.eventsourcing_axon.query.Repository.OperationRepository;
import com.guestu.eventsourcing_axon.query.entities.Account;
import com.guestu.eventsourcing_axon.query.entities.Operation;
import com.guestu.eventsourcing_axon.query.queries.GetAllAccountsQuery;
import com.guestu.eventsourcing_axon.query.utils.OperationType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor //pour injecter le AccountRepository et le OperationRepository dans un constructeur construit par Lombok
@Slf4j
public class AccountServiceHandler {
    AccountRepository accountRepository;
    OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("Created************************************************");
        log.info(accountCreatedEvent.getId());
        Account account= new Account();
        account.setId(accountCreatedEvent.getId());
        account.setBalance(accountCreatedEvent.getInitialBalance());
        account.setCurrency(accountCreatedEvent.getCurrency());
        account.setStatus(accountCreatedEvent.getStatus());
    accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("Activated************************************************");
        log.info(accountActivatedEvent.getId());
        accountRepository.findById(accountActivatedEvent.getId()).ifPresent(account ->{
            account.setStatus(accountActivatedEvent.getAccountStatus());
            accountRepository.save(account);
        } );
    }

    @EventHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("Credited************************************************");
        log.info(accountCreditedEvent.getId());
        accountRepository.findById(accountCreditedEvent.getId()).ifPresent(account ->{
            account.setBalance(account.getBalance()+accountCreditedEvent.getAmount());

            Operation operation=new Operation();
            operation.setAccount(account);
            operation.setAmout(accountCreditedEvent.getAmount());
            operation.setType(OperationType.CREDIT);
            operation.setCreatedAt(new Date());
            operationRepository.save(operation);
              Operation savedOp=operationRepository.getById(operation.getId());
            account.getOperations().add(savedOp);
            accountRepository.save(account);
        } );
    }

    @EventHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("Debited************************************************");
        log.info(accountDebitedEvent.getId());
        accountRepository.findById(accountDebitedEvent.getId()).ifPresent(account ->{
            account.setBalance(account.getBalance()-accountDebitedEvent.getAmount());

            Operation operation=new Operation();
            operation.setAmout(accountDebitedEvent.getAmount());
            operation.setType(OperationType.DEBIT);
            operation.setCreatedAt(new Date());
            operation.setAccount(account);
            operationRepository.save(operation);
            Operation savedOp=operationRepository.getById(operation.getId());
            account.getOperations().add(savedOp);
            accountRepository.save(account);

        } );
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery getAllAccountsQuery){
    return accountRepository.findAll();
    }
}
