<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<ul class="side-nav" id="mobile-menu">
    <li><a href="<c:url value="/respondent/dashboard"/>">Мои анкеты</a></li>
    <sec:authorize access="hasRole('ROLE_EDITOR')">
        <li><a href="<c:url value="/editor/interview-list"/>">Список анкет</a></li>
        <li><a href="<c:url value="/viewer/statistics"/>">Статистика</a></li>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <li><a href="<c:url value="/j_spring_security_logout"/>">Выход</a></li>
    </sec:authorize>
</ul>