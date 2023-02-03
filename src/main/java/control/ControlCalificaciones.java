package control;

import dao.CalificacionJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.Persistence;
import objetosNegocio.Calificacion;

public class ControlCalificaciones {

    private static CalificacionJpaController calificacionJpaController = new CalificacionJpaController(Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU"));

    public void agregarCalificacion(Calificacion calificacion) throws PreexistingEntityException {
        calificacionJpaController.create(calificacion);
    }

    public void editarCalificacion(Calificacion calificacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        calificacionJpaController.edit(calificacion);
    }

    public void eliminarCalificacion(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        calificacionJpaController.destroy(id);
    }

    public List<Calificacion> obtenerCalificaciones() {
        return calificacionJpaController.findCalificacionEntities();
    }

    public Calificacion obtenerCalificacionPorId(Integer id) {
        return calificacionJpaController.findCalificacion(id);
    }

}
