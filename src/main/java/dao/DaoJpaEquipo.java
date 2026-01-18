package dao;

import jakarta.persistence.EntityManager;
import model.Equipo;
import model.Jugador;

/**
 * DAO para entidad Equipo
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaEquipo extends AbstractDao<Equipo> implements GenericDao<Equipo>{

    public DaoJpaEquipo(EntityManager entityManager) {
        super(entityManager, Equipo.class);
    }

    // Specific to Equipo

    // findByName

    // problema N+1, aparte de findAll() crear una fetchJoin query
}
