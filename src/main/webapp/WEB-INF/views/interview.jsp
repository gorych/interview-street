<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>
    <title>Interview Street - ${interview.name}</title>

    <style>
        footer.page-footer {
            margin-top: 20px;
            padding-top: 0;
        }

        .question {
            border: 1px solid #000;
            border-radius: 5px;
            padding: 0 10px 10px;
            margin-bottom: 50px;
        }

        .question:first-of-type {
            margin-top: 20px;
        }

        .question-text {
            font-size: 1.4rem;
        }

        .number {
            width: 30px;
            height: 30px;
            margin: 0 auto -15px;
            position: relative;
            bottom: 15px;
            line-height: 30px;
            border-radius: 3px;
            font-weight: bold;
            text-align: center;
            color: white;
        }

        .input-field label.fix {
            left: 0;
        }

    </style>

</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">

    <div class="row">
        <h5 class="header teal-text">${interview.name}</h5>
        <h6>${interview.introductoryText}</h6>
    </div>

    <div class="row">

        <c:forEach var="question" items="${questions}" varStatus="current">
            <div class="question col s12">
                <div class="number teal">${current.index + 1}</div>
                <h5 class="question-text">${question.text}</h5>

                <c:forEach var="answer" items="${question.answers}">
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
                                <label class="fix" for="${answer.id}">Введите текст ответа</label>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="input-field col offset-l2 l8 m12 s12">
                                <input class="rating" type="number" min="3" max="${answer.text}"
                                       value="3" title="Введите значение"/>
                                <label class="active">Оценка</label>
                            </div>
                            <div class="col offset-l2 col l8 m12 s12 rating center">
                                <c:forEach begin="1" end="3">
                                    <i class="small material-icons red-text text-lighten-1">star_rate</i>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:forEach>

        <!--
        <div class="question col s12">
            <div class="number teal">4</div>
            <h5 class="question-text">Как часто выходите в театр?</h5>
            <div class="input-field col offset-l2 l8 m12 s12" data-answer="${question.answers[0].id}">
                <input class="rating" type="number" min="3" max="10"
                       value="3" title="Минимум 3 - Максимум 10"/>
                <label class="active">Оценка</label>
            </div>
            <div class="col offset-l2 col l8 m12 s12 rating center">
                <i class="small material-icons red-text text-lighten-1">star_rate</i>
                <i class="small material-icons red-text text-lighten-1">star_rate</i>
                <i class="small material-icons red-text text-lighten-1">star_rate</i>
            </div>
        </div>

        <div class="question col s12">
            <div class="number teal">1</div>
            <h5 class="question-text">Как часто выходите в театр?</h5>
            <p>
                <input name="group1" type="radio" id="test1"/>
                <label for="test1">Вариант ответа</label>
            </p>
            <p>
                <input name="group1" type="radio" id="test1"/>
                <label for="test1">Вариант ответа</label>
            </p>
            <p>
                <input name="group1" type="radio" id="test1"/>
                <label for="test1">Вариант ответа</label>
            </p>
            <p>
                <input name="group1" type="radio" id="test1"/>
                <label for="test1">Вариант ответа</label>
            </p>
        </div>

        <div class="question col s12">
            <div class="number teal">2</div>
            <h5 class="question-text">Как часто выходите в театр?</h5>
            <p>
                <input name="group2" type="checkbox" id="test2"/>
                <label for="test2">Вариант ответа</label>
            </p>
            <p>
                <input name="group2" type="checkbox" id="test3"/>
                <label for="test3">Вариант ответа</label>
            </p>
            <p>
                <input name="group2" type="checkbox" id="test4"/>
                <label for="test4">Вариант ответа</label>
            </p>
            <p>
                <input name="group2" type="checkbox" id="test5"/>
                <label for="test5">Вариант ответа</label>
            </p>
            <div class="input-field">
                <input id="email" type="email" class="validate">
                <label class="fix" for="email">Email</label>
            </div>
        </div>

        <div class="question col s12">
            <div class="number teal">3</div>
            <h5 class="question-text">Как часто выходите в театр?</h5>
            <div class="input-field">
                <input id="email" type="email" class="validate">
                <label class="fix" for="email">Email</label>
            </div>
        </div>-->

    </div>
</main>

<footer class="page-footer teal">
    <div class="footer-copyright">
        <div class="container">
            Interview Street
        </div>
    </div>
</footer>

<%@include file="fragments/general-js.jsp" %>

</body>
</html>