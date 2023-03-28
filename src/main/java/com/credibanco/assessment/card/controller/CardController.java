package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.exception.CustomException;
import com.credibanco.assessment.card.model.Card;
import com.credibanco.assessment.card.model.CardFrontEnd;
import com.credibanco.assessment.card.service.impl.PSIMPL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

// https://github.com/codejava-official/spring-boot-crud-intellij

@Controller
// @RestController
@RequestMapping("/tarjeta")
public class CardController{

    public static JdbcTemplate jdbcTemplate;

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

    @GetMapping("/eliminar/{numero_validacion}")
    public String deleteUser(@PathVariable("numero_validacion") int numero_validacion, RedirectAttributes ra) {
        PSIMPL.cardRepository.deleteByNumeroValidacion(numero_validacion);
        System.out.println("Se ha eliminado la tarjeta, 00");
        return "redirect:/tarjeta/mostrartarjetas";
    }

    @PostMapping
    @RequestMapping(value = "/insertcard", method = RequestMethod.POST)
    public String CrearTarjeta(@ModelAttribute("card") Card card) {
        card.setNumero_validacion(); // Se agrega el número de validación
        this.psimpl.crearPersona(card); // Crear una instancia de Persona con los datos para insertar en la base de datos
        System.out.println("Tarjeta creada: " + card);
        return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCard); // Devuelve un estado 201 de creación exitosa
    }

    @GetMapping
    @RequestMapping(value = "/consultar/{numero_tarjeta}", method = RequestMethod.GET)
    public String ConsultarTarjeta(@PathVariable long numero_tarjeta, Model model) {
        Card card = PSIMPL.cardRepository.findByNumeroTarjeta(numero_tarjeta);
        System.out.println("Tarjeta consultada: " + card);
        CardFrontEnd cardMasked = new CardFrontEnd();
        cardMasked.setNumero_tarjetaEnmascarado(maskCreditCardNumber(String.valueOf(card.getNumero_tarjeta())));
        cardMasked.setTitular(card.getTitular());
        cardMasked.setCedula(card.getCedula());
        cardMasked.setTipo(card.getTipo());
        cardMasked.setTelefono(card.getTelefono());
        cardMasked.setEnrolada(card.isEnrolada());
        model.addAttribute("listMaskeds", cardMasked);
        return "cards"; // cards.html
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
            cardFrontEnd.setNumero_validacion(listCards.get(i).getNumero_validacion());
            cardFrontEnd.setNumero_tarjetaEnmascarado(maskCreditCardNumber(String.valueOf(listCards.get(i).getNumero_tarjeta())));
            cardFrontEnd.setTitular(listCards.get(i).getTitular());
            cardFrontEnd.setCedula(listCards.get(i).getCedula());
            cardFrontEnd.setTipo(listCards.get(i).getTipo());
            cardFrontEnd.setTelefono(listCards.get(i).getTelefono());
            cardFrontEnd.setEnrolada(listCards.get(i).isEnrolada());
            listCardsMasked.add(cardFrontEnd);
        }
        model.addAttribute("listMaskeds", listCardsMasked);
        return "cards"; // cards.html
    }

    public static String maskCreditCardNumber(String cardNumber) {
        int length = cardNumber.length();
        StringBuilder maskedNumber = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = cardNumber.charAt(i);
            if (i < 6 || i > length - 5 && Character.isDigit(c)) { // if (i < length - 4 && Character.isDigit(c))
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
        Randomizer();
        String message = "Estas en el localhost para Spring Boot.";
        System.out.println(message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    public static void Randomizer() {
        long min = 1000000000L; // Mínimo valor de 10 dígitos
        long max = 9999999999999999L; // Máximo valor de 16 dígitos
        long randomNum = ThreadLocalRandom.current().nextLong(min, max + 1);
        System.out.println("Número aleatorio generado: " + randomNum);
    }

    @GetMapping
    @RequestMapping(value = "/enrolar", method = RequestMethod.GET)
    public String enrolarTarjeta() {
        return "enroll";
    }

    @PostMapping
    @RequestMapping(value = "/enrolledcard", method = RequestMethod.POST)
    public String EnrolarTarjeta(HttpServletRequest request) { // Para obtener cualquier dato del request en el formulario HTML
        int numero_validacion = Integer.parseInt(request.getParameter("numero_validacion"));
        long numero_tarjeta = Long.parseLong(request.getParameter("numero_tarjeta"));

        List<Card> cardList = (List<Card>) PSIMPL.cardRepository.findAll();
        Set<Integer> usedValidationNumbers = new HashSet<>();
        Set<Long> usedCardNumbers = new HashSet<>();
        HashMap<Integer, Long> hashNumberValidationAndCard = new HashMap<>();
        for (int i = 0; i < cardList.size(); i++) {
            System.out.println(cardList.get(i).getNumero_validacion());
            hashNumberValidationAndCard.put(cardList.get(i).getNumero_validacion(), cardList.get(i).getNumero_tarjeta());
            usedValidationNumbers.add(cardList.get(i).getNumero_validacion());
            usedCardNumbers.add(cardList.get(i).getNumero_tarjeta());
        }

        if(hashNumberValidationAndCard.containsKey(numero_validacion) &&
                hashNumberValidationAndCard.get(numero_validacion) == numero_tarjeta){
            PSIMPL.cardRepository.updateEnroll(numero_tarjeta, true);
            System.out.println("00, Exito");
            //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de validación debe estar entre 1 y 100.");
            return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        }
        else if (hashNumberValidationAndCard.get(numero_validacion) == null){
            throw new CustomException("02, Numero de validacion incorrecto");
        }
        else {
            throw new CustomException("01, Tarjeta no existe");
        }
    }

    public static void insertDataInBatch(List<CardFrontEnd> dataList) {
        String sql = "INSERT INTO data_table (id, name, age) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CardFrontEnd cardFrontEnd = dataList.get(i);
                ps.setInt(1, cardFrontEnd.getId());
                ps.setString(2, cardFrontEnd.getNumero_tarjetaEnmascarado());
                ps.setInt(3, cardFrontEnd.getNumero_validacion());
            }

            @Override
            public int getBatchSize() {
                return dataList.size();
            }
        });
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