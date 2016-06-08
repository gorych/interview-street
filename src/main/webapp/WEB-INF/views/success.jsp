<%@ page import="by.gstu.interviewstreet.util.DateUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>

    <title>Interview Street - Успешное прохождение</title>

    <style>
        background-color: #e0e0e0;
    </style>
</head>
<body>

<nav class="white">
    <div class="nav-wrapper container">
        <a href=""><img class="brand-logo brand-logo-color-fix responsive-img"
                        src="<c:url value="/resources/img/logo.png"/>"></a>
    </div>
</nav>
<main>
    <div class="parallax-container">
        <div class="section no-pad-bot">
            <div class="container">
                <br>
                <h1 class="header center teal-text text-lighten-2 hide-on-small-only">Спасибо за заполнение анкеты</h1>
                <h2 class="header center teal-text text-lighten-2 hide-on-large-only">Спасибо за заполнение анкеты</h2>
                <div class="row center">
                    <h5 class="header col s12 white-text light">Нам важен каждый Ваш ответ!</h5>
                </div>
                    <c:if test="${show_link}">
                        <div class="row center">
                            <a href="<c:url value="/gateway"/>" id="download-button"
                            class="btn-large waves-effect waves-light teal lighten-1">На главную</a>
                        </div>
                    </c:if>
                <div class="row center">
                    <p class="header col s12 light white-text">Interview
                        Street, <%=DateUtils.YYYY.format(DateUtils.getToday())%>
                    </p>
                </div>
            </div>
        </div>
        <div class="parallax"><img src="<c:url value="/resources/img/background.jpg"/>">
        </div>
    </div>
</main>

<%@include file="fragments/general-js.jsp" %>

</body>
</html>
