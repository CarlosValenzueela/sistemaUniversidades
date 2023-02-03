package servlets;

import dao.UsuarioJpaController;
import dao.EscuelaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jwt.JWT;
import objetosNegocio.Usuario;
import objetosNegocio.Escuela;

@WebServlet(name = "registrarUsuario", urlPatterns = {"/registrarUsuario"})
public class registrarUsuario extends HttpServlet {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    UsuarioJpaController usuarioDAO = new UsuarioJpaController(factory);
    EscuelaJpaController escuelaDAO = new EscuelaJpaController(factory);
    Usuario usuarioSesion = new Usuario();

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
            out.println("<title>Servlet registrarUsuario</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registrarUsuario at " + request.getContextPath() + "</h1>");
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

        String curp = request.getParameter("txtCurp");
        String nombre = request.getParameter("txtNombre");
        String apellido = request.getParameter("txtapellidos");
        String fechaNacimiento = request.getParameter("fecha");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaNacimientoConvertida = null;
        try {
            fechaNacimientoConvertida = formatoFecha.parse(fechaNacimiento);
        } catch (ParseException ex) {
            System.out.println(ex);
        }

        String actividad = request.getParameter("radioActividad");
        Boolean actividadConvertida;
        if (actividad.equalsIgnoreCase("Activo")) {
            actividadConvertida = true;
        } else {
            actividadConvertida = false;
        }

        String sexo = request.getParameter("radioSexo");
        String usuario = request.getParameter("txtUsuario");
        String contrasenia = request.getParameter("txtPassword");

        String escuela = request.getParameter("escuela");
        Escuela escuelaConvertida = escuelaDAO.consultarEscuelaNombre(escuela);

        System.out.println(escuelaConvertida);
        String tipoUsuario = request.getParameter("tipoUsuario");

        try {
            Usuario comparar = usuarioDAO.consultarUsuarioCURP(curp);
            request.setAttribute("siErrorUsuario", "¡Ya existe un usuario registrado con la misma CURP!");
            request.getRequestDispatcher("registroUsuario.jsp").forward(request, response);
        } catch (NoResultException ex) {
            try {
                Usuario compararDos = usuarioDAO.consultarUsuarioUser(usuario);
                request.setAttribute("siErrorUsuario", "¡Ya existe un usuario registrado con el mismo nombre de usuario!");
                request.getRequestDispatcher("registroUsuario.jsp").forward(request, response);
            } catch (NoResultException exDos) {
//                    try {
                usuarioSesion = new Usuario(usuario, contrasenia, curp, nombre, apellido, fechaNacimientoConvertida, sexo, actividadConvertida, escuelaConvertida);
                usuarioDAO.create(usuarioSesion);
                request.getRequestDispatcher("registroUsuario.jsp").forward(request, response);
//                    } catch (Exception exTres) {
//                        request.setAttribute("siErrorUsuario", "siErrorUsuario");
//                        request.getRequestDispatcher("registroUsuario.jsp").forward(request, response);
//                    }
            }
        }
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
