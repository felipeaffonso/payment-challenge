package br.com.fza.paymentchallenge.rest;

import br.com.fza.paymentchallenge.exceptions.CouldNotCreateTransferException;
import br.com.fza.paymentchallenge.exceptions.TransferNotFoundException;
import br.com.fza.paymentchallenge.rest.converters.TransferConverter;
import br.com.fza.paymentchallenge.rest.request.TransferRequest;
import br.com.fza.paymentchallenge.rest.response.TransferResponse;
import br.com.fza.paymentchallenge.services.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Api(
        value = "/transfers",
        description = "Account Transfer Operations",
        protocols = "http",
        tags = "transfer"
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(name = "/transfers", value = "/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransferConverter transferConverter;

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create a new Transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse createTransfer(final @Valid @RequestBody TransferRequest transferRequest) {
        try {
            return this.transferService.createTransfer(
                    transferRequest.getSourceAccountNumber(),
                    transferRequest.getTargetAccountNumber(),
                    transferRequest.getAmount())
                    .map(this.transferConverter::convert)
                    .orElseThrow(() -> new CouldNotCreateTransferException(transferRequest));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Find a Specific Transfer")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{number}", produces = APPLICATION_JSON_UTF8_VALUE)
    public TransferResponse findTransaction(final @PathVariable("number") Long id) {
        try {
            return this.transferService.findTransfer(id)
                    .map(this.transferConverter::convert)
                    .orElseThrow(() -> new TransferNotFoundException("Transfer # " + id + " does not exists."));
        } catch (final TransferNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}