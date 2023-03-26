package com.credibanco.assessment.card.model;

public class CardFrontEnd extends Card {
    private String numero_tarjetaEnmascarado;

    public CardFrontEnd() {
    }

    public String getNumero_tarjetaEnmascarado() {
        return numero_tarjetaEnmascarado;
    }

    public void setNumero_tarjetaEnmascarado(String numero_tarjetaEnmascarado) {
        this.numero_tarjetaEnmascarado = numero_tarjetaEnmascarado;
    }
}
