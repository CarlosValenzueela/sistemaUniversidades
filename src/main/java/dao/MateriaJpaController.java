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
import objetosNegocio.Calificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import objetosNegocio.Materia;
import objetosNegocio.MateriaPlandeestudio;
import objetosNegocio.MateriasSerializacion;

/**
 *
 * @author angel
 */
public class MateriaJpaController implements Serializable {

    public MateriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Materia materia) {
        if (materia.getCalificaciones() == null) {
            materia.setCalificaciones(new ArrayList<Calificacion>());
        }
        if (materia.getPlanesDeEstudio() == null) {
            materia.setPlanesDeEstudio(new ArrayList<MateriaPlandeestudio>());
        }
        if (materia.getMaterias() == null) {
            materia.setMaterias(new ArrayList<MateriasSerializacion>());
        }
        if (materia.getMateriasSeriadas() == null) {
            materia.setMateriasSeriadas(new ArrayList<MateriasSerializacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Calificacion> attachedCalificaciones = new ArrayList<Calificacion>();
            for (Calificacion calificacionesCalificacionToAttach : materia.getCalificaciones()) {
                calificacionesCalificacionToAttach = em.getReference(calificacionesCalificacionToAttach.getClass(), calificacionesCalificacionToAttach.getId());
                attachedCalificaciones.add(calificacionesCalificacionToAttach);
            }
            materia.setCalificaciones(attachedCalificaciones);
            List<MateriaPlandeestudio> attachedPlanesDeEstudio = new ArrayList<MateriaPlandeestudio>();
            for (MateriaPlandeestudio planesDeEstudioMateriaPlandeestudioToAttach : materia.getPlanesDeEstudio()) {
                planesDeEstudioMateriaPlandeestudioToAttach = em.getReference(planesDeEstudioMateriaPlandeestudioToAttach.getClass(), planesDeEstudioMateriaPlandeestudioToAttach.getId());
                attachedPlanesDeEstudio.add(planesDeEstudioMateriaPlandeestudioToAttach);
            }
            materia.setPlanesDeEstudio(attachedPlanesDeEstudio);
            List<MateriasSerializacion> attachedMaterias = new ArrayList<MateriasSerializacion>();
            for (MateriasSerializacion materiasMateriasSerializacionToAttach : materia.getMaterias()) {
                materiasMateriasSerializacionToAttach = em.getReference(materiasMateriasSerializacionToAttach.getClass(), materiasMateriasSerializacionToAttach.getId());
                attachedMaterias.add(materiasMateriasSerializacionToAttach);
            }
            materia.setMaterias(attachedMaterias);
            List<MateriasSerializacion> attachedMateriasSeriadas = new ArrayList<MateriasSerializacion>();
            for (MateriasSerializacion materiasSeriadasMateriasSerializacionToAttach : materia.getMateriasSeriadas()) {
                materiasSeriadasMateriasSerializacionToAttach = em.getReference(materiasSeriadasMateriasSerializacionToAttach.getClass(), materiasSeriadasMateriasSerializacionToAttach.getId());
                attachedMateriasSeriadas.add(materiasSeriadasMateriasSerializacionToAttach);
            }
            materia.setMateriasSeriadas(attachedMateriasSeriadas);
            em.persist(materia);
            for (Calificacion calificacionesCalificacion : materia.getCalificaciones()) {
                Materia oldMateriaOfCalificacionesCalificacion = calificacionesCalificacion.getMateria();
                calificacionesCalificacion.setMateria(materia);
                calificacionesCalificacion = em.merge(calificacionesCalificacion);
                if (oldMateriaOfCalificacionesCalificacion != null) {
                    oldMateriaOfCalificacionesCalificacion.getCalificaciones().remove(calificacionesCalificacion);
                    oldMateriaOfCalificacionesCalificacion = em.merge(oldMateriaOfCalificacionesCalificacion);
                }
            }
            for (MateriaPlandeestudio planesDeEstudioMateriaPlandeestudio : materia.getPlanesDeEstudio()) {
                Materia oldMateriaOfPlanesDeEstudioMateriaPlandeestudio = planesDeEstudioMateriaPlandeestudio.getMateria();
                planesDeEstudioMateriaPlandeestudio.setMateria(materia);
                planesDeEstudioMateriaPlandeestudio = em.merge(planesDeEstudioMateriaPlandeestudio);
                if (oldMateriaOfPlanesDeEstudioMateriaPlandeestudio != null) {
                    oldMateriaOfPlanesDeEstudioMateriaPlandeestudio.getPlanesDeEstudio().remove(planesDeEstudioMateriaPlandeestudio);
                    oldMateriaOfPlanesDeEstudioMateriaPlandeestudio = em.merge(oldMateriaOfPlanesDeEstudioMateriaPlandeestudio);
                }
            }
            for (MateriasSerializacion materiasMateriasSerializacion : materia.getMaterias()) {
                Materia oldMateriaOfMateriasMateriasSerializacion = materiasMateriasSerializacion.getMateria();
                materiasMateriasSerializacion.setMateria(materia);
                materiasMateriasSerializacion = em.merge(materiasMateriasSerializacion);
                if (oldMateriaOfMateriasMateriasSerializacion != null) {
                    oldMateriaOfMateriasMateriasSerializacion.getMaterias().remove(materiasMateriasSerializacion);
                    oldMateriaOfMateriasMateriasSerializacion = em.merge(oldMateriaOfMateriasMateriasSerializacion);
                }
            }
            for (MateriasSerializacion materiasSeriadasMateriasSerializacion : materia.getMateriasSeriadas()) {
                Materia oldMateriaSeriadaOfMateriasSeriadasMateriasSerializacion = materiasSeriadasMateriasSerializacion.getMateriaSeriada();
                materiasSeriadasMateriasSerializacion.setMateriaSeriada(materia);
                materiasSeriadasMateriasSerializacion = em.merge(materiasSeriadasMateriasSerializacion);
                if (oldMateriaSeriadaOfMateriasSeriadasMateriasSerializacion != null) {
                    oldMateriaSeriadaOfMateriasSeriadasMateriasSerializacion.getMateriasSeriadas().remove(materiasSeriadasMateriasSerializacion);
                    oldMateriaSeriadaOfMateriasSeriadasMateriasSerializacion = em.merge(oldMateriaSeriadaOfMateriasSeriadasMateriasSerializacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Materia persistentMateria = em.find(Materia.class, materia.getId());
            List<Calificacion> calificacionesOld = persistentMateria.getCalificaciones();
            List<Calificacion> calificacionesNew = materia.getCalificaciones();
            List<MateriaPlandeestudio> planesDeEstudioOld = persistentMateria.getPlanesDeEstudio();
            List<MateriaPlandeestudio> planesDeEstudioNew = materia.getPlanesDeEstudio();
            List<MateriasSerializacion> materiasOld = persistentMateria.getMaterias();
            List<MateriasSerializacion> materiasNew = materia.getMaterias();
            List<MateriasSerializacion> materiasSeriadasOld = persistentMateria.getMateriasSeriadas();
            List<MateriasSerializacion> materiasSeriadasNew = materia.getMateriasSeriadas();
            List<String> illegalOrphanMessages = null;
            for (Calificacion calificacionesOldCalificacion : calificacionesOld) {
                if (!calificacionesNew.contains(calificacionesOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionesOldCalificacion + " since its materia field is not nullable.");
                }
            }
            for (MateriaPlandeestudio planesDeEstudioOldMateriaPlandeestudio : planesDeEstudioOld) {
                if (!planesDeEstudioNew.contains(planesDeEstudioOldMateriaPlandeestudio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriaPlandeestudio " + planesDeEstudioOldMateriaPlandeestudio + " since its materia field is not nullable.");
                }
            }
            for (MateriasSerializacion materiasOldMateriasSerializacion : materiasOld) {
                if (!materiasNew.contains(materiasOldMateriasSerializacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriasSerializacion " + materiasOldMateriasSerializacion + " since its materia field is not nullable.");
                }
            }
            for (MateriasSerializacion materiasSeriadasOldMateriasSerializacion : materiasSeriadasOld) {
                if (!materiasSeriadasNew.contains(materiasSeriadasOldMateriasSerializacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MateriasSerializacion " + materiasSeriadasOldMateriasSerializacion + " since its materiaSeriada field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Calificacion> attachedCalificacionesNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionesNewCalificacionToAttach : calificacionesNew) {
                calificacionesNewCalificacionToAttach = em.getReference(calificacionesNewCalificacionToAttach.getClass(), calificacionesNewCalificacionToAttach.getId());
                attachedCalificacionesNew.add(calificacionesNewCalificacionToAttach);
            }
            calificacionesNew = attachedCalificacionesNew;
            materia.setCalificaciones(calificacionesNew);
            List<MateriaPlandeestudio> attachedPlanesDeEstudioNew = new ArrayList<MateriaPlandeestudio>();
            for (MateriaPlandeestudio planesDeEstudioNewMateriaPlandeestudioToAttach : planesDeEstudioNew) {
                planesDeEstudioNewMateriaPlandeestudioToAttach = em.getReference(planesDeEstudioNewMateriaPlandeestudioToAttach.getClass(), planesDeEstudioNewMateriaPlandeestudioToAttach.getId());
                attachedPlanesDeEstudioNew.add(planesDeEstudioNewMateriaPlandeestudioToAttach);
            }
            planesDeEstudioNew = attachedPlanesDeEstudioNew;
            materia.setPlanesDeEstudio(planesDeEstudioNew);
            List<MateriasSerializacion> attachedMateriasNew = new ArrayList<MateriasSerializacion>();
            for (MateriasSerializacion materiasNewMateriasSerializacionToAttach : materiasNew) {
                materiasNewMateriasSerializacionToAttach = em.getReference(materiasNewMateriasSerializacionToAttach.getClass(), materiasNewMateriasSerializacionToAttach.getId());
                attachedMateriasNew.add(materiasNewMateriasSerializacionToAttach);
            }
            materiasNew = attachedMateriasNew;
            materia.setMaterias(materiasNew);
            List<MateriasSerializacion> attachedMateriasSeriadasNew = new ArrayList<MateriasSerializacion>();
            for (MateriasSerializacion materiasSeriadasNewMateriasSerializacionToAttach : materiasSeriadasNew) {
                materiasSeriadasNewMateriasSerializacionToAttach = em.getReference(materiasSeriadasNewMateriasSerializacionToAttach.getClass(), materiasSeriadasNewMateriasSerializacionToAttach.getId());
                attachedMateriasSeriadasNew.add(materiasSeriadasNewMateriasSerializacionToAttach);
            }
            materiasSeriadasNew = attachedMateriasSeriadasNew;
            materia.setMateriasSeriadas(materiasSeriadasNew);
            materia = em.merge(materia);
            for (Calificacion calificacionesNewCalificacion : calificacionesNew) {
                if (!calificacionesOld.contains(calificacionesNewCalificacion)) {
                    Materia oldMateriaOfCalificacionesNewCalificacion = calificacionesNewCalificacion.getMateria();
                    calificacionesNewCalificacion.setMateria(materia);
                    calificacionesNewCalificacion = em.merge(calificacionesNewCalificacion);
                    if (oldMateriaOfCalificacionesNewCalificacion != null && !oldMateriaOfCalificacionesNewCalificacion.equals(materia)) {
                        oldMateriaOfCalificacionesNewCalificacion.getCalificaciones().remove(calificacionesNewCalificacion);
                        oldMateriaOfCalificacionesNewCalificacion = em.merge(oldMateriaOfCalificacionesNewCalificacion);
                    }
                }
            }
            for (MateriaPlandeestudio planesDeEstudioNewMateriaPlandeestudio : planesDeEstudioNew) {
                if (!planesDeEstudioOld.contains(planesDeEstudioNewMateriaPlandeestudio)) {
                    Materia oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio = planesDeEstudioNewMateriaPlandeestudio.getMateria();
                    planesDeEstudioNewMateriaPlandeestudio.setMateria(materia);
                    planesDeEstudioNewMateriaPlandeestudio = em.merge(planesDeEstudioNewMateriaPlandeestudio);
                    if (oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio != null && !oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio.equals(materia)) {
                        oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio.getPlanesDeEstudio().remove(planesDeEstudioNewMateriaPlandeestudio);
                        oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio = em.merge(oldMateriaOfPlanesDeEstudioNewMateriaPlandeestudio);
                    }
                }
            }
            for (MateriasSerializacion materiasNewMateriasSerializacion : materiasNew) {
                if (!materiasOld.contains(materiasNewMateriasSerializacion)) {
                    Materia oldMateriaOfMateriasNewMateriasSerializacion = materiasNewMateriasSerializacion.getMateria();
                    materiasNewMateriasSerializacion.setMateria(materia);
                    materiasNewMateriasSerializacion = em.merge(materiasNewMateriasSerializacion);
                    if (oldMateriaOfMateriasNewMateriasSerializacion != null && !oldMateriaOfMateriasNewMateriasSerializacion.equals(materia)) {
                        oldMateriaOfMateriasNewMateriasSerializacion.getMaterias().remove(materiasNewMateriasSerializacion);
                        oldMateriaOfMateriasNewMateriasSerializacion = em.merge(oldMateriaOfMateriasNewMateriasSerializacion);
                    }
                }
            }
            for (MateriasSerializacion materiasSeriadasNewMateriasSerializacion : materiasSeriadasNew) {
                if (!materiasSeriadasOld.contains(materiasSeriadasNewMateriasSerializacion)) {
                    Materia oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion = materiasSeriadasNewMateriasSerializacion.getMateriaSeriada();
                    materiasSeriadasNewMateriasSerializacion.setMateriaSeriada(materia);
                    materiasSeriadasNewMateriasSerializacion = em.merge(materiasSeriadasNewMateriasSerializacion);
                    if (oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion != null && !oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion.equals(materia)) {
                        oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion.getMateriasSeriadas().remove(materiasSeriadasNewMateriasSerializacion);
                        oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion = em.merge(oldMateriaSeriadaOfMateriasSeriadasNewMateriasSerializacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materia.getId();
                if (findMateria(id) == null) {
                    throw new NonexistentEntityException("The materia with id " + id + " no longer exists.");
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
            Materia materia;
            try {
                materia = em.getReference(Materia.class, id);
                materia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Calificacion> calificacionesOrphanCheck = materia.getCalificaciones();
            for (Calificacion calificacionesOrphanCheckCalificacion : calificacionesOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the Calificacion " + calificacionesOrphanCheckCalificacion + " in its calificaciones field has a non-nullable materia field.");
            }
            List<MateriaPlandeestudio> planesDeEstudioOrphanCheck = materia.getPlanesDeEstudio();
            for (MateriaPlandeestudio planesDeEstudioOrphanCheckMateriaPlandeestudio : planesDeEstudioOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the MateriaPlandeestudio " + planesDeEstudioOrphanCheckMateriaPlandeestudio + " in its planesDeEstudio field has a non-nullable materia field.");
            }
            List<MateriasSerializacion> materiasOrphanCheck = materia.getMaterias();
            for (MateriasSerializacion materiasOrphanCheckMateriasSerializacion : materiasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the MateriasSerializacion " + materiasOrphanCheckMateriasSerializacion + " in its materias field has a non-nullable materia field.");
            }
            List<MateriasSerializacion> materiasSeriadasOrphanCheck = materia.getMateriasSeriadas();
            for (MateriasSerializacion materiasSeriadasOrphanCheckMateriasSerializacion : materiasSeriadasOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Materia (" + materia + ") cannot be destroyed since the MateriasSerializacion " + materiasSeriadasOrphanCheckMateriasSerializacion + " in its materiasSeriadas field has a non-nullable materiaSeriada field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(materia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Materia> findMateriaEntities() {
        return findMateriaEntities(true, -1, -1);
    }

    public List<Materia> findMateriaEntities(int maxResults, int firstResult) {
        return findMateriaEntities(false, maxResults, firstResult);
    }

    private List<Materia> findMateriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Materia.class));
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

    public Materia findMateria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Materia.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Materia> consultarMateriasClave(String clave) {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Materia c WHERE c.clave = :clave");
        consulta.setParameter("clave", clave);
        List<Materia> materias = consulta.getResultList();
        return materias;
    }
    
    public List<Materia> consultarMateriasNombre(String nombre) {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Materia c WHERE c.nombre = :nombre");
        consulta.setParameter("nombre", nombre);
        List<Materia> materias = consulta.getResultList();
        return materias;
    }

    public int getMateriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Materia> rt = cq.from(Materia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Materia consultarPorClave(String clave) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Materia c WHERE c.clave = :clave");
        consulta.setParameter("clave", clave);
        Materia materiaConsultada = (Materia) consulta.getSingleResult();
        return materiaConsultada;
    }

    public Materia consultarPorNombre(String nombre) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Materia c WHERE c.nombre = :nombre");
        consulta.setParameter("nombre", nombre);
        Materia materiaConsultada = (Materia) consulta.getSingleResult();
        return materiaConsultada;
    }

}
