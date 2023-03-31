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
public class PSIMPLcard {
    public static CardRepository cardRepository;

    @Autowired
    public PSIMPLcard(CardRepository cardRepository) {
        PSIMPLcard.cardRepository = cardRepository;
    }

    public static List<Card> consultarTarjetas() {
        return (List<Card>) cardRepository.findAll();
    }

    public static void crearPersona(Card nuevaCard) {
        cardRepository.save(nuevaCard);    // Guarda la entidad en la base de datos
    }

    public Card modificarTarjeta(Card card) {
        return cardRepository.save(card);
    }

    public Card buscarTarjeta(int id) {
        return cardRepository.findById(id).get(); // repo.findById(id).orElse(null)
    }

    public void eliminarPersona(int id) {
        cardRepository.deleteById(id);
    }
}
