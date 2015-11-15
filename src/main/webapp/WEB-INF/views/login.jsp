<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вход</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
</head>
<body>
<div class="valign-wrapper height-fix">
    <div class="container">
        <div class="row">
            <div class="col m6 s12 hide-on-small-only">
                <h2>Interview Street</h2>

                <p>Это бесплатный онлайн-опросник, который позволяет создать анкету с профессиональным оформлением и
                    содержанием несколькими кликами мыши. Вы можете просматривать данные ответов в реальном времени в
                    форме графиков, таблиц и файлов данных в самых распространенных форматах. Вам не нужно что-либо
                    скачивать или устанавливать. Interview Street доступен из любого браузера. Кроме того, Вы можете
                    отвечать на анкеты и просматривать данные ответов на своих мобильных устройствах.</p>
            </div>
            <div class="col m6 s12">
                <h2 class="center-align">Вход</h2>

                <div class="row">
                    <form class="col s12" action="<c:url value="/j_spring_security_check"/>" method="POST">
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="passportSeries" placeholder="Серия и номер паспорта(слитно)"
                                       name="j_username" type="text" pattern="[0-9]{7}"
                                       oninvalid="invalidPassportData(this);"
                                       oninput="invalidPassportData(this);"/>
                                <label for="passportSeries">Паспортные данные</label>
                            </div>
                            <c:out value="${auth_error}"/>
                        </div>
                        <div class="row">
                            <div class="col s12">
                                <p>
                                    <input type="checkbox" id="remember">
                                    <label for="remember">Запомнить меня</label>
                                </p>
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
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="fragments/js_imports.html" %>
</body>
</html>