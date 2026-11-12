
package ma.ensa.util;

import ma.ensa.model.Utilisateur;
import ma.ensa.model.Donneur;
import ma.ensa.model.RendezVous;
import ma.ensa.model.Collecte;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Charger les propriétés personnelles
            Properties properties = new Properties();

            InputStream input = HibernateUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db-local.properties");

            if (input == null) {
                throw new RuntimeException("Fichier db-local.properties introuvable !");
            }

            properties.load(input);
            input.close();

            // Configurer Hibernate
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            configuration.setProperty(
                    "hibernate.connection.username",
                    properties.getProperty("db.username")
            );

            configuration.setProperty(
                    "hibernate.connection.password",
                    properties.getProperty("db.password")
            );

            // Enregistrer les entités
            configuration.addAnnotatedClass(Utilisateur.class);
            configuration.addAnnotatedClass(Donneur.class);
            configuration.addAnnotatedClass(RendezVous.class);
            configuration.addAnnotatedClass(Collecte.class);

            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


