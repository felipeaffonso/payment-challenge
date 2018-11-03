package br.com.fza.paymentchallenge.rest;

import br.com.fza.paymentchallenge.exceptions.AccountNotFoundException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.converters.AccountConverter;
import br.com.fza.paymentchallenge.rest.converters.AccountRequestConverter;
import br.com.fza.paymentchallenge.rest.request.AccountRequest;
import br.com.fza.paymentchallenge.rest.response.AccountResponse;
import br.com.fza.paymentchallenge.services.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(
        value = "/accounts",
        description = "Account Operations",
        protocols = "http",
        tags = "account"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(name = "/accounts", value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    private final AccountRequestConverter accountRequestConverter;

    private final AccountConverter accountConverter;

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create a new Account")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(final @Valid @RequestBody AccountRequest accountRequest) {
        try {

            final Account createdAccount = this.accountService.createAccount(this.accountRequestConverter.convert(accountRequest));
            return this.accountConverter.convert(createdAccount);
        } catch(final Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Find a Specific Account")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value= "/{number}", produces = APPLICATION_JSON_UTF8_VALUE)
    public AccountResponse findAccount(final @PathVariable("number") Long id) {
        try {
            return  this.accountService.findAccount(id)
                    .map(this.accountConverter::convert)
                    .orElseThrow(() -> new AccountNotFoundException("Account # " + id + " does not exists."));
        } catch(final AccountNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch(final Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Find all Accounts")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public Collection<AccountResponse> findAllAccount() {
        try {
            return StreamSupport.stream(this.accountService.findAllAccounts().spliterator(), false)
                    .map(this.accountConverter::convert)
                    .collect(Collectors.toList());
        } catch(final Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}