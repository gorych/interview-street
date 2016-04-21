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
            <div data-quest="${question.id}" class="question col s12">
                <div class="number teal">${current.index + 1}</div>
                <h5 class="question-text">${question.text}</h5>

                <c:forEach var="answer" items="${question.sortedAnswers}">
                    <c:set var="answerType" value="${answer.type.name}"/>
                    <c:set var="questType" value="${question.type.name}"/>
                    <c:choose>
                        <c:when test="${questType eq 'checkbox' || questType eq 'radio'}">
                            <c:if test="${answerType ne 'text'}">
                                <p>
                                    <input name="group${question.id}" type="${answerType}" id="${answer.id}"/>
                                    <label for="${answer.id}">${answer.text}</label>
                                </p>
                            </c:if>
                            <c:if test="${answerType eq 'text'}">
                                <div class="row special-row valign-wrapper">
                                    <div class="col l1 m1 s1 optional-col">
                                        <input class="optional-answer" name="group${question.id}" type="${questType}" id="${answer.id}"/>
                                        <label for="${answer.id}"></label>
                                    </div>
                                    <div class="col l11 m11 s11 special-col">
                                        <div class="input-field special-input">
                                            <input type="text" class="optional-text" placeholder="Свой вариант ответа"
                                                   title="Свой вариант ответа"/>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:when>
                        <c:when test="${answerType eq 'text'}">
                            <div class="input-field">
                                <input id="${answer.id}" type="text">
                                <label class="fix" for="${answer.id}">${answer.text}</label>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="rating center">
                                <input id="${answer.id}" type="hidden" value="1"/>
                                <div class="rating-wrapper center">
                                    <i class="material-icons selected red-text text-lighten-1">star_border</i>
                                    <span>1</span>
                                </div>
                                <c:forEach begin="2" end="${answer.text}" varStatus="cur">
                                    <div class="rating-wrapper center">
                                        <i class="material-icons red-text text-lighten-1">star_border</i>
                                        <span>${cur.index}</span>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

            </div>
        </c:forEach>

        <a id="send-form-btn"
           class="waves-effect waves-light btn-large teal lighten-1 right"><i class="material-icons right">send</i>Отправить
            анкету</a>
    </div>
</main>

<footer class="page-footer grey lighten-4">
    <div class="footer-copyright">
        <div class="container teal-text">
            <strong>Interview Street, <%=DateUtils.YYYY.format(DateUtils.getToday())%>
            </strong>
        </div>
    </div>
</footer>

<%@include file="fragments/general-js.jsp" %>
<script src="<c:url value="/resources/js/interview.js"/>"></script>

</body>
</html>