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
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import objetosNegocio.Materia;
import objetosNegocio.MateriaPlandeestudio;
import objetosNegocio.Plandeestudio;

/**
 *
 * @author angel
 */
public class MateriaPlandeestudioJpaController implements Serializable {

    public MateriaPlandeestudioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MateriaPlandeestudio materiaPlandeestudio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materia = materiaPlandeestudio.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getId());
                materiaPlandeestudio.setMateria(materia);
            }
            Plandeestudio plandeestudio = materiaPlandeestudio.getPlandeestudio();
            if (plandeestudio != null) {
                plandeestudio = em.getReference(plandeestudio.getClass(), plandeestudio.getId());
                materiaPlandeestudio.setPlandeestudio(plandeestudio);
            }
            em.persist(materiaPlandeestudio);
            if (materia != null) {
                materia.getPlanesDeEstudio().add(materiaPlandeestudio);
                materia = em.merge(materia);
            }
            if (plandeestudio != null) {
                plandeestudio.getMaterias().add(materiaPlandeestudio);
                plandeestudio = em.merge(plandeestudio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MateriaPlandeestudio materiaPlandeestudio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriaPlandeestudio persistentMateriaPlandeestudio = em.find(MateriaPlandeestudio.class, materiaPlandeestudio.getId());
            Materia materiaOld = persistentMateriaPlandeestudio.getMateria();
            Materia materiaNew = materiaPlandeestudio.getMateria();
            Plandeestudio plandeestudioOld = persistentMateriaPlandeestudio.getPlandeestudio();
            Plandeestudio plandeestudioNew = materiaPlandeestudio.getPlandeestudio();
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getId());
                materiaPlandeestudio.setMateria(materiaNew);
            }
            if (plandeestudioNew != null) {
                plandeestudioNew = em.getReference(plandeestudioNew.getClass(), plandeestudioNew.getId());
                materiaPlandeestudio.setPlandeestudio(plandeestudioNew);
            }
            materiaPlandeestudio = em.merge(materiaPlandeestudio);
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.getPlanesDeEstudio().remove(materiaPlandeestudio);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.getPlanesDeEstudio().add(materiaPlandeestudio);
                materiaNew = em.merge(materiaNew);
            }
            if (plandeestudioOld != null && !plandeestudioOld.equals(plandeestudioNew)) {
                plandeestudioOld.getMaterias().remove(materiaPlandeestudio);
                plandeestudioOld = em.merge(plandeestudioOld);
            }
            if (plandeestudioNew != null && !plandeestudioNew.equals(plandeestudioOld)) {
                plandeestudioNew.getMaterias().add(materiaPlandeestudio);
                plandeestudioNew = em.merge(plandeestudioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materiaPlandeestudio.getId();
                if (findMateriaPlandeestudio(id) == null) {
                    throw new NonexistentEntityException("The materiaPlandeestudio with id " + id + " no longer exists.");
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
            MateriaPlandeestudio materiaPlandeestudio;
            try {
                materiaPlandeestudio = em.getReference(MateriaPlandeestudio.class, id);
                materiaPlandeestudio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materiaPlandeestudio with id " + id + " no longer exists.", enfe);
            }
            Materia materia = materiaPlandeestudio.getMateria();
            if (materia != null) {
                materia.getPlanesDeEstudio().remove(materiaPlandeestudio);
                materia = em.merge(materia);
            }
            Plandeestudio plandeestudio = materiaPlandeestudio.getPlandeestudio();
            if (plandeestudio != null) {
                plandeestudio.getMaterias().remove(materiaPlandeestudio);
                plandeestudio = em.merge(plandeestudio);
            }
            em.remove(materiaPlandeestudio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MateriaPlandeestudio> findMateriaPlandeestudioEntities() {
        return findMateriaPlandeestudioEntities(true, -1, -1);
    }

    public List<MateriaPlandeestudio> findMateriaPlandeestudioEntities(int maxResults, int firstResult) {
        return findMateriaPlandeestudioEntities(false, maxResults, firstResult);
    }

    private List<MateriaPlandeestudio> findMateriaPlandeestudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MateriaPlandeestudio.class));
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

    public MateriaPlandeestudio findMateriaPlandeestudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MateriaPlandeestudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriaPlandeestudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MateriaPlandeestudio> rt = cq.from(MateriaPlandeestudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Boolean consultarMateriaRepetida(Materia materia, Plandeestudio plan) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM MateriaPlandeestudio c WHERE c.materia = :materia AND c.plandeestudio = :plan");
        consulta.setParameter("materia", materia);
        consulta.setParameter("plan", plan);
        try {
            MateriaPlandeestudio relaMateriaPlan = (MateriaPlandeestudio) consulta.getSingleResult();
            System.out.println("llego hasta abajo");
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * public Usuario consultarUsuarioInicioSesion(String user, String password)
     * { EntityManager em = getEntityManager(); Query consulta =
     * em.createQuery("SELECT c FROM Usuario c WHERE c.user = :user AND
     * c.password = :password"); consulta.setParameter("user", user);
     * consulta.setParameter("password", password); Usuario usuario = (Usuario)
     * consulta.getSingleResult(); return usuario; }
     */
}
