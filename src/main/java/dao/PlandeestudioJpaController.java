/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import objetosNegocio.MateriaPlandeestudio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import objetosNegocio.EscuelaPlandeestudio;
import objetosNegocio.Plandeestudio;

/**
 *
 * @author angel
 */
public class PlandeestudioJpaController implements Serializable {

    public PlandeestudioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plandeestudio plandeestudio) {
        if (plandeestudio.getMaterias() == null) {
            plandeestudio.setMaterias(new ArrayList<MateriaPlandeestudio>());
        }
        if (plandeestudio.getEscuela() == null) {
            plandeestudio.setEscuela(new ArrayList<EscuelaPlandeestudio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<MateriaPlandeestudio> attachedMaterias = new ArrayList<MateriaPlandeestudio>();
            for (MateriaPlandeestudio materiasMateriaPlandeestudioToAttach : plandeestudio.getMaterias()) {
                materiasMateriaPlandeestudioToAttach = em.getReference(materiasMateriaPlandeestudioToAttach.getClass(), materiasMateriaPlandeestudioToAttach.getId());
                attachedMaterias.add(materiasMateriaPlandeestudioToAttach);
            }
            plandeestudio.setMaterias(attachedMaterias);
            List<EscuelaPlandeestudio> attachedEscuela = new ArrayList<EscuelaPlandeestudio>();
            for (EscuelaPlandeestudio escuelaEscuelaPlandeestudioToAttach : plandeestudio.getEscuela()) {
                escuelaEscuelaPlandeestudioToAttach = em.getReference(escuelaEscuelaPlandeestudioToAttach.getClass(), escuelaEscuelaPlandeestudioToAttach.getId());
                attachedEscuela.add(escuelaEscuelaPlandeestudioToAttach);
            }
            plandeestudio.setEscuela(attachedEscuela);
            em.persist(plandeestudio);
            for (MateriaPlandeestudio materiasMateriaPlandeestudio : plandeestudio.getMaterias()) {
                Plandeestudio oldPlandeestudioOfMateriasMateriaPlandeestudio = materiasMateriaPlandeestudio.getPlandeestudio();
                materiasMateriaPlandeestudio.setPlandeestudio(plandeestudio);
                materiasMateriaPlandeestudio = em.merge(materiasMateriaPlandeestudio);
                if (oldPlandeestudioOfMateriasMateriaPlandeestudio != null) {
                    oldPlandeestudioOfMateriasMateriaPlandeestudio.getMaterias().remove(materiasMateriaPlandeestudio);
                    oldPlandeestudioOfMateriasMateriaPlandeestudio = em.merge(oldPlandeestudioOfMateriasMateriaPlandeestudio);
                }
            }
            for (EscuelaPlandeestudio escuelaEscuelaPlandeestudio : plandeestudio.getEscuela()) {
                Plandeestudio oldPlandeestudioOfEscuelaEscuelaPlandeestudio = escuelaEscuelaPlandeestudio.getPlandeestudio();
                escuelaEscuelaPlandeestudio.setPlandeestudio(plandeestudio);
                escuelaEscuelaPlandeestudio = em.merge(escuelaEscuelaPlandeestudio);
                if (oldPlandeestudioOfEscuelaEscuelaPlandeestudio != null) {
                    oldPlandeestudioOfEscuelaEscuelaPlandeestudio.getEscuela().remove(escuelaEscuelaPlandeestudio);
                    oldPlandeestudioOfEscuelaEscuelaPlandeestudio = em.merge(oldPlandeestudioOfEscuelaEscuelaPlandeestudio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plandeestudio plandeestudio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plandeestudio persistentPlandeestudio = em.find(Plandeestudio.class, plandeestudio.getId());
            List<MateriaPlandeestudio> materiasOld = persistentPlandeestudio.getMaterias();
            List<MateriaPlandeestudio> materiasNew = plandeestudio.getMaterias();
            List<EscuelaPlandeestudio> escuelaOld = persistentPlandeestudio.getEscuela();
            List<EscuelaPlandeestudio> escuelaNew = plandeestudio.getEscuela();
            List<String> illegalOrphanMessages = null;
            for (MateriaPlandeestudio materiasOldMateriaPlandeestudio : materiasOld) {
                if (!materiasNew.contains(materiasOldMateriaPlandeestudio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriaPlandeestudio " + materiasOldMateriaPlandeestudio + " since its plandeestudio field is not nullable.");
                }
            }
            for (EscuelaPlandeestudio escuelaOldEscuelaPlandeestudio : escuelaOld) {
                if (!escuelaNew.contains(escuelaOldEscuelaPlandeestudio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EscuelaPlandeestudio " + escuelaOldEscuelaPlandeestudio + " since its plandeestudio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<MateriaPlandeestudio> attachedMateriasNew = new ArrayList<MateriaPlandeestudio>();
            for (MateriaPlandeestudio materiasNewMateriaPlandeestudioToAttach : materiasNew) {
                materiasNewMateriaPlandeestudioToAttach = em.getReference(materiasNewMateriaPlandeestudioToAttach.getClass(), materiasNewMateriaPlandeestudioToAttach.getId());
                attachedMateriasNew.add(materiasNewMateriaPlandeestudioToAttach);
            }
            materiasNew = attachedMateriasNew;
            plandeestudio.setMaterias(materiasNew);
            List<EscuelaPlandeestudio> attachedEscuelaNew = new ArrayList<EscuelaPlandeestudio>();
            for (EscuelaPlandeestudio escuelaNewEscuelaPlandeestudioToAttach : escuelaNew) {
                escuelaNewEscuelaPlandeestudioToAttach = em.getReference(escuelaNewEscuelaPlandeestudioToAttach.getClass(), escuelaNewEscuelaPlandeestudioToAttach.getId());
                attachedEscuelaNew.add(escuelaNewEscuelaPlandeestudioToAttach);
            }
            escuelaNew = attachedEscuelaNew;
            plandeestudio.setEscuela(escuelaNew);
            plandeestudio = em.merge(plandeestudio);
            for (MateriaPlandeestudio materiasNewMateriaPlandeestudio : materiasNew) {
                if (!materiasOld.contains(materiasNewMateriaPlandeestudio)) {
                    Plandeestudio oldPlandeestudioOfMateriasNewMateriaPlandeestudio = materiasNewMateriaPlandeestudio.getPlandeestudio();
                    materiasNewMateriaPlandeestudio.setPlandeestudio(plandeestudio);
                    materiasNewMateriaPlandeestudio = em.merge(materiasNewMateriaPlandeestudio);
                    if (oldPlandeestudioOfMateriasNewMateriaPlandeestudio != null && !oldPlandeestudioOfMateriasNewMateriaPlandeestudio.equals(plandeestudio)) {
                        oldPlandeestudioOfMateriasNewMateriaPlandeestudio.getMaterias().remove(materiasNewMateriaPlandeestudio);
                        oldPlandeestudioOfMateriasNewMateriaPlandeestudio = em.merge(oldPlandeestudioOfMateriasNewMateriaPlandeestudio);
                    }
                }
            }
            for (EscuelaPlandeestudio escuelaNewEscuelaPlandeestudio : escuelaNew) {
                if (!escuelaOld.contains(escuelaNewEscuelaPlandeestudio)) {
                    Plandeestudio oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio = escuelaNewEscuelaPlandeestudio.getPlandeestudio();
                    escuelaNewEscuelaPlandeestudio.setPlandeestudio(plandeestudio);
                    escuelaNewEscuelaPlandeestudio = em.merge(escuelaNewEscuelaPlandeestudio);
                    if (oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio != null && !oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio.equals(plandeestudio)) {
                        oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio.getEscuela().remove(escuelaNewEscuelaPlandeestudio);
                        oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio = em.merge(oldPlandeestudioOfEscuelaNewEscuelaPlandeestudio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plandeestudio.getId();
                if (findPlandeestudio(id) == null) {
                    throw new NonexistentEntityException("The plandeestudio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plandeestudio plandeestudio;
            try {
                plandeestudio = em.getReference(Plandeestudio.class, id);
                plandeestudio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plandeestudio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MateriaPlandeestudio> materiasOrphanCheck = plandeestudio.getMaterias();
            for (MateriaPlandeestudio materiasOrphanCheckMateriaPlandeestudio : materiasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plandeestudio (" + plandeestudio + ") cannot be destroyed since the MateriaPlandeestudio " + materiasOrphanCheckMateriaPlandeestudio + " in its materias field has a non-nullable plandeestudio field.");
            }
            List<EscuelaPlandeestudio> escuelaOrphanCheck = plandeestudio.getEscuela();
            for (EscuelaPlandeestudio escuelaOrphanCheckEscuelaPlandeestudio : escuelaOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plandeestudio (" + plandeestudio + ") cannot be destroyed since the EscuelaPlandeestudio " + escuelaOrphanCheckEscuelaPlandeestudio + " in its escuela field has a non-nullable plandeestudio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(plandeestudio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plandeestudio> findPlandeestudioEntities() {
        return findPlandeestudioEntities(true, -1, -1);
    }

    public List<Plandeestudio> findPlandeestudioEntities(int maxResults, int firstResult) {
        return findPlandeestudioEntities(false, maxResults, firstResult);
    }

    private List<Plandeestudio> findPlandeestudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plandeestudio.class));
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

    public Plandeestudio findPlandeestudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plandeestudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlandeestudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plandeestudio> rt = cq.from(Plandeestudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Plandeestudio consultarPorNombre(String nombre) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Plandeestudio c WHERE c.nombre = :nombre");
        consulta.setParameter("nombre", nombre);
        Plandeestudio planEstudio = (Plandeestudio) consulta.getSingleResult();
        return planEstudio;
    }

}
