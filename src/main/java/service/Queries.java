package service;

import dao.DaoJpaEquipo;
import dto.StandingsDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.Equipo;
import model.Jugador;
import model.Sponsor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLOutput;
import java.util.List;

public class Queries {

    private static final Logger logger = LogManager.getLogger(Queries.class);
    private final EntityManager em;
    private DaoJpaEquipo daoJpaEquipo;

    public Queries(EntityManager em, DaoJpaEquipo daoJpaEquipo) {
        this.em = em;
        this.daoJpaEquipo = daoJpaEquipo;
    }

    public void allQueries(){


        // 1. Utiliza una consulta nativa (NativeQuery) para obtener las características de la competición.
        // No puse info de la competicion misma, uso jugadores
        logger.info(">>> Consulta 1. NativeQuery, player info");
        for (Object o : nativeQuery() ) {
            o.toString();
        }

        // 2.Consulta y recupera todos los equipos participantes en la competición.
        logger.info(">>> Consulta 2. QueryAllTeams");
        for (Equipo team : queryAllTeams() ) {
            System.out.println(team.getNombreEquipo());
        }

        // 3.Obtén la lista de todos los deportistas de un equipo específico.
        logger.info(">>> Consulta 3. QueryAllFromTeam");
        for (Jugador j :   queryAllFromTeam(1L)) {
            System.out.println(j.getNombreJugador() +" "+j.getApellidoJugador());
        }

        // Task 4: Sponsors of a Team
        logger.info(">>> Consulta 4. QuerySponsorsFromTeam");
        for (Sponsor s : querySponsorsFromTeam(1L)) {
            System.out.println(s.getNombreComercial() + ", "+ s.getSector());
        }


        // 5.Genera una lista de deportistas y patrocinadores vinculados a un equipo específico.
        // (DTO o Object[])
        logger.info(">>> Consulta 5. QueryPlayersAndSponsorsFromTeam");
        for (Object[] o : queryPlayersAndSponsorsFromTeam(1L)) {
            System.out.println(o.toString());
        }


        // 6. Calcula y presenta la edad promedio de los deportistas de un equipo determinado.
        logger.info(">>> Consulta 6. Average age");
        Double avgAge = queryAvgAge(1L);
        System.out.println(daoJpaEquipo.findById(1L).getNombreEquipo() + "\n" +
                "Average age: "+ avgAge);


        // 7. Cuenta cuantos deportistas tienen más de veintitrés años en la competición agrupados por nacionalidad.
        List<Object[]> listCountByNat = getListCountByNat();
        logger.info("Consulta 7. Over 23 by nationality: ");

        if (listCountByNat.isEmpty()) {
            logger.warn("No players found matching the criteria. Check dates in DB");
        }

        for (Object[] fila : listCountByNat) {
            String nacionalidad = (String) fila[0];
            // COUNT returns a Long in JPA/Hibernate
            Long cantidad = (Long) fila[1];

            logger.info("Nacionalidad: {} | Cantidad: {}", nacionalidad, cantidad);
        }


        // 8. Visualiza la clasificación al inicio, a mitad de temporada y al final de esta.

        List<StandingsDTO> standings = queryLeagueStandings(1L);
        parseStandings(standings);
        logger.info("SNAPSHOT: {} (Hasta Partido ID: {})", "Inicio", 1L);

        standings = queryLeagueStandings(20L);
        parseStandings(standings);
        logger.info("SNAPSHOT: {} (Hasta Partido ID: {})", "Mitad", 20L);

        standings = queryLeagueStandings(45L);
        parseStandings(standings);
        logger.info("SNAPSHOT: {} (Hasta Partido ID: {})", "Final", 45L);



        // 9. Determina y muestra los tres equipos con más puntos y los tres con menos.
        showTopAndBottomTeams();

        // 10. Muestra las nuevas incorporaciones a la competición (utiliza una NamedQuery).
        // 11. Enumera todos los fichajes realizados entre los diferentes equipos.
        // nuevosTransfers porque no hay jugadores nuevos
        showNewTransfers();

        // 12. Realiza un recuento del total de deportistas que participan en la competición.
        Long total = em.createQuery("SELECT COUNT(j) FROM Jugador j", Long.class).getSingleResult();
        logger.info("Total jugadores: {}", total);

        // 13. Dado dos equipos muestra sus patrocinadores comunes. (Intersect)
        commonSponsors();


        // Task 14
        executeCriteriaExamples(23, "HIROSHIMA THUNDERS", "Japon");

    }

    private void commonSponsors() {
        List<Sponsor> common = em.createQuery(
                        "SELECT s FROM Equipo e1 JOIN e1.sponsors s WHERE e1.id = :id1 AND s IN " +
                                "(SELECT s2 FROM Equipo e2 JOIN e2.sponsors s2 WHERE e2.id = :id2)", Sponsor.class)
                .setParameter("id1", 1L).setParameter("id2", 2L).getResultList();

        if (common.isEmpty()) {
            logger.warn("No sponsors found matching the criteria. Check dates in DB");
        }

        for (Sponsor s : common) {
            logger.info("{} : {}", s.getNombreComercial(),s.getSector());
        }
    }

    private static void parseStandings(List<StandingsDTO> standings) {
        for (StandingsDTO row : standings) {
            // Record accessor syntax: nombre() instead of getNombre()
            logger.info("Equipo: {} | Puntos: {}", row.nombre(), row.puntos());
        }
    }


    // 1. Utiliza una consulta nativa (NativeQuery) una para obtener las características de la competición.
    // No puse info de la competicion misma, uso entrenadores
    public List<Object[]> nativeQuery(){
      //  Query q = em.createNativeQuery("SELECT nombre, sede, categoria FROM competicion");
        Query q = em.createNativeQuery("SELECT name, last_name, nationality FROM coaches");
        List<Object[]> results = q.getResultList();

        for (Object[] row : results) {
            String name = (String) row[0];
            String surname = (String) row[1];
            String nat = (String) row[2];
            System.out.println(name + " " + surname + " (" + nat + ")");
        }

        return results;
    }


    // 2.Consulta y recupera todos los equipos participantes en la competición.
    private List<Equipo>  queryAllTeams() {
        return em.createQuery("SELECT e FROM Equipo e", Equipo.class).getResultList();
    }

    // 3.Obtén la lista de todos los deportistas de un equipo específico.
    private List<Jugador> queryAllFromTeam(Long id) {
        List<Jugador> jugadores = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo.id = :id", Jugador.class)
                .setParameter("id", id).getResultList();
        return jugadores;
    }

    // 4.Identifica y lista todos los patrocinadores asociados a un equipo concreto.
    private List<Sponsor> querySponsorsFromTeam(Long id) {
         return em.createQuery("SELECT e.sponsors FROM Equipo e WHERE e.id = :id", Sponsor.class)
                .setParameter("id", id).getResultList();
    }

    // 5.Genera una lista de deportistas y patrocinadores vinculados a un equipo específico.
    private List<Object[]> queryPlayersAndSponsorsFromTeam(Long id) {
         return em.createQuery("SELECT j.nombreJugador, s.nombreComercial FROM Equipo e JOIN e.jugadores j JOIN e.sponsors s WHERE e.id = :id")
                .setParameter("id", id).getResultList();
    }

    // 6. Calcula y presenta la edad promedio de los deportistas de un equipo determinado.
    private Double queryAvgAge(Long id) {
        Double avgBirthYear = em.createQuery(
                        "SELECT AVG(YEAR(j.fechaNacimiento)) FROM Jugador j WHERE j.equipo.id = :id",
                        Double.class)
                .setParameter("id", id)
                .getSingleResult();

        return 2026 - avgBirthYear;
    }

    // 7. Cuenta cuantos deportistas tienen más de veintitrés años en la competición agrupados por nacionalidad.
    private List getListCountByNat() {
        return em.createQuery(
                        "SELECT j.nacionalidad, COUNT(j) FROM Jugador j WHERE YEAR(j.fechaNacimiento) <= 2003 GROUP BY j.nacionalidad")
                .getResultList();
    }

    // 8. Visualiza la clasificación al inicio, a mitad de temporada y al final de esta.
    // Inicio: maxId = 0 (or 1).
    // Mitad: maxId = totalPartidos / 2.
    // Final: maxId = totalPartidos.
    public List<StandingsDTO> queryLeagueStandings(Long maxId) {
        // Note: SUM in Hibernate returns a Long.
        // If your record has an Integer, it will crash!
        String jpql = "SELECT new dto.StandingsDTO(e.nombreEquipo, " +
                "SUM(CASE WHEN (p.equipoLocal = e AND p.setsLocal > p.setsVisitante) OR " +
                "             (p.equipoVisitante = e AND p.setsVisitante > p.setsLocal) THEN 3L ELSE 0L END)) " +
                "FROM Equipo e, Partido p " +
                "WHERE p.id <= :maxId " +
                "GROUP BY e.nombreEquipo " +
                "ORDER BY 2 DESC";

        return em.createQuery(jpql, StandingsDTO.class)
                .setParameter("maxId", maxId)
                .getResultList();
    }

    // 9. Determina y muestra los tres equipos con más puntos y los tres con menos.
    public List<StandingsDTO> getTop3Teams() {
        return em.createQuery(
                        "SELECT new dto.StandingsDTO(e.nombreEquipo, " +
                                "SUM(CASE WHEN (p.equipoLocal = e AND p.setsLocal > p.setsVisitante) OR " +
                                "             (p.equipoVisitante = e AND p.setsVisitante > p.setsLocal) THEN 3L ELSE 0L END)) " +
                                "FROM Equipo e, Partido p " +
                                "GROUP BY e.nombreEquipo " +
                                "ORDER BY 2 DESC", StandingsDTO.class) // Sort DESC
                .setMaxResults(3) // LIMIT 3
                .getResultList();
    }

    public List<StandingsDTO> getBottom3Teams() {
        return em.createQuery(
                        "SELECT new dto.StandingsDTO(e.nombreEquipo, " +
                                "SUM(CASE WHEN (p.equipoLocal = e AND p.setsLocal > p.setsVisitante) OR " +
                                "             (p.equipoVisitante = e AND p.setsVisitante > p.setsLocal) THEN 3L ELSE 0L END)) " +
                                "FROM Equipo e, Partido p " +
                                "GROUP BY e.nombreEquipo " +
                                "ORDER BY 2 ASC", StandingsDTO.class) // Sort DESC
                .setMaxResults(3) // LIMIT 3
                .getResultList();
    }


    public void showTopAndBottomTeams() {

        List<StandingsDTO> top3 = getTop3Teams();
        List<StandingsDTO> bottom3 = getBottom3Teams();

        // 2. Top 3
        logger.info("=== LOS 3 MEJORES (TOP) ===");
        top3.forEach(team ->
                logger.info("> {} con {} puntos", team.nombre(), team.puntos()));

        // 3. Bottom 3
        logger.info("=== LOS 3 PEORES (BOTTOM) ===");
        bottom3.forEach(team ->
                logger.info("> {} con {} puntos", team.nombre(), team.puntos()));
    }


    // 10. Muestra las nuevas incorporaciones a la competición (utiliza una NamedQuery).

    public void showNewTransfers() {
        logger.info("--- CONSULTA 10: NUEVAS INCORPORACIONES ---");

        // We call the query by its "Alias" defined in the Entity
        List<Jugador> incorporaciones = em
                .createNamedQuery("Jugador.newTransfers", Jugador.class)
                .getResultList();

        if (incorporaciones.isEmpty()) {
            logger.info("No hay fichajes recientes en el mercado.");
        } else {
            for (Jugador j : incorporaciones) {
                logger.info("FICHAJE: {} {} se ha unido a {}",
                        j.getNombreJugador(), j.getApellidoJugador(), j.getEquipo().getNombreEquipo());
            }
        }
    }

    // 11. Enumera todos los fichajes realizados entre los diferentes equipos.

    // 12. Realiza un recuento del total de deportistas que participan en la competición.

    // 13. Dado dos equipos muestra sus patrocinadores comunes.

    /*
     14. Utiliza CriteriaQuery para poder filtrar por todos los atributos de los deportistas, edad, nombre, equipo, etc
     ordenados por un criterio. Lanza tres ejemplos distintos con diferentes atributos,
     uno debe incluir todos los atributos y el resto solo una parte de ellos.
     Ejemplo: Dame la lista de deportistas, que tenga X edad, pertenezcan al equipo Y ordenados por nombre.
     */
    public void executeCriteriaExamples(int targetAge, String teamName, String nationality) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // --- EXAMPLE 1: ALL ATTRIBUTES (Age + Team + Name + Nationality) ---
        CriteriaQuery<Jugador> cq1 = cb.createQuery(Jugador.class);
        Root<Jugador> root1 = cq1.from(Jugador.class);

        // Logic: Current Year (2026) - Year of Birth = Age
        // cb.function("YEAR", Integer.class, root1.get("fechaNacimiento"))
        int birthYearTarget = 2026 - targetAge;

        Predicate allFilters = cb.and(
                cb.equal(cb.function("YEAR", Integer.class, root1.get("fechaNacimiento")), birthYearTarget),
                cb.equal(root1.get("equipo").get("nombreEquipo"), teamName),
                cb.equal(root1.get("nacionalidad"), nationality)
        );

        cq1.where(allFilters).orderBy(cb.asc(root1.get("nombreJugador")));
        List<Jugador> res1 = em.createQuery(cq1).getResultList();
        logResults("Example 1 (All filters)", res1);


        // --- EXAMPLE 2: PARTIAL (Team + Position) ---
        CriteriaQuery<Jugador> cq2 = cb.createQuery(Jugador.class);
        Root<Jugador> root2 = cq2.from(Jugador.class);

        cq2.where(cb.equal(root2.get("equipo").get("nombreEquipo"), teamName));
        cq2.orderBy(cb.desc(root2.get("apellidoJugador")));

        List<Jugador> res2 = em.createQuery(cq2).getResultList();
        logResults("Example 2 (Team Only)", res2);


        // --- EXAMPLE 3: PARTIAL (Age Only) ---
        CriteriaQuery<Jugador> cq3 = cb.createQuery(Jugador.class);
        Root<Jugador> root3 = cq3.from(Jugador.class);

        // Find players born before 2000 (Veterans)
        cq3.where(cb.lessThan(cb.function("YEAR", Integer.class, root3.get("fechaNacimiento")), 2000));

        List<Jugador> res3 = em.createQuery(cq3).getResultList();
        logResults("Example 3 (Age > 26)", res3);
    }

    private void logResults(String title, List<Jugador> players) {
        logger.info(">>> Results for {}: {} found", title, players.size());

        if (players.isEmpty()) {
            logger.warn("    No matches found for this filter.");
        } else {
            for (Jugador j : players) {
                // Calculate age manually for the log if you want to verify
                int age = 2026 - j.getFechaNacimiento().getYear();

                logger.info("    - {} {} | Age: {} | Team: {} | Nat: {}",
                        j.getNombreJugador(),
                        j.getApellidoJugador(),
                        age,
                        j.getEquipo().getNombreEquipo(),
                        j.getNacionalidad());
            }
        }
        logger.info("-------------------------------------------------");
    }



}
