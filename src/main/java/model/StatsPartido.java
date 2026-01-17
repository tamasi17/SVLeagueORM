package model;

import jakarta.persistence.*;

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
}
