package com.guestu.eventsourcing_axon.commands.controllers;


import com.guestu.eventsourcing_axon.common_api.commands.CreateAccountCommand;
import com.guestu.eventsourcing_axon.common_api.commands.CreditAccountCommand;
import com.guestu.eventsourcing_axon.common_api.commands.DebitAccountCommand;
import com.guestu.eventsourcing_axon.common_api.dtos.CreateAccountRequestDTO;
import com.guestu.eventsourcing_axon.common_api.dtos.CreditAccountRequestDTO;
import com.guestu.eventsourcing_axon.common_api.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping(path="/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CreateAccountCommand createAccountCommand=new CreateAccountCommand(UUID.randomUUID().toString(),request.getInitialBalance(),request.getCurrency());
        CompletableFuture<String> createCommandResponse = commandGateway.send(createAccountCommand);
        return createCommandResponse;
    }

    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO){
        CreditAccountCommand creditAccountCommand=new CreditAccountCommand(creditAccountRequestDTO.getAccountId(),creditAccountRequestDTO.getAmount(),creditAccountRequestDTO.getCurrency());
        CompletableFuture<String> creditCommandResponse=commandGateway.send(creditAccountCommand);
        return  creditCommandResponse;
    }

    @PutMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO){
        DebitAccountCommand debitAccountCommand = new DebitAccountCommand(debitAccountRequestDTO.getAccountId(), debitAccountRequestDTO.getAmount(), debitAccountRequestDTO.getCurrency());
        CompletableFuture<String> debitCommandResponse=commandGateway.send(debitAccountCommand);
        return  debitCommandResponse;
    }


    //public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO)

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
     Stream eventStoreStream= eventStore.readEvents(accountId).asStream();
     return eventStoreStream;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> responseEntity=new ResponseEntity<>("Error: "+exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
