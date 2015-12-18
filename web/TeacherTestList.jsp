<%-- 

    Author     : Alejandro
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="TeacherHeader.html" %>

<div class="col-md-offset-2 text-primary col-md-8" style="margin-top: 30px">  
    <div class="list-group">
        <a href="#" class="list-group-item active">
            Seleccionar test
        </a>
        <c:forEach var="test" items="${user.testCollection}">
            <a href="" class="list-group-item">${test.nombre}</a>
        </c:forEach>
    </div>
</div>
<%@include file="Footer.html"%>