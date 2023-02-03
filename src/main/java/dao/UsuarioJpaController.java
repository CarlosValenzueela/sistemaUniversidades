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
import objetosNegocio.Escuela;
import objetosNegocio.Usuario;

/**
 *
 * @author angel
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Escuela escuela = usuario.getEscuela();
            if (escuela != null) {
                escuela = em.getReference(escuela.getClass(), escuela.getId());
                usuario.setEscuela(escuela);
            }
            em.persist(usuario);
            if (escuela != null) {
                escuela.getUsuarios().add(usuario);
                escuela = em.merge(escuela);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Escuela escuelaOld = persistentUsuario.getEscuela();
            Escuela escuelaNew = usuario.getEscuela();
            if (escuelaNew != null) {
                escuelaNew = em.getReference(escuelaNew.getClass(), escuelaNew.getId());
                usuario.setEscuela(escuelaNew);
            }
            usuario = em.merge(usuario);
            if (escuelaOld != null && !escuelaOld.equals(escuelaNew)) {
                escuelaOld.getUsuarios().remove(usuario);
                escuelaOld = em.merge(escuelaOld);
            }
            if (escuelaNew != null && !escuelaNew.equals(escuelaOld)) {
                escuelaNew.getUsuarios().add(usuario);
                escuelaNew = em.merge(escuelaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Escuela escuela = usuario.getEscuela();
            if (escuela != null) {
                escuela.getUsuarios().remove(usuario);
                escuela = em.merge(escuela);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario consultarUsuarioInicioSesion(String user, String password) {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Usuario c WHERE c.user = :user AND c.password = :password");
        consulta.setParameter("user", user);
        consulta.setParameter("password", password);
        Usuario usuario = (Usuario) consulta.getSingleResult();
        return usuario;
    }

    public Usuario consultarUsuarioCURP(String CURP) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Usuario c WHERE c.curp = :curp");
        consulta.setParameter("curp", CURP);
        Usuario usuario = (Usuario) consulta.getSingleResult();
        return usuario;
    }
    
    public Usuario consultarUsuarioUser(String user) throws NoResultException {
        EntityManager em = getEntityManager();
        Query consulta = em.createQuery("SELECT c FROM Usuario c WHERE c.user = :user");
        consulta.setParameter("user", user);
        Usuario usuario = (Usuario) consulta.getSingleResult();
        return usuario;
    }

}
