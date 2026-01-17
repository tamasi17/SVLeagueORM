package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Clase que define un Jugador.
 * En la base de datos la tabla se llama players.
 * @author mati
 */
@Entity
@Table(name = "players")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String apellido;

    @Column(name = "birth_date")
    private LocalDate fechaNacimiento;

    @Column(name = "number")
    private int dorsal;

    @Column(name = "nationality")
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Posicion posicion;

    // Relación ManyToOne: Muchos jugadores pertenecen a un equipo
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "team_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_PLAYER_TEAM")
    )
    private Equipo equipo;

    // Relación OneToMany: Un jugador tiene muchas estadísticas (en distintos partidos)
    @OneToMany(
            mappedBy = "jugador",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<StatsPartido> statsPartidos;


    // Constructor, Getters y Setters


    public Jugador() {
    }

    public Jugador(String name, String apellido, LocalDate fechaNacimiento,
                   int dorsal, String nacionalidad, Posicion posicion,
                   Equipo equipo, List<StatsPartido> statsPartidos) {
        this.name = name;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.dorsal = dorsal;
        this.nacionalidad = nacionalidad;
        this.posicion = posicion;
        this.equipo = equipo;
        this.statsPartidos = statsPartidos;
    }

    public int getEdad() {
        if (this.fechaNacimiento != null) {
            return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
        }
        return 0;
    }



}
