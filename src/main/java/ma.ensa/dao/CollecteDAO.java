
package ma.ensa.dao;

import ma.ensa.model.Collecte;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.ensa.util.HibernateUtil;
import java.util.List;

public class CollecteDAO {

    public void ajouter(Collecte collecte) {
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.persist(collecte);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    public List<Collecte> lister() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.createQuery(
                    "FROM Collecte ORDER BY dateCollecte",
                    Collecte.class
            ).list();
        }
    }

    public Collecte trouverParId(Long id) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Collecte.class, id);
        }
    }

    public void modifier(Collecte collecte) {
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.merge(collecte);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la modification", e);
        }
    }

    public void supprimer(Long id) {
        Transaction transaction = null;

        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            Collecte collecte = session.get(Collecte.class, id);

            if (collecte != null) {
                session.remove(collecte);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression", e);
        }
    }
}
