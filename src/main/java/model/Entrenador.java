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
    private String nombre;

    @Column(name = "last_name", nullable = false, length = 100)
    private String apellido;

    @Column(name = "nationality")
    private String nacionalidad;

    // Apuntamos al atributo entrenador en el propietario de la relacion (equipo)
    @OneToOne(mappedBy = "entrenador")
    private Equipo equipo;

    // Constructores, Getters y Setters

    public Entrenador() {
    }

    public Entrenador(String nombre, String apellido, String nacionalidad, Equipo equipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nacionalidad = nacionalidad;
        this.equipo = equipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + "("+ id + ") \n"
                + nacionalidad + "\n" + equipo;
    }
}