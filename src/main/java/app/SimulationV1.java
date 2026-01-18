package app;

import config.JpaUtil;
import jakarta.persistence.EntityManager;
import util.DataLoader;

public class SimulationV1 {

    public static void main(String[] args) {


        try (EntityManager entityManager = JpaUtil.getEntityManagerFactory().createEntityManager()){

            DataLoader dataLoader = new DataLoader(entityManager);

            dataLoader.loadTest();

            /* TODO
            In your createEquipo method, you are likely creating the Jugador objects and adding them to a Set,
            but you aren't properly linking them to the equipo instance before the save operation happens,
            or you are trying to save them in the wrong order.
             */



        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            // if you dont auto close entity manager, close it here
        }

    }

}
