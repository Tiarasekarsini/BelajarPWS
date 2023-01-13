/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.Belajar;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ws.a.Belajar.exceptions.NonexistentEntityException;
import ws.a.Belajar.exceptions.PreexistingEntityException;

/**
 *
 * @author ASUS
 */
public class SayuranJpaController implements Serializable {

    public SayuranJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ws.a_Belajar_jar_0.0.1-SNAPSHOTPU");
    
    public SayuranJpaController(){}

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sayuran sayuran) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(sayuran);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSayuran(sayuran.getId()) != null) {
                throw new PreexistingEntityException("Sayuran " + sayuran + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sayuran sayuran) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            sayuran = em.merge(sayuran);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sayuran.getId();
                if (findSayuran(id) == null) {
                    throw new NonexistentEntityException("The sayuran with id " + id + " no longer exists.");
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
            Sayuran sayuran;
            try {
                sayuran = em.getReference(Sayuran.class, id);
                sayuran.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sayuran with id " + id + " no longer exists.", enfe);
            }
            em.remove(sayuran);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sayuran> findSayuranEntities() {
        return findSayuranEntities(true, -1, -1);
    }

    public List<Sayuran> findSayuranEntities(int maxResults, int firstResult) {
        return findSayuranEntities(false, maxResults, firstResult);
    }

    private List<Sayuran> findSayuranEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sayuran.class));
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

    public Sayuran findSayuran(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sayuran.class, id);
        } finally {
            em.close();
        }
    }

    public int getSayuranCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sayuran> rt = cq.from(Sayuran.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
