package service;

import dao.DaoJpaEquipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Equipo;
import model.Jugador;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarketService {

    private static final Logger logger = LogManager.getLogger(MarketService.class);
    private final EntityManager entityManager;

    public MarketService(EntityManager em) {
        this.entityManager = em;
    }


    public void transferirJugador(Long jugadorId, Equipo equipoDestino) {
        // 1. Finding player (must be in persistence context)
        // With em.find or DAO's findById, those objects enter Persistence Context
        // These objects are Managed, the em is "dirty checking" all those objects.
        Jugador jugador = entityManager.find(Jugador.class, jugadorId);
        Equipo equipoOrigen = jugador.getEquipo();

        if (jugador != null && equipoDestino != null) {
            logger.info("MERCADO: {} {} deja el {} para unirse al {}",
                    jugador.getNombreJugador(), jugador.getApellidoJugador(),
                    equipoOrigen.getNombreEquipo(), equipoDestino.getNombreEquipo());

            // 2. IMPORTANTE: Quitarlo del equipo antiguo (limpieza de lista)
            if (equipoOrigen != null) {
                equipoOrigen.getJugadores().remove(jugador);
            }


            // 3. Usar el helper para añadirlo al nuevo equipo
            // Esto internamente hace: jugador.setEquipo(equipoDestino)
            equipoDestino.addJugador(jugador);

            // Mark as a new incorporation for the destination team
            jugador.setEsNuevo(true);

        }
    }

    public void transferMarketSimulado(DaoJpaEquipo daoJpaEquipo){

        EntityTransaction tx = entityManager.getTransaction();

        try {
        tx.begin();
            // Ejemplo simple: El mejor jugador de un equipo se va al campeón
            Equipo tokyo = daoJpaEquipo.findById(1L);
            Equipo toray = daoJpaEquipo.findById(2L);

            // We out here practising streams like we did not have an exam tomorrow
            // findFirst() returns an Optional, so we use .orElseThrow() or .get()
            Jugador crack = tokyo.getJugadores().stream()
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("¡El equipo no tiene jugadores!"));

            transferirJugador(crack.getId(), toray);

            // Al hacer commit, JPA detecta que el 'equipo_id' del jugador ha cambiado
            // y lanzará un UPDATE automático en la tabla 'jugador'
            tx.commit();
            logger.info("Mercado de fichajes cerrado con éxito.");
        } catch (Exception e) {
            tx.rollback();
            logger.error("Error en el mercado", e);
        }
    }
}
