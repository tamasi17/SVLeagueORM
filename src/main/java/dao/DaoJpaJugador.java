package dao;

import jakarta.persistence.EntityManager;
import model.Jugador;
import java.util.List;

/**
 * DAO para entidad Jugador
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaJugador extends AbstractDao<Jugador> implements GenericDao<Jugador>{

    public DaoJpaJugador(EntityManager entityManager) {
        super(entityManager, Jugador.class);
    }

    // Specific to Jugador

    // findByName

    // problema N+1, aparte de findAll() crear una fetchJoin query
}
