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
                <section class='question'>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="password" type="text" length="200" placeholder="Введите вопрос">
                            <label for="password">Вопрос</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <select>
                                <option value="" disabled selected>Выберите тип ответа</option>
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                            </select>
                            <label>Тип ответа</label>
                        </div>
                        <div class="input-field col s6">
                            <input id="last_name" type="text" class="validate">
                            <label for="last_name">Текст ответа</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <select>
                                <option value="" disabled selected>Выберите тип ответа</option>
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                            </select>
                            <label>Тип ответа</label>
                        </div>
                        <div class="input-field col s6">
                            <input id="last_name" type="text" class="validate">
                            <label for="last_name">Текст ответа</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <select>
                                <option value="" disabled selected>Выберите тип ответа</option>
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                            </select>
                            <label>Тип ответа</label>
                        </div>
                        <div class="input-field col s6">
                            <input id="last_name" type="text" class="validate">
                            <label for="last_name">Текст ответа</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <select>
                                <option value="" disabled selected>Выберите тип ответа</option>
                                <option value="1">Option 1</option>
                                <option value="2">Option 2</option>
                                <option value="3">Option 3</option>
                            </select>
                            <label>Тип ответа</label>
                        </div>
                        <div class="input-field col s6">
                            <input id="last_name" type="text" class="validate">
                            <label for="last_name">Текст ответа</label>
                        </div>
                    </div>
                    <a class="btn-floating btn-middle waves-effect waves-light green"><i class="material-icons">add</i></a>

                    <div class="divider divider-margin-fix"></div>
                    <div class="right-align">
                        <a href="#"
                           class=" modal-action modal-close waves-effect waves-green btn-flat">Сохранить</a>
                        <a href="#" class=" modal-action modal-close waves-effect waves-red btn-flat">Удалить</a>
                    </div>
                </section>
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
