package br.com.fza.paymentchallenge.rest;

import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.model.Transfer;
import br.com.fza.paymentchallenge.rest.converters.TransferConverter;
import br.com.fza.paymentchallenge.rest.response.TransferResponse;
import br.com.fza.paymentchallenge.services.TransferService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransferController.class)
@AutoConfigureMockMvc
public class TransferControllerTest {

    @MockBean
    private TransferService transferService;
    @MockBean
    private TransferConverter transferConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTransferMustReturnBadRequestWithEmptyPayload() throws Exception {
        this.mockMvc.perform(
                post("/transfers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void createTransferMustReturnInternalServerErrorWhenTransferServiceFails() throws Exception {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;
        final Throwable exception = new NullPointerException();

        given(this.transferService.createTransfer(sourceAccountId, targetAccountId, BigDecimal.TEN))
                .willThrow(exception);

        this.mockMvc.perform(
                post("/transfers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"sourceAccountNumber\": 1, \"targetAccountNumber\": 2, \"amount\": 10}")
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void createTransferMustReturnInternalServerErrorWhenTransferReturnsEmpty() throws Exception {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        given(this.transferService.createTransfer(sourceAccountId, targetAccountId, BigDecimal.TEN))
                .willReturn(Optional.empty());

        this.mockMvc.perform(
                post("/transfers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"sourceAccountNumber\": 1, \"targetAccountNumber\": 2, \"amount\": 10}")
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void createTransferMustReturnCreatedWithValidInput() throws Exception {
        final Long sourceAccountId = 1L;
        final Long targetAccountId = 2L;

        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(1L)
                .name("Source")
                .version(0)
                .build();
        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(2L)
                .name("Target")
                .version(0)
                .build();

        final Transfer persistedTransfer = Transfer.builder()
                .id(1L)
                .amount(TEN)
                .source(sourceAccount)
                .target(targetAccount)
                .build();

        given(this.transferService.createTransfer(sourceAccountId, targetAccountId, BigDecimal.TEN))
                .willReturn(Optional.of(persistedTransfer));

        final TransferResponse transferResponse = TransferResponse.builder()
                .transferNumber(1L)
                .sourceAccountNumber(sourceAccount.getId())
                .targetAccountNumber(targetAccount.getId())
                .transferredAmount(TEN)
                .build();

        given(transferConverter.convert(persistedTransfer))
                .willReturn(transferResponse);

        this.mockMvc.perform(
                post("/transfers")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content("{\"sourceAccountNumber\": 1, \"targetAccountNumber\": 2, \"amount\": 10}")
        )       .andExpect(status().isCreated())
                .andExpect(jsonPath("transferNumber", is(1)))
                .andExpect(jsonPath("sourceAccountNumber", is(1)))
                .andExpect(jsonPath("targetAccountNumber", is(2)))
                .andExpect(jsonPath("transferredAmount", is(10)));
    }

    @Test
    public void findTransferMustReturnInternalServerErrorWithInvalidId() throws Exception{
        final Long id = 1L;

        final Throwable exception = new NullPointerException("Could not find transfer");
        given(this.transferService.findTransfer(id))
                .willThrow(exception);

        mockMvc.perform(get("/transfers/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    @Test
    public void findTransferMustReturnNotFoundWithInvalidTransferId() throws Exception{
        final Long id = 1L;

        given(this.transferService.findTransfer(id))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/transfers/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void findTransferMustReturnTransferResponseWithValidInput() throws Exception{

        final Long id = 1L;
        final Account sourceAccount = Account.builder()
                .balance(TEN)
                .id(id)
                .name("Source")
                .version(0)
                .build();
        final Account targetAccount = Account.builder()
                .balance(TEN)
                .id(id)
                .name("Target")
                .version(0)
                .build();

        final Transfer persistedTransfer = Transfer.builder()
                .id(id)
                .amount(TEN)
                .source(sourceAccount)
                .target(targetAccount)
                .build();

        given(this.transferService.findTransfer(id))
                .willReturn(Optional.of(persistedTransfer));

        final TransferResponse transferResponse = TransferResponse.builder()
                .transferNumber(1L)
                .sourceAccountNumber(sourceAccount.getId())
                .targetAccountNumber(targetAccount.getId())
                .transferredAmount(TEN)
                .build();

        given(this.transferConverter.convert(persistedTransfer))
                .willReturn(transferResponse);

        mockMvc.perform(get("/transfers/" + id).accept(APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}