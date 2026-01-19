package model;

import jakarta.persistence.*;

import java.util.Objects;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getPresupuestoAportado() {
        return presupuestoAportado;
    }

    public void setPresupuestoAportado(double presupuestoAportado) {
        this.presupuestoAportado = presupuestoAportado;
    }

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(id, sponsor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return  nombreComercial + " (" + sector + ")";
    }
}
