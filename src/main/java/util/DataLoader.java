package util;

import dao.*;
import jakarta.persistence.EntityManager;
import model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase para generar la información que se utiliza en Simulacion o Main
 * @author mati
 */
public class DataLoader {

    private final EntityManager entityManager;
    private final DaoJpaEntrenador daoJpaEntrenador;
    private final DaoJpaEquipo daoJpaEquipo;
    private final DaoJpaEstadio daoJpaEstadio;
    private final DaoJpaJugador daoJpaJugador;
    private final DaoJpaSponsor daoJpaSponsor;


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
        Set<Jugador> jugadores = new HashSet<>();
        jugadores.add(nishida);

        Sponsor panasonic = new Sponsor();
        panasonic.setNombreComercial("Panasonic");
        Set<Sponsor> sponsors = new HashSet<>();
        sponsors.add(panasonic);

        Equipo bluteon = createEquipo("Osaka Bluteon", "Osaka", panasonicArena, tuomas, jugadores, sponsors);
        Set<Equipo> equipos = new HashSet<>();
        equipos.add(bluteon);

        // Inicializar Set<Entidad> en constructores para usar directamente addJugador o addSponsor?
        // Necesitarás helpers para relaciones bidireccionales
        panasonic.setEquipos(equipos);
        nishida.setEquipo(bluteon);

    }


    private Equipo createEquipo(String nombre, String ciudad, Estadio estadio,
                                Entrenador entrenador, Set<Jugador> jugadores, Set<Sponsor> sponsors){
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCiudad(ciudad);
        equipo.setEstadio(estadio);
        equipo.setEntrenador(entrenador);
        equipo.setSponsors(sponsors);
        equipo.setJugadores(jugadores);

        daoJpaEquipo.save(equipo);

        return equipo;
    }
}
