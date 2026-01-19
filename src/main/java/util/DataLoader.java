package util;

import dao.*;
import jakarta.persistence.EntityManager;
import model.*;

import java.time.LocalDate;
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
        panasonicArena.setCiudad("Osaka");
        daoJpaEstadio.save(panasonicArena);

        Entrenador tuomas = new Entrenador();
        tuomas.setNombre("Tuomas");
        tuomas.setApellido("Sammelvuo");
        tuomas.setNacionalidad("Finland");
        daoJpaEntrenador.save(tuomas);


        Sponsor panasonic = new Sponsor();
        panasonic.setNombreComercial("Panasonic");
        panasonic.setSector("Tech");
        Set<Sponsor> sponsors = new HashSet<>();
        sponsors.add(panasonic);
        daoJpaSponsor.save(panasonic);

        Equipo bluteon = createEquipo("Osaka Bluteon", "Osaka", panasonicArena,
                tuomas, sponsors, LocalDate.ofYearDay(1951,12), "bluteon.com");
        Set<Equipo> equipos = new HashSet<>();
        equipos.add(bluteon);

        // Inicializar Set<Entidad> en constructores para usar directamente addJugador o addSponsor?
        // Necesitarás helpers para relaciones bidireccionales
        panasonic.setEquipos(equipos);
        daoJpaSponsor.update(panasonic);

        Jugador nishida = new Jugador();
        nishida.setName("Yuji");
        nishida.setApellido("Nishida");
        nishida.setDorsal(11);
        nishida.setPosicion(Posicion.HITTER);
        nishida.setEquipo(bluteon);
        Set<Jugador> jugadores = new HashSet<>();
        jugadores.add(nishida);
        daoJpaJugador.save(nishida);


    }


    private Equipo createEquipo(String nombre, String ciudad, Estadio estadio,
                                Entrenador entrenador, Set<Sponsor> sponsors, LocalDate foundation, String web){
        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCiudad(ciudad);
        equipo.setEstadio(estadio);
        equipo.setEntrenador(entrenador);
        equipo.setSponsors(sponsors);
        equipo.setFechaFundacion(foundation);
        equipo.setWeb(web);

        daoJpaEquipo.save(equipo);

        return equipo;
    }
}
