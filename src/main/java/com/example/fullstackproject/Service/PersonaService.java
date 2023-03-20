package com.example.fullstackproject.Service;

import com.example.fullstackproject.Entity.Persona;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface PersonaService {

    public List<Persona> consultarPersonas();

    public Persona crearPersona(Persona persona) throws ParseException;

    public Persona modificarPersona(Persona persona);

    public Persona buscarPersona(int id);

    public void eliminarPersona(int id);

}
