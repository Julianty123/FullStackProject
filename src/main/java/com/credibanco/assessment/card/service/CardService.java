package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.model.Card;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface CardService {

    public List<Card> consultarTarjetas();

    // void actualizarTarjeta(int numero_validacion, long numero_tarjeta);

    public void crearPersona(Card card) throws ParseException;

    public Card modificarTarjeta(Card card);

    public Card buscarTarjeta(int id);

    public void eliminarPersona(int id);

}
