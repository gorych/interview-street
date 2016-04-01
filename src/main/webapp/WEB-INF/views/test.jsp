<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.html" %>
    <link href="<c:url value="/resources/css/designer.css"/>" rel="stylesheet" type="text/css">
    <title>Interview Street - Редактор анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<c:set var="interviewId" value="${interview.id}" scope="session"/>
<main class="container">
    <div class="row">

        <div class="col l12 m12 s12">
            <h4 class="header teal-text">${interview.name}</h4>
            <h5 class="interview-type">открытая</h5>

            <!--Icon bar-->
            <div class="row icons-row">
                <div class="col l12 m12 s12">

                    <i id="clipboard-btn" class="small material-icons light-blue-text text-accent-3"
                       data-clipboard-target=".interview-url" title="Копировать адрес ссылки">settings_ethernet</i>
                    <i class="small material-icons orange-text text-accent-4" title="Список респондентов">supervisor_account</i>
                    <i class="small material-icons green-text text-accent-4" title="Анализ результатов">equalizer</i>
                    <i class="small material-icons blue-grey-text text-lighten-2" title="Предварительный просмотр">pageview</i>
                    <i class="small material-icons brown-text text-lighten-1" title="Распечатать анкету">print</i>
                </div>
            </div>

            <!--Interview initial text-->
            <div class="row narrow-row">
                <div class="input-field col l12 m12 s12">
                    <input
                            value="Здравствуйте, потратьте, пожалуйста, несколько минут своего времени на заполнение следующей анкеты."
                            id="initial-text" type="text" length="200"/>
                    <label class="active" for="initial-text">Вводный текст анкеты</label>
                </div>
            </div>
        </div>

        <!--This is container for forms-->
        <div id="question-container" class="col offset-l1 offset-m1 l10 m10 s12 center">
            <a class="btn-floating btn-large add-quest-btn first-btn waves-effect waves-light blue-grey lighten-2 hoverable"
               title="Добавить вопрос"><i class="material-icons">add</i></a>
        </div>
    </div>

    <!--Hidden input for clipboard-->
    <label>
        <input type="text" class="interview-url" value="/test/url/place/here2d"/>
    </label>
</main>

<%@include file="fragments/footer.jsp" %>

<%@include file="fragments/templates/form-template.jsp"%>
<%@include file="fragments/templates/stag-list-template.jsp"%>

<%@include file="fragments/general-js.html" %>
<script src="<c:url value="/resources/vendors/clipboard/clipboard.js"/>"></script>
<script src="<c:url value="/resources/vendors/js-render/jsrender.js"/>"></script>
<script src="/resources/js/designer.js"></script>

</body>
</html>
