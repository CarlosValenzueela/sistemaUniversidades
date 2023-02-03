/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.MateriaJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.MateriaPlandeestudioJpaController;
import dao.PlandeestudioJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Materia;
import objetosNegocio.MateriaPlandeestudio;
import objetosNegocio.Plandeestudio;
import javax.persistence.NoResultException;

@WebServlet(name = "administrarPE", urlPatterns = {"/administrarPE"})
public class administrarPE extends HttpServlet {

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
            out.println("<title>Servlet administrarPE</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet administrarPE at " + request.getContextPath() + "</h1>");
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
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        String nomPlan = request.getParameter("txtNombrePlan");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("sistemaUniversidades_XP_PU");
        MateriaPlandeestudioJpaController relacionMateriaPlanDAO = new MateriaPlandeestudioJpaController(factory);
        PlandeestudioJpaController planDAO = new PlandeestudioJpaController(factory);
        MateriaJpaController materiaDAO = new MateriaJpaController(factory);

          Plandeestudio planNuevo = new Plandeestudio(request.getParameter("txtNombrePlan"));
          
        
//        ArrayList<MateriaPlandeestudio> listaRelProductoVenta = new ArrayList<>();

        try{
            Plandeestudio compararPlan = planDAO.consultarPorNombre(nomPlan);
            request.setAttribute("siErrorPlan", "¡Ya existe un Plan de estudio registrado con el mismo nombre!");
            request.getRequestDispatcher("AdministrarPE.jsp").forward(request, response);

        }catch (NoResultException ex){

            planDAO.create(planNuevo);
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < 9; j++) {
                    Materia materia = materiaDAO.consultarPorNombre(request.getParameter("sem" + i + "mat" + j));
                    MateriaPlandeestudio materiaPlan = new MateriaPlandeestudio(materia, planNuevo);
                    relacionMateriaPlanDAO.create(materiaPlan);
                    PrintWriter out = response.getWriter();       
                    request.getRequestDispatcher("AdministrarPE.jsp").forward(request, response);
                }
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
