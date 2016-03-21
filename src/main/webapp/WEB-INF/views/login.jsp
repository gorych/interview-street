<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html class="full-height">
<head>
    <title>Interview Street - Вход</title>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.html" %>
</head>
<body class="full-height">
<%@include file="fragments/mobile/header.html" %>
<div class="valign-wrapper height-fix">
    <div class="container">
        <div class="row">
            <div class="col m6 l6 hide-on-small-only">
                <h2>Interview Street</h2>
                <p>Это бесплатный онлайн-опросник, который позволяет создать анкету с профессиональным оформлением и
                    содержанием несколькими кликами мыши. Вы можете просматривать данные ответов в реальном времени в
                    форме графиков, таблиц и файлов данных в самых распространенных форматах. Вам не нужно что-либо
                    скачивать или устанавливать. Interview Street доступен из любого браузера. Кроме того, Вы можете
                    отвечать на анкеты и просматривать результаты на своих мобильных устройствах.</p>
            </div>
            <div class="col m6 l6 s12">
                <h2 class="center-align">Вход</h2>

                <div class="row">
                    <sf:form class="col s12" action="/j_spring_security_check" method="POST">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="passportSeries" placeholder="Серия и номер паспорта(слитно)"
                                       name="j_username" type="text" pattern="[А-я]{2}[0-9]{7}"
                                       oninvalid="invalidPassportData(this);"
                                       oninput="invalidPassportData(this);" data-error="Пользователь не существует"/>
                                <label class="activate" for="passportSeries">Паспортные данные</label>
                            </div>
                        </div>
                        <c:if test="${not empty auth_error}">
                            <div class="error-alert form-alert-error-fix">
                                <c:out value="${auth_error}"/>
                            </div>
                        </c:if>
                        <div class="row">
                            <div class="col s12" class="remember-me-checkbox-fix">
                                <input type="checkbox" id="remember_me" name="j_spring_security_remember_me">
                                <label for="remember_me">Запомнить меня</label>
                            </div>
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
    </div>
</div>
<%@include file="fragments/general-js.html" %>
</body>
</html>