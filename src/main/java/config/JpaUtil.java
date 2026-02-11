package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 */
public class JpaUtil {

    private static final EntityManagerFactory entityManagerFactory = buildManagerFactory();

    private static EntityManagerFactory buildManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("orm_competicion");
        } catch (Exception e) {
            System.err.println("Error al crear EntityManagerFactory: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory(){ return entityManagerFactory;}

    public static void shutdown(){
        if (entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }

}
