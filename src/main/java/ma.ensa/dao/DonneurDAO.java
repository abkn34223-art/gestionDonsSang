package ma.ensa.dao;

import ma.ensa.model.Donneur;
import ma.ensa.util.HibernateUtil;
import ma.ensa.model.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DonneurDAO {

    public void save(Donneur donneur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(donneur);
            tx.commit();
        }
    }

    public Donneur findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT d FROM Donneur d JOIN FETCH d.utilisateur WHERE d.id = :id",
                    Donneur.class).setParameter("id", id).uniqueResult();
        }
    }

    public List<Donneur> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT d FROM Donneur d JOIN FETCH d.utilisateur",
                    Donneur.class).list();
        }
    }

    public Donneur findByUtilisateurId(Long utilisateurId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Donneur d WHERE d.utilisateur.id = :id",
                            Donneur.class
                    ).setParameter("id", utilisateurId)
                    .uniqueResult();
        }
    }

    public void update(Donneur donneur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(donneur);
            tx.commit();
        }
    }
    public void supprimerDonneurEtUtilisateur(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Donneur donneur = session.get(Donneur.class, id);
            if (donneur != null) {Utilisateur utilisateur = donneur.getUtilisateur();
                session.remove(donneur);
                session.remove(utilisateur);}
            tx.commit();
        } catch (Exception e) {if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression du donneur", e);
        }
    }
}
