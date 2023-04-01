package com.credibanco.assessment.card;

import com.credibanco.assessment.card.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.numero_referencia = :numero_referencia")
    Transaction findByNumeroReferencia(@Param("numero_referencia") int numero_referencia);

    @Transactional
    @Modifying
    @Query("DELETE from Transaction t where t.numero_referencia = :numero_referencia")
    void deleteByNumeroReferencia(@Param("numero_referencia") int numero_referencia);
}
