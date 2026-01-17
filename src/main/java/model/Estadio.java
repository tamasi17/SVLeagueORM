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


}
