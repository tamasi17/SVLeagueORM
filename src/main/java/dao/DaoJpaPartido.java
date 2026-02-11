package dao;

import jakarta.persistence.EntityManager;
import model.Jugador;
import model.Partido;

/**
 * DAO para entidad Partido
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaPartido extends AbstractDao<Partido> implements GenericDao<Partido>{

    public DaoJpaPartido(EntityManager entityManager) {
        super(entityManager, Partido.class);
    }

    // Specific to Partido

    // findByEquipoLocal, findByEquipoVisitante

    // problema N+1, aparte de findAll() crear una fetchJoin query

}
