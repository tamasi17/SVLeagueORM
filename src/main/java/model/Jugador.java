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
    private String nombreJugador;

    @Column(name = "last_name", nullable = false)
    private String apellidoJugador;

    @Column(name = "birth_date")
    private LocalDate fechaNacimiento;

    @Column(name = "number")
    private int dorsal;

    @Column(name = "nationality")
    private String nacionalidad;

    @Column(name = "isNew")
    private boolean esNuevo = false;


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

    public Jugador(String nombreJugador, String apellidoJugador, LocalDate fechaNacimiento,
                   int dorsal, String nacionalidad, Posicion posicion,
                   Equipo equipo, List<StatsPartido> statsPartidos) {
        this.nombreJugador = nombreJugador;
        this.apellidoJugador = apellidoJugador;
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

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getApellidoJugador() {
        return apellidoJugador;
    }

    public void setApellidoJugador(String apellidoJugador) {
        this.apellidoJugador = apellidoJugador;
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

    public boolean isEsNuevo() { return esNuevo; }

    public void setEsNuevo(boolean esNuevo) { this.esNuevo = esNuevo; }

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
        return Objects.equals(id, jugador.id) && Objects.equals(nombreJugador, jugador.nombreJugador) && Objects.equals(apellidoJugador, jugador.apellidoJugador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombreJugador, apellidoJugador);
    }

    @Override
    public String toString() {
        return nombreJugador + " " + apellidoJugador + " #" + dorsal +
                "\n" + fechaNacimiento + ", " + nacionalidad +
                "\n" + posicion + ", " + equipo;
    }

    public String toStringStats(){
        return "#" + dorsal + ", " + nombreJugador + " " + apellidoJugador +
                "\n" + posicion + ", " + equipo;
    }
}
