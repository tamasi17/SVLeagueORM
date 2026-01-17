package model;

import jakarta.persistence.*;

@Entity
@Table(name = "coaches")
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String nombre;

    @Column(name = "name", nullable = false, length = 100)
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


}