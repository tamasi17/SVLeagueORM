package model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "coaches")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String nombreEntrenador;

    @Column(name = "last_name", nullable = false, length = 100)
    private String apellidoEntrenador;

    @Column(name = "nationality")
    private String nacionalidad;

    // Apuntamos al atributo entrenador en el propietario de la relacion (equipo)
    @OneToOne(mappedBy = "entrenador")
    private Equipo equipo;

    // Constructores, Getters y Setters

    public Entrenador() {
    }

    public Entrenador(String nombreEntrenador, String apellidoEntrenador, String nacionalidad, Equipo equipo) {
        this.nombreEntrenador = nombreEntrenador;
        this.apellidoEntrenador = apellidoEntrenador;
        this.nacionalidad = nacionalidad;
        this.equipo = equipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }

    public String getApellidoEntrenador() {
        return apellidoEntrenador;
    }

    public void setApellidoEntrenador(String apellidoEntrenador) {
        this.apellidoEntrenador = apellidoEntrenador;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entrenador that = (Entrenador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return nombreEntrenador + " " + apellidoEntrenador + "("+ id + ") \n"
                + nacionalidad + "\n" + equipo;
    }
}