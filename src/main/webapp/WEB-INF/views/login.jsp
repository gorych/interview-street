<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Interview Street - Вход</title>
    <%@include file="fragments/meta.html" %>

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendors/materialize/materialize.min.css"/>">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet" type="text/css">
</head>
<body class="valign-wrapper">

<main class="container valign">
    <div class="row">

        <div class="col m6 l6 hide-on-small-only">
            <h2>Interview Street</h2>
            <p align="justify">
                Это бесплатный онлайн-опросник, который позволяет создать анкету с профессиональным оформлением и
                содержанием несколькими кликами мыши. Вы можете просматривать данные ответов в реальном времени в
                форме графиков, таблиц и файлов данных в самых распространенных форматах. Вам не нужно что-либо
                скачивать или устанавливать. Interview Street доступен из любого браузера. Кроме того, Вы можете
                отвечать на анкеты и просматривать результаты на своих мобильных устройствах.
            </p>
        </div>

        <div class="col m6 l6 s12">
            <h2 class="center-align">Вход</h2>

            <div class="row">
                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <sf:form class="col s12" action="${loginUrl}" method="POST">
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="login" name="j_username" type="text">
                            <label class="active" for="login">Имя пользователя</label>
                        </div>
                        <div class="input-field col s12">
                            <input id="pass" name="j_password" type="password">
                            <label class="active" for="pass">Пароль</label>
                        </div>
                        <c:if test="${not empty auth_error}">
                            <div class="red-text error">
                                <c:out value="${auth_error}"/>
                            </div>
                        </c:if>
                    </div>
                    <div class="divider"></div>
                    <div class="row">
                        <div class="col m12">
                            <p class="right-align">
                                <button class="btn btn-large waves-effect waves-light" type="submit" name="action">
                                    Войти
                                </button>
                            </p>
                        </div>
                    </div>
                </sf:form>
            </div>
        </div>
    </div>
</main>

<%@include file="fragments/general-js.jsp" %>
<script src="<c:url value="/resources/js/login.js"/>"></script>

</body>
</html>