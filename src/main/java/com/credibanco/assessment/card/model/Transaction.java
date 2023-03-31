package com.credibanco.assessment.card.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private long numero_tarjeta;
    private int numero_referencia; // Referencia de la compra (6 digitos)
    private float valor_compra;    // Valor total de la compra
    private String direccion_compra;
    private LocalDateTime fecha_compra;

    public Transaction() {

    }

    public Transaction(long numero_tarjeta, int numero_referencia, float valor_compra, String direccion_compra) {
        this.numero_tarjeta = numero_tarjeta;
        this.numero_referencia = numero_referencia;
        this.valor_compra = valor_compra;
        this.direccion_compra = direccion_compra;
        this.fecha_compra = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(long numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }

    public int getNumero_referencia() {
        return numero_referencia;
    }

    public void setNumero_referencia(int numero_referencia) {
        this.numero_referencia = numero_referencia;
    }

    public double getValor_compra() {
        return valor_compra;
    }

    public void setValor_compra(float valor_compra) {
        this.valor_compra = valor_compra;
    }

    public String getDireccion_compra() {
        return direccion_compra;
    }

    public void setDireccion_compra(String direccion_compra) {
        this.direccion_compra = direccion_compra;
    }

    public LocalDateTime getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDateTime fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", numero_tarjeta=" + numero_tarjeta +
                ", numero_referencia=" + numero_referencia +
                ", valor_compra=" + valor_compra +
                ", direccion_compra='" + direccion_compra + '\'' +
                '}';
    }
}
