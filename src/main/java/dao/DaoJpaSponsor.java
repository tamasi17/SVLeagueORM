package dao;

import jakarta.persistence.EntityManager;
import model.Jugador;
import model.Sponsor;

/**
 * DAO para entidad Sponsor
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaSponsor extends AbstractDao<Sponsor> implements GenericDao<Sponsor>{

    public DaoJpaSponsor(EntityManager entityManager) {
        super(entityManager, Sponsor.class);
    }

    // Specific to Sponsor

    // findByName, findBySector

    // problema N+1, aparte de findAll() crear una fetchJoin query

}
