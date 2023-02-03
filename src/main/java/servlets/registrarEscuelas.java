package servlets;

import dao.EscuelaJpaController;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import objetosNegocio.Escuela;
import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.io.InputStream;
import javax.persistence.NoResultException;
import javax.servlet.annotation.WebServlet;
import org.apache.commons.io.IOUtils;

@MultipartConfig
@WebServlet(name = "registrarEscuelas", urlPatterns = {"/registrarEscuelas"})
public class registrarEscuelas extends HttpServlet {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    EscuelaJpaController escuelaDAO = new EscuelaJpaController(factory);
    Escuela escuelaNueva;

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet registrarEscuelas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registrarEscuelas at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
        EscuelaJpaController escuelaDAO = new EscuelaJpaController(factory);

        String clave = request.getParameter("clave");
        String nombre = request.getParameter("nombre");

        Part filePart = request.getPart("logotipo"); //
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        byte[] logotipoConvertido = IOUtils.toByteArray(fileContent);
        escuelaNueva = new Escuela(clave, nombre, logotipoConvertido);

        try {
            Escuela escuelaPrueba = escuelaDAO.consultarEscuelaNombre(nombre);
            request.setAttribute("errorEscuela", "siError");
            request.getRequestDispatcher("registrarEscuelas.jsp").forward(request, response);
        } catch (NoResultException ex) {
            try {
                escuelaDAO.create(escuelaNueva);
                request.getRequestDispatcher("registrarEscuelas.jsp").forward(request, response);
            } catch (Exception exDos) {
                request.setAttribute("errorEscuela", "siError");
                request.getRequestDispatcher("registrarEscuelas.jsp").forward(request, response);
            }
        }
//        File fi = new File(realPath);
//        byte[] logotipoConvertido;
//        try {
//            logotipoConvertido = Files.readAllBytes(fi.toPath());
//            Escuela escuelaNueva = new Escuela(clave, nombre, logotipoConvertido);
//            escuelaDAO.create(escuelaNueva);
//        } catch (IOException ex) {
//            System.out.println("\n\n\n\n" + ex);
//        }
//        Escuela escuelaPrueba = escuelaDAO.consultarEscuelaNombre("tobarito");
//        byte[] photo = escuelaPrueba.getLogotipo();
//        String bphoto = Base64.getEncoder().encodeToString(photo);
//        request.setAttribute("bphoto", bphoto);
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

}
