package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    // findBySector

    public Sponsor findByName(String name) {
        try {
            return em.createQuery(
                            "SELECT s FROM Sponsor s WHERE s.nombreComercial = :name", Sponsor.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    // problema N+1, aparte de findAll() crear una fetchJoin query

}
