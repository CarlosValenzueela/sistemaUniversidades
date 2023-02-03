package control;

import javax.persistence.Persistence;
import dao.PlandeestudioJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.List;
import objetosNegocio.Plandeestudio;

public class ControlPlanEstudio {

    private static PlandeestudioJpaController planJpaController = new PlandeestudioJpaController(Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU"));

    public void agregarPlan(Plandeestudio plan) throws PreexistingEntityException {
        planJpaController.create(plan);
    }

    public void editarAlumno(Plandeestudio plan) throws IllegalOrphanException, NonexistentEntityException, Exception   {
        planJpaController.edit(plan);
    }

    public void eliminarPlan(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        planJpaController.destroy(id);
    }

    public List<Plandeestudio> obtenerPlanes() {
        return planJpaController.findPlandeestudioEntities();
    }

    public Plandeestudio obtenerPlanPorId(Integer id) {
        return planJpaController.findPlandeestudio(id);
    }

}
