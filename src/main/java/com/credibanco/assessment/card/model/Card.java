package com.credibanco.assessment.card.model;

import com.credibanco.assessment.card.service.impl.PSIMPL;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "cards") // Nombre de la tabla en la base de datos a la que esta asociada
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el valor de forma automática
    private int id;

    // @Column(name = "numero_validacion", unique = true) // Nombre de la columna en la base de datos a la que esta asociada
    private int numero_validacion;

    private long numero_tarjeta;

    private String titular;
    private long cedula;
    private String tipo;
    private int telefono;
    private boolean enrolada;

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero_validacion() {
        return this.numero_validacion;
    }

    public void setNumero_validacion() {
        this.numero_validacion = ThreadLocalRandom.current().nextInt(100);

        // Verifica si el numero de validacion ya existe en la base de datos
        List<Card> cardList = (List<Card>) PSIMPL.cardRepository.findAll();
        Set<Integer> usedValidationNumbers = new HashSet<>();
        for (int i = 0; i < cardList.size(); i++) {
            System.out.println(cardList.get(i).getNumero_validacion());
            usedValidationNumbers.add(cardList.get(i).getNumero_validacion());
        }
        while (usedValidationNumbers.contains(this.numero_validacion)) {
            System.out.printf("El numero de validacion ya existe (%d)\n", this.numero_validacion);
            this.numero_validacion = ThreadLocalRandom.current().nextInt(100);
            System.out.printf("Se ha generado un nuevo numero de validacion: %d\n", this.numero_validacion);
        }
    }

    public long getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(long numeroTarjeta) {
        this.numero_tarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean isEnrolada() {
        return enrolada;
    }

    public void setEnrolada(boolean enrolada) {
        this.enrolada = enrolada;
    }

    @Override
    public String toString() {
        return String.format(
                "Card[numero_validacion='%d', numero_tarjeta='%d', titular='%s', cedula='%d', tipo='%s', telefono='%d', enrolada='%b']\n",
                numero_validacion, numero_tarjeta, titular, cedula, tipo, telefono, enrolada);
    }
}

/*
public CardTest(int numeroValidacion, long numeroTarjeta, String titular, long cedula, String tipo, int telefono) {
        if (numeroValidacion < 1 || numeroValidacion > 100) {
            throw new IllegalArgumentException("El número de validación debe estar entre 1 y 100.");
        }
        this.numeroValidacion = numeroValidacion;
        if (String.valueOf(numeroTarjeta).length() < 16 || String.valueOf(numeroTarjeta).length() > 19) {
            throw new IllegalArgumentException("El número de tarjeta debe tener entre 16 y 19 dígitos.");
        }
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        if (String.valueOf(cedula).length() < 10 || String.valueOf(cedula).length() > 15) {
            throw new IllegalArgumentException("La cédula debe tener entre 10 y 15 caracteres.");
        }
        this.cedula = cedula;
        this.tipo = tipo;
        if (String.valueOf(telefono).length() != 10) {
            throw new IllegalArgumentException("El teléfono debe tener 10 dígitos.");
        }
        this.telefono = telefono;
    }
 */