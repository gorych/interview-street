<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Interview Street - Статистика</title>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box box-padding-fix">
            <h4 class="col l12 m12 s12 header teal-text">Статистика</h4>

            <div class="input-field col l4 m6 s12">
                <select onchange="loadInterviews(this)">
                    <option value="" disabled selected>Выберите тип анкеты</option>
                    <option value="0">Все</option>
                    <option value="1">Открытая</option>
                    <option value="2">Скрытая</option>
                </select>
                <label>Тип анкеты</label>
            </div>
            <div class="input-field col l4 m6 s12">
                <select id="interviews" onchange="loadInterviewQuestions(this)">
                    <option value="" disabled selected>Выберите анкету</option>
                </select>
                <label>Анкета</label>
            </div>
            <div class="input-field col l4 m6 s12">
                <select id="subdivisions">
                    <option value="" disabled selected>Выберите подразделение</option>
                </select>
                <label>Подразделение</label>
            </div>
        </div>

    </div>
    <div class="row tabs-wrapper">
        <div class="col l3" id="questions">
            Анкета не выбрана
        </div>
        <div class="col l9">
            <div id="piechart" class="center visualization" style="width: 900px; height: 500px;"></div>
        </div>
    </div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
<script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart);
    function drawChart(array) {
        alert(1)
        var data = google.visualization.arrayToDataTable([
            ['Task', 'Hours per Day'],
            ['Work',     11],
            ['Eat',      2],
            ['Commute',  2],
            ['Watch TV', 2],
            ['Sleep',    7]
        ]);

        var options = {
            title: 'My Daily Activities'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        chart.draw(data, options);
    }
    $('.tabs-wrapper .visualization').pushpin({top: $('.tabs-wrapper').offset().top});
</script>
</body>
</html>
