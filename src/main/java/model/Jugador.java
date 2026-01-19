package model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getDorsal() {
        return dorsal;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public List<StatsPartido> getStatsPartidos() {
        return statsPartidos;
    }

    public void setStatsPartidos(List<StatsPartido> statsPartidos) {
        this.statsPartidos = statsPartidos;
    }

    public void addEstadistica(StatsPartido stat) {
        this.statsPartidos.add(stat);
        stat.setJugador(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(id, jugador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return name + " " + apellido + " #" + dorsal +
                "\n" + fechaNacimiento + ", " + nacionalidad +
                "\n" + posicion + ", " + equipo;
    }

    public String toStringStats(){
        return "#" + dorsal + ", " + name + " " + apellido +
                "\n" + posicion + ", " + equipo;
    }
}
