package br.com.fza.paymentchallenge.repository;

import br.com.fza.paymentchallenge.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {

}
