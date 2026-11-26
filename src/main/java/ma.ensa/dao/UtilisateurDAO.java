package ma.ensa.dao;

import ma.ensa.model.Utilisateur;
import ma.ensa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

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
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(utilisateur);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {tx.rollback();}
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur", e);
        }
    }

    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Utilisateur utilisateur = session.get(Utilisateur.class, id);
            if (utilisateur != null) {session.remove(utilisateur);}
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {tx.rollback();}
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    //empecher l'admin a modifier un donneur avec un email déjà utilisé par un autre utilisateur
    public boolean emailExiste(String email, Long idExclu) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(u) FROM Utilisateur u WHERE u.email = :email";
            if (idExclu != null) {hql += " AND u.id != :id";}
            var query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            if (idExclu != null) {query.setParameter("id", idExclu);}
            Long nombre = query.uniqueResult();
            return nombre > 0;
        }
    }

    public List<Utilisateur> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Utilisateur",
                    Utilisateur.class).getResultList();
        }
    }
}
