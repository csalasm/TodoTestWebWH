/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.facades.CategoriaFacade;
import controller.facades.PreguntaFacade;
import controller.facades.RespuestaFacade;
import controller.parameters.AddAnswerParameters;
import controller.parameters.AddQuestionParameters;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.jpa.Categoria;
import model.jpa.Pregunta;
import model.jpa.Respuesta;
import model.jpa.Usuario;

/**
 *
 * @author andresbailen93
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 2, maxRequestSize = 1024 * 1024 * 2)
public class AddQuestionServlet extends HttpServlet {

    @EJB
    private CategoriaFacade categoriaFacade;
    @EJB
    private PreguntaFacade preguntaFacade;
    @EJB
    private RespuestaFacade respuestaFacade;

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

        /*if (u != null) { // Si no esta autenticado, redirigimos a la pantalla principal
            processErrorLogin(request, response);
            return;
        }*/
        int action = Integer.parseInt(request.getParameter("ActionButton"));
        if(0 == action){
            createQuestion(request, response);
            redirectToCreateQuestion(request, response);

        }
        if(1 == action){
            createQuestion(request,response);
            redirectMainPageTeacher(request, response);
        }
        if(2 == action){
            redirectMainPageTeacher(request, response);
        }
        

    }

    private void processErrorLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ERROR_LOGIN", "true");
        RequestDispatcher rd = getServletContext().getNamedDispatcher("/login.jsp");
        rd.forward(request, response);
    }

    private void redirectToCreateQuestion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categoria> categoria_list = categoriaFacade.findAll();
        request.setAttribute("categories", categoria_list);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/AddQuestion.jsp");
        rd.forward(request, response);
    }

    private void redirectMainPageTeacher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute(name, this);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/MainPageTeacher.jsp");
        rd.forward(request, response);
    }
    
    private void createQuestion(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        List<Categoria> lista_categoria = categoriaFacade.findByName(request.getParameter("Categoria"));
        AddQuestionParameters addQuestionParam = new AddQuestionParameters(request);
        addQuestionParam.setCategoria(lista_categoria.get(0));

        //Creamos la pregunta
        Pregunta pregunta = new Pregunta();
        pregunta.setIdCategoria(addQuestionParam.getCategory());
        pregunta.setImagen(addQuestionParam.getImage());
        pregunta.setTexto(addQuestionParam.getQuestion());
        //Insertamos la pregunta en la bse de datos.
        preguntaFacade.create(pregunta);

        AddAnswerParameters addAnswerParam = new AddAnswerParameters(request);
        Respuesta respuesta;

        for (int i = 0; i < addAnswerParam.getAnswer_resp().size(); i++) {
            respuesta = new Respuesta();
            respuesta.setIdPregunta(pregunta);
            respuesta.setTexto(addAnswerParam.getAnswer_resp().get(i).getTexto());
            respuesta.setCorrecta(addAnswerParam.getAnswer_resp().get(i).getCorrecta());
            respuestaFacade.create(respuesta);
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
