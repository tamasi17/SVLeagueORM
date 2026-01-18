package app;

import config.JpaUtil;
import jakarta.persistence.EntityManager;
import util.DataLoader;

public class SimulationV1 {

    public static void main(String[] args) {


        try (EntityManager entityManager = JpaUtil.getEntityManagerFactory().createEntityManager()){

            DataLoader dataLoader = new DataLoader(entityManager);

            dataLoader.loadTest();



        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            // if you dont auto close entity manager, close it here
        }

    }

}
