package com.example.fullstackproject;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Modifying
    // Define una Query personalizada
    @Query("update Card u set u.numero_validacion = :numero_validacion, u.numero_tarjeta = :numero_tarjeta, u.titular = :titular, u.cedula = :cedula, u.tipo = :tipo, u.telefono = :telefono where u.id = :id")
    void update(@Param("numero_validacion") int numero_validacion, @Param("numero_tarjeta") long numero_tarjeta);
}