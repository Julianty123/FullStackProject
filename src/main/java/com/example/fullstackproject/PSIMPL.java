package com.example.fullstackproject;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class PSIMPL implements CardService {

    public static int ultima_matricula;
    private final CardRepository cardRepository;

    @Autowired
    public PSIMPL(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> consultarPersonas() {
        return (List<Card>) this.cardRepository.findAll();
    }

    @Override
    public Card crearPersona(Card nuevaCard) throws ParseException {
        // Crear una instancia de Persona con los datos que se desean insertar en la base de datos
        // Persona nuevaPersona = new Persona();
        // ThreadLocalRandom.current().nextInt(100)
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el nombre: ");
        String nombre = sc.nextLine();
        nuevaCard.setNombre(nombre);

        System.out.print("Ingrese el porcentaje de comisión: ");
        double comision = sc.nextDouble();
        nuevaCard.setPorcentaje_comision(comision);

        System.out.print("Ingrese la fecha de admisión (en formato dd/mm/yyyy): ");
        String fechaString = sc.next();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaAdmision = sdf.parse(fechaString);
        nuevaCard.setFecha_admision(fechaAdmision);

        System.out.print("¿La persona está de vacaciones? (true/false): ");
        boolean vacaciones = sc.nextBoolean();
        nuevaCard.setDe_vacaciones(vacaciones);

        System.out.println("Persona creada: " + nuevaCard.getMatricula() + " "
                + nuevaCard.getNombre() + " " + nuevaCard.getPorcentaje_comision() + " "
                + nuevaCard.getFecha_admision() + " " + nuevaCard.isDe_vacaciones());

        nuevaCard = this.cardRepository.save(nuevaCard);
        ultima_matricula = this.cardRepository.save(nuevaCard).getMatricula();
        return nuevaCard;
    }

    @Override
    public Card modificarPersona(Card card) {
        return this.cardRepository.save(card);
    }

    @Override
    public Card buscarPersona(int id) {
        return this.cardRepository.findById(id).get(); // repo.findById(id).orElse(null)
    }

    @Override
    public void eliminarPersona(int id) {
        this.cardRepository.deleteById(id);
    }
}
