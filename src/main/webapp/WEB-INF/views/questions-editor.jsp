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
<div class="navbar-fixed">
    <nav class="white">
        <div class="nav-wrapper container">
            <a href="#" class="brand-logo brand-logo-color-fix center">Название анкеты</a>
            <ul class="right hide-on-med-and-down">
                <a class="waves-effect waves-light btn">Выход</a>
            </ul>
        </div>
    </nav>
</div>
<main class="container">
    <div class="row">
        <div class="col l12 m12 s12">
            <div class="box box-padding-fix">
                <h4 class="header teal-text">Список вопросов</h4>
                <h6>В данной анкете пока нет ни одного вопроса.</h6>
            </div>
            <div id="interview_questions">
            </div>
        </div>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red" id="addQuestionBtn">
            <i class="large material-icons" title="Добавить вопрос">add</i>
        </a>
    </div>
</main>
<%@include file="fragments/small_footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
