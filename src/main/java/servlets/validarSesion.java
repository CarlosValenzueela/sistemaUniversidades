package servlets;

import dao.UsuarioJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jwt.JWT;
import objetosNegocio.Usuario;

@WebServlet(name = "validarSesion", urlPatterns = {"/validarSesion"})
public class validarSesion extends HttpServlet {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
    UsuarioJpaController usuarioDAO = new UsuarioJpaController(factory);
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
        
        String usuario = request.getParameter("txtuser");
        String contrasenia = request.getParameter("txtpass");

        HttpSession session = request.getSession();

        try {
            usuarioSesion = usuarioDAO.consultarUsuarioInicioSesion(usuario, contrasenia);
            String nombreCompleto = usuarioSesion.getNombre() + " " + usuarioSesion.getApellido();

            // request.setAttribute("usuario", nombreCompleto);
            session.setAttribute("usuario", nombreCompleto);

            String token = JWT.generarJWT(response, usuarioSesion);

            session.setAttribute("token", token);

            response.sendRedirect("menuPrincipal.jsp");
            //request.getRequestDispatcher("menuPrincipal.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("error", "Error al iniciar sesión, credenciales invalidas");
            response.sendRedirect("inicioSesion.jsp");
            //request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
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
        processRequest(request, response);
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
