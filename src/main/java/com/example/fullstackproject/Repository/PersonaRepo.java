package com.example.fullstackproject.Repository;

import com.example.fullstackproject.Entity.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaRepo extends CrudRepository<Persona, Integer> {

}
