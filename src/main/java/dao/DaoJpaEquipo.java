package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Equipo;
import model.Jugador;

import java.util.List;
import java.util.Set;

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

    public List<Equipo> findAllWithPlayers() {
        return this.em.createQuery(
                        "SELECT DISTINCT e FROM Equipo e LEFT JOIN FETCH e.jugadores", Equipo.class)
                .getResultList();
    }

    // findByName

    // problema N+1, aparte de findAll() crear una fetchJoin query
}
