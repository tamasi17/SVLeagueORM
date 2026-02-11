package util;

import model.Equipo;
import model.Jugador;
import model.Sponsor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Clase que recibe datos sobre la liga de un json y separa en Map<Equipo, List<Jugador> y
 * un Set<Sponsor> teniendo en cuenta relaciones bidireccionales.
 * Finalmente, envia toda esa información al DataLoader que carga la liga en la Simulacion.
 */
public class DataService {

    private static final Logger logger = LogManager.getLogger(DataService.class);


    public void prepareAndExecute(String jsonPath, DataLoader loader) {

        List<Equipo> teams = JsonLoader.parseLeague(jsonPath);

        Map<Equipo, List<Jugador>> leagueMap = new HashMap<>();
        Set<Sponsor> uniqueSponsors = new HashSet<>();

        for (Equipo team : teams) {
            // Jackson no corrige automáticamente las relaciones bidireccionales
            if (team.getJugadores() != null) {
                team.getJugadores().forEach(j -> j.setEquipo(team));
                leagueMap.put(team, new ArrayList<>(team.getJugadores()));
            }

            // Colecciona los sponsors para la lista
            if (team.getSponsors() != null) {
                uniqueSponsors.addAll(team.getSponsors());
            }
        }

        logger.debug("League loading prepared.");
        loader.loadLeague(leagueMap, new ArrayList<>(uniqueSponsors));
    }
}
