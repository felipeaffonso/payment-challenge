package br.com.fza.paymentchallenge.services.impl;

import br.com.fza.paymentchallenge.exceptions.AccountNotFoundException;
import br.com.fza.paymentchallenge.exceptions.CouldNotCreateTransferException;
import br.com.fza.paymentchallenge.exceptions.CouldNotFindTransferException;
import br.com.fza.paymentchallenge.exceptions.NegativeAccountBalanceException;
import br.com.fza.paymentchallenge.model.Account;
import br.com.fza.paymentchallenge.model.Transfer;
import br.com.fza.paymentchallenge.repository.AccountRepository;
import br.com.fza.paymentchallenge.repository.TransferRepository;
import br.com.fza.paymentchallenge.services.AccountService;
import br.com.fza.paymentchallenge.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Optional<Transfer> createTransfer(final Long sourceAccountId, final Long targetAccountId, final BigDecimal amount) {
        try {

            final Account sourceAccount = this.accountService.findAccount(sourceAccountId)
                    .orElseThrow(() ->
                            new AccountNotFoundException("The source account " + sourceAccountId + " does not exists."));
            final Account targetAccount = this.accountService.findAccount(targetAccountId)
                    .orElseThrow(() ->
                            new AccountNotFoundException("The target account " + targetAccountId + " does not exists"));

            sourceAccount.giveMoney(amount);
            targetAccount.receiveMoney(amount);

            final Transfer transfer = Transfer
                    .builder()
                    .source(sourceAccount)
                    .target(targetAccount)
                    .amount(amount)
                    .build();
            final Account persistedSourceAccount = this.accountRepository.save(sourceAccount);
            final Account persistedTargetAccount = this.accountRepository.save(targetAccount);

            final Transfer persistedTransfer = this.transferRepository.save(transfer);

            log.info("The transfer {} was created. Removed {} euros from account {} and added to account {}",
                    persistedTransfer.getId(),
                    amount,
                    persistedSourceAccount.getId(),
                    persistedTargetAccount.getId());

            return Optional.of(persistedTransfer);
        } catch (final NegativeAccountBalanceException | AccountNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new CouldNotCreateTransferException(sourceAccountId, targetAccountId, amount, e);
        }
    }

    @Override
    public Optional<Transfer> findTransfer(Long id) {
        try {
            return this.transferRepository.findById(id);
        } catch (final Exception e) {
            throw new CouldNotFindTransferException("Could not find Transfer with id: " + id, e);
        }
    }
}