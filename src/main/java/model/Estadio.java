package model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Clase que define un Estadio
 * @author mati
 */
@Entity
@Table(name = "stadiums")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stadium_name", nullable = false, length = 100)
    private String nombreEstadio;

    @Column(name = "city")
    private String ciudad;

    @Column(name = "capacity")
    private int capacidad;

    @OneToOne(mappedBy = "estadio")
    private Equipo equipo;

    // Constructor, Getters y Setters

    public Estadio() {
    }

    public Estadio(String nombreEstadio, String ciudad, int capacidad, Equipo equipo) {
        this.nombreEstadio = nombreEstadio;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.equipo = equipo;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEstadio() {
        return nombreEstadio;
    }

    public void setNombreEstadio(String nombreEstadio) {
        this.nombreEstadio = nombreEstadio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Estadio estadio = (Estadio) o;
        return Objects.equals(id, estadio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return  id +
                " - " + nombreEstadio + ", " + ciudad +
                "\nCapacidad: " + capacidad +
                "\nEquipo: " + equipo;
    }
}
