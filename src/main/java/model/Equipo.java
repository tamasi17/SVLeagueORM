package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Clase que define un Equipo.
 * En la base de datos la tabla se llama teams.
 * @author mati
 */
@Entity
@Table(name = "teams")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "city")
    private String ciudad;

    @Column(name = "website")
    private String web;

    @Column(name = "foundation_date")
    private LocalDate fechaFundacion;


    // RELACIONES

    // Si borramos el equipo, borramos su estadio
    // Al cargar un equipo, quiero saber su estadio y entrenador de inmediato.
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    private Estadio estadio;

    // Si borramos equipo, el entrenador se mantiene en la db
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    private Entrenador entrenador;

    // Orphan removal, si borramos un jugador, se borra el resto de su info en el equipo
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Jugador> jugadores;

    // Si borramos el equipo, no se borra el sponsor (lo usan otros equipos)
    // Crea la tabla intermedia team_sponsors
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "team_sponsors", // Tabla intermedia
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "sponsor_id")
    )
    private Set<Sponsor> sponsors;

    // Constructores, Getters y Setters


    public Equipo() {
    }

    public Equipo(String nombre, String ciudad, String web,
                  LocalDate fechaFundacion, Estadio estadio, Entrenador entrenador,
                  Set<Jugador> jugadores, Set<Sponsor> sponsors) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.web = web;
        this.fechaFundacion = fechaFundacion;
        this.estadio = estadio;
        this.entrenador = entrenador;
        this.jugadores = jugadores;
        this.sponsors = sponsors;
    }


}
