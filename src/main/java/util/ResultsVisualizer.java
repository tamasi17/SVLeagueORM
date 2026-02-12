package util;

import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Clase que facilita la visualizacion de datos de las queries
 * @author mati
 */
public class ResultsVisualizer {


    private static final Logger logger = LogManager.getLogger(ResultsVisualizer.class);

    // Ayuditas visuales de Gemini <3

    public static void imprimirTabla(List<Object[]> resultados) {
        logger.info("--------------------------------------------------");
        logger.info(String.format("%-25s | %s", "EQUIPO", "VICTORIAS"));
        logger.info("--------------------------------------------------");

        for (Object[] fila : resultados) {
            logger.info(String.format("%-25s | %d", fila[0], ((Number) fila[1]).intValue()));
        }
        logger.info("--------------------------------------------------");
    }

    public static void mostrarTopAnotadores(EntityManager entityManager) {
        String jpql = "SELECT s.jugador.nombreJugador, s.jugador.apellidoJugador, SUM(s.puntos) as totalPuntos " +
                "FROM StatsPartido s " +
                "GROUP BY s.jugador.id " +
                "ORDER BY totalPuntos DESC";

        List<Object[]> top = entityManager.createQuery(jpql).setMaxResults(5).getResultList();

        logger.info("--- TOP 5 ANOTADORES DE LA LIGA ---");
        for (Object[] row : top) {
            logger.info("{} {}: {} puntos", row[0], row[1], row[2]);
        }
    }


}
