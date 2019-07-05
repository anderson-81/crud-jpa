package emcrud.connections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionSession {

     public static EntityManager GetEntityManager() {
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("PU-SQLite3");
            return factory.createEntityManager();
        }catch(Exception e){
            return null;
        }
    }
}
