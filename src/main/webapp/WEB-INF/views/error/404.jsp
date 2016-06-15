<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../fragments/meta.html" %>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/error-page.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/vendors/materialize/materialize.min.css"/>">

    <title>Interview Street - Доступ запрещен</title>
</head>
<body class="valign-wrapper">
<div class="container valign">
    <div class="row">
        <div class="col s12 m12 l12">
            <h1>404</h1>

            <p><strong>Ресурс не найден</strong></p>

            <p>Не найден путь к файлу или ресурсу.</p>

            <p>
                Возможно, вы ошиблись при наборе адреса, перешли по неверной ссылке либо страница была удалена.
            </p>

            <a href="JavaScript:history.go(-1);">Перейти на предыдущую страницу</a>

            <br/>
            <a href="<c:url value="/"/>" class="logo logo-img-1x">
                <img width="32" height="32" title="На главную"
                     src="<c:url value="/resources/img/error-page-logo.png"/>"/>
            </a>
        </div>
    </div>
</div>
</body>
</html>
