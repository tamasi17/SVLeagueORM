package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "home_sets_won")
    private int setsLocal;

    @Column(name = "away_sets_won")
    private int setsVisitante;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estadio getLugar() {
        return lugar;
    }

    public void setLugar(Estadio lugar) {
        this.lugar = lugar;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public int getSetsLocal() {
        return setsLocal;
    }

    public void setSetsLocal(int setsLocal) {
        this.setsLocal = setsLocal;
    }

    public int getSetsVisitante() {
        return setsVisitante;
    }

    public void setSetsVisitante(int setsVisitante) {
        this.setsVisitante = setsVisitante;
    }

    public List<StatsPartido> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(List<StatsPartido> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public void addEstadistica(StatsPartido stats){
        this.estadisticas.add(stats);
        stats.setPartido(this);
    }


    public String getEncuentroFormateado() {
        return equipoLocal + " - " + equipoVisitante + "(" + fecha + ")";
    }

    public String getResultadoFormateado() {
        return equipoLocal + " " + setsLocal + " - " + setsVisitante + " " + equipoVisitante;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Partido partido = (Partido) o;
        return Objects.equals(id, partido.id) && Objects.equals(fecha, partido.fecha) && Objects.equals(lugar, partido.lugar) && Objects.equals(equipoLocal, partido.equipoLocal) && Objects.equals(equipoVisitante, partido.equipoVisitante) && Objects.equals(estadisticas, partido.estadisticas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fecha, lugar, equipoLocal, equipoVisitante, estadisticas);
    }

    @Override
    public String toString() {
        return getResultadoFormateado() +
                "\n" + lugar + " - " + fecha;
    }
}
