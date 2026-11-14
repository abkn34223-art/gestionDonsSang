package ma.ensa.dao;

import ma.ensa.model.Collecte;
import ma.ensa.model.StatutCollecte;
import ma.ensa.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CollecteDAO {
    public void ajouter(Collecte collecte){
        Transaction transaction =null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(collecte);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }
    public void modifier(Collecte collecte){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(collecte);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la modification de la collecte", e);
        }
    }
    public void supprimer(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Collecte collecte = session.find(Collecte.class, id);
            if (collecte != null){
                session.remove(collecte);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de la collecte", e);
        }
    }
    public void mettreAJourStatut(Long id, StatutCollecte newStatut){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Collecte collecte = session.find(Collecte.class, id);
            if (collecte != null){
                collecte.setStatut(newStatut);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du statut de la collecte", e);
        }
    }
    public Collecte trouverParId(Long id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(Collecte.class, id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la recherche de la collecte", e);
        }
    }
    public List<Collecte> lister(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "FROM Collecte", Collecte.class
            ).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException( "Erreur lors de la récupération des collectes", e);
        }
    }
}
