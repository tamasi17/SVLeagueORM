package model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
}
