package dao;

import jakarta.persistence.EntityManager;
import model.Estadio;
import model.Jugador;

/**
 * DAO para entidad Estadio
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaEstadio extends AbstractDao<Estadio> implements GenericDao<Estadio>{

    public DaoJpaEstadio(EntityManager entityManager) {
        super(entityManager, Estadio.class);
    }

    // Specific to Estadio

    // findByName

}
