package util;

import dao.*;
import jakarta.persistence.EntityManager;
import model.*;

import java.util.Arrays;
import java.util.Set;

/**
 * Clase para generar la informacion que se utiliza en Simulacion o Main
 * @author mati
 */
public class DataLoader {

    private final EntityManager entityManager;
    private DaoJpaEquipo daoJpaEquipo;
    private DaoJpaJugador daoJpaJugador;
    private DaoJpaEntrenador daoJpaEntrenador;
    private DaoJpaEstadio daoJpaEstadio;
    private DaoJpaSponsor daoJpaSponsor;


    public DataLoader(EntityManager em) {
        this.entityManager = em;
        this.daoJpaEquipo = new DaoJpaEquipo(em);
        this.daoJpaJugador = new DaoJpaJugador(em);
        this.daoJpaEntrenador = new DaoJpaEntrenador(em);
        this.daoJpaSponsor = new DaoJpaSponsor(em);
        this.daoJpaEstadio = new DaoJpaEstadio(em);
    }

    public void loadTest(){
        System.out.println("Test: Info Loading");

        Estadio panasonicArena = new Estadio();
        panasonicArena.setNombre("Panasonic Arena");

        Entrenador tuomas = new Entrenador();
        tuomas.setNombre("Tuomas");
        tuomas.setApellido("Sammelvuo");

        Jugador nishida = new Jugador();
        nishida.setName("Yuji");
        nishida.setApellido("Nishida");
        nishida.setDorsal(11);
        nishida.setPosicion(Posicion.HITTER);

        Sponsor panasonic = new Sponsor();
        panasonic.setNombreComercial("Panasonic");

        createEquipo("Osaka Bluteon", "Osaka", panasonicArena, tuomas, nishida, panasonic);

    }


    private Equipo createEquipo(String nombre, String ciudad, Estadio estadio,
                                Entrenador entrenador, Jugador jugador, Sponsor sponsor){
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCiudad(ciudad);
        equipo.setEstadio(estadio);
        equipo.setEntrenador(entrenador);
        equipo.addSponsor(sponsor);
        equipo.addJugador(jugador);

        daoJpaEquipo.save(equipo);

        return equipo;
    }
}
