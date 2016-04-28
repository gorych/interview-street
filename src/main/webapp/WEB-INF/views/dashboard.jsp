<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>
    <link href="<c:url value="/resources/css/dashboard.css"/>" rel="stylesheet" type="text/css">

    <title>Interview Street - Мои анкеты</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <%@include file="fragments/chip.jsp" %>

        <div class="collection with-header">
            <div class="collection-header teal lighten-1 white-text">
                <h4>Ваши анкеты</h4>
                <h6>Количество: ${user_interviews.size()}</h6>
            </div>
            <div class="row narrow-row">
                <c:if test="${empty user_interviews}">
                    <div class="collection-item teal lighten-5">
                        <h6>У вас нет анкет для прохождения.</h6>
                    </div>
                </c:if>

                <c:forEach var="uInterview" items="${user_interviews}">
                    <c:set var="interview" value="${uInterview.interview}"/>

                    <div class="collection-item avatar">

                        <c:choose>
                            <c:when test="${interview.isNew}">
                                <i class="large-i material-icons circle green lighten-1"
                                   title="Анкета добавлена сегодня">schedule</i>
                                <span class="title"><a class="green-text"
                                                       href="<c:url value="/respondent/${interview.hash}/interview"/>">${interview.name}</a></span>
                            </c:when>
                            <c:when test="${interview.isDeadline}">
                                <i class="large-i material-icons circle red lighten-1"
                                   title="Сегодня последний день для прохождения анкеты">watch_later</i>
                                <span class="title"><a class="red-text"
                                                       href="<c:url value="/respondent/${interview.hash}/interview"/>">${interview.name}</a></span>
                            </c:when>
                            <c:otherwise>
                                <i class="large-i material-icons circle blue-grey"
                                   title="Ещё есть время для прохождения">timelapse</i>
                                <span class="title"><a class="blue-grey-text text-darken-4"
                                                       href="<c:url value="/respondent/${interview.hash}/interview"/>">${interview.name}</a></span>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${interview.secondPassage}">
                            <p class="blue-grey-text">Доступно многократное прохождение</p>
                        </c:if>
                        <p class="publication-date">${interview.formatPlacementDate}</p>
                        <p class="end-date">${interview.formatEndDate}</p>
                        <a href="<c:url value="/respondent/${interview.hash}/interview"/>"
                           class="secondary-content"><i class="material-icons">send</i></a>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
</main>

<%@include file="fragments/thanks-for-passing-modal.jsp" %>

<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/general-js.jsp" %>

</body>
</html>