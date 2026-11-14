
package ma.ensa.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory =
            buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Charger les identifiants MySQL
            Properties properties = new Properties();

            try (InputStream input = HibernateUtil.class
                    .getClassLoader()
                    .getResourceAsStream("db-local.properties")) {

                if (input == null) {
                    throw new RuntimeException(
                            "Fichier db-local.properties introuvable"
                    );
                }

                properties.load(input);
            }

            // Vérifier les identifiants
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password", "");

            if (username == null || username.isBlank()) {
                throw new RuntimeException(
                        "Nom d'utilisateur MySQL manquant"
                );
            }

            // Charger la configuration Hibernate
            Configuration configuration =
                    new Configuration().configure("hibernate.cfg.xml");

            configuration.setProperty(
                    "hibernate.connection.username", username
            );

            configuration.setProperty(
                    "hibernate.connection.password", password
            );

            return configuration.buildSessionFactory();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
