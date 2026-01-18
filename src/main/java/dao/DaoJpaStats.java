package dao;

import jakarta.persistence.EntityManager;
import model.Jugador;
import model.StatsPartido;

/**
 * DAO para entidad Stats
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaStats extends AbstractDao<StatsPartido> implements GenericDao<StatsPartido>{

    public DaoJpaStats(EntityManager entityManager) {
        super(entityManager, StatsPartido.class);
    }

    // Specific to Stats

    // findByPlayer, findByMatch

    // problema N+1, aparte de findAll() crear una fetchJoin query

}
