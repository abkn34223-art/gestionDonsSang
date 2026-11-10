
package ma.ensa.dao;

import ma.ensa.model.RendezVous;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.ensa.util.HibernateUtil;
import java.util.List;

public class RendezVousDAO {

    public void ajouter(RendezVous rendezVous) {
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.persist(rendezVous);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    public List<RendezVous> lister() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "SELECT r FROM RendezVous r " +
                            "LEFT JOIN FETCH r.collecte " +
                            "LEFT JOIN FETCH r.donneur",
                    RendezVous.class
            ).list();
        }
    }

    public RendezVous trouverParId(Long id) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "SELECT r FROM RendezVous r " +
                                    "LEFT JOIN FETCH r.collecte " +
                                    "LEFT JOIN FETCH r.donneur " +
                                    "WHERE r.id = :id",
                            RendezVous.class
                    ).setParameter("id", id)
                    .uniqueResult();
        }
    }

    public List<RendezVous> trouverParDonneur(Long donneurId) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "FROM RendezVous r WHERE r.donneur.id = :id",
                            RendezVous.class
                    ).setParameter("id", donneurId)
                    .list();
        }
    }

    public List<RendezVous> trouverParCollecte(Long collecteId) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                            "FROM RendezVous r WHERE r.collecte.id = :id",
                            RendezVous.class
                    ).setParameter("id", collecteId)
                    .list();
        }
    }

    public void modifier(RendezVous rendezVous) {
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.merge(rendezVous);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la modification", e);
        }
    }
}
