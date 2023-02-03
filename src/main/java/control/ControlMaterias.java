package control;

import dao.MateriaJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.Persistence;
import objetosNegocio.Materia;

public class ControlMaterias {

    private static MateriaJpaController materiaJpaController = new MateriaJpaController(Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU"));

    public void agregarMateria(Materia materia) throws PreexistingEntityException {
        materiaJpaController.create(materia);
    }

    public void editarMateria(Materia materia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        materiaJpaController.edit(materia);
    }

    public void eliminaMateria(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        materiaJpaController.destroy(id);
    }

    public List<Materia> obtenerMaterias() {
        return materiaJpaController.findMateriaEntities();
    }

    public Materia obtenerMateriaPorId(Integer id) {
        return materiaJpaController.findMateria(id);
    }

}
