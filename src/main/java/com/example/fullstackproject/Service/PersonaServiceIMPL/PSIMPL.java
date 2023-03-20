package com.example.fullstackproject.Service.PersonaServiceIMPL;

import com.example.fullstackproject.Entity.Persona;
import com.example.fullstackproject.Repository.PersonaRepo;
import com.example.fullstackproject.Service.PersonaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class PSIMPL implements PersonaService {

    public static int ultima_matricula;
    private final PersonaRepo personaRepo;

    @Autowired
    public PSIMPL(PersonaRepo personaRepo) {
        this.personaRepo = personaRepo;
    }

    @Override
    public List<Persona> consultarPersonas() {
        return (List<Persona>) this.personaRepo.findAll();
    }

    @Override
    public Persona crearPersona(Persona nuevaPersona) throws ParseException {
        // Crear una instancia de Persona con los datos que se desean insertar en la base de datos
        // Persona nuevaPersona = new Persona();
        // ThreadLocalRandom.current().nextInt(100)
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre: ");
        String nombre = sc.nextLine();
        nuevaPersona.setNombre(nombre);

        System.out.print("Ingrese el porcentaje de comisión: ");
        double comision = sc.nextDouble();
        nuevaPersona.setPorcentaje_comision(comision);

        System.out.print("Ingrese la fecha de admisión (en formato dd/mm/yyyy): ");
        String fechaString = sc.next();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaAdmision = sdf.parse(fechaString);
        nuevaPersona.setFecha_admision(fechaAdmision);

        System.out.print("¿La persona está de vacaciones? (true/false): ");
        boolean vacaciones = sc.nextBoolean();
        nuevaPersona.setDe_vacaciones(vacaciones);

        System.out.println("Persona creada: " + nuevaPersona.getMatricula() + " "
                + nuevaPersona.getNombre() + " " + nuevaPersona.getPorcentaje_comision() + " "
                + nuevaPersona.getFecha_admision() + " " + nuevaPersona.isDe_vacaciones());

        nuevaPersona = this.personaRepo.save(nuevaPersona);
        ultima_matricula = this.personaRepo.save(nuevaPersona).getMatricula();
        return nuevaPersona;
    }

    @Override
    public Persona modificarPersona(Persona persona) {
        return this.personaRepo.save(persona);
    }

    @Override
    public Persona buscarPersona(int id) {
        return this.personaRepo.findById(id).get(); // repo.findById(id).orElse(null)
    }

    @Override
    public void eliminarPersona(int id) {
        this.personaRepo.deleteById(id);
    }
}
