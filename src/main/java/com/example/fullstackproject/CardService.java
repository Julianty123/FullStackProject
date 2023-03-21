package com.example.fullstackproject;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface CardService {

    public List<Card> consultarPersonas();

    public Card crearPersona(Card card) throws ParseException;

    public Card modificarPersona(Card card);

    public Card buscarPersona(int id);

    public void eliminarPersona(int id);

}
