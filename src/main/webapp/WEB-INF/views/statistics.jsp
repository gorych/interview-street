<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>
    <link href="<c:url value="/resources/css/statistics.css"/>" rel="stylesheet" type="text/css">
    <title>Interview Street - Статистика</title>
</head>
<body>

<%@include file="fragments/header.jsp" %>

<main class="container">
    <div class="row">
        <div class="col l9 m12 s12">
            <h4 class="header teal-text">${interview_name}</h4>
        </div>
    </div>

    <c:if test="${not_answers || empty statistics}">
        <h6 class="red-text">По данной анкете пока не собрано ни одного ответа.</h6>
    </c:if>

    <c:if test="${not not_answers && not empty statistics}">
        <div class="col l12 m12">
            <ul class="collapsible" data-collapsible="accordion">
                <c:forEach var="statistic" items="${statistics}" varStatus="cur">
                    <c:set var="questType" value="${statistic.questionType}"/>
                    <li>
                        <div class="collapsible-header collapsible-header-fix active">
                                ${cur.index + 1}.&nbsp;${statistic.questionText}
                        </div>
                        <div class="collapsible-body">
                            <c:if test="${questType ne 'text'}">
                                <div class="row mobile-row center-align hide-on-large-only">
                                    <a class="btn-floating table-btn"><i class="material-icons">list</i></a>
                                    <a class="btn-floating pie-chart-btn"><i class="material-icons">album</i></a>
                                    <a class="btn-floating col-chart-btn"><i
                                            class="material-icons">assessment</i></a>
                                </div>
                            </c:if>
                            <div class="row collapsible-row valign-wrapper">
                                <div class="col offset-l1 offset-m1 l10 m10 s12">
                                    <c:choose>
                                        <c:when test="${questType ne 'text'}">

                                            <!--Container for chart-->
                                            <div class="chart-container"></div>

                                            <table id="'datatable'+${cur.index}" class="centered">
                                                <thead>
                                                <tr>
                                                    <c:if test="${questType eq 'rating'}">
                                                        <th>Оценка респондента</th>
                                                    </c:if>
                                                    <c:if test="${questType ne 'rating'}">
                                                        <th>Ответ респондента</th>
                                                    </c:if>
                                                    <th>Ответило, чел</th>
                                                    <th>Ответило, %</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="data" items="${statistic.answerData}">
                                                    <tr>
                                                        <th class="center-align">${data.key}</th>
                                                        <td>${data.value[0]}</td>
                                                        <td>${data.value[1]}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                            <c:if test="${questType eq 'rating'}">
                                                <span class="center-align teal-text left-align">
                                                    <b>
                                                        Максимально допустимая оценка: ${statistic.maxEstimate}<br/>
                                                        Ответило человек: ${statistic.total}
                                                    </b>
                                                </span>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="data" items="${statistic.answerData}">
                                                <div class="custom-answer">
                                                        ${data.key} (x${data.value[0]})
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <c:if test="${questType ne 'text'}">
                                    <div class="col l1 valign hide-on-med-and-down">
                                        <a class="btn-floating table-btn"><i class="material-icons">list</i></a>
                                        <a class="btn-floating pie-chart-btn"><i class="material-icons">album</i></a>
                                        <a class="btn-floating col-chart-btn"><i
                                                class="material-icons">assessment</i></a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</main>

<%@include file="fragments/footer.jsp" %>

<%@include file="fragments/general-js.jsp" %>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/data.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="<c:url value="/resources/js/statistics.js"/>"></script>

</body>
</html>
