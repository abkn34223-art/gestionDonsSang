package ma.ensa.dao;

import ma.ensa.model.Donneur;
import ma.ensa.util.HibernateUtil;
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
            return session.get(Donneur.class, id);
        }
    }

    public List<Donneur> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Donneur", Donneur.class
            ).list();
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
}
