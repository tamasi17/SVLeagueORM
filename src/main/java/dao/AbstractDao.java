package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.DataService;

import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T>{

    private static final Logger logger = LogManager.getLogger(AbstractDao.class);

    protected EntityManager em;
    private final Class<T> entityClass;

    public AbstractDao(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            logger.error("Error saving entity {}", entity.getClass() ,e);
        }
    }

    public T findById(Long id) {
        return em.find(entityClass, id);
    }

    public void delete(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            // Merge first to ensure entity is "attached" to current session before deleting
            em.merge(entity);
            em.remove(entity);
            transaction.commit();
        } catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
            logger.error("Error deleting entity {}", entity.getClass() ,e);

        }
    }

    public void update(T entity) {
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        } catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
            logger.error("Error updating entity {}", entity.getClass() ,e);

        }
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }


}
