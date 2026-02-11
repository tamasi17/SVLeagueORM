package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DataService;

import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T> {

    private static final Logger logger = LogManager.getLogger(AbstractDao.class);

    protected EntityManager em;
    private final Class<T> entityClass;

    public AbstractDao(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        logger.debug("Persisting entity: {}", entity);
        em.persist(entity);
    }

    public T findById(Long id) {
        logger.debug("Finding by Id: {}", id);
        return em.find(entityClass, id);
    }

    public void delete(T entity) {
        logger.debug("Deleting entity: {}", entity);
        // Merge first to ensure entity is "attached" to current session before deleting
        em.merge(entity);
        em.remove(entity);
    }

    public void update(T entity) {
        logger.debug("Updating entity: {}", entity);
        em.merge(entity);
    }

    public List<T> findAll() {
        logger.debug("Finding all entities.");
        return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }


}
