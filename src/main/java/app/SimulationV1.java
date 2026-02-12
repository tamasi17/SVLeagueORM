package app;

import config.JpaUtil;
import dao.DaoJpaEquipo;
import dao.DaoJpaPartido;
import dao.DaoJpaStats;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Equipo;
import model.Partido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MarketService;
import service.Queries;
import util.DataLoader;
import util.DataService;
import util.MatchEngine;

import java.util.List;

import static util.ResultsVisualizer.imprimirTabla;
import static util.ResultsVisualizer.mostrarTopAnotadores;

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
            entityManager.clear();

            // Confirmamos carga de datos
            Equipo testTeam = daoEquipo.findById(7L);
            logger.debug("Confirmamos carga: {} con {} jugadores.",
                    testTeam.getNombreEquipo(),
                    testTeam.getJugadores().size());

            // Simulación Partidos y Estadísticas

            logger.info("Comenzamos liga!");
            MatchEngine simuladorLiga = new MatchEngine();

            // Find all with players
            List<Equipo> teamsInDb = daoEquipo.findAllWithPlayers();
            simularLiga(teamsInDb, simuladorLiga, daoPartidos, entityManager);
            logger.info("Liga finalizada");

            // Resultados liga

            imprimirTabla(daoEquipo.obtenerClasificacion());
            mostrarTopAnotadores(entityManager);

            // Mercado de fichajes
            MarketService marketService = new MarketService(entityManager);
            marketService.transferMarketSimulado(daoEquipo);

            // Consultas
            Queries consultas = new Queries(entityManager, daoEquipo);
            consultas.allQueries();




        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            // si no fuera try(EntityManager) cerrar aqui
        }

    }

    public static void simularLiga(List<Equipo> league, MatchEngine simuladorLiga,
                                   DaoJpaPartido daoJpaPartido, EntityManager entityManager) {

        EntityTransaction tx = entityManager.getTransaction();

        /* Transactions should be in bigger methods like this one
        / but I made the early mistake of including them in the DAO methods. Don't.
        */

        try {
            tx.begin();

            for (int i = 0; i < league.size(); i++) {
                for (int j = i + 1; j < league.size(); j++) {
                    Equipo home = league.get(i);
                    Equipo away = league.get(j);

                    Partido match = simuladorLiga.playMatch(home, away);

                    logger.info("Match {} has {} stats ready to be saved.",
                            match.getId(), match.getEstadisticas().size());
                    // Cascade.ALL saves linked Stats too
                    daoJpaPartido.save(match);

                    logger.info("Match saved: {} {} - {} {}",
                            home.getNombreEquipo(), match.getSetsLocal(),
                            match.getSetsVisitante(), away.getNombreEquipo());

                    entityManager.flush();
                    entityManager.clear();
                }
            }

            tx.commit();
            logger.info("Season simulation saved successfully.");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Simulation failed! Changes rolled back.", e);
        }
    }



}
