package br.com.fza.paymentchallenge.repository;

import br.com.fza.paymentchallenge.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
