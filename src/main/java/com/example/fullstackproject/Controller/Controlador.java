package com.example.fullstackproject.Controller;

import com.example.fullstackproject.Entity.Persona;
import com.example.fullstackproject.Service.PersonaServiceIMPL.PSIMPL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/persona")
public class Controlador {

    @Autowired
    private PSIMPL psimpl;

    /*@GetMapping("/error")
    public ResponseEntity<String> handleErrors(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error: " + ex.getMessage());
    }*/

    @ExceptionHandler(value = ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Se ha producido un error interno.");
    }

    @GetMapping("/")
    public ResponseEntity<String> localHost() {
        String message = "Estas en el localhost para Spring Boot.";
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(value = {Exception.class})
    public void defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        System.out.println("Error: " + e.getMessage());
    }

    @GetMapping
    @RequestMapping(value = "/consultarpersonas", method = RequestMethod.GET)
    public ResponseEntity<?> ConsultarPersonas() {
        List<Persona> listaPersona = this.psimpl.consultarPersonas();
        System.out.println("Personas consultadas!");
        return ResponseEntity.ok(listaPersona);
    }

    @PostMapping
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    public ResponseEntity<?> CrearPersona() throws ParseException {
        Persona nuevaPersona = this.psimpl.crearPersona(new Persona()); // Guardar la entidad en la base de datos
        System.out.println("ultima matricula: " + PSIMPL.ultima_matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona); // Devuelve un estado 201 de creación exitosa
    }

    @PutMapping
    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public ResponseEntity<?> ModificarPersona(@RequestBody Persona persona) throws ParseException {
        Persona personaCreada = this.psimpl.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }

    @GetMapping
    @RequestMapping(value = "/buscar/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> BuscarPersona(@PathVariable int id) {
        Persona persona = this.psimpl.buscarPersona(id);
        return ResponseEntity.ok(persona);
    }

    @DeleteMapping
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> EliminarPersona(@PathVariable int id) {
        this.psimpl.eliminarPersona(id);
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
