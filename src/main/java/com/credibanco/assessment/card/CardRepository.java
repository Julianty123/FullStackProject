package com.credibanco.assessment.card;

import com.credibanco.assessment.card.model.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {
    @Modifying
    @Transactional
    @Query("update Card c set c.enrolada = :enrolada where c.numero_tarjeta = :numero_tarjeta") // , u.titulo = :titulo
    void updateEnroll(@Param("numero_tarjeta") long numero_tarjeta, @Param("enrolada") boolean enrolada);

    @Query("SELECT c FROM Card c WHERE c.numero_tarjeta = :numeroTarjeta")
    Card findByNumeroTarjeta(@Param("numeroTarjeta") long numeroTarjeta);

    @Modifying // Indica que es una operación de modificación de datos
    @Transactional // Ejecuta la operación dentro de una transacción, ya sea de actualizacion eliminacion o insercion
    @Query("delete from Card c where c.numero_validacion = :numero_validacion") // Define la consulta personalizada
    void deleteByNumeroValidacion(@Param("numero_validacion") int numero_validacion);
}