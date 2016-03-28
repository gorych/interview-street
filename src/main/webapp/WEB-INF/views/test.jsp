<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.html" %>
    <link href="/resources/css/designer.css" rel="stylesheet" type="text/css">
    <title>Interview Street - Редактор анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<c:set var="interviewId" value="${interview.id}" scope="session"/>
<main class="container">
    <div class="row">

        <div class="col l12 m12 s12">
            <h4 class="header teal-text">${interview.name}</h4>
            <h5 class="interview-type">открытая</h5>
            <div class="row icons-row">
                <div class="col l12 m12 s12">
                    <input class="hide" id="url-to-interview" value="https://ac.me/qmE_jpnYXFo" title=""/>
                    <i id="clipboard-btn" class="small material-icons blue-text text-accent-2"
                       data-clipboard-target="#url-to-interview" title="Копировать адрес ссылки">settings_ethernet</i>
                    <i class="small material-icons orange-text text-accent-4" title="Список респондентов">supervisor_account</i>
                    <i class="small material-icons green-text text-accent-4" title="Анализ результатов">equalizer</i>
                    <i class="small material-icons brown-text text-accent-4" title="Распечатать анкету">print</i>
                </div>
            </div>
            <div class="row narrow-row">
                <div class="input-field col l12 m12 s12">
                    <input
                            value="Здравствуйте, потратьте, пожалуйста, несколько минут своего времени на заполнение следующей анкеты."
                            id="initial-text" type="text" length="200"/>
                    <label class="active" for="initial-text">Вводный текст анкеты</label>
                </div>
            </div>
        </div>

        <div id="question-container" class="col offset-l1 offset-m1 l10 m10 s12 center">
            <a class="btn-floating btn-large question-btn waves-effect waves-light green accent-4 hoverable"
               title="Добавить вопрос"><i class="material-icons">add</i></a>

            <div class="divider grey lighten-1"></div>

            <div class="section">
                <div class="row narrow-row center">
                    <nav>
                        <div class="nav-wrapper center">
                            <ul class="center">
                                <li><a href="sass.html"><i class="material-icons">search</i></a></li>
                                <li><a href="badges.html"><i class="material-icons">view_module</i></a></li>
                                <li><a href="collapsible.html"><i class="material-icons">refresh</i></a></li>
                                <li><a href="mobile.html"><i class="material-icons">more_vert</i></a></li>
                            </ul>
                        </div>
                    </nav>
                    <div class="input-field col l12 m12 s12">
                        <input
                                value="1. Введите текст вопроса"
                                id="quest-text" type="text" length="250"/>
                        <label class="active" for="quest-text">Текст вопроса</label>
                    </div>
                    <div class="col l1">
                        <i style="position: absolute" class="small material-icons"
                           title="Список респондентов">delete</i>
                    </div>
                    <div class="input-field  col offset-l2 l8 m12 s12">
                        <input
                                id="disabled" type="text" length="100"/>
                        <label class="active" for="disabled">Введите примечание к вопросу</label>
                    </div>
                </div>
            </div>

            <div class="divider grey lighten-1"></div>

            <a class="btn-floating btn-large question-btn waves-effect waves-light green accent-4 hoverable"
               title="Добавить вопрос"><i class="material-icons">add</i></a>
        </div>
    </div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/general-js.html" %>
<script src="/resources/vendors/clipboard/clipboard.js"></script>
<script src="/resources/js/designer.js"></script>
</body>
</html>
