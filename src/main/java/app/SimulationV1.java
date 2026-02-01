package app;

import config.JpaUtil;
import dao.DaoJpaEquipo;
import dao.DaoJpaPartido;
import dao.DaoJpaSponsor;
import dao.DaoJpaStats;
import jakarta.persistence.EntityManager;
import model.Partido;
import model.StatsPartido;
import util.DataLoader;
import util.DataService;

import java.util.Random;

public class SimulationV1 {

    public static void main(String[] args) {


        try (EntityManager entityManager = JpaUtil.getEntityManagerFactory().createEntityManager()) {

            DaoJpaPartido daoPartidos = new DaoJpaPartido(entityManager);
            DaoJpaStats daoStats = new DaoJpaStats(entityManager);
            DaoJpaEquipo daoEquipo = new DaoJpaEquipo(entityManager);

            // Carga datos inicial: Equipos, Jugadores, Entrenadores, Sponsors...
            DataLoader dataLoader = new DataLoader(entityManager);
            DataService dataService = new DataService();

            dataService.prepareAndExecute("src/main/resources/svLeagueInfo/svTeams.json", dataLoader);

            // Confirmamos carga de datos

            // ToDo: Missing Jugador (birth date y nationality), Equipo (city, foundation date)
            // ToDo: Entrenador (nationality), Sponsor (industry sector)

            System.out.println(" >>>> Confirmamos carga con equipo aleatorio: " + daoEquipo.findById(7L).getNombreEquipo());

            // Simulación Partidos y Estadísticas




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
