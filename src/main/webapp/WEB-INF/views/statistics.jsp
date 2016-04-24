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

    <c:if test="${empty statistics}">
        <h6 class="red-text">По данной анкете пока не собрано ни одного ответа.</h6>
    </c:if>

    <c:if test="${not empty statistics}">
        <div class="col l12 m12">
            <ul class="collapsible" data-collapsible="accordion">
                <c:forEach var="statistic" items="${statistics}" varStatus="cur">
                    <c:set var="key" value="${statistic.key}"/>
                    <li>
                        <div class="collapsible-header collapsible-header-fix active">
                                ${cur.index + 1}.&nbsp;${key.text}
                        </div>
                        <div class="collapsible-body">
                            <c:if test="${key.type ne 'text'}">
                                <div class="row mobile-row center-align hide-on-large-only">
                                    <a class="btn-floating" style="margin-bottom: 5px"><i
                                            class="material-icons">list</i></a>
                                    <a class="btn-floating" style="margin-bottom: 5px"><i
                                            class="material-icons">album</i></a>
                                    <a class="btn-floating" style="margin-bottom: 5px"><i
                                            class="material-icons">assessment</i></a>
                                </div>
                            </c:if>
                            <div class="row collapsible-row valign-wrapper">
                                <div class="col offset-l1 offset-m1 l10 m10 s12">
                                    <c:choose>
                                        <c:when test="${key.type ne 'text'}">
                                            <table class="centered hide-on-small-only">
                                                <c:if test="${key.type eq 'rating'}">
                                                    <caption class="left-align teal-text">
                                                        Максимально допустимая оценка: ${statistic.value[0].total}
                                                    </caption>
                                                </c:if>
                                                <thead>
                                                <tr>
                                                    <th>Оценка респондента</th>
                                                    <th>Ответило %, (чел.)</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="data" items="${statistic.value}">
                                                        <tr>
                                                            <th class="center-align">${data.answer}</th>
                                                            <td>${data.percent}% (${data.count} чел.)</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="data" items="${statistic.value}">
                                                <div class="custom-answer">
                                                        ${data.answer} (x${data.count})
                                                </div>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <c:if test="${key.type ne 'text'}">
                                    <div class="col l1 valign hide-on-med-and-down">
                                        <a class="btn-floating" style="margin-bottom: 5px"><i
                                                class="material-icons">toc</i></a>
                                        <a class="btn-floating" style="margin-bottom: 5px"><i
                                                class="material-icons">album</i></a>
                                        <a class="btn-floating" style="margin-bottom: 5px"><i
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

</body>
</html>
