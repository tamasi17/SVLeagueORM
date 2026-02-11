package app;

import config.JpaUtil;
import dao.DaoJpaEquipo;
import dao.DaoJpaPartido;
import dao.DaoJpaSponsor;
import dao.DaoJpaStats;
import jakarta.persistence.EntityManager;
import model.Equipo;
import model.Partido;
import model.StatsPartido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DataLoader;
import util.DataService;
import util.MatchEngine;

import java.util.List;
import java.util.Random;

public class SimulationV1 {

    private static final Logger logger = LogManager.getLogger(SimulationV1.class);

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

            logger.debug("Confirmamos carga con equipo aleatorio: " + daoEquipo.findById(7L).getNombreEquipo());

            // Simulación Partidos y Estadísticas

            logger.info("Comenzamos liga!");
            MatchEngine simuladorLiga = new MatchEngine();
            simularLiga(daoEquipo.findAll(), simuladorLiga, daoPartidos);
            logger.info("Liga finalizada");


            // Mercado de fichajes


        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            // si no fuera try(EntityManager) cerrar aqui
        }

    }

    public static void simularLiga(List<Equipo> league, MatchEngine simuladorLiga, DaoJpaPartido daoJpaPartido) {
        for (int i = 0; i < league.size(); i++) {
            for (int j = i + 1; j < league.size(); j++) {
                Equipo home = league.get(i);
                Equipo away = league.get(j);

                Partido match = simuladorLiga.playMatch(home, away);

                // Cascade.ALL saves linked Stats too
                daoJpaPartido.save(match);

                logger.info("Match saved: {} {} - {} {}",
                        home.getNombreEquipo(), match.getSetsLocal(),
                        match.getSetsVisitante(), away.getNombreEquipo());

                // Recomendado flush() y clear() al final de cada ciclo, pero no estoy abriendo transacciones aqui.
            }
        }
    }
}
