package util;

import dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.*;

import java.time.LocalDate;
import java.util.*;

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

    Map<Equipo, List<Jugador>> leagueData = new HashMap<>();

    public DataLoader(EntityManager em) {
        this.entityManager = em;
        this.daoJpaEquipo = new DaoJpaEquipo(em);
        this.daoJpaJugador = new DaoJpaJugador(em);
        this.daoJpaEntrenador = new DaoJpaEntrenador(em);
        this.daoJpaSponsor = new DaoJpaSponsor(em);
        this.daoJpaEstadio = new DaoJpaEstadio(em);
    }


    /**
     * Metodo que carga los datos de la liga para la simulacion dentro de una transaccion
     * @param leagueData
     * @param allSponsors
     */
    public void loadLeague(Map<Equipo, List<Jugador>> leagueData, List<Sponsor> allSponsors) {

        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();

            // Save all sponsors
            for (Sponsor s : allSponsors) {
                daoJpaSponsor.save(s);
            }

            // OUTER LOOP recorre equipos
            for (Map.Entry<Equipo, List<Jugador>> entry : leagueData.entrySet()) {

                Equipo currentTeam = entry.getKey();
                List<Jugador> roster = entry.getValue();

                // Save team dependencies (Estadio y Entrenador)
                daoJpaEstadio.save(currentTeam.getEstadio());
                daoJpaEntrenador.save(currentTeam.getEntrenador());

                // Save team
                daoJpaEquipo.save(currentTeam);
                System.out.println("Saved Team: " + currentTeam.getNombre());

                // INNER LOOP para asignar los jugadores de cada equipo

                for (Jugador p : roster) {

                    // Helper !!
                    currentTeam.addJugador(p);
                    // Save it
                    daoJpaJugador.save(p);
                }

                System.out.println("Succesfully loaded " + roster.size() +
                        " players for " + currentTeam.getNombre());

                daoJpaEquipo.update(currentTeam);
            }

            tx.commit();
            System.out.println("Data load completed succesfully");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


    public void loadTest() {
        System.out.println("Test: Info Loading");

        Estadio panasonicArena = new Estadio();
        panasonicArena.setNombre("Panasonic Arena");
        panasonicArena.setCiudad("Osaka");
        daoJpaEstadio.save(panasonicArena);

        Estadio asueArena = new Estadio();
        asueArena.setNombre("Asue Arena Osaka");
        asueArena.setCiudad("Osaka");
        daoJpaEstadio.save(asueArena);

        Entrenador tuomas = new Entrenador();
        tuomas.setNombre("Tuomas");
        tuomas.setApellido("Sammelvuo");
        tuomas.setNacionalidad("Finland");
        daoJpaEntrenador.save(tuomas);

        Entrenador lecat = new Entrenador();
        lecat.setNombre("Olivier");
        lecat.setApellido("Lecat");
        lecat.setNacionalidad("France");
        daoJpaEntrenador.save(lecat);


        Sponsor panasonic = new Sponsor();
        panasonic.setNombreComercial("Panasonic");
        panasonic.setSector("Tech");
        Set<Sponsor> sponsors = new HashSet<>();
        sponsors.add(panasonic);
        daoJpaSponsor.save(panasonic);

        Sponsor daido = new Sponsor();
        daido.setNombreComercial("Daido Life");
        daido.setSector("Insurance");
        sponsors.add(daido);
        daoJpaSponsor.save(daido);

        Equipo bluteon = createEquipo("Osaka Bluteon", "Osaka", panasonicArena,
                tuomas, sponsors, LocalDate.ofYearDay(1951, 12), "bluteon.com");

        Equipo suntory = createEquipo("Suntory Sunbirds Osaka", "Osaka", asueArena,
                lecat, sponsors, LocalDate.ofYearDay(1942, 10), "suntory.com");

        Set<Equipo> equipos = new HashSet<>();
        equipos.add(bluteon);
        equipos.add(suntory);

        // Inicializar Set<Entidad> en constructores para usar directamente addJugador o addSponsor?
        // Necesitarás helpers para relaciones bidireccionales
        panasonic.setEquipos(equipos);
        daoJpaSponsor.update(panasonic);

        // Players
        Jugador nishida = new Jugador();
        nishida.setName("Yuji");
        nishida.setApellido("Nishida");
        nishida.setDorsal(11);
        nishida.setPosicion(Posicion.OPPOSITE_HITTER);
        nishida.setEquipo(bluteon);
        Set<Jugador> jugadoresBluteon = new HashSet<>();
        jugadoresBluteon.add(nishida);
        daoJpaJugador.save(nishida);

        Jugador ran = new Jugador();
        ran.setName("Ran");
        ran.setApellido("Takahashi");
        ran.setDorsal(12);
        ran.setPosicion(Posicion.OUTSIDE_HITTER);
        ran.setEquipo(suntory);
        Set<Jugador> jugadoresSuntory = new HashSet<>();
        jugadoresSuntory.add(ran);
        daoJpaJugador.save(ran);


    }


    private Equipo createEquipo(String nombre, String ciudad, Estadio estadio,
                                Entrenador entrenador, Set<Sponsor> sponsors, LocalDate foundation, String web) {
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
