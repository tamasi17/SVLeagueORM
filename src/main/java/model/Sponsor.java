package model;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Clase que define un patrocinador
 * @author mati
 */
@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name", nullable = false)
    private String nombreComercial;

    @Column(name = "industry_sector")
    private String sector;

    @Column(name = "contribution_amount")
    private double presupuestoAportado;

    // Relación ManyToMany, Equipo es propietario de la relacion
    @ManyToMany(mappedBy = "sponsors")
    private Set<Equipo> equipos;

    // Constructores, Getters y Setters


    public Sponsor() {
    }

    public Sponsor(String nombreComercial, String sector, double presupuestoAportado, Set<Equipo> equipos) {
        this.nombreComercial = nombreComercial;
        this.sector = sector;
        this.presupuestoAportado = presupuestoAportado;
        this.equipos = equipos;
    }


}
