<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" media="screen" href="/resources/css/error-page.css">
    <title>Interview Street - Доступ запрещен</title>
</head>
<body>
<div class="container">
    <h1>403</h1>

    <p><strong>Доступ запрещен</strong></p>

    <p>У Вас нет доступа на страницу, которую вы запросили.</p>

    <p>
        Возможно, Вы ошиблись при наборе адреса, перешли по неверной
        ссылке или для доступа к данной странице необходимы соответствующие права.
    </p>
    
    <a href="JavaScript:history.go(-1);">Перейти на предыдущую страницу</a>
    
    <br/>
    <a href="<c:url value="/"/>" class="logo logo-img-1x">
        <img width="32" height="32" title="На главную" src="/resources/img/error-page-logo.png"/>
    </a>
</div>
</body>
</html>
