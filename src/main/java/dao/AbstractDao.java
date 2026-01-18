package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public abstract class AbstractDao<T> implements GenericDao<T>{

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
            System.out.println(e.getLocalizedMessage());
            throw e;
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
            System.out.println(e.getLocalizedMessage());
            throw e;
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
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
    }


}
