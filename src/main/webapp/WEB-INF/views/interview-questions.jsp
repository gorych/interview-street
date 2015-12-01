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
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box box-padding-fix ">
            <h6>${user_initials},<br/><br/> потратьте, пожалуйста, несколько минут своего времени на заполнение
                следующей анкеты.</h6>
        </div>
        <form method="POST" action="<c:url value="/send-interview/${interview.id}"/>">
            <c:forEach var="form" items="${forms}" varStatus="cur">
                <section>
                    <div class="badge teal valign-wrapper"><h6 class="valig text">${cur.index + 1}</h6></div>
                    <div class="question" id="${form.question.id}">

                        <h5 class="header black-text">${form.question.text}</h5>

                        <div class="answers">
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
            <input type="submit" value="Submit">
        </form>
    </div>
    <div class="row">
        <div class="hide-on-large-only">
            <div class="col s12 m12">
                <a class="waves-effect waves-light teal white-text btn">Отправить анкету</a>
            </div>
        </div>
    </div>
</main>

<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
