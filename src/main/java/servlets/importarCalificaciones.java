package servlets;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import control.ControlAlumno;
import dao.AlumnoJpaController;
import dao.exceptions.PreexistingEntityException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import objetosNegocio.Alumno;
import objetosNegocio.Calificacion;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import objetosNegocio.Materia;
import control.ControlCalificaciones;
import control.ControlMaterias;

/**
 *
 * @author angel
 */
@MultipartConfig
@WebServlet(name = "importarCalificaciones", urlPatterns = {"/importarCalificaciones"})
public class importarCalificaciones extends HttpServlet {

    private ControlAlumno controlAlumno = new ControlAlumno();
    private ControlCalificaciones controlCalificaciones = new ControlCalificaciones();
    private ControlMaterias controlMaterias = new ControlMaterias();
    public static List<String> listaDatosTabla = new ArrayList<>();
    public static int alumnosRegistrados = 0;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Part filePart = request.getPart("archivo"); // Retrieves <input type="file" name="archivo">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();

        List[] calificaciones = null;
        Boolean archivoInvalido = false;

        switch (getFileExtension(fileName)) {
            case "xlsx":
                calificaciones = importarXLSX(fileContent);
                break;
            case "xls":
                calificaciones = importarXLS(fileContent);
                break;
            case "csv": {
                try {
                    calificaciones = importarCSV(fileContent);
                } catch (CsvValidationException ex) {
                    Logger.getLogger(importarCalificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default: {
                archivoInvalido = true;
            }

        }

        session.setAttribute("listaCalificaciones", calificaciones);
        session.setAttribute("archivoInvalido", archivoInvalido);
        response.sendRedirect("importarCalificaciones.jsp");
        //request.getRequestDispatcher("importarAlumnos.jsp").forward(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static String getFileExtension(String name) {
        int dotIndex = name.lastIndexOf('.');
        return (dotIndex == -1) ? "" : name.substring(dotIndex + 1);
    }

    private List[] importarXLSX(InputStream fileContent) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook(fileContent);
        XSSFSheet sheet = wb.getSheetAt(0);

        //Listas a rellenar
        List calificaciones = new ArrayList<Calificacion>();
        List camposVacios = new ArrayList<Integer>();
        List calificacionesPreexistentes = new ArrayList<Integer>();
        List alumnoIncorrecto = new ArrayList<Integer>();
        List claveMateriaIncorrecta = new ArrayList<Integer>();
        List calificacionesIncorrectas = new ArrayList<Integer>();
        List camposVaciosMateria = new ArrayList<Integer>();

        //Comparar datos de materias para saber si se encuentran activas
        List<Materia> listaMaterias = new ArrayList<>();
        listaMaterias = controlMaterias.obtenerMaterias();

        //Comparar datos de alumnos para verificar al alumno
        List<Alumno> listaAlumnos = new ArrayList<>();
        listaAlumnos = controlAlumno.obtenerAlumnos();

        //Lista auxiliar solo para imprimir datos en la tabla
        //Objetos a guardar
        Alumno alumno = new Alumno();
        Materia materia = new Materia();
        Calificacion calificacion = new Calificacion();
        Boolean continuarAux = false;

        int numFilas = sheet.getLastRowNum();
        for (int a = 1; a <= numFilas; a++) {

            //Fila en la que te encuentras
            Row fila = sheet.getRow(a);

            if (fila != null) {
                String celdaAux = "";
                try {
                    celdaAux = fila.getCell(0).getStringCellValue();
                } catch (Exception e) {
                    camposVacios.add(a + 1);
                    continue;
                }

                for (int i = 0; i < listaAlumnos.size(); i++) {
                    if (fila.getCell(0).getStringCellValue().equals(listaAlumnos.get(i).getMatricula())) {
                        alumno = listaAlumnos.get(i);
                        listaDatosTabla.add(alumno.getMatricula());
                        alumnosRegistrados++;
                        continuarAux = true;
                        break;
                    }
                }

                if (continuarAux) {
                    //Fila de claves de materias
                    for (int j = 1; j < 9; j++) {

                        for (int i = 0; i < listaMaterias.size(); i++) {
                            fila = sheet.getRow(0);
                            String celdaActual = fila.getCell(j).getStringCellValue();
                            if (!(celdaActual.equals(""))) {

                                String filaCelda = fila.getCell(j).getStringCellValue();
                                String materiaLista = listaMaterias.get(i).getClave();

                                if (filaCelda.equals(materiaLista)) {
                                    fila = sheet.getRow(a);
                                    materia = listaMaterias.get(i);

                                    int nota = 0;
                                    String celdaCalificacion = "";
                                    try {
                                        celdaCalificacion = fila.getCell(j).getStringCellValue();
                                    } catch (Exception e) {
                                        listaDatosTabla.add("-1");
                                        break;
                                    }

                                    if (celdaCalificacion.equals("")) {

                                    } else {
                                        //Expresion regular de la calificacion 
                                        if (validarCalificacion(fila.getCell(j).getStringCellValue())) {
                                            nota = Integer.parseInt(fila.getCell(j).getStringCellValue());

                                            calificacion = new Calificacion(null, materia, alumno, nota);

                                            try {
                                                listaDatosTabla.add(nota + "");
                                                controlCalificaciones.agregarCalificacion(calificacion);
                                                calificaciones.add(calificacion);
                                                break;
                                            } catch (PreexistingEntityException e) {
                                                calificacionesPreexistentes.add(a + 1);
                                            }

                                        } else {
                                            calificacionesIncorrectas.add(a + 1);
                                            listaDatosTabla.add("-1");
                                            break;
                                        }
                                    }
                                } else if (i == listaMaterias.size()-1) {
                                    if (claveMateriaIncorrecta.contains(j+1)) {
                                        listaDatosTabla.add("-1");
                                        break;
                                    } else {
                                        claveMateriaIncorrecta.add(j+1);
                                        listaDatosTabla.add("-1");
                                    }
                                }

                            } else {
                                //  EL PROBLEMA ES QUE SE METE DOS VECES
                                if (camposVaciosMateria.contains(j+1)) {
                                    listaDatosTabla.add("-1");
                                    break;
                                } else {
                                    camposVaciosMateria.add(j+1);
                                }
                            }
                        }
                    }

                } else {
                    alumnoIncorrecto.add(a + 1);
                }

                //Si la primera fila no contiene valores, no se necesita seguir con el procedimiento
            } else {
                break;

            }
        }

        return new List[]{calificaciones, camposVacios, calificacionesPreexistentes, alumnoIncorrecto, claveMateriaIncorrecta, calificacionesIncorrectas, camposVaciosMateria};
    }

//    private List[] importarXLSX(InputStream fileContent) throws IOException {
//        XSSFWorkbook wb = new XSSFWorkbook(fileContent);
//        XSSFSheet sheet = wb.getSheetAt(0);
//
//        //Listas a rellenar
//        List calificaciones = new ArrayList<Calificacion>();
//        List camposVacios = new ArrayList<Integer>();
//        List calificacionesPreexistentes = new ArrayList<Integer>();
//        List alumnoIncorrecto = new ArrayList<Integer>();
//        List claveMateriaIncorrecta = new ArrayList<Integer>();
//        List calificacionesIncorrectas = new ArrayList<Integer>();
//
//        //Comparar datos de materias para saber si se encuentran activas
//        List<Materia> listaMaterias = new ArrayList<>();
//        listaMaterias = controlMaterias.obtenerMaterias();
//
//        //Comparar datos de alumnos para verificar al alumno
//        List<Alumno> listaAlumnos = new ArrayList<>();
//        listaAlumnos = controlAlumno.obtenerAlumnos();
//
//        int numFilas = sheet.getLastRowNum();
//        for (int a = 0; a <= numFilas; a++) {
//
//            //Fila en la que te encuentras
//            Row fila = sheet.getRow(a);
//
//            if (fila != null) {
//                if (fila.getCell(0) == null
//                        || fila.getCell(1) == null
//                        || fila.getCell(2) == null
//                        || fila.getCell(3) == null
//                        || fila.getCell(4) == null
//                        || fila.getCell(5) == null
//                        || fila.getCell(6) == null
//                        || fila.getCell(7) == null
//                        || fila.getCell(8) == null) {
//                    camposVacios.add(new Integer(a + 1));
//
//                } else {
//                    //Cambiar encabezado a fila de alumnos
//                    if (a == 0) {
//                        a++;
//                    }
//                    fila = sheet.getRow(a);
//
//                    Alumno alumno = new Alumno();
//                    Materia materia = new Materia();
//                    for (int i = 0; i < listaAlumnos.size(); i++) {
//                        if (fila.getCell(0).getStringCellValue().equals(listaAlumnos.get(i).getMatricula())) {
//                            alumno = listaAlumnos.get(i);
//                            break;
//                        }
//                    }
//                    if (alumno != null) {
//
//                        //Fila de claves de materias
//                        for (int j = 1; j < 9; j++) {
//                            fila = sheet.getRow(0);
//                            for (int i = 0; i < listaMaterias.size(); i++) {
//                                String filaCelda = fila.getCell(j).getStringCellValue();
//                                String materiaLista = listaMaterias.get(i).getClave();
//                                if (filaCelda.equals(materiaLista)) {
//                                    fila = sheet.getRow(a);
//                                    materia = listaMaterias.get(i);
//
//                                    //Expresion regular de la calificacion 
//                                    if (validarCalificacion(fila.getCell(j).getStringCellValue())) {
//                                        Calificacion calificacion = new Calificacion(null, materia, alumno, Integer.parseInt(fila.getCell(j).getStringCellValue()));
//
//                                        try {
//                                            controlCalificaciones.agregarCalificacion(calificacion);
//                                            calificaciones.add(calificacion);
//                                            break;
//                                        } catch (PreexistingEntityException e) {
//                                            calificacionesPreexistentes.add(new Integer(a + 1));
//                                        }
//
//                                    } else {
//                                        calificacionesIncorrectas.add(new Integer(a + 1));
//                                    }
//                                } else if (i == listaMaterias.size()) {
//                                    if (claveMateriaIncorrecta.contains(j)) {
//                                        break;
//                                    } else {
//                                        claveMateriaIncorrecta.add(j);
//                                    }
//                                }
//
//                            }
//                        }
//
//                    } else {
//                        alumnoIncorrecto.add(new Integer(a + 1));
//                    }
//                }
//
//                //Si la primera fila no contiene valores, no se necesita seguir con el procedimiento
//            } else {
//                break;
//
//            }
//        }
//
//        return new List[]{calificaciones, camposVacios, calificacionesPreexistentes, alumnoIncorrecto, claveMateriaIncorrecta, calificacionesIncorrectas};
//    }
    private List[] importarXLS(InputStream fileContent) throws IOException {
        Workbook wb = new HSSFWorkbook(fileContent);
        Sheet sheet = wb.getSheetAt(0);

        int numFilas = sheet.getLastRowNum();

        List calificaciones = new ArrayList<Calificacion>();
        List camposVacios = new ArrayList<Integer>();
        List calificacionesPreexistentes = new ArrayList<Integer>();
        List matriculaIncorrecta = new ArrayList<Integer>();
        List claveMateriaIncorrecta = new ArrayList<Integer>();
        List calificacionesIncorrectas = new ArrayList<Integer>();
        /*
        for (int a = 1; a <= numFilas; a++) {
            Row fila = sheet.getRow(a);
            if (fila != null) {
                if (fila.getCell(0) == null
                        || fila.getCell(1) == null
                        || fila.getCell(2) == null) {
                    alumnosCamposVacios.add(new Integer(a + 1));
                } else {
                    if (validarCURP(fila.getCell(2).getStringCellValue())) {
                        if (validarNombre(fila.getCell(1).getStringCellValue())) {
                            if (validarMatricula(fila.getCell(0).getStringCellValue())) {
                                Alumno alumno = new Alumno(null,
                                        fila.getCell(0).getStringCellValue(),
                                        fila.getCell(1).getStringCellValue(),
                                        fila.getCell(2).getStringCellValue(),
                                        null);
                                try {
                                    controlAlumno.agregarAlumno(alumno);
                                    alumnos.add(alumno);
                                } catch (PreexistingEntityException ex) {
                                    alumnosPreexistentes.add(new Integer(a + 1));
                                }
                            } else {
                                alumnosMatriculaIncorrecta.add(new Integer(a + 1));
                            }
                        } else {
                            alumnosNombreIncorrecto.add(new Integer(a + 1));
                        }

                    } else {
                        alumnosCurpIncorrecta.add(new Integer(a + 1));
                    }
                }

            }
        }
         */
        return new List[]{calificaciones, camposVacios, calificacionesPreexistentes, matriculaIncorrecta, claveMateriaIncorrecta, calificacionesIncorrectas};
    }

    private List[] importarCSV(InputStream fileContent) throws IOException, CsvValidationException {
        CSVReader csv = new CSVReader(new InputStreamReader(fileContent));

        String[] fila = null;

        List calificaciones = new ArrayList<Calificacion>();
        List camposVacios = new ArrayList<Integer>();
        List calificacionesPreexistentes = new ArrayList<Integer>();
        List matriculaIncorrecta = new ArrayList<Integer>();
        List claveMateriaIncorrecta = new ArrayList<Integer>();
        List calificacionesIncorrectas = new ArrayList<Integer>();

        int contador = 0;

        /*
        while ((fila = csv.readNext()) != null) {
            if (contador >= 1) {
                if (fila != null) {
                    if (fila[0].isEmpty()
                            || fila[1].isEmpty()
                            || fila[2].isEmpty()) {
                        alumnosCamposVacios.add(new Integer(contador + 1));
                    } else {
                        if (validarCURP(fila[2])) {
                            if (validarNombre(fila[1])) {
                                if (validarMatricula(fila[0])) {
                                    Alumno alumno = new Alumno(null,
                                            fila[0],
                                            fila[1],
                                            fila[2],
                                            null);
                                    try {
                                        controlAlumno.agregarAlumno(alumno);
                                        alumnos.add(alumno);
                                    } catch (PreexistingEntityException ex) {
                                        alumnosPreexistentes.add(new Integer(contador + 1));
                                    }
                                } else {
                                    alumnosMatriculaIncorrecta.add(new Integer(contador + 1));
                                }
                            } else {
                                alumnosNombreIncorrecto.add(new Integer(contador + 1));
                            }

                        } else {
                            alumnosCurpIncorrecta.add(new Integer(contador + 1));
                        }

                    }
                }
            }
            contador++;

        }
         */
        csv.close();

        return new List[]{calificaciones, camposVacios, calificacionesPreexistentes, matriculaIncorrecta, claveMateriaIncorrecta, calificacionesIncorrectas};
    }

    public boolean validarCalificacion(String str) {
        return str.matches("^(1[0]|[0-9])$");
    }

}
