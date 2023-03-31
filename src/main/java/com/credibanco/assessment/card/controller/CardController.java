package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.exception.CustomException;
import com.credibanco.assessment.card.model.Card;
import com.credibanco.assessment.card.model.CardFrontEnd;
import com.credibanco.assessment.card.model.Transaction;
import com.credibanco.assessment.card.service.impl.PSIMPLcard;
import com.credibanco.assessment.card.service.impl.PSIMPLtransaction;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
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

    // Si no ocurre ninguna excepción en el metodo, se considera la transaccion aprobada automaticamente
    public String estadoTransaccion;
    public static JdbcTemplate jdbcTemplate;


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
        PSIMPLcard.cardRepository.deleteByNumeroValidacion(numero_validacion);
        System.out.println("Se ha eliminado la tarjeta, 00");
        return "redirect:/tarjeta/mostrartarjetas";
    }

    @PostMapping
    @RequestMapping(value = "/insertcard", method = RequestMethod.POST)
    public String CrearTarjeta(@ModelAttribute("card") Card card) {
        card.setNumero_validacion(); // Se agrega el número de validación
        PSIMPLcard.crearPersona(card); // Crear una instancia de Persona con los datos para insertar en la base de datos
        System.out.println("Tarjeta creada: " + card);
        return "redirect:/tarjeta/mostrartarjetas"; // Redirecciona a la página de tarjetas
        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCard); // Devuelve un estado 201 de creación exitosa
    }

    @GetMapping
    @RequestMapping(value = "/consultar/{numero_tarjeta}", method = RequestMethod.GET)
    public String ConsultarTarjeta(@PathVariable long numero_tarjeta, Model model) {
        Card card = PSIMPLcard.cardRepository.findByNumeroTarjeta(numero_tarjeta);
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

    @GetMapping("/jsoncard")
    public ResponseEntity<List<Card>> getCards() {
        List<Card> listCards = PSIMPLcard.consultarTarjetas();
        return ResponseEntity.ok(listCards);
    }

    @GetMapping("/jsontransaction")
    public ResponseEntity<List<Transaction>> getTransactions() {
        List<Transaction> transactionList = PSIMPLtransaction.consultarTransacciones();
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping
    @RequestMapping(value = "/mostrartarjetas", method = RequestMethod.GET)
    public String MostrarTarjetas(Model model) {
        List<Card> listCards = PSIMPLcard.consultarTarjetas();
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

        List<Card> cardList = (List<Card>) PSIMPLcard.cardRepository.findAll();
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
            PSIMPLcard.cardRepository.updateEnroll(numero_tarjeta, true);
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

    @GetMapping
    @RequestMapping(value = "/transaccion", method = RequestMethod.GET)
    public String HacerTransaccion() {
        return "transaction";
    }

    /*
    Con Transactional Spring automáticamente detecta una excepción y realiza un rollback, es decir,
    deshace cualquier cambio realizado en la base de datos durante la transacción.
     */
    @Transactional
    @PostMapping
    @RequestMapping(value = "/transactiondone", method = RequestMethod.POST)
    public String ResultadoTransaccion(HttpServletRequest request){
        long numero_tarjeta = Long.parseLong(request.getParameter("numero_tarjeta"));
        int numero_referencia = Integer.parseInt(request.getParameter("numero_referencia"));
        float valor_compra = Float.parseFloat(request.getParameter("valor_compra"));
        String direccion_compra = request.getParameter("direccion_compra");
        System.out.println("Requests: " + numero_referencia + " " + numero_tarjeta + " " + valor_compra + " " + direccion_compra);

        List<Card> cardList = (List<Card>) PSIMPLcard.cardRepository.findAll();
        Set<Long> usedCardNumbers = new HashSet<>();
        HashMap<Long, Boolean> hashCardNumber_Enrolled = new HashMap<>();
        for (int i = 0; i < cardList.size(); i++) {
            hashCardNumber_Enrolled.put(cardList.get(i).getNumero_tarjeta(), cardList.get(i).isEnrolada());
            usedCardNumbers.add(cardList.get(i).getNumero_tarjeta());
        }

        if(usedCardNumbers.contains(numero_tarjeta) && hashCardNumber_Enrolled.get(numero_tarjeta)){
            PSIMPLtransaction.transactionRepository.save(new Transaction(numero_tarjeta, numero_referencia, valor_compra, direccion_compra));
            System.out.println("00, Compra exitosa");
            estadoTransaccion = "Aprobada";
            //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de validación debe estar entre 1 y 100.");
            return "redirect:/tarjeta/jsontransaction"; // Redirecciona a la página de tarjetas
        }
        else if(hashCardNumber_Enrolled.get(numero_tarjeta) == null){
            estadoTransaccion = "Rechazada";
            throw new CustomException("01, Tarjeta no existe");
        }
        else {
            estadoTransaccion = "Rechazada";
            throw new CustomException("02, Tarjeta no enrolada");
        }
    }

    @GetMapping
    @RequestMapping(value = "/anulartransaccion", method = RequestMethod.GET)
    public String AnularTransaccion() {
        return "cancel-transaction";
    }

    //@DeleteMapping("/{numero_referencia}")
    @RequestMapping(value = "/transactioncanceled", method = RequestMethod.POST)

    public ResponseEntity<?> TransaccionAnulada(HttpServletRequest request) {
        int numero_tarjeta = Integer.parseInt(request.getParameter("numero_tarjeta"));
        int numero_referencia = Integer.parseInt(request.getParameter("numero_referencia"));
        float valor_compra = Float.parseFloat(request.getParameter("valor_compra"));
        Transaction transaction = PSIMPLtransaction.transactionRepository.findByNumeroReferencia(numero_referencia);

        List<Transaction> transactionList = (List<Transaction>) PSIMPLtransaction.transactionRepository.findAll();
        Set<Integer> referenceList = new HashSet<>();
        for (int i = 0; i < transactionList.size(); i++) {
            referenceList.add(transactionList.get(i).getNumero_referencia());
        }

        // Código de respuesta (00, 01, 02), mensaje (Compra anulada, numero de
        // referencia inválido, No se puede anular transacción), número de referencia

        if (!referenceList.contains(numero_referencia)) {
            //  return ResponseEntity.notFound().build();
            throw new CustomException(String.format("01, Numero de referencia invalido, %d", numero_referencia));
        }
        else {
            LocalDateTime fechaActual = LocalDateTime.now();
            LocalDateTime fechaCompra = transaction.getFecha_compra();
            Duration duracion = Duration.between(fechaCompra, fechaActual);
            long minutosTranscurridos = duracion.toMinutes();

            if (minutosTranscurridos > 5) {
                throw new CustomException(String.format("02, No se puede anular la transaccion después de 5 minutos, %d", numero_referencia));
            }
            else{
                System.out.printf("00, Compra anulada, %d\n", numero_referencia);
                PSIMPLtransaction.transactionRepository.deleteByNumeroReferencia(numero_referencia);  // Anula la compra, es decir, la elimina de la base de datos
                return ResponseEntity.ok().build();
            }
        }
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