package com.example.fullstackproject.Controller;

import com.example.fullstackproject.Entity.Persona;
import com.example.fullstackproject.Service.PersonaServiceIMPL.PSIMPL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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


@Controller // @RestController
@RequestMapping("/tarjeta")
public class Controlador {
    private final ResourceLoader resourceLoader;
    public Controlador(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private PSIMPL psimpl;


    /*@GetMapping("/error")
    public ResponseEntity<String> handleErrors(ServletException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ha ocurrido un error: " + ex.getMessage());
    }*/

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }
    /*
    @GetMapping("/index")
    public String home(Model model) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/public/index.html");
        model.addAttribute("content", new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
        return "index.html";
    }
     */

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
    @RequestMapping(value = "/buscartarjetas", method = RequestMethod.GET)
    public ResponseEntity<?> BuscarTarjetas() {
        List<Persona> listaPersona = this.psimpl.consultarPersonas();
        System.out.println("Tarjetas buscadas!");
        return ResponseEntity.ok(listaPersona);
    }

    @PostMapping
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    public ResponseEntity<?> CrearTarjeta() throws ParseException {
        Persona nuevaPersona = this.psimpl.crearPersona(new Persona()); // Guardar la entidad en la base de datos
        System.out.println("ultima matricula: " + PSIMPL.ultima_matricula);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPersona); // Devuelve un estado 201 de creación exitosa
    }

    @GetMapping
    @RequestMapping(value = "/consultar/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> ConsultarTarjeta(@PathVariable int id) {
        Persona persona = this.psimpl.buscarPersona(id);
        return ResponseEntity.ok(persona);
    }

    @PostMapping
    @RequestMapping(value = "/enrolar", method = RequestMethod.POST)
    public ResponseEntity<?> enrolarTarjeta() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Persona());
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

/*
USE jugos; // Seleccionar la base de datos
// PRIMARY KEY significa que el valor es unico y no se puede repetir
CREATE TABLE usuarios (
    matricula INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    porcentaje_comision DECIMAL(5,2),
    fecha_admision DATE,
    de_vacaciones BOOLEAN
);

INSERT INTO usuarios (nombre, porcentaje_comision, fecha_admision, de_vacaciones)
VALUES ('Hola NENES', 0.10, '2022-03-21', true);

DELETE FROM usuarios WHERE matricula = 2;
SELECT * FROM usuarios;
TRUNCATE TABLE usuarios;
 */
