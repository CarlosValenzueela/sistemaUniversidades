/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sistemaUniversidades_XP;

import dao.AlumnoJpaController;
import dao.CalificacionJpaController;
import dao.EscuelaJpaController;
import dao.MateriaJpaController;
import dao.UsuarioJpaController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import jwt.JWT;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import objetosNegocio.Alumno;
import objetosNegocio.Calificacion;
import objetosNegocio.Escuela;
import objetosNegocio.Materia;
import objetosNegocio.MateriaPlandeestudio;
import objetosNegocio.MateriasSerializacion;
import objetosNegocio.Escuela;
import dao.EscuelaJpaController;
import dao.MateriaPlandeestudioJpaController;
import dao.PlandeestudioJpaController;
import dao.exceptions.PreexistingEntityException;
import java.io.IOException;
import java.util.List;
import objetosNegocio.Plandeestudio;

/**
 *
 * @author angel
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
        PlandeestudioJpaController planEstudioDAO = new PlandeestudioJpaController(factory);
        MateriaJpaController materiaDAO = new MateriaJpaController(factory);
        MateriaPlandeestudioJpaController relacionMateriaPlanDAO = new MateriaPlandeestudioJpaController(factory);
        AlumnoJpaController alumnoDAO = new AlumnoJpaController(factory);
        
        CalificacionJpaController calificacionDAO = new CalificacionJpaController(factory);


        //System.out.println(planEstudioDAO.consultarPorNombre("PlanPruebas"));

        List <Calificacion> listacalif = calificacionDAO.findCalificacionEntities();
        System.out.println("\n\n" + listacalif.size());
        for (Calificacion calificacion : listacalif) {
            System.out.println(calificacion);
        }

        
        
//        Plandeestudio planEstudio = planEstudioDAO.findPlandeestudio(1);
//        Materia materia = materiaDAO.findMateria(4); 
//        MateriaPlandeestudio listaMateriaPlan = new  MateriaPlandeestudio(materia, planEstudio);
//        relacionMateriaPlanDAO.create(listaMateriaPlan);
//        AlumnoJpaController alumnoDAO = new AlumnoJpaController(factory);
//        Escuela escuelaPrueba = escuelaDAO.consultarEscuelaNombre("Conalep");
//        byte[] logotipoNuevo = escuelaPrueba.getLogotipo();
//
//        System.out.println(logotipoNuevo);     
//        byte[] logotipoConvertido;
//        try {
//            logotipoConvertido = Files.readAllBytes(fi.toPath());
//            Escuela escuelaNueva = new Escuela(clave, nombre, logotipoConvertido);
//            escuelaDAO.create(escuelaNueva);
//        } catch (IOException ex) {
//            System.out.println("hubo error");
//        }
//        // Alumnos
//        Alumno alumno1 = new Alumno(5, "0001", "hola", "fsfsdfsf", new ArrayList<Calificacion>());
//        Alumno alumno2 = new Alumno(6, "0002", "hola", "dvvvdvdvdv", new ArrayList<Calificacion>());
//        Alumno alumno3 = new Alumno(3, "0003", "tres", "sdfsdfdsggvved", new ArrayList<Calificacion>());
//        Alumno alumno4 = new Alumno(3, "0004", "cuatro", "sdfsdfsdfsdfd", new ArrayList<Calificacion>());
//        Alumno alumno5 = new Alumno(3, "0005", "quinto", "fsdfsdfsdfjsdfsdif", new ArrayList<Calificacion>());
//        Alumno alumno6 = new Alumno(6, "0006", "sexto", "666666", new ArrayList<Calificacion>());
//        Alumno alumno7 = new Alumno(3, "0007", "septimo", "sss", new ArrayList<Calificacion>());
//
//
//        // Materias
//        Materia materia1 = new Materia(1, "ffsdf", "algebrea", new ArrayList<Calificacion>(), new ArrayList<MateriaPlandeestudio>(), new ArrayList<MateriasSerializacion>(), new ArrayList<MateriasSerializacion>());
//        Materia materia2 = new Materia(2, "sdsd", "programacion1", new ArrayList<Calificacion>(), new ArrayList<MateriaPlandeestudio>(), new ArrayList<MateriasSerializacion>(), new ArrayList<MateriasSerializacion>());
//
//        // Calificaciones
//        Calificacion cal1 = new Calificacion(1, materia1, alumno1, 10);
//        Calificacion cal2 = new Calificacion(2, materia1, alumno1, 8);
//        Calificacion cal3 = new Calificacion(3, materia2, alumno2, 8);
//        Calificacion cal4 = new Calificacion(4, materia2, alumno2, 7);
//        Calificacion cal5 = new Calificacion(5, materia1, alumno3, 6);
//        Calificacion cal6 = new Calificacion(6, materia2, alumno3, 6);
//        Calificacion cal7 = new Calificacion(5, materia1, alumno4, 10);
//        Calificacion cal8 = new Calificacion(6, materia2, alumno4, 10);
//        Calificacion cal9 = new Calificacion(5, materia1, alumno5, 9);
//        Calificacion cal10 = new Calificacion(6, materia2, alumno5, 8);
//        Calificacion cal11 = new Calificacion(5, materia1, alumno6, 9);
//        Calificacion cal12 = new Calificacion(6, materia2, alumno6, 8);
//        Calificacion cal13 = new Calificacion(5, materia1, alumno7, 6);
//        Calificacion cal14 = new Calificacion(6, materia2, alumno7, 6);
//
//
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
//        
//        AlumnoJpaController jpaAlumno = new AlumnoJpaController(factory);
//        MateriaJpaController jpaMateria = new MateriaJpaController(factory);
//        CalificacionJpaController jpaCalificacion = new CalificacionJpaController(factory);
//        
//        jpaAlumno.create(alumno1);
//        jpaAlumno.create(alumno2);
//        jpaAlumno.create(alumno3);
//        jpaAlumno.create(alumno4);
//        jpaAlumno.create(alumno5);
//        jpaAlumno.create(alumno6);
//        jpaAlumno.create(alumno7);
//        
//        jpaMateria.create(materia1);
//        jpaMateria.create(materia2);
//        
//        jpaCalificacion.create(cal1);
//        jpaCalificacion.create(cal2);
//        jpaCalificacion.create(cal3);
//        jpaCalificacion.create(cal4);
//        jpaCalificacion.create(cal5);
//        jpaCalificacion.create(cal6);
//        jpaCalificacion.create(cal7);
//        jpaCalificacion.create(cal8);
//        jpaCalificacion.create(cal9);
//        jpaCalificacion.create(cal10);
//        jpaCalificacion.create(cal11);
//        jpaCalificacion.create(cal12);
//        jpaCalificacion.create(cal13);
//        jpaCalificacion.create(cal14);
//
//        InputStream inputStream = null;
//        JasperPrint jasperPrint = null;
//        
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistemauniversidades?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "sesamo");
//        } catch (SQLException ex) {
//            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        
//        try {
//            inputStream = new FileInputStream("src/main/java/reportes/ReporteAlumno.jrxml");
//        } catch(FileNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, "Error al leer el fichero de carga jasper report: " + ex.getMessage());
//        }
//        
//        Map parametro = new HashMap();
//            parametro.put("matricula", "0001");
//        
//        try {
//            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//            jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conn);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/java/reportes/reporte.pdf"); 
//        } catch (JRException e) {
//            JOptionPane.showMessageDialog(null, "Error al leer el fichero de carga jasper report: " + e.getMessage());
//        }
    }

}
