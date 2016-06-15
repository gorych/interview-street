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

<main>
    <div class="row teal">
        <div class="container">
            <div class="col l9 m9 s9">
                <h3 class="white-text">Статистика</h3>
            </div>
            <div class="col l3 m3 s3">
                <div class="fixed-action-btn click-to-toggle export-btn">
                    <a href="<c:url value="/download/excel/${interview.hash}?exportType=all"/>"
                       class="btn-floating red accent-1" data-position="left" data-tooltip="Экспорт в Excel">
                        <i class="large material-icons">file_download</i>
                    </a>
                </div>
            </div>
            <div class="col l12 m12 s12">
                <ul class="collapsible" data-collapsible="accordion">
                    <li>
                        <div class="collapsible-header"><i class="material-icons">filter_list</i>Настройка фильтров
                        </div>
                        <div class="collapsible-body white">
                            <div class="row">
                                <form style="padding: 0 1rem">
                                    <div class="col l12 m12 s12">
                                        <div class="input-field col s12">
                                            <select id="interviews">
                                                <option value="" disabled selected>Выберите анкету</option>
                                                <c:forEach var="item" items="${interviews}">
                                                    <c:choose>
                                                        <c:when test="${interview.id eq item.id}">
                                                            <option data-type="${interview.type.name}" selected
                                                                    value="${item.hash}">${item.name}&nbsp;(${item.type.rusName})
                                                            </option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option data-type="${interview.type.name}"
                                                                    value="${item.hash}">${item.name}&nbsp;(${item.type.rusName})
                                                            </option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                            <label for="interviews">Анкета</label>
                                        </div>
                                    </div>
                                    <div class="col l6 m6 s12">
                                        <fieldset>
                                            <legend>Фильтрация по подразделению</legend>
                                            <div class="input-field col s12">
                                                <select id="subs" disabled>
                                                    <option value="" disabled selected>Выберите подразделение</option>
                                                    <option value="0">Все</option>
                                                    <c:forEach var="sub" items="${subdivisions}">
                                                        <option value="${sub.id}">${sub.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <label for="subs">Подразделение</label>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col l6 m6 s12">
                                        <fieldset>
                                            <legend>Фильтрация по дате публикования</legend>
                                            <div class="input-field col s12">
                                                <select id="publish-id" disabled>
                                                    <option value="0" disabled selected>За все время</option>
                                                    <c:forEach var="pInterview" items="${published_interviews}">
                                                        <option value="${pInterview.id}">${pInterview.formatDate}</option>
                                                    </c:forEach>
                                                </select>
                                                <label for="publish-id">Дата размещения</label>
                                            </div>
                                        </fieldset>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row container">
        <div class="col l12 m12 s12">
            <h5 id="interview-name" class="teal-text">${interview.name}</h5>

            <c:if test="${not_answers}">
                <h6 class="red-text">По данной анкете пока не собрано ни одного ответа.</h6>
            </c:if>
            <c:if test="${empty statistics}">
                <h6>Настройте фильтры для просмотра результатов.</h6>
            </c:if>
        </div>
        <div class="col l12 m12 s12">
            <ul id="statistics-container" class="collapsible hide" data-collapsible="accordion"></ul>
        </div>
    </div>

    <div id="respondent-list" class="modal bottom-sheet">
        <div class="modal-content">
            <h4>Ответившие на вопрос</h4>
            <p class="list-body"></p>
        </div>
        <div class="modal-footer">
            <a href="#" class=" modal-action modal-close waves-effect waves-green btn-flat">Закрыть</a>
        </div>
    </div>
</main>

<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/templates/statistics-item-template.jsp" %>

<%@include file="fragments/general-js.jsp" %>
<script src="<c:url value="/resources/vendors/highchart/highcharts.js"/>"></script>
<script src="<c:url value="/resources/vendors/highchart/data.js"/>"></script>
<script src="<c:url value="/resources/vendors/highchart/exporting.js"/>"></script>
<script src="<c:url value="/resources/vendors/js-render/jsrender.js"/>"></script>
<script src="<c:url value="/resources/js/statistics.js"/>"></script>

</body>
</html>
