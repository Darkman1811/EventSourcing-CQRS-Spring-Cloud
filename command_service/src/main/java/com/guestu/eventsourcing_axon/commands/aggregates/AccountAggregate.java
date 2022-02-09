package com.guestu.eventsourcing_axon.commands.aggregates;

import com.guestu.eventsourcing_axon.common_api.commands.CreateAccountCommand;
import com.guestu.eventsourcing_axon.common_api.commands.CreditAccountCommand;
import com.guestu.eventsourcing_axon.common_api.commands.DebitAccountCommand;
import com.guestu.eventsourcing_axon.common_api.enums.AccountStatus;
import com.guestu.eventsourcing_axon.common_api.events.AccountActivatedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountCreatedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountCreditedEvent;
import com.guestu.eventsourcing_axon.common_api.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
     private String accountId;
     private double balance;
     private String currency;
     private AccountStatus status;

    public AccountAggregate() {
        // Required by Axon
    }



    @CommandHandler
    public  AccountAggregate(CreateAccountCommand createAccountCommand) {
        if(createAccountCommand.getInitialBalance()<0){
            throw new RuntimeException("Solde negative");
        }
        AccountCreatedEvent accountCreatedEvent=new AccountCreatedEvent(createAccountCommand.getId(),createAccountCommand.getInitialBalance(),createAccountCommand.getCurrency(), AccountStatus.CREATED);
        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountId=accountCreatedEvent.getId();
        this.balance=accountCreatedEvent.getInitialBalance();
        this.currency=accountCreatedEvent.getCurrency();
        this.status=accountCreatedEvent.getStatus();

        AccountActivatedEvent accountActivatedEvent=new AccountActivatedEvent(accountCreatedEvent.getId(),AccountStatus.ACTIVATED);
        AggregateLifecycle.apply(accountActivatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        this.status=accountActivatedEvent.getAccountStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        if(creditAccountCommand.getAmount()<0){
            throw new RuntimeException("montant négative");
        }

        AccountCreditedEvent accountCreditedEvent = new AccountCreditedEvent(creditAccountCommand.getId(), creditAccountCommand.getAmount(), creditAccountCommand.getCurrency());
        AggregateLifecycle.apply(accountCreditedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance=this.balance+accountCreditedEvent.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        if(debitAccountCommand.getAmount()<0){
            throw new RuntimeException("montant négative");
        }

        AccountDebitedEvent accountDebitedEvent = new AccountDebitedEvent(debitAccountCommand.getId(), debitAccountCommand.getAmount(), debitAccountCommand.getCurrency());
        AggregateLifecycle.apply(accountDebitedEvent);
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance=this.balance-accountDebitedEvent.getAmount();
    }


}
