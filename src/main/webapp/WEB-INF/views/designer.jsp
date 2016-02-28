<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Interview Street - Редактор анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<c:set var="interviewId" value="${interview.id}" scope="session"/>
<main class="container">
    <div class="row">
        <div class="main-col col l12 m12 s12">
            <div class="box box-padding-fix">
                <h4 class="header teal-text">${interview.name}</h4>
                <c:if test="${interview.type.id eq 2}">
                    <h6><a href="localhost:8080/interview-street/${interview.hash}/anonymous">Ссылка:</a>
                        localhost:8080/interview-street/${interview.hash}/anonymous</h6>
                </c:if>
                <h6>Тип: ${interview.type.name}</h6>
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
                <div class="row">
                    <section class="col s12">
                        <div class="badge teal valign-wrapper"><h6 class="valig text">${cur.index + 1}</h6></div>
                        <div class="question" id="${form.question.id}">

                            <h5 class="header black-text">${form.question.text}</h5>

                            <div class="answers">
                                <c:forEach var="el" items="${answer_forms[cur.index]}" varStatus="loop">
                                <c:choose>
                                <c:when test="${el.answer.type.type eq 'slider'}">
                                    <p class="range-field">
                                        <input type="range" id="${el.answer.id}"/>
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
                                <c:choose>
                                <c:when test="${loop.last && loop.index%2==0}">
                                <div class="input-field input-field-fix empty-padding col l12 m12 s12">
                                    </c:when>
                                    <c:otherwise>
                                    <div class="input-field input-field-fix empty-padding col l6 m6 s12">
                                        </c:otherwise>
                                        </c:choose>
                                        <input id="${el.answer.id}" type="text" class="validate">
                                        <label for="${el.answer.id}">${el.answer.text}</label>
                                    </div>
                                    </c:otherwise>
                                    </c:choose>
                                    </c:forEach>
                                </div>
                                <div class="divider"></div>
                                <div class="right-align">
                                    <a href="JavaScript:editForm('${form.question.id}','${interview.id}')"
                                       class="waves-effect waves-green btn-flat">Изменить</a>
                                    <a href="JavaScript:deleteQuestion('${form.question.id}')"
                                       class="waves-effect waves-red btn-flat">Удалить</a>
                                </div>
                            </div>
                            <!--This is a bug, but it works so. Don't change!-->
                    </section>
                </div>
            </c:forEach>
            <div id="interview-questions">
                <div class="row"></div>
            </div>
        </div>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a onclick="buildForm('${interview.id}')" class="btn-floating btn-large red" id="addQuestionBtn">
            <i class="large material-icons" title="Добавить вопрос">add</i>
        </a>
    </div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
