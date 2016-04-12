<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<nav class="white">
    <div class="nav-wrapper container">
        <a href=""><img class="brand-logo brand-logo-color-fix responsive-img fix" src="<c:url value="/resources/img/logo.png"/>"></a>
        <a href="#" data-activates="mobile-menu" class="teal-text button-collapse"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
            <li><a href="<c:url value="/interviews"/>">Мои анкеты</a></li>
            <sec:authorize access="hasRole('ROLE_EDITOR')">
                <li><a href="<c:url value="/editor/interview-list"/>">Список анкет</a></li>
                <li><a href="<c:url value="/statistics"/>">Статистика</a></li>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <a href="<c:url value="/j_spring_security_logout"/>" class="waves-effect waves-light btn">Выход</a>
            </sec:authorize>
        </ul>
        <%@include file="mobile/menu.jsp" %>
    </div>
</nav>
