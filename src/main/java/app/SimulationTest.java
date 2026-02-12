package app;

import config.JpaUtil;
import dao.DaoJpaEquipo;
import dao.DaoJpaPartido;
import dao.DaoJpaStats;
import jakarta.persistence.EntityManager;
import model.Equipo;
import model.Partido;
import model.StatsPartido;
import util.DataLoader;

import java.util.Random;

/**
 * Test para comprobar funcionamiento de daos y persistencia
 * @author mati
 */
public class SimulationTest {

    public static void main(String[] args) {


        try (EntityManager entityManager = JpaUtil.getEntityManagerFactory().createEntityManager()) {

            DaoJpaPartido daoPartidos = new DaoJpaPartido(entityManager);
            DaoJpaStats daoStats = new DaoJpaStats(entityManager);
            DaoJpaEquipo daoEquipo = new DaoJpaEquipo(entityManager);

            // Carga datos inicial: Equipos, Jugadores, Entrenadores, Sponsors...
            DataLoader dataLoader = new DataLoader(entityManager);
            dataLoader.loadTest();

            // Simulacion Partidos y Estadisticas

            Random random = new Random();

            Equipo bluteon = daoEquipo.findById(1L);
            Equipo suntory = daoEquipo.findById(2L);

            Partido clasico = new Partido();
            clasico.setEquipoLocal(bluteon);
            clasico.setEquipoVisitante(suntory);
            clasico.setSetsLocal(random.nextInt(3) + 1);
            clasico.setSetsVisitante(random.nextInt(3) + 1);

            StatsPartido statsYuji = new StatsPartido();
            if (!bluteon.getJugadores().isEmpty()) {
                statsYuji.setJugador(bluteon.getJugadores().iterator().next());
            }
            StatsPartido statsRan = new StatsPartido();
            if (!suntory.getJugadores().isEmpty()) {
                statsRan.setJugador(suntory.getJugadores().iterator().next());
            }

            fillStats(statsYuji, clasico, random);
            fillStats(statsRan, clasico, random);

            System.out.println("\n ======= \n" + clasico.getResultadoFormateado());
            System.out.println(statsYuji);
            System.out.println(statsRan);

            // Mercado de fichajes


        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            // if you dont auto close entity manager, close it here
        }

    }

    private static StatsPartido fillStats(StatsPartido statsPartido, Partido partido, Random random) {
        statsPartido.setPartido(partido);

        int ataques = random.nextInt(20,23);
        statsPartido.setAtaques(ataques);
        statsPartido.setPuntos(ataques - (random.nextInt(5)));

        int saques = random.nextInt(10,13);
        statsPartido.setSaques(saques);
        statsPartido.setAces(saques - (random.nextInt(5)));

        int bloqueos = random.nextInt(2, 5);
        statsPartido.setBloqueosIntentados(bloqueos);
        statsPartido.setBloqueosConseguidos(bloqueos - (random.nextInt(2)));

        return statsPartido;
    }

}
