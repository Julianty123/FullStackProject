package com.example.fullstackproject.Service.PersonaServiceIMPL;

import com.example.fullstackproject.Entity.Persona;
import com.example.fullstackproject.Repository.PersonaRepo;
import com.example.fullstackproject.Service.PersonaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class PSIMPL implements PersonaService {

    @Autowired // Campo de inyeccion, no recomendado (Ver porque esta advertencia)
    private PersonaRepo repo;

    @Override
    public List<Persona> consultarPersonas() {
        return (List<Persona>) this.repo.findAll();
    }

    @Override
    public Persona crearPersona(Persona persona) {
        // persona.setCorreo(persona.getCorreo());
        return this.repo.save(persona);
    }

    @Override
    public Persona modificarPersona(Persona persona) {
        return this.repo.save(persona);
    }

    @Override
    public Persona buscarPersona(int id) {
        return this.repo.findById(id).get(); // repo.findById(id).orElse(null)
    }

    @Override
    public void eliminarPersona(int id) {
        this.repo.deleteById(id);
    }
}
