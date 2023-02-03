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
import objetosNegocio.Materia;
import objetosNegocio.MateriasSerializacion;

/**
 *
 * @author angel
 */
public class MateriasSerializacionJpaController implements Serializable {

    public MateriasSerializacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MateriasSerializacion materiasSerializacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia materia = materiasSerializacion.getMateria();
            if (materia != null) {
                materia = em.getReference(materia.getClass(), materia.getId());
                materiasSerializacion.setMateria(materia);
            }
            Materia materiaSeriada = materiasSerializacion.getMateriaSeriada();
            if (materiaSeriada != null) {
                materiaSeriada = em.getReference(materiaSeriada.getClass(), materiaSeriada.getId());
                materiasSerializacion.setMateriaSeriada(materiaSeriada);
            }
            em.persist(materiasSerializacion);
            if (materia != null) {
                materia.getMaterias().add(materiasSerializacion);
                materia = em.merge(materia);
            }
            if (materiaSeriada != null) {
                materiaSeriada.getMaterias().add(materiasSerializacion);
                materiaSeriada = em.merge(materiaSeriada);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MateriasSerializacion materiasSerializacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MateriasSerializacion persistentMateriasSerializacion = em.find(MateriasSerializacion.class, materiasSerializacion.getId());
            Materia materiaOld = persistentMateriasSerializacion.getMateria();
            Materia materiaNew = materiasSerializacion.getMateria();
            Materia materiaSeriadaOld = persistentMateriasSerializacion.getMateriaSeriada();
            Materia materiaSeriadaNew = materiasSerializacion.getMateriaSeriada();
            if (materiaNew != null) {
                materiaNew = em.getReference(materiaNew.getClass(), materiaNew.getId());
                materiasSerializacion.setMateria(materiaNew);
            }
            if (materiaSeriadaNew != null) {
                materiaSeriadaNew = em.getReference(materiaSeriadaNew.getClass(), materiaSeriadaNew.getId());
                materiasSerializacion.setMateriaSeriada(materiaSeriadaNew);
            }
            materiasSerializacion = em.merge(materiasSerializacion);
            if (materiaOld != null && !materiaOld.equals(materiaNew)) {
                materiaOld.getMaterias().remove(materiasSerializacion);
                materiaOld = em.merge(materiaOld);
            }
            if (materiaNew != null && !materiaNew.equals(materiaOld)) {
                materiaNew.getMaterias().add(materiasSerializacion);
                materiaNew = em.merge(materiaNew);
            }
            if (materiaSeriadaOld != null && !materiaSeriadaOld.equals(materiaSeriadaNew)) {
                materiaSeriadaOld.getMaterias().remove(materiasSerializacion);
                materiaSeriadaOld = em.merge(materiaSeriadaOld);
            }
            if (materiaSeriadaNew != null && !materiaSeriadaNew.equals(materiaSeriadaOld)) {
                materiaSeriadaNew.getMaterias().add(materiasSerializacion);
                materiaSeriadaNew = em.merge(materiaSeriadaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materiasSerializacion.getId();
                if (findMateriasSerializacion(id) == null) {
                    throw new NonexistentEntityException("The materiasSerializacion with id " + id + " no longer exists.");
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
            MateriasSerializacion materiasSerializacion;
            try {
                materiasSerializacion = em.getReference(MateriasSerializacion.class, id);
                materiasSerializacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materiasSerializacion with id " + id + " no longer exists.", enfe);
            }
            Materia materia = materiasSerializacion.getMateria();
            if (materia != null) {
                materia.getMaterias().remove(materiasSerializacion);
                materia = em.merge(materia);
            }
            Materia materiaSeriada = materiasSerializacion.getMateriaSeriada();
            if (materiaSeriada != null) {
                materiaSeriada.getMaterias().remove(materiasSerializacion);
                materiaSeriada = em.merge(materiaSeriada);
            }
            em.remove(materiasSerializacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MateriasSerializacion> findMateriasSerializacionEntities() {
        return findMateriasSerializacionEntities(true, -1, -1);
    }

    public List<MateriasSerializacion> findMateriasSerializacionEntities(int maxResults, int firstResult) {
        return findMateriasSerializacionEntities(false, maxResults, firstResult);
    }

    private List<MateriasSerializacion> findMateriasSerializacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MateriasSerializacion.class));
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

    public MateriasSerializacion findMateriasSerializacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MateriasSerializacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getMateriasSerializacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MateriasSerializacion> rt = cq.from(MateriasSerializacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
