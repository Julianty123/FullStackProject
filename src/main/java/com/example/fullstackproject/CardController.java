package com.example.fullstackproject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


/*
/creartarjeta
Crear tarjeta: numero de validacion: al azar 1 - 100 para poder enrolar la tarjeta
Datos request:
PAN: numero de tarjeta de 16 a 19 digitos
titular
cedula: 10 a 15 caracteres
tipo: credito o debito
telefono: 10 digitos
Datos response:
exito o fallido..

/enrolartarjeta
Datos request:
PAN
numero de validacion
Datos response:
00, 01, 02 mensaje exito, tarjeta no existe, numero de validacion invalido, pan enmascarado
 */

// https://github.com/codejava-official/spring-boot-form-thymeleaf

@Controller
// @RestController
@RequestMapping("/tarjeta")
public class CardController {

    @Autowired
    private PSIMPL psimpl;


    /*@GetMapping("/error")
    public ResponseEntity<String> handleErrors(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error: " + ex.getMessage());
    }*/

    @GetMapping("/crear")
    public String crear() {
        return "create";
    }

    @GetMapping
    @RequestMapping(value = "/mostrartarjetas", method = RequestMethod.GET)
    public String MostrarTarjetas(Model model) {
        List<Card> listCards = this.psimpl.consultarTarjetas();
        System.out.println("Tarjetas mostradas!");
        model.addAttribute("listCards", listCards);
        return "cards"; // cards.html
    }

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

    @PostMapping
    @RequestMapping(value = "/insertcard", method = RequestMethod.POST)
    public String CrearTarjeta(@ModelAttribute("card") Card card) throws ParseException {
        card.setNumero_validacion(); // Se agrega el número de validación
        System.out.println("Tarjeta creada: " + card);
        this.psimpl.crearPersona(card); // Guarda la entidad en la base de datos
        return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCard); // Devuelve un estado 201 de creación exitosa
    }

    @GetMapping
    @RequestMapping(value = "/enrolar", method = RequestMethod.GET)
    public String enrolarTarjeta() {
        return "enroll";
    }

    @PostMapping
    @RequestMapping(value = "/enrollcard", method = RequestMethod.POST)
    public String EnrolarTarjeta(@ModelAttribute("card") Card card) throws ParseException {
        this.psimpl.actualizarTarjeta(card.getNumero_validacion(), card.getNumero_tarjeta());
        return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
    }

    @GetMapping
    @RequestMapping(value = "/consultar/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> ConsultarTarjeta(@PathVariable int id) {
        Card card = this.psimpl.buscarPersona(id);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping
    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> EliminarPersona(@PathVariable int id) {
        this.psimpl.eliminarPersona(id);
        return ResponseEntity.ok().build();
    }

    public void CrearTransaccion(){

    }
    public void AnularTransaccion(){

    }

    /*@PutMapping
    @RequestMapping(value = "/modificar", method = RequestMethod.PUT)
    public ResponseEntity<?> ModificarPersona(@RequestBody Persona persona) throws ParseException {
        Persona personaCreada = this.psimpl.crearPersona(persona);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }*/

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