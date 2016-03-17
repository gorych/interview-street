<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general_css.html" %>
    <title>Interview Street - ${interview.name}</title>
</head>
<body>
<nav class="white">
    <div class="nav-wrapper container">
        <a href=""><img class="brand-logo right brand-logo-color-fix responsive-img fix" src="/resources/img/logo.png"></a>
    </div>
</nav>
<main class="container">
    <div class="row">
        <div class="chip-wrapper box-padding-fix ">
            <h4 class="header teal-text">${interview.name}</h4>
            <c:choose>
                <c:when test="${forms.size() < 1}">
                    <h6>В данной анкете пока нет ни одного вопроса.</h6>
                </c:when>
                <c:otherwise>
                    <div class="preview">${user_initials},<br/> потратьте, пожалуйста, несколько минут своего времени на
                        заполнение
                        следующей анкеты.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <form method="POST">
            <input type="hidden" name="interviewId" value="${interview.id}">
            <c:forEach var="form" items="${forms}" varStatus="cur">
                <section class="top-margin">
                    <div class="badge teal valign-wrapper"><h6 class="valig text">${cur.index + 1}</h6></div>
                    <div class="question" id="${form.question.id}">
                        <h5 class="header black-text">${form.question.text}</h5>

                        <div class="answers answer-margin-fix">
                            <c:forEach var="el" items="${answer_forms[cur.index]}">
                                <c:choose>
                                    <c:when test="${el.answer.type.type eq 'slider'}">
                                        <p class="range-field">
                                            <input name="${form.question.id}" type="range" id="${el.answer.id}" min="1"
                                                   max="100"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'radiobutton'}">
                                        <p>
                                            <input type="radio" name="${form.question.id}" value="${el.answer.text}"
                                                   id="${el.answer.id}"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:when test="${el.answer.type.type eq 'checkbox'}">
                                        <p>
                                            <input type="checkbox" name="${form.question.id}" value="${el.answer.text}"
                                                   id="${el.answer.id}"/>
                                            <label for="${el.answer.id}">${el.answer.text}</label>
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="input-field col l12 s12 m12">
                                                <input name="${form.question.id}" id="${el.answer.id}" type="text"
                                                       class="validate">
                                                <label for="${el.answer.id}">${el.answer.text}</label>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </section>
            </c:forEach>
            <br/>
            <c:if test="${forms.size() >= 1}">
                <a id="sendInterview" class="btn-large waves-effect waves-light right">
                    Отправить анкету
                    <i class="material-icons right">send</i>
                </a>
            </c:if>
        </form>
    </div>
</main>
<div class="page-footer page-footer-fix white">
    <div class="footer-copyright footer-copyright-fix">
        <div class="container black-text text-lighten-3">
            &copy;Егор Семенченя, ГГТУ 2015<a class="brown-text text-lighten-3" href="#"></a>
        </div>
    </div>
</div>
<%@include file="fragments/general_js.html" %>
</body>
</html>
