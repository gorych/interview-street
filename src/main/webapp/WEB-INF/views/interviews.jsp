<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Мои анкеты</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box">
            <div class="chip">
                Здравствуйте, Егор Сергеевич, Вы вошли под правами респондента.
                <i class="material-icons">close</i>
            </div>
            <h4 class="header teal-text text-lighten-1">Анонимные анкеты</h4>
            <h6>Все нижеперечисленные анкеты являются исключительно анонимными. Мы гарантируем конфиденциальность Ваших
                ответов.</h6>

            <div class="fixed-action-btn-fix">
                <div class="fixed-action-btn-location-fix">
                    <a class="btn-floating btn-middle waves-effect waves-light red" title="Показать неанонимные анкеты"><i
                            class="material-icons">visibility</i></a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title truncate">Название анкеты длинное1</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title">Название анкеты 2</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title">Название анкеты 3</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title">Название анкеты 4</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title">Название анкеты 5</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
        <div class="col s12 m6 l4">
            <div class="card teal darken-1 z-depth-2">
                <div class="card-content white-text">
                    <span class="card-title">Название анкеты 6</span>

                    <p>I am a very simple card. I am good at containing small bits of information.
                        I am convenient because I require little markup to use effectively.</p>
                </div>
                <div class="card-action">
                    <a class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="fragments/footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
