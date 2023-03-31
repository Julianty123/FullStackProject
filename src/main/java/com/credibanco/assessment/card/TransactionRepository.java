package com.credibanco.assessment.card;

import com.credibanco.assessment.card.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.numero_referencia = :numero_referencia")
    Transaction findByNumeroReferencia(@Param("numero_referencia") int numero_referencia);

    @Query("DELETE from Transaction t where t.numero_referencia = :numero_referencia")
    Transaction deleteByNumeroReferencia(@Param("numero_referencia") int numero_referencia);
}
