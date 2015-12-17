/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.facades.CategoriaFacade;
import controller.parameters.AddCategoryParameters;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.jpa.Categoria;
import model.jpa.Usuario;

/**
 *
 * @author Jesus
 */
@WebServlet(name = "AddTopicServlet", urlPatterns = {"/AddTopicServlet"})
public class AddCategoryServlet extends HttpServlet {

    @EJB
    private CategoriaFacade categoriaFacade;

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

        Usuario u;
        HttpSession session = request.getSession(true);
        u = (Usuario) session.getAttribute("user");
        AddCategoryParameters atp = new AddCategoryParameters(request);

        List<Categoria> list_cat = categoriaFacade.findByName(atp.getCategoryName());

        if (list_cat.size() == 0) { //No existe ninguno con el nombre. buscado
            Categoria new_cat = new Categoria();
            new_cat.setNombre(atp.getCategoryName());
            categoriaFacade.create(new_cat);   //Crea la nueva categoria
        }

        processAddTopic(request, response);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddTopicServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTopicServlet at " + request.getContextPath() + "</h1>");
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

    private void processErrorAddTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ERROR_ADD_TOPIC", "true");
        RequestDispatcher rd = getServletContext().getNamedDispatcher("/AddTest.jsp");
        rd.forward(request, response);
    }

    private void processAddTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ADD_TOPIC_OK", "true");
        RequestDispatcher rd = getServletContext().getNamedDispatcher("/AddTest.jsp");
        rd.forward(request, response);
    }
}