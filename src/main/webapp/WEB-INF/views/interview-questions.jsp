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
    <div class="col">
      <div class="box box-padding-fix ">
        <h6>Юзер Юзерович,<br/> потратьте, пожалуйста, несколько минут своего времени на заполнение
          следующей анкеты.</h6>
      </div>
      <section>
        <div class="question">
          <h5 class="header black-text">Как часто Вы ходите в кафе?</h5>

          <div class="answers">
            <form action="#">
              <p>
                <input name="group1" type="radio" id="test1"/>
                <label for="test1">Каждый день</label>
              </p>

              <p>
                <input name="group1" type="radio" id="test2"/>
                <label for="test2">Несколько раз в неделю</label>
              </p>

              <p>
                <input name="group1" type="radio" id="test3"/>
                <label for="test3">Один раз в месяц</label>
              </p>

              <p>
                <input name="group1" type="radio" id="test4"/>
                <label for="test4">Несколько раз в месяц</label>
              </p>

              <p>
                <input name="group1" type="radio" id="test5"/>
                <label for="test5">Несколько раз в год</label>
              </p>

              <p>
                <input name="group1" type="radio" id="test6"/>
                <label for="test6">Никогда</label>
              </p>
            </form>
          </div>
        </div>
      </section>
      <section>
        <div class="question">
          <h5 class="header black-text">В какую возрастную группу Вы входите?</h5>

          <div class="answers">
            <form action="#">
              <p>
                <input type="checkbox" id="test8" checked="checked"/>
                <label for="test8"><20</label>
              </p>

              <p>
                <input type="checkbox" id="test9" checked="checked"/>
                <label for="test9">21-30</label>
              </p>

              <p>
                <input type="checkbox" id="test10" checked="checked"/>
                <label for="test10">31-40</label>
              </p>

              <p>
                <input type="checkbox" id="test11" checked="checked"/>
                <label for="test11">41-50</label>
              </p>

              <div class="row">
                <div class="input-field col s12">
                  <input id="email" type="email">
                  <label for="email" data-error="wrong" data-success="right">Ваш вариант
                    ответа</label>
                </div>
              </div>

            </form>
          </div>
        </div>
      </section>
      <section>
        <div class="question">
          <h5 class="header black-text">Ваши предложения</h5>

          <div class="answers">
            <form action="#">
              <div class="row">
                <div class="input-field col s6">
                  <input id="first_name" type="text" class="validate">
                  <label for="first_name">Имя</label>
                </div>
                <div class="input-field col s6">
                  <input id="last_name" type="text" class="validate">
                  <label for="last_name">Фамилия</label>
                </div>
                <div class="input-field col s12">
                  <input id="password" type="password" class="validate">
                  <label for="password">Ваши предложения</label>
                </div>
              </div>
            </form>
          </div>
        </div>
      </section>
      <section>
        <div class="question">
          <h5 class="header black-text">Выберите из диапазона от 1 до 100 насколько эффективно работает наше
            предприятие</h5>

          <div class="answers">
            <p class="range-field">
              <input type="range" id="test11" min="1" max="100"/>
              <label for="test11">Оцените по шкале от 1 до 100</label>
            </p>
          </div>
        </div>
      </section>
    </div>
  </div>
  <div class="row">
    <div class="hide-on-large-only">
      <div class="col s12 m12">
        <a class="waves-effect waves-light teal white-text btn">Отправить анкету</a>
      </div>
    </div>
  </div>
</main>

<%@include file="fragments/footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
