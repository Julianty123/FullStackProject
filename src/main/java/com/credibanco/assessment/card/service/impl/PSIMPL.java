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
    public static CardRepository cardRepository;

    @Autowired
    public PSIMPL(CardRepository cardRepository) {
        PSIMPL.cardRepository = cardRepository;
    }

    @Override
    public List<Card> consultarTarjetas() {
        return (List<Card>) cardRepository.findAll();
    }

    @Override
    public void crearPersona(Card nuevaCard) {
        cardRepository.save(nuevaCard);    // Guarda la entidad en la base de datos
    }

    @Override
    public Card modificarTarjeta(Card card) {
        return cardRepository.save(card);
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
