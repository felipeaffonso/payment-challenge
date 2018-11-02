package br.com.fza.paymentchallenge.repository;

import br.com.fza.paymentchallenge.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
