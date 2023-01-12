/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wsc.UASwsc;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import wsc.UASwsc.exceptions.NonexistentEntityException;
import wsc.UASwsc.exceptions.PreexistingEntityException;

/**
 *
 * @author DELL
 */
public class Biodata7JpaController implements Serializable {

    public Biodata7JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("wsc_UASwsc_jar_0.0.1-SNAPSHOTPU");
    
    public Biodata7JpaController(){}
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Biodata7 biodata7) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(biodata7);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBiodata7(biodata7.getId()) != null) {
                throw new PreexistingEntityException("Biodata7 " + biodata7 + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Biodata7 biodata7) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            biodata7 = em.merge(biodata7);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = biodata7.getId();
                if (findBiodata7(id) == null) {
                    throw new NonexistentEntityException("The biodata7 with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Biodata7 biodata7;
            try {
                biodata7 = em.getReference(Biodata7.class, id);
                biodata7.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The biodata7 with id " + id + " no longer exists.", enfe);
            }
            em.remove(biodata7);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Biodata7> findBiodata7Entities() {
        return findBiodata7Entities(true, -1, -1);
    }

    public List<Biodata7> findBiodata7Entities(int maxResults, int firstResult) {
        return findBiodata7Entities(false, maxResults, firstResult);
    }

    private List<Biodata7> findBiodata7Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Biodata7.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Biodata7 findBiodata7(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Biodata7.class, id);
        } finally {
            em.close();
        }
    }

    public int getBiodata7Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Biodata7> rt = cq.from(Biodata7.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
