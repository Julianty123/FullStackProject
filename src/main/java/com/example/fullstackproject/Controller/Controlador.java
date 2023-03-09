package com.example.fullstackproject.Controller;

import com.example.fullstackproject.Entity.Persona;
import com.example.fullstackproject.Service.PersonaServiceIMPL.PSIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class Controlador {

    @Autowired
    private PSIMPL impl;

    @GetMapping
    @RequestMapping(value = "ConsultarPersonas", method = RequestMethod.GET)
    public ResponseEntity<?> ConsultarPersonas() {
        List<Persona> listaPersona = this.impl.consultarPersonas();
        return ResponseEntity.ok(listaPersona);
    }

    @GetMapping
    @RequestMapping(value = "CrearPersona", method = RequestMethod.POST)
    public ResponseEntity<?> CrearPersona(@RequestBody Persona persona) {
        Persona personaCreada = this.impl.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }

    @PutMapping
    @RequestMapping(value = "ModificarPersona", method = RequestMethod.PUT)
    public ResponseEntity<?> ModificarPersona(@RequestBody Persona persona) {
        Persona personaCreada = this.impl.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }

    @GetMapping
    @RequestMapping(value = "BuscarPersona/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> BuscarPersona(@PathVariable int id) {
        Persona persona = this.impl.buscarPersona(id);
        return ResponseEntity.ok(persona);
    }

    @GetMapping
    @RequestMapping(value = "EliminarPersona/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> EliminarPersona(@PathVariable int id) {
        this.impl.eliminarPersona(id);
        return ResponseEntity.ok().build();
    }

    /*
        Period period = Period.of(1, 2, 3);
    periodo = period.getDays();
    System.out.println(period);
     */

    /*@GetMapping("/{id}")
    public Persona getPostById(@PathVariable Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la entrada del blog."));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Persona createPost(@RequestBody Persona persona) {
        return postRepository.save(persona);
    }

    @PutMapping("/{id}")
    public Persona updatePost(@PathVariable Integer id, @RequestBody Persona updatedPersona) {
        return postRepository.findById(id)
                .map(persona -> {
                    persona.setTitle(updatedPersona.getTitle());
                    persona.setContent(updatedPersona.getContent());
                    return postRepository.save(persona);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró la entrada del blog."));
    }*/
}
