package dao;

import jakarta.persistence.EntityManager;
import model.Entrenador;
import model.Jugador;

/**
 * DAO para entidad Entrenador
 * Hereda operaciones CRUD de AbstractDAO.
 * @author mati
 */
public class DaoJpaEntrenador extends AbstractDao<Entrenador> implements GenericDao<Entrenador>{

    public DaoJpaEntrenador(EntityManager entityManager) {
        super(entityManager, Entrenador.class);
    }

    // Specific to Entrenador

    // findByName

}
