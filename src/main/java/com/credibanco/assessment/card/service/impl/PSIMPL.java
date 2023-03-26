package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.CardRepository;
import com.credibanco.assessment.card.model.Card;
import com.credibanco.assessment.card.service.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PSIMPL implements CardService {

    public static int ultima_tarjeta;
    public static CardRepository cardRepository;

    @Autowired
    public PSIMPL(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> consultarTarjetas() {
        return (List<Card>) this.cardRepository.findAll();
    }



    @Override
    public void crearPersona(Card nuevaCard) {
        // Crear una instancia de Persona con los datos que se desean insertar en la base de datos
        // Persona nuevaPersona = new Persona();
        /*Scanner sc = new Scanner(System.in);
        nuevaCard.setNumero_validacion();
        System.out.println("Numero de validacion: " + nuevaCard.getNumero_validacion());

        System.out.print("Numero tarjeta: ");
        long numero = sc.nextLong();
        nuevaCard.setNumero_tarjeta(numero);

        System.out.print("Titular: ");
        String comision = sc.next();
        nuevaCard.setTitular(comision);

        System.out.print("Cedula: ");
        long cedula = sc.nextLong();
        nuevaCard.setCedula(cedula);

        System.out.print("Tipo: ");
        String tipo = sc.next();
        nuevaCard.setTipo(tipo);

        System.out.print("Telefono: ");
        int telefono = sc.nextInt();
        nuevaCard.setCedula(telefono);*/

        nuevaCard = this.cardRepository.save(nuevaCard);
        // ultima_tarjeta = this.cardRepository.save(nuevaCard).getMatricula();
    }

    @Override
    public Card modificarTarjeta(Card card) {
        return this.cardRepository.save(card);
    }

    @Override
    public Card buscarTarjeta(int id) {
        return this.cardRepository.findById(id).get(); // repo.findById(id).orElse(null)
    }

    @Override
    public void eliminarPersona(int id) {
        this.cardRepository.deleteById(id);
    }
}
