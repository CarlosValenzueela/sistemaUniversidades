/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.AlumnoJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.Persistence;
import objetosNegocio.Alumno;

/**
 *
 * @author angel
 */
public class ControlAlumno {

    private static AlumnoJpaController alumnoJpaController = new AlumnoJpaController(Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU"));

    public void agregarAlumno(Alumno alumno) throws PreexistingEntityException {
        alumnoJpaController.create(alumno);
    }

    public void editarAlumno(Alumno alumno) throws IllegalOrphanException, NonexistentEntityException, Exception   {
        alumnoJpaController.edit(alumno);
    }

    public void eliminarAlumno(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        alumnoJpaController.destroy(id);
    }

    public List<Alumno> obtenerAlumnos() {
        return alumnoJpaController.findAlumnoEntities();
    }

    public Alumno obtenerAlumnoPorId(Integer id) {
        return alumnoJpaController.findAlumno(id);
    }

}