
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
            Configuration configuration = new Configuration();

            // Charger la configuration Hibernate
            configuration.configure("hibernate.cfg.xml");

            // Charger les identifiants MySQL personnels
            Properties properties = new Properties();

            try (InputStream input =
                         HibernateUtil.class.getClassLoader()
                                 .getResourceAsStream("db-local.properties")) {

                if (input == null) {
                    throw new RuntimeException(
                            "Fichier db-local.properties introuvable"
                    );
                }

                properties.load(input);
            }

            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            if (username == null || password == null) {
                throw new RuntimeException(
                        "Identifiants MySQL manquants"
                );
            }

            // Configurer les identifiants de connexion
            configuration.setProperty(
                    "hibernate.connection.username", username
            );

            configuration.setProperty(
                    "hibernate.connection.password", password
            );

            // Enregistrer les entités
            configuration.addAnnotatedClass(Utilisateur.class);
            configuration.addAnnotatedClass(Donneur.class);
            configuration.addAnnotatedClass(RendezVous.class);
            configuration.addAnnotatedClass(Collecte.class);

            // Construire la SessionFactory
            sessionFactory = configuration.buildSessionFactory();

            System.out.println("Hibernate initialisé avec succès !");

        } catch (Exception e) {
            System.err.println(
                    "Erreur lors de l'initialisation de Hibernate :"
            );

            e.printStackTrace();

            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
