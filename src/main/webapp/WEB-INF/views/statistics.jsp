<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.html" %>
    <title>Interview Street - Статистика</title>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="chip-wrapper box-padding-fix">
            <h4 class="col l9 m9 s9 header teal-text">Статистика</h4>
            <!-- Modal Trigger -->
            <a class="col l3 m3 s3 header waves-effect waves-light btn modal-trigger right" style="margin-top:7.5px"
               href="#filterModal">Фильтрация</a>
        </div>
    </div>
    <div class="row tabs-wrapper">
        <div class="col l2" id="questions">
            Анкета не выбрана
        </div>
        <div class="col l10 m12 s12 right">
            <!--<table class="centered highlight">
                <thead>
                <tr>
                    <th>Да, чел</th>
                    <th>Нет, чел</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>5/50</td>
                    <td>5/50</td>
                </tr>
                </tbody>
            </table>-->
            <div id="piechart" class="center visualization" style="width: 800px; height: 500px;">

            </div>
        </div>
    </div>
    <!-- Modal Structure -->
    <div id="filterModal" class="modal bottom-sheet">
        <form id="filterForm">
            <div class="modal-content row">
                <div class="input-field col l3 m6 s12">
                    <select id="types" onchange="loadInterviews(this)" name="type">
                        <option value="" disabled selected>Выберите тип анкеты</option>
                        <option value="1">Открытая</option>
                        <option value="2">Скрытая</option>
                    </select>
                    <label>Тип анкеты</label>
                </div>
                <div class="input-field col l3 m6 s12">
                    <select onchange="loadInterviewQuestions(this)" id="interviews" name="interview">
                        <option value="" disabled selected>Выберите анкету</option>
                    </select>
                    <label>Анкета</label>
                </div>
                <div class="input-field col l3 m6 s12">
                    <select id="subdivisions" name="subdivision">
                        <option value="" disabled selected>Выберите подразделение</option>
                    </select>
                    <label>Подразделение</label>
                </div>
                <div class="input-field col l3 m6 s12">
                    <select id="display">
                        <option value="" disabled selected>Выберите вид отображения</option>
                        <option value="1">Табличный</option>
                        <option value="2">Круговая диаграмма</option>
                    </select>
                    <label>Вид отображения</label>
                </div>
            </div>
        </form>
        <div class="modal-footer">
            <a href="#" class=" modal-action modal-close waves-effect waves-red btn-flat">Отмена</a>
            <a href="#" class=" modal-action modal-close waves-effect waves-green btn-flat">Показать</a>
        </div>
    </div>

</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/general-js.html" %>
<script type="text/javascript">
    google.load("visualization", "1", {packages: ["corechart"]});
    google.setOnLoadCallback(drawChart);
    drawChart(null);
</script>
</body>
</html>

