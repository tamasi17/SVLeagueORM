package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que define un Partido
 * @author mati
 */
@Entity
@Table(name = "matches")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_date")
    private LocalDateTime fecha;

    // Relación ManyToOne: muchos partidos se juegan en un estadio
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stadium_id")
    private Estadio lugar;

    // Relación ManyToOne: El equipo que juega en su casa
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Equipo equipoLocal;

    // Relación ManyToOne: El equipo que viene de fuera
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Equipo equipoVisitante;

    // Relación OneToMany: Un partido genera muchas estadísticas (una por jugador)
    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatsPartido> estadisticas;

    // Constructores, Getters y Setters


    public Partido() {
    }

    public Partido(LocalDateTime fecha, Estadio lugar, Equipo equipoLocal,
                   Equipo equipoVisitante, List<StatsPartido> estadisticas) {
        this.fecha = fecha;
        this.lugar = lugar;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.estadisticas = estadisticas;
    }


}
