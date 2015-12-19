/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.facades.CategoriaFacade;
import controller.facades.PreguntaFacade;
import controller.facades.TestFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.jpa.Categoria;
import model.jpa.Pregunta;
import model.jpa.Test;
import model.jpa.Usuario;

/**
 *
 * @author andresbailen93
 */
public class AddQuestionByCategoryServlet extends HttpServlet {

    HttpSession session;
    Usuario u;
    Test test;
    @EJB
    PreguntaFacade preguntaFacade;
    @EJB
    CategoriaFacade categoriaFacade;

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
        session = request.getSession();
        u = (Usuario) session.getAttribute("user");

        if (u == null) { // Si no esta autenticado, redirigimos a la pantalla principal
            processErrorLogin(request, response);
            return;
        }
        addQuestionByCategory(request);
        redirectAddQuestionByCategory(request, response);
    }

    private void addQuestionByCategory(HttpServletRequest request) throws ServletException, IOException {
        test = (Test) session.getAttribute("test");

        int numPreguntas = Integer.parseInt(request.getParameter("numeroPreg"));
        List<Categoria> lista_categoria = categoriaFacade.findByName(request.getParameter("Categoria"));
        //Collection<Pregunta> lista_preguntas = lista_categoria.get(0).getPreguntaCollection();
        List<Pregunta> lista_preguntas = preguntaFacade.findPreguntasByNum(lista_categoria.get(0));

        long seed = System.nanoTime();
        Collections.shuffle(lista_preguntas, new Random(seed));

        List<Pregunta> lista_preguntas_add = new ArrayList<>();
        //Si marcamos un numero superior de preguntas de las que hay no las inserta.
        for (int i = 0; i < lista_preguntas.size(); i++) {
            if (i < numPreguntas) {
                lista_preguntas_add.add(lista_preguntas.get(i));
            }
        }

        Collection<Test> listaTest;
        for (Pregunta p : lista_preguntas_add) {
            listaTest = p.getTestCollection();
            listaTest.add(test);
            p.setTestCollection(listaTest);
            preguntaFacade.edit(p);
        }
    }

    private void processErrorLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("Session_Loggin","false");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    private void redirectAddQuestionByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categoria> categoria_list = categoriaFacade.findAll();
        request.setAttribute("categories", categoria_list);
        request.setAttribute("usuario", u);
        request.setAttribute("AddQuestion_category","true");

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/AddQuestionByCategory.jsp");
        rd.forward(request, response);
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
