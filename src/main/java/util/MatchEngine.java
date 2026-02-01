package util;

import model.Equipo;
import model.Jugador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

/**
 * Clase que organiza los enfrentamientos de la liga en Simulation/Main
 * @author mati
 */
public class MatchEngine {
    private static final Logger logger = LogManager.getLogger(MatchEngine.class);
    private final Random random = new Random();

    public void playMatch(Equipo home, Equipo away) {
        logger.info("Empezando partido: {} vs {}", home.getNombreEquipo(), away.getNombreEquipo());

        // Example: Simulating player performance
        simulateRosterStats(home);
        simulateRosterStats(away);

        logger.info("Partido terminado: {} vs {}", home.getNombreEquipo(), away.getNombreEquipo());
    }

    private void simulateRosterStats(Equipo team) {
        for (Jugador p : team.getJugadores()) {
            // ToDo: Logic for volleyball stats
            int aces = random.nextInt(0, 5);
            int kills = random.nextInt(5, 20);

            // Log it using the {} syntax we discussed
            logger.debug("Player {} recorded {} kills", p.getNombreJugador(), kills);

            // ToDo: Actualizar estadisticas
            // updatePlayerStats(p, aces, kills);
        }
    }


}
