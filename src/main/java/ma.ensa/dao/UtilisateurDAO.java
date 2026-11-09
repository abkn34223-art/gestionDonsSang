package ma.ensa.dao;

import ma.ensa.model.Utilisateur;
import ma.ensa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UtilisateurDAO {

    public void save(Utilisateur utilisateur) {
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de l'ajout de l'utilisateur", e);
        }
    }

    public Utilisateur findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Utilisateur.class, id);
        }
    }

    public Utilisateur findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Utilisateur u WHERE u.email = :email",
                            Utilisateur.class
                    ).setParameter("email", email)
                    .uniqueResult();
        }
    }

    public void update(Utilisateur utilisateur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(utilisateur);
            tx.commit();
        }
    }

    public void delete(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Utilisateur utilisateur = session.get(Utilisateur.class, id);

            if (utilisateur != null) {
                session.remove(utilisateur);
            }

            tx.commit();
        }
    }
}
