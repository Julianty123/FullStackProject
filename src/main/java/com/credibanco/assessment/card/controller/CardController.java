package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.exception.CustomException;
import com.credibanco.assessment.card.model.Card;
import com.credibanco.assessment.card.model.CardFrontEnd;
import com.credibanco.assessment.card.service.impl.PSIMPL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final PSIMPL psimpl;

    @Autowired
    public CardController(PSIMPL psimpl) {
        this.psimpl = psimpl;
    }

    /*@GetMapping("/error")
    public ResponseEntity<String> handleErrors(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error: " + ex.getMessage());
    }*/

    @GetMapping("/crear")
    public String crear() {
        return "create";    // create.html
    }

    @PostMapping
    @RequestMapping(value = "/insertcard", method = RequestMethod.POST)
    public String CrearTarjeta(@ModelAttribute("card") Card card) {
        card.setNumero_validacion(); // Se agrega el número de validación
        System.out.println("Tarjeta creada: " + card);
        this.psimpl.crearPersona(card); // Guarda la entidad en la base de datos
        return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCard); // Devuelve un estado 201 de creación exitosa
    }

    @GetMapping
    @RequestMapping(value = "/consultar/{numero_tarjeta}", method = RequestMethod.GET)
    public ResponseEntity<?> ConsultarTarjeta(@PathVariable int numero_tarjeta) {
        Card card = PSIMPL.cardRepository.findByNumeroTarjeta(numero_tarjeta);
        return ResponseEntity.ok(card);
    }

    @GetMapping("/json")
    public ResponseEntity<List<Card>> getCards() {
        List<Card> listCards = this.psimpl.consultarTarjetas();
        return ResponseEntity.ok(listCards);
    }

    @GetMapping
    @RequestMapping(value = "/mostrartarjetas", method = RequestMethod.GET)
    public String MostrarTarjetas(Model model) {
        List<Card> listCards = this.psimpl.consultarTarjetas();
        List<CardFrontEnd> listCardsMasked = new ArrayList<>();
        // ciclo for con i
        for (int i = 0; i < listCards.size(); i++) {
            CardFrontEnd cardFrontEnd = new CardFrontEnd();
            cardFrontEnd.setNumero_tarjetaEnmascarado(maskCreditCardNumber(String.valueOf(listCards.get(i).getNumero_tarjeta())));
            cardFrontEnd.setTitular(listCards.get(i).getTitular());
            cardFrontEnd.setCedula(listCards.get(i).getCedula());
            cardFrontEnd.setTipo(listCards.get(i).getTipo());
            cardFrontEnd.setTelefono(listCards.get(i).getTelefono());
            cardFrontEnd.setEnrolada(listCards.get(i).isEnrolada());
            listCardsMasked.add(cardFrontEnd);
        }
        // model.addAttribute("listCards", listCards);
        model.addAttribute("listMaskeds", listCardsMasked);
        return "cards"; // cards.html
    }

    /*public static String maskCreditCardNumber(String cardNumber) {
        int length = cardNumber.length();
        StringBuilder maskedNumber = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = cardNumber.charAt(i);
            if (i < length - 4 && Character.isDigit(c)) {
                maskedNumber.append('*');
            } else {
                maskedNumber.append(c);
            }
        }
        return maskedNumber.toString();
    }*/

    public static String maskCreditCardNumber(String cardNumber) {
        int length = cardNumber.length();
        StringBuilder maskedNumber = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = cardNumber.charAt(i);
            if (i < 6 || i > length - 5 && Character.isDigit(c)) {
                maskedNumber.append(c);
            } else {
                maskedNumber.append('*');
            }
        }
        return maskedNumber.toString();
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

    @GetMapping
    @RequestMapping(value = "/enrolar", method = RequestMethod.GET)
    public String enrolarTarjeta() {
        return "enroll";
    }

    @PostMapping
    @RequestMapping(value = "/enrolledcard", method = RequestMethod.POST)
    public String EnrolarTarjeta(HttpServletRequest request) { // Para obtener cualquier dato del request en el formulario HTML
        int numeroValidacion = Integer.parseInt(request.getParameter("numero_validacion"));
        long numeroTarjeta = Long.parseLong(request.getParameter("numero_tarjeta"));

        List<Card> cardList = (List<Card>) PSIMPL.cardRepository.findAll();
        Set<Integer> usedValidationNumbers = new HashSet<>();
        for (int i = 0; i < cardList.size(); i++) {
            System.out.println(cardList.get(i).getNumero_validacion());
            usedValidationNumbers.add(cardList.get(i).getNumero_validacion());
        }

        if(usedValidationNumbers.contains(numeroValidacion)){
            PSIMPL.cardRepository.updateEnroll(numeroTarjeta, true);
            System.out.println("La tarjeta ha sido enrolada!");
            // throw new CustomException("00, Exito");
            return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        }
        else{
            throw new CustomException("02, Numero de validacion invalido");
            // throw new CustomException("01, Tarjeta no existe");
        }
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