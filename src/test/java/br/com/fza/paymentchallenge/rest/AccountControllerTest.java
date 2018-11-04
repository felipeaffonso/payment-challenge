package br.com.fza.paymentchallenge.rest;

import br.com.fza.paymentchallenge.exceptions.CouldNotCreateAccountException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.rest.converters.AccountConverter;
import br.com.fza.paymentchallenge.rest.converters.AccountRequestConverter;
import br.com.fza.paymentchallenge.rest.request.AccountRequest;
import br.com.fza.paymentchallenge.rest.response.AccountResponse;
import br.com.fza.paymentchallenge.services.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountRequestConverter accountRequestConverter;

    @MockBean
    private AccountConverter accountConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAccountMustReturnErrorWithEmptyPayload() throws Exception{
        mockMvc.perform(
                post("/accounts")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
                    .content("")
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void createAccountMustReturnErrorWithInvalidPayload() throws Exception{
        mockMvc.perform(
                post("/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountMustReturnInternalServerErrorWhenAccountServiceFails() throws Exception{
        final Account account = Account.builder().name("Felipe").balance(TEN).build();

        given(this.accountRequestConverter.convert(ArgumentMatchers.any(AccountRequest.class))).willReturn(account);
        final Throwable exception = new CouldNotCreateAccountException("Message", null);

        given(this.accountService.createAccount(account))
                .willThrow(exception);

        mockMvc.perform(
                post("/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\": \"Felipe\", \"initialBalance\": 10}")
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void createAccountMustReturnAccountResponseWithValidInput() throws Exception{
        final Account account = Account.builder().name("Felipe").balance(TEN).build();

        given(this.accountRequestConverter.convert(ArgumentMatchers.any(AccountRequest.class))).willReturn(account);

        final Account persistedAccount = Account.builder().name("Felipe").id(1L).version(0).balance(TEN).build();

        given(this.accountService.createAccount(account))
                .willReturn(persistedAccount);

        final AccountResponse accountResponse = AccountResponse.builder()
                .currentBalance(TEN)
                .name("Felipe")
                .number(1L)
                .build();


        given(this.accountConverter.convert(persistedAccount))
                .willReturn(accountResponse);

        mockMvc.perform(
                post("/accounts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\": \"Felipe\", \"initialBalance\": 10}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("number", is(1)))
                .andExpect(jsonPath("name", is("Felipe")))
                .andExpect(jsonPath("currentBalance", is(TEN.intValue())));
    }

    @Test
    public void findAccountMustReturnAccountResponseWithValidId() throws Exception {
        final Long id = 1L;

        final Account persistedAccount = Account.builder()
                .balance(TEN)
                .name("Felipe")
                .version(0)
                .id(id)
                .build();

        given(this.accountService.findAccount(id))
                .willReturn(Optional.of(persistedAccount));

        final AccountResponse accountResponse = AccountResponse.builder()
                .number(id)
                .name("Felipe")
                .currentBalance(TEN)
                .build();

        given(this.accountConverter.convert(persistedAccount))
                .willReturn(accountResponse);

        mockMvc.perform(get("/accounts/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("number", is(id.intValue())))
                .andExpect(jsonPath("name", is("Felipe")))
                .andExpect(jsonPath("currentBalance", is(TEN.intValue())));
    }

    @Test
    public void findAccountMustReturnNotFoundResponseWithInvalidId() throws Exception {
        final Long id = 1L;

        given(this.accountService.findAccount(id))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/accounts/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAccountMustReturnInternalServerErrorWhenConverterThrowsException() throws Exception {
        final Long id = 1L;

        final Account persistedAccount = Account.builder()
                .balance(TEN)
                .name("Felipe")
                .version(0)
                .id(id)
                .build();

        given(this.accountService.findAccount(id))
                .willReturn(Optional.of(persistedAccount));

        final Throwable exception = new NullPointerException();

        given(this.accountConverter.convert(persistedAccount))
                .willThrow(exception);

        mockMvc.perform(get("/accounts/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findAllAccountMustReturnEmptyListWhenNoAccountIsFound() throws Exception{

        final Account persistedAccount = Account.builder()
                .balance(TEN)
                .name("Felipe")
                .version(0)
                .id(1L)
                .build();

        given(this.accountService.findAllAccounts())
                .willReturn(singletonList(persistedAccount));

        final Throwable exception = new NullPointerException();

        given(this.accountConverter.convert(persistedAccount)).willThrow(exception);

        mockMvc.perform(get("/accounts").accept(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void findAllAccountMustReturnInternalServerErrorWhenConverterThrowsException() throws Exception{

        given(this.accountService.findAllAccounts())
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/accounts").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(0)));
    }
}