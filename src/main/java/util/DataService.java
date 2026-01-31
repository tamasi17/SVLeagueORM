package util;

import model.Equipo;
import model.Jugador;
import model.Sponsor;

import java.util.*;

/**
 * Clase que recibe datos sobre la liga de un json y separa en Map<Equipo, List<Jugador> y
 * un Set<Sponsor> teniendo en cuenta relaciones bidireccionales.
 * Finalmente, envia toda esa información al DataLoader que carga la liga en la Simulacion.
 */
public class DataService {

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

            // Collect unique sponsors for the global list
            if (team.getSponsors() != null) {
                uniqueSponsors.addAll(team.getSponsors());
            }
        }


        loader.loadLeague(leagueMap, new ArrayList<>(uniqueSponsors));
    }
}
