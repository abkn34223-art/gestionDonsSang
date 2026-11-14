package ma.ensa.dao;

import ma.ensa.util.HibernateUtil;
import ma.ensa.model.RendezVous;
import ma.ensa.model.StatutRendezVous;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RendezVousDAO {
    public void ajouter(RendezVous rendezVous){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(rendezVous);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'ajout du rendez-vous", e);
        }
    }
    public void modifier(RendezVous rendezvous){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(rendezvous);
            transaction.commit();
        }catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la modification du rendez vous", e);
        }
    }
    public void supprimer(Long id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            RendezVous rendezVous = session.find(RendezVous.class, id);
            if(rendezVous != null){
                session.remove(rendezVous);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression du rendez vous", e);
        }
    }
    public RendezVous trouverParId(Long id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.find(RendezVous.class, id);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la recherche du rendez vous", e);
        }
    }
    public List<RendezVous> lister(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                    "FROM RendezVous", RendezVous.class
            ).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException( "Erreur lors de la récupération des rendez vous", e);
        }
    }
    public void mettreAJourStatut(Long id, StatutRendezVous newStatut){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            RendezVous rendezVous = session.find(RendezVous.class, id);
            if (rendezVous != null){
                rendezVous.setStatut(newStatut);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du statut de le rendez vous", e);
        }
    }
    public List<RendezVous> listerParCollecte(Long collecteId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery(
                            "FROM RendezVous r WHERE r.collecte.id = :colId",
                            RendezVous.class).setParameter("colId", collecteId)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des rendez-vous de la collecte", e);
        }
    }
    public List<RendezVous> listerParDonneur(Long donneurId){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.createQuery("FROM RendezVous r WHERE r.donneur.id = :donId",
                            RendezVous.class).setParameter("donId", donneurId)
                    .getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des rendez-vous du donneur", e);
        }
    }
}
