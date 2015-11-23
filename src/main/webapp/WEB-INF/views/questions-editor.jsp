<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Редактор анкет</title>
</head>
<body>
<div class="navbar-fixed">
    <nav class="white">
        <div class="nav-wrapper container">
            <a href="#" class="brand-logo brand-logo-color-fix left">${interview.name}</a>
            <ul class="right hide-on-med-and-down">
                <li><a href="<c:url value="/interviews"/>">Мои анкеты</a></li>
                <sec:authorize access="hasRole('ROLE_EDITOR')">
                    <li><a href="<c:url value="/interview-list"/>">Список анкет</a></li>
                    <li><a href="#">Статистика</a></li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <a href="<c:url value="/j_spring_security_logout"/>" class="waves-effect waves-light btn">Выход</a>
                </sec:authorize>
            </ul>
        </div>
    </nav>
</div>
<main class="container">
    <div class="row">
        <div class="col l12 m12 s12">
            <c:if test="${forms.size() < 1}">
                <div class="box box-padding-fix">
                    <h4 class="header teal-text">Список вопросов</h4>
                    <h6>В данной анкете пока нет ни одного вопроса.</h6>
                </div>
            </c:if>
            <c:forEach var="form" items="${forms}" varStatus="cur">
                <section>
                    <div class="question">
                        <h5 class="header black-text">${form.question.text}</h5>

                        <div class="answers">
                            <c:forEach var="el" items="${answer_forms[cur.index]}">
                                <c:choose>
                                    <c:when test="${el.answer.type.type eq 'slider'}">
                                        <p class="range-field">
                                            <input type="range" id="test11" min="1" max="100"/>
                                            <label for="test11">Оцените по шкале от 1 до 100</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'radiobutton'}">
                                        <p>
                                            <input name="group1" type="radio" id="test2"/>
                                            <label for="test2">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'checkbox'}">
                                        <p>
                                            <input type="checkbox" id="test8" checked="checked"/>
                                            <label for="test8">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="input-field col l12 s12 m12">
                                                <input id="first_name" type="text" class="validate">
                                                <label for="first_name">${el.answer.text}</label>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </section>
            </c:forEach>
            <div id="interview_questions">
            </div>
        </div>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a onclick="buildForm('${interviewId}')" class="btn-floating btn-large red" id="addQuestionBtn">
            <i class="large material-icons" title="Добавить вопрос">add</i>
        </a>
    </div>
</main>
<%@include file="fragments/small_footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
