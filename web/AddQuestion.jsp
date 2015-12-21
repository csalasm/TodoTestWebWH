<%@include file="TeacherHeader.html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container" style="margin-top:80px; margin-bottom: 80px">

    <div class="col-sm-6 col-sm-offset-3">

        <div class="panel responsive panel-primary" style="border-color: #31b0d5;">

            <a href="#" class="list-group-item active">
                A&ntildeadir pregunta
            </a>
            <div class="panel-body">

                <form name="Category" class="form" action="AddCategoryServlet" method="post">

                    <div class="form-group">

                        <input type="text" class="form-control" id="addcategoria" name="addcategory" value placeholder="Categor�a">

                        <button type="submit" class="btn btn-primary btn-xs pull-right">A�adir Categor�a</button>
                    </div> 
                </form>

                <form  class="form-horizontal" action="AddQuestionServlet"  method="post" enctype="multipart/form-data" >
                    <div class="form-group">
                        <label for="Categor�a" class="col-sm-2 control-label">Categor�a: </label>
                        <div class="col-sm-6">
                            <select class="form-control" id="categoria" name="Categoria" required>
                                <c:forEach var="categorias" items="${categories}">
                                    <c:choose>
                                        <c:when test="${categoria == categorias.nombre}">
                                            <option value="${categorias.nombre}" selected="">${categorias.nombre}</option>

                                        </c:when>
                                        <c:otherwise>
                                            <option value="${categorias.nombre}">${categorias.nombre}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Pregunta" class="col-sm-2 control-label">Pregunta: </label>
                        <div class="col-sm-6 ">
                            <textarea name="pregunta" class="form-control" rows="3"  placeholder="Pregunta" required></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="Imagen" class="col-sm-2 control-label">Selecciona un fichero:</label><div class="col-sm-6">
                            <input type="file" name="fileName" class="btn"><br>
                        </div>
                    </div>

                    <div class="form-group ">
                        <label for="Respuestas" class="control-label">Respuestas: </label>

                        <div id="answers">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="respuesta" id="respuesta" value="0" checked>
                                    <input type="text" class="form-control " id="Respuesta" name="respuestaText[]" value placeholder="Respuesta" required>
                                </label>
                            </div>
                            <div class="radio">
                                <label>

                                    <input type="radio" name="respuesta" id="respuesta" value="1" >
                                    <input type="text" class="form-control" id="Respuesta" name="respuestaText[]" value placeholder="Respuesta" required>
                                </label>
                            </div>
                        </div>

                        <div  id="answersadded">
                        </div>

                    </div>


                    <div class="form-group">
                        <div class="col-sm-offset-1">
                            <button type="button" class="btn btn-primary" id="addRespuesta"><i class="glyphicon glyphicon-plus"></i></button>
                            <button type="button" class="btn btn-primary" id="removeRespuesta"><i class="glyphicon glyphicon-minus"></i></button>

                        </div>  
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1">
                            <button type="submit" class="btn btn-primary btn-xs" id="boton" name="ActionButton" value="0" >A�adir Pregunta</button>
                        </div>
                    </div>

            </form>

            <form  class="form-vertical" action="AddQuestionServlet"  method="post" enctype="multipart/form-data" >
                <div class="btn-group-vertical pull-right">
                    <button type="submit" class="btn btn-primary btn-xs pull-right" id="boton" name="ActionButton" value="1">A�adir Pregunta por Categoria</button>

                    <button type="submit" class="btn btn-primary btn-xs pull-right" id="boton" name="ActionButton" value="2">Volver</button>
                </div>
            </form>
            </div>
        </div>

    </div>
</div>
</div>

<%@include file="Footer.html"%>