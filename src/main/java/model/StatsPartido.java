package model;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Clase que guarda las Estadisticas personales de un jugador en un partido
 * @author mati
 */
@Entity
@Table(name = "player_match_stats")
public class StatsPartido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELACIONES

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Partido partido;

    // ATRIBUTOS DE RENDIMIENTO

    @Column(name = "attacks_attempted")
    private int ataques;

    @Column(name = "points_scored")
    private int puntos;

    @Column(name = "serves_total")
    private int saques;

    @Column(name = "service_aces")
    private int aces;

    @Column(name = "sets_attempted")
    private int colocacionesIntentadas;

    @Column(name = "sets_successful")
    private int colocacionesConseguidas;

    @Column(name = "blocks_attempted")
    private int bloqueosIntentados;

    @Column(name = "blocks_successful")
    private int bloqueosConseguidos;

    @Column(name = "receptions_successful")
    private int recepcionesConseguidas;

    @Column(name = "receptions_attempted")
    private int recepcionesIntentadas;

    // Constructores, Getters y Setters


    public StatsPartido() {
    }

    // Do i really need a constructor like this? Looks bad. Feels bad.
    public StatsPartido(Jugador jugador, Partido partido, int ataques, int puntos,
                   int saques, int aces, int colocacionesIntentadas, int colocacionesConseguidas,
                   int bloqueosIntentados, int bloqueosConseguidos,
                   int recepcionesConseguidas, int recepcionesIntentadas) {
        this.jugador = jugador;
        this.partido = partido;
        this.ataques = ataques;
        this.puntos = puntos;
        this.saques = saques;
        this.aces = aces;
        this.colocacionesIntentadas = colocacionesIntentadas;
        this.colocacionesConseguidas = colocacionesConseguidas;
        this.bloqueosIntentados = bloqueosIntentados;
        this.bloqueosConseguidos = bloqueosConseguidos;
        this.recepcionesConseguidas = recepcionesConseguidas;
        this.recepcionesIntentadas = recepcionesIntentadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public int getAtaques() {
        return ataques;
    }

    public void setAtaques(int ataques) {
        this.ataques = ataques;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getSaques() {
        return saques;
    }

    public void setSaques(int saques) {
        this.saques = saques;
    }

    public int getAces() {
        return aces;
    }

    public void setAces(int aces) {
        this.aces = aces;
    }

    public int getColocacionesIntentadas() {
        return colocacionesIntentadas;
    }

    public void setColocacionesIntentadas(int colocacionesIntentadas) {
        this.colocacionesIntentadas = colocacionesIntentadas;
    }

    public int getColocacionesConseguidas() {
        return colocacionesConseguidas;
    }

    public void setColocacionesConseguidas(int colocacionesConseguidas) {
        this.colocacionesConseguidas = colocacionesConseguidas;
    }

    public int getBloqueosIntentados() {
        return bloqueosIntentados;
    }

    public void setBloqueosIntentados(int bloqueosIntentados) {
        this.bloqueosIntentados = bloqueosIntentados;
    }

    public int getBloqueosConseguidos() {
        return bloqueosConseguidos;
    }

    public void setBloqueosConseguidos(int bloqueosConseguidos) {
        this.bloqueosConseguidos = bloqueosConseguidos;
    }

    public int getRecepcionesConseguidas() {
        return recepcionesConseguidas;
    }

    public void setRecepcionesConseguidas(int recepcionesConseguidas) {
        this.recepcionesConseguidas = recepcionesConseguidas;
    }

    public int getRecepcionesIntentadas() {
        return recepcionesIntentadas;
    }

    public void setRecepcionesIntentadas(int recepcionesIntentadas) {
        this.recepcionesIntentadas = recepcionesIntentadas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StatsPartido that = (StatsPartido) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return  jugador.toStringStats() +
                "\nAtaques: " + ataques +
                "\nPuntos: " + puntos +
                "\nSaques: " + saques +
                "\nAces: " + aces +
                "\nColocaciones Intentadas: " + colocacionesIntentadas +
                "\nColocaciones Conseguidas: " + colocacionesConseguidas +
                "\nBloqueos Intentados: " + bloqueosIntentados +
                "\nBloqueos Conseguidos: " + bloqueosConseguidos +
                "\nRecepciones Intentadas: " + recepcionesIntentadas +
                "\nRecepciones Conseguidas: " + recepcionesConseguidas;
    }
}
