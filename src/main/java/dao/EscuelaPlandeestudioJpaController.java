/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import objetosNegocio.Escuela;
import objetosNegocio.EscuelaPlandeestudio;
import objetosNegocio.Plandeestudio;

/**
 *
 * @author angel
 */
public class EscuelaPlandeestudioJpaController implements Serializable {

    public EscuelaPlandeestudioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EscuelaPlandeestudio escuelaPlandeestudio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escuela escuela = escuelaPlandeestudio.getEscuela();
            if (escuela != null) {
                escuela = em.getReference(escuela.getClass(), escuela.getId());
                escuelaPlandeestudio.setEscuela(escuela);
            }
            Plandeestudio plandeestudio = escuelaPlandeestudio.getPlandeestudio();
            if (plandeestudio != null) {
                plandeestudio = em.getReference(plandeestudio.getClass(), plandeestudio.getId());
                escuelaPlandeestudio.setPlandeestudio(plandeestudio);
            }
            em.persist(escuelaPlandeestudio);
            if (escuela != null) {
                escuela.getPlanesDeEstudio().add(escuelaPlandeestudio);
                escuela = em.merge(escuela);
            }
            if (plandeestudio != null) {
                plandeestudio.getEscuela().add(escuelaPlandeestudio);
                plandeestudio = em.merge(plandeestudio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EscuelaPlandeestudio escuelaPlandeestudio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EscuelaPlandeestudio persistentEscuelaPlandeestudio = em.find(EscuelaPlandeestudio.class, escuelaPlandeestudio.getId());
            Escuela escuelaOld = persistentEscuelaPlandeestudio.getEscuela();
            Escuela escuelaNew = escuelaPlandeestudio.getEscuela();
            Plandeestudio plandeestudioOld = persistentEscuelaPlandeestudio.getPlandeestudio();
            Plandeestudio plandeestudioNew = escuelaPlandeestudio.getPlandeestudio();
            if (escuelaNew != null) {
                escuelaNew = em.getReference(escuelaNew.getClass(), escuelaNew.getId());
                escuelaPlandeestudio.setEscuela(escuelaNew);
            }
            if (plandeestudioNew != null) {
                plandeestudioNew = em.getReference(plandeestudioNew.getClass(), plandeestudioNew.getId());
                escuelaPlandeestudio.setPlandeestudio(plandeestudioNew);
            }
            escuelaPlandeestudio = em.merge(escuelaPlandeestudio);
            if (escuelaOld != null && !escuelaOld.equals(escuelaNew)) {
                escuelaOld.getPlanesDeEstudio().remove(escuelaPlandeestudio);
                escuelaOld = em.merge(escuelaOld);
            }
            if (escuelaNew != null && !escuelaNew.equals(escuelaOld)) {
                escuelaNew.getPlanesDeEstudio().add(escuelaPlandeestudio);
                escuelaNew = em.merge(escuelaNew);
            }
            if (plandeestudioOld != null && !plandeestudioOld.equals(plandeestudioNew)) {
                plandeestudioOld.getEscuela().remove(escuelaPlandeestudio);
                plandeestudioOld = em.merge(plandeestudioOld);
            }
            if (plandeestudioNew != null && !plandeestudioNew.equals(plandeestudioOld)) {
                plandeestudioNew.getEscuela().add(escuelaPlandeestudio);
                plandeestudioNew = em.merge(plandeestudioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = escuelaPlandeestudio.getId();
                if (findEscuelaPlandeestudio(id) == null) {
                    throw new NonexistentEntityException("The escuelaPlandeestudio with id " + id + " no longer exists.");
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
            EscuelaPlandeestudio escuelaPlandeestudio;
            try {
                escuelaPlandeestudio = em.getReference(EscuelaPlandeestudio.class, id);
                escuelaPlandeestudio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escuelaPlandeestudio with id " + id + " no longer exists.", enfe);
            }
            Escuela escuela = escuelaPlandeestudio.getEscuela();
            if (escuela != null) {
                escuela.getPlanesDeEstudio().remove(escuelaPlandeestudio);
                escuela = em.merge(escuela);
            }
            Plandeestudio plandeestudio = escuelaPlandeestudio.getPlandeestudio();
            if (plandeestudio != null) {
                plandeestudio.getEscuela().remove(escuelaPlandeestudio);
                plandeestudio = em.merge(plandeestudio);
            }
            em.remove(escuelaPlandeestudio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EscuelaPlandeestudio> findEscuelaPlandeestudioEntities() {
        return findEscuelaPlandeestudioEntities(true, -1, -1);
    }

    public List<EscuelaPlandeestudio> findEscuelaPlandeestudioEntities(int maxResults, int firstResult) {
        return findEscuelaPlandeestudioEntities(false, maxResults, firstResult);
    }

    private List<EscuelaPlandeestudio> findEscuelaPlandeestudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EscuelaPlandeestudio.class));
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

    public EscuelaPlandeestudio findEscuelaPlandeestudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EscuelaPlandeestudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscuelaPlandeestudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EscuelaPlandeestudio> rt = cq.from(EscuelaPlandeestudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
