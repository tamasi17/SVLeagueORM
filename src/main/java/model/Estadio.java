package model;

import jakarta.persistence.*;

import java.time.LocalDate;
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
    private String nombre;

    @Column(name = "city")
    private String ciudad;

    @Column(name = "capacity")
    private int capacidad;

    @OneToOne(mappedBy = "estadio")
    private Equipo equipo;

    // Constructor, Getters y Setters

    public Estadio() {
    }

    public Estadio(String nombre, String ciudad, int capacidad, Equipo equipo) {
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return capacidad == estadio.capacidad && Objects.equals(id, estadio.id) && Objects.equals(nombre, estadio.nombre) && Objects.equals(ciudad, estadio.ciudad) && Objects.equals(equipo, estadio.equipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, ciudad, capacidad, equipo);
    }

    @Override
    public String toString() {
        return  id +
                " - " + nombre + ", " + ciudad +
                "\nCapacidad: " + capacidad +
                "\nEquipo: " + equipo;
    }
}
