<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.gstu.interviewstreet.web.util.DateUtils" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>
    <link href="<c:url value="/resources/css/interview.css"/>" rel="stylesheet" type="text/css">

    <title>Interview Street - ${interview.name}</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">

    <div class="row">
        <h5 class="header teal-text">${interview.name}</h5>
        <h6>${interview.introductoryText}</h6>
    </div>

    <div class="row">
        <c:if test="${empty questions}">
            <h5 class="red-text">Извините, в данной анкете пока нет ни одного вопроса.</h5>
        </c:if>

        <c:forEach var="question" items="${questions}" varStatus="current">
            <div class="question col s12">
                <div class="number teal">${current.index + 1}</div>
                <h5 class="question-text">${question.text}</h5>

                <c:forEach var="answer" items="${question.sortedAnswers}">
                    <c:set var="answerType" value="${answer.type.name}"/>
                    <c:choose>
                        <c:when test="${answerType eq 'checkbox' || answerType eq 'radio'}">
                            <p>
                                <input name="group${question.id}" type="${answerType}" id="${answer.id}"/>
                                <label for="${answer.id}">${answer.text}</label>
                            </p>
                        </c:when>
                        <c:when test="${answerType eq 'text'}">
                            <div class="input-field">
                                <input id="${answer.id}" type="text" class="validate">
                                <label class="fix" for="${answer.id}">${answer.text}</label>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!--<div class="input-field col offset-l2 l8 m12 s12">
                            <input class="rating" type="number" min="1" max="${answer.text}"
                            value="1" title="Введите значение"/>
                            <label class="active">Оценка</label>
                            </div>-->
                            <div class="col offset-l2 col l8 m12 s12 rating center">
                                <input id="${answer.id}" type="hidden" value="1">
                                <c:forEach begin="1" end="5">
                                    <i class="small material-icons red-text text-lighten-1">star_rate</i>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

            </div>
        </c:forEach>

        <a onclick="validator.checkFormInputs()"
           class="waves-effect waves-light btn-large teal lighten-1 right"><i class="material-icons right">send</i>Отправить
            анкету</a>
    </div>
</main>

<div id="error-block" class="hide col s12 m12 l12">
    <h6 class="error red lighten-1 z-depth-1">Вы не ответили на этот вопрос или на его часть.</h6>
</div>

<footer class="page-footer grey lighten-4">
    <div class="footer-copyright">
        <div class="container teal-text">
            Interview Street, <%=DateUtils.YYYY.format(DateUtils.getToday())%>
        </div>
    </div>
</footer>

<%@include file="fragments/general-js.jsp" %>
<script src="<c:url value="/resources/js/interview.js"/>"></script>

</body>
</html>