package com.example.fullstackproject.Repository;

import com.example.fullstackproject.Entity.Persona;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PersonaRepo extends CrudRepository<Persona, Integer> { //extends

    /* Define una Query personalizada
    @Query("SELECT p FROM Persona p WHERE p.matricula = :matricula")
    public Persona findByMatricula(@Param("matricula") int matricula);
     */
}

