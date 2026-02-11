package util;

import model.Equipo;
import model.Jugador;
import model.Partido;
import model.StatsPartido;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Clase que organiza los enfrentamientos de la liga en Simulation/Main
 * Gana el mejor de 5, player stats en funcion de la posicion.
 * @author mati
 */
public class MatchEngine {
    private static final Logger logger = LogManager.getLogger(MatchEngine.class);
    private final Random random = new Random();

    public Partido playMatch(Equipo home, Equipo away) {
        logger.info("Empezando partido: {} vs {}", home.getNombreEquipo(), away.getNombreEquipo());

        Partido match = new Partido();
        match.setEquipoLocal(home);
        match.setEquipoVisitante(away);
        match.setLugar(home.getEstadio());
        match.setFecha(LocalDateTime.now());

        // Match score (Volleyball is best of 5)
        // One team wins 3 sets, the other 0-2.
        int winnerSets = 3;
        int loserSets = random.nextInt(3); // 0, 1, or 2

        if (random.nextBoolean()) { // Home wins
            match.setSetsLocal(winnerSets);
            match.setSetsVisitante(loserSets);
        } else { // Away wins
            match.setSetsLocal(loserSets);
            match.setSetsVisitante(winnerSets);
        }

        // Simulate stats for both teams
        simulateRosterStats(home, match);
        simulateRosterStats(away, match);

        logger.info("Partido terminado: {} {}-{} {}",
                home.getNombreEquipo(), match.getSetsLocal(),
                match.getSetsVisitante(), away.getNombreEquipo());

        return match;
    }

    private void simulateRosterStats(Equipo team, Partido match) {
        for (Jugador p : team.getJugadores()) {
            StatsPartido stats = new StatsPartido();
            stats.setJugador(p);

            // Helper to link the stat to the match
            match.addEstadistica(stats);

            // Position-based logic
            switch (p.getPosicion()) {
                case OPPOSITE_HITTER, OUTSIDE_HITTER -> { // Attackers
                    stats.setAtaques(random.nextInt(15, 30));
                    stats.setPuntos(random.nextInt(10, 25));
                    stats.setAces(random.nextInt(0, 4));
                }
                case MIDDLE_BLOCKER -> {
                    stats.setBloqueosIntentados(random.nextInt(10, 20));
                    stats.setBloqueosConseguidos(random.nextInt(2, 8));
                    stats.setPuntos(stats.getBloqueosConseguidos() + random.nextInt(2, 5));
                }
                case SETTER -> {
                    stats.setColocacionesIntentadas(random.nextInt(30, 50));
                    stats.setColocacionesConseguidas(random.nextInt(20, 40));
                    stats.setAces(random.nextInt(0, 2));
                }
                case LIBERO -> {
                    stats.setRecepcionesIntentadas(random.nextInt(15, 25));
                    stats.setRecepcionesConseguidas(random.nextInt(10, 20));
                    stats.setPuntos(0);
                }
            }

            logger.debug("Player {} ({}) generated {} points",
                    p.getNombreJugador(), p.getPosicion(), stats.getPuntos());
        }
    }


}
