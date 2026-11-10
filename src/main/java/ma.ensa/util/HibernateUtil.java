
package ma.ensa.util;

import ma.ensa.model.Utilisateur;
import ma.ensa.model.Donneur;
import ma.ensa.model.RendezVous;
import ma.ensa.model.Collecte;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();

            configuration.configure("hibernate.cfg.xml");

            // Enregistrer les entités
            configuration.addAnnotatedClass(Utilisateur.class);
            configuration.addAnnotatedClass(Donneur.class);
            configuration.addAnnotatedClass(RendezVous.class);
            configuration.addAnnotatedClass(Collecte.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

