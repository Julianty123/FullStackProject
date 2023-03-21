package com.example.fullstackproject;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "usuarios") // Nombre de la tabla en la base de datos a la que esta asociada
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula") // Nombre de la columna en la base de datos a la que esta asociada

    private int matricula;
    private String nombre;
    private double porcentaje_comision;
    private Date fecha_admision;
    private boolean de_vacaciones;

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPorcentaje_comision() {
        return porcentaje_comision;
    }

    public void setPorcentaje_comision(double porcentaje_comision) {
        this.porcentaje_comision = porcentaje_comision;
    }

    public Date getFecha_admision() {
        return fecha_admision;
    }

    public void setFecha_admision(Date fecha_admision) {
        this.fecha_admision = fecha_admision;
    }

    public boolean isDe_vacaciones() {
        return de_vacaciones;
    }

    public void setDe_vacaciones(boolean de_vacaciones) {
        this.de_vacaciones = de_vacaciones;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
