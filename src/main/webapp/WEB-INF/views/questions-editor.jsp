<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Редактор анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<c:set var="interviewId" value="${interview.id}" scope="session"/>
<main class="container">
    <div class="row">
        <div class="col l12 m12 s12">

            <div class="box box-padding-fix">
                <h4 class="header teal-text">${interview.name}</h4>
                <c:choose>
                    <c:when test="${forms.size() < 1}">
                        <h6>В данной анкете пока нет ни одного вопроса.</h6>
                    </c:when>
                    <c:otherwise>
                        <h6>Вопросов: ${forms.size()}</h6>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:forEach var="form" items="${forms}" varStatus="cur">
                <section id="${form.question.id}">
                    <div class="question">
                        <h5 class="header black-text">${form.question.text}</h5>

                        <div class="answers">
                            <c:forEach var="el" items="${answer_forms[cur.index]}">
                                <c:choose>
                                    <c:when test="${el.answer.type.type eq 'slider'}">
                                        <p class="range-field">
                                            <input type="range" id="${el.answer.id}" min="1" max="100"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'radiobutton'}">
                                        <p>
                                            <input name="${cur.index}" type="radio" id="${el.answer.id}"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'checkbox'}">
                                        <p>
                                            <input type="checkbox" id="${el.answer.id}"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="input-field col l12 s12 m12">
                                                <input id="${el.answer.id}" type="text" class="validate">
                                                <label for="${el.answer.id}">${el.answer.text}</label>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                        <div class="divider"></div>
                        <div class="right-align">
                            <a href="#" class="waves-effect waves-orange btn-flat">Изменить</a>
                            <a href="JavaScript:deleteQuestion('${form.question.id}')"
                               class="waves-effect waves-red btn-flat">Удалить</a>
                        </div>
                    </div>
                </section>
            </c:forEach>
            <div id="interview_questions">
                <div class="row">
                </div>
            </div>
        </div>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a onclick="buildForm('${interview.id}')" class="btn-floating btn-large red" id="addQuestionBtn">
            <i class="large material-icons" title="Добавить вопрос">add</i>
        </a>
    </div>
</main>
<%@include file="fragments/small_footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
