package com.example.fullstackproject;

import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Integer> {

    /* Define una Query personalizada
    @Query("SELECT p FROM Persona p WHERE p.matricula = :matricula")
    public Persona findByMatricula(@Param("matricula") int matricula);
    */
}

