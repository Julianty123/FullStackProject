package com.credibanco.assessment.card;

import com.credibanco.assessment.card.model.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {
    @Modifying
    @Transactional  // Se ejecuta dentro de una transaccion, ya sea de actualizacion eliminacion o insercion
    // Define una Query personalizada , u.numero_tarjeta = :numero_tarjeta
    @Query("update Card c set c.enrolada = :enrolada where c.numero_tarjeta = :numero_tarjeta")
    void updateEnroll(@Param("numero_tarjeta") long numero_tarjeta, @Param("enrolada") boolean enrolada);

    @Query("SELECT c FROM Card c WHERE c.numero_tarjeta = :numeroTarjeta")
    Card findByNumeroTarjeta(@Param("numeroTarjeta") long numeroTarjeta);
}