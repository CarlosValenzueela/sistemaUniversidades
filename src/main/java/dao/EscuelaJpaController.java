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
import objetosNegocio.EscuelaPlandeestudio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import objetosNegocio.Escuela;
import objetosNegocio.Usuario;

/**
 *
 * @author angel
 */
public class EscuelaJpaController implements Serializable {

    public EscuelaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Escuela escuela) {
        if (escuela.getPlanesDeEstudio() == null) {
            escuela.setPlanesDeEstudio(new ArrayList<EscuelaPlandeestudio>());
        }
        if (escuela.getUsuarios() == null) {
            escuela.setUsuarios(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EscuelaPlandeestudio> attachedPlanesDeEstudio = new ArrayList<EscuelaPlandeestudio>();
            for (EscuelaPlandeestudio planesDeEstudioEscuelaPlandeestudioToAttach : escuela.getPlanesDeEstudio()) {
                planesDeEstudioEscuelaPlandeestudioToAttach = em.getReference(planesDeEstudioEscuelaPlandeestudioToAttach.getClass(), planesDeEstudioEscuelaPlandeestudioToAttach.getId());
                attachedPlanesDeEstudio.add(planesDeEstudioEscuelaPlandeestudioToAttach);
            }
            escuela.setPlanesDeEstudio(attachedPlanesDeEstudio);
            List<Usuario> attachedUsuarios = new ArrayList<Usuario>();
            for (Usuario usuariosUsuarioToAttach : escuela.getUsuarios()) {
                usuariosUsuarioToAttach = em.getReference(usuariosUsuarioToAttach.getClass(), usuariosUsuarioToAttach.getId());
                attachedUsuarios.add(usuariosUsuarioToAttach);
            }
            escuela.setUsuarios(attachedUsuarios);
            em.persist(escuela);
            for (EscuelaPlandeestudio planesDeEstudioEscuelaPlandeestudio : escuela.getPlanesDeEstudio()) {
                Escuela oldEscuelaOfPlanesDeEstudioEscuelaPlandeestudio = planesDeEstudioEscuelaPlandeestudio.getEscuela();
                planesDeEstudioEscuelaPlandeestudio.setEscuela(escuela);
                planesDeEstudioEscuelaPlandeestudio = em.merge(planesDeEstudioEscuelaPlandeestudio);
                if (oldEscuelaOfPlanesDeEstudioEscuelaPlandeestudio != null) {
                    oldEscuelaOfPlanesDeEstudioEscuelaPlandeestudio.getPlanesDeEstudio().remove(planesDeEstudioEscuelaPlandeestudio);
                    oldEscuelaOfPlanesDeEstudioEscuelaPlandeestudio = em.merge(oldEscuelaOfPlanesDeEstudioEscuelaPlandeestudio);
                }
            }
            for (Usuario usuariosUsuario : escuela.getUsuarios()) {
                Escuela oldEscuelaOfUsuariosUsuario = usuariosUsuario.getEscuela();
                usuariosUsuario.setEscuela(escuela);
                usuariosUsuario = em.merge(usuariosUsuario);
                if (oldEscuelaOfUsuariosUsuario != null) {
                    oldEscuelaOfUsuariosUsuario.getUsuarios().remove(usuariosUsuario);
                    oldEscuelaOfUsuariosUsuario = em.merge(oldEscuelaOfUsuariosUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Escuela escuela) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escuela persistentEscuela = em.find(Escuela.class, escuela.getId());
            List<EscuelaPlandeestudio> planesDeEstudioOld = persistentEscuela.getPlanesDeEstudio();
            List<EscuelaPlandeestudio> planesDeEstudioNew = escuela.getPlanesDeEstudio();
            List<Usuario> usuariosOld = persistentEscuela.getUsuarios();
            List<Usuario> usuariosNew = escuela.getUsuarios();
            List<String> illegalOrphanMessages = null;
            for (EscuelaPlandeestudio planesDeEstudioOldEscuelaPlandeestudio : planesDeEstudioOld) {
                if (!planesDeEstudioNew.contains(planesDeEstudioOldEscuelaPlandeestudio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EscuelaPlandeestudio " + planesDeEstudioOldEscuelaPlandeestudio + " since its escuela field is not nullable.");
                }
            }
            for (Usuario usuariosOldUsuario : usuariosOld) {
                if (!usuariosNew.contains(usuariosOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuariosOldUsuario + " since its escuela field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EscuelaPlandeestudio> attachedPlanesDeEstudioNew = new ArrayList<EscuelaPlandeestudio>();
            for (EscuelaPlandeestudio planesDeEstudioNewEscuelaPlandeestudioToAttach : planesDeEstudioNew) {
                planesDeEstudioNewEscuelaPlandeestudioToAttach = em.getReference(planesDeEstudioNewEscuelaPlandeestudioToAttach.getClass(), planesDeEstudioNewEscuelaPlandeestudioToAttach.getId());
                attachedPlanesDeEstudioNew.add(planesDeEstudioNewEscuelaPlandeestudioToAttach);
            }
            planesDeEstudioNew = attachedPlanesDeEstudioNew;
            escuela.setPlanesDeEstudio(planesDeEstudioNew);
            List<Usuario> attachedUsuariosNew = new ArrayList<Usuario>();
            for (Usuario usuariosNewUsuarioToAttach : usuariosNew) {
                usuariosNewUsuarioToAttach = em.getReference(usuariosNewUsuarioToAttach.getClass(), usuariosNewUsuarioToAttach.getId());
                attachedUsuariosNew.add(usuariosNewUsuarioToAttach);
            }
            usuariosNew = attachedUsuariosNew;
            escuela.setUsuarios(usuariosNew);
            escuela = em.merge(escuela);
            for (EscuelaPlandeestudio planesDeEstudioNewEscuelaPlandeestudio : planesDeEstudioNew) {
                if (!planesDeEstudioOld.contains(planesDeEstudioNewEscuelaPlandeestudio)) {
                    Escuela oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio = planesDeEstudioNewEscuelaPlandeestudio.getEscuela();
                    planesDeEstudioNewEscuelaPlandeestudio.setEscuela(escuela);
                    planesDeEstudioNewEscuelaPlandeestudio = em.merge(planesDeEstudioNewEscuelaPlandeestudio);
                    if (oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio != null && !oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio.equals(escuela)) {
                        oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio.getPlanesDeEstudio().remove(planesDeEstudioNewEscuelaPlandeestudio);
                        oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio = em.merge(oldEscuelaOfPlanesDeEstudioNewEscuelaPlandeestudio);
                    }
                }
            }
            for (Usuario usuariosNewUsuario : usuariosNew) {
                if (!usuariosOld.contains(usuariosNewUsuario)) {
                    Escuela oldEscuelaOfUsuariosNewUsuario = usuariosNewUsuario.getEscuela();
                    usuariosNewUsuario.setEscuela(escuela);
                    usuariosNewUsuario = em.merge(usuariosNewUsuario);
                    if (oldEscuelaOfUsuariosNewUsuario != null && !oldEscuelaOfUsuariosNewUsuario.equals(escuela)) {
                        oldEscuelaOfUsuariosNewUsuario.getUsuarios().remove(usuariosNewUsuario);
                        oldEscuelaOfUsuariosNewUsuario = em.merge(oldEscuelaOfUsuariosNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = escuela.getId();
                if (findEscuela(id) == null) {
                    throw new NonexistentEntityException("The escuela with id " + id + " no longer exists.");
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
            Escuela escuela;
            try {
                escuela = em.getReference(Escuela.class, id);
                escuela.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The escuela with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EscuelaPlandeestudio> planesDeEstudioOrphanCheck = escuela.getPlanesDeEstudio();
            for (EscuelaPlandeestudio planesDeEstudioOrphanCheckEscuelaPlandeestudio : planesDeEstudioOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escuela (" + escuela + ") cannot be destroyed since the EscuelaPlandeestudio " + planesDeEstudioOrphanCheckEscuelaPlandeestudio + " in its planesDeEstudio field has a non-nullable escuela field.");
            }
            List<Usuario> usuariosOrphanCheck = escuela.getUsuarios();
            for (Usuario usuariosOrphanCheckUsuario : usuariosOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escuela (" + escuela + ") cannot be destroyed since the Usuario " + usuariosOrphanCheckUsuario + " in its usuarios field has a non-nullable escuela field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(escuela);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Escuela> findEscuelaEntities() {
        return findEscuelaEntities(true, -1, -1);
    }

    public List<Escuela> findEscuelaEntities(int maxResults, int firstResult) {
        return findEscuelaEntities(false, maxResults, firstResult);
    }

    private List<Escuela> findEscuelaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Escuela.class));
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

    public Escuela findEscuela(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Escuela.class, id);
        } finally {
            em.close();
        }
    }

    public int getEscuelaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Escuela> rt = cq.from(Escuela.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
        public Escuela consultarEscuelaNombre(String nombre) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Escuela c WHERE c.nombre = :nombre");
        consulta.setParameter("nombre", nombre);
        Escuela escuela = (Escuela) consulta.getSingleResult();
        return escuela;
    }
    
}
