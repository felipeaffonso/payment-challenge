package br.com.fza.paymentchallenge.repository;

import br.com.fza.paymentchallenge.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
