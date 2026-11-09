package ma.ensa.util;

public class TestConnexion {

    public static void main(String[] args) {

        if (HibernateUtil.getSessionFactory() != null) {
            System.out.println("Connexion Hibernate réussie !");
        } else {System.out.println("Échec de la connexion Hibernate !");
        }

        HibernateUtil.getSessionFactory().close();
    }
}

