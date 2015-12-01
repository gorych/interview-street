<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Мои анкеты</title>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages': ['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

            // Create the data table.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Topping');
            data.addColumn('number', 'Slices');
            data.addRows([
                ['Mushrooms', 3],
                ['Onions', 1],
                ['Olives', 1],
                ['Zucchini', 1],
                ['Pepperoni', 2]
            ]);

            // Set chart options
            var options = {
                'title': 'How Much Pizza I Ate Last Night',
                'width': 400,
                'height': 300
            };

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
    <script type="text/javascript">
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Quarks', 'Leptons', 'Gauge Bosons', 'Scalar Bosons'],
                [2/3, -1, 0, 0],
                [2/3, -1, 0, null],
                [2/3, -1, 0, null],
                [-1/3, 0, 1, null],
                [-1/3, 0, -1, null],
                [-1/3, 0, null, null],
                [-1/3, 0, null, null]
            ]);

            var options = {
                title: 'Charges of subatomic particles',
                legend: { position: 'top', maxLines: 2 },
                colors: ['#5C3292', '#1A8763', '#871B47', '#999999'],
                interpolateNulls: false,
            };

            var chart = new google.visualization.Histogram(document.getElementById('chart_div1'));
            chart.draw(data, options);
        }
    </script>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box">
            <c:if test="${empty chip || (chip eq true)}">
                <div class="chip">
                    Здравствуйте, ${user_initials}, Вы вошли под правами респондента.
                    <i class="material-icons" onclick="hideChip()">close</i>
                </div>
            </c:if>
            <h4 class="teal-text">Ваши анкеты</h4>

            <c:choose>
                <c:when test="${userInterviews.size() < 1}">
                    <h6>У вас пока нет анкет для прохождения.</h6>
                </c:when>
                <c:otherwise>
                    <h6>Количество: ${userInterviews.size()}</h6>
                </c:otherwise>
            </c:choose>
        </div>
        <c:forEach var="item" items="${userInterviews}">
            <div class="col s6 m6 l4">
                <div class="card teal darken-1 z-depth-2">
                    <div class="card-content white-text">
                        <span class="card-title card-title-fix">${item.interview.name}</span>
                        <h6>Дата размещения:<br/> ${item.interview.placementDate}</h6>

                        <p>${item.interview.description}. А может и не для всех. Это как пойдет</p>
                    </div>
                    <div class="card-action">
                        <a href="/interview-questions/${item.interview.id}"
                           class="waves-effect waves-light grey lighten-2 black-text btn">Перейти к анкете</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div id="chart_div"></div>
    <div id="chart_div1" style="width: 900px; height: 500px;"></div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
