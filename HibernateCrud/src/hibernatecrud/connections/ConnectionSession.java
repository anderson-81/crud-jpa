package hibernatecrud.connections;

import java.io.File;
import java.io.IOException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionSession {

    public static int statusdb = 0;
    public static Process process;

    public static void StartDatabase() {
        if (statusdb == 0) {
            try {
                ProcessBuilder pb = new ProcessBuilder();
                File file = new File("mysql/bin/mysqld.exe");
                pb.command(file.getAbsolutePath());
                process = pb.start();
                statusdb = 1;
                System.out.println("START DATABASE.");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static Session OpenSession() {
        try {
            SessionFactory sessionfactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionfactory.openSession();
            return session;
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static void StopDatabase() {
        if (statusdb != 0) {
            try {
                process.destroy();
                System.out.println("STOP DATABASE.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
