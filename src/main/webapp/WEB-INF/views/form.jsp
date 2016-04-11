<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.html" %>
    <link href="/resources/css/form.css" rel="stylesheet" type="text/css">
    <title>Interview Street - Добавление/Редактирование анкеты</title>
</head>
<body>
<main class="valign-wrapper center">
    <div class="row container valign">

        <c:choose>
            <c:when test="${edit_mode eq true}">
                <input name="id" class="edit-mode" type="hidden" value="${interview.id}"/>
                <%@include file="fragments/fill-edit-form.jsp" %>
            </c:when>
            <c:otherwise>
                <%@include file="fragments/empty-add-form.jsp" %>
            </c:otherwise>
        </c:choose>

        <div class="promo-container hide">
            <div class="col s12 m12 l12 center">
                <h3>Выберите тип создаваемого опроса</h3>
            </div>

            <div class="col s12 m4 l4">
                <div class="center promo open" data-type="${open_type.id}">
                    <h3 class="modal-title hide">Добавление открытого опроса</h3>
                    <i class="material-icons">${open_type.visibilityIcon}</i>
                    <p class="promo-caption">Открытый опрос</p>
                    <p class="light center">
                        Досутпен только для авторизованных пользователей. Вся информация о респондентах сохраняется.
                    </p>
                </div>
            </div>

            <div class="col s12 m4 l4">
                <div class="center promo close" data-type="${close_type.id}">
                    <h3 class="modal-title hide">Добавление анонимного опроса</h3>
                    <i class="material-icons">${close_type.visibilityIcon}</i>
                    <p class="promo-caption">Анонимный опрос</p>
                    <p class="light center">
                        Досутпен для всех пользователей по конкретному адресу. Информация о респондентах не
                        сохраняется.
                    </p>
                </div>
            </div>

            <div class="col s12 m4 l4">
                <div class="center promo expert" data-type="${expert_type.id}">
                    <h3 class="modal-title hide">Добавление экспертного опроса</h3>
                    <i class="material-icons">${expert_type.visibilityIcon}</i>
                    <p class="promo-caption">Экспертный опрос</p>
                    <p class="light center">
                        Респондентами опроса являются эксперты. Для прохождения необходимо зарегистрировать аккаунт.
                    </p>
                </div>
            </div>
        </div>

        <div class="preloader-wrapper center-align big">
            <div class="spinner-layer spinner-red-only">
                <div class="circle-clipper left">
                    <div class="circle"></div>
                </div><div class="gap-patch">
                <div class="circle"></div>
            </div><div class="circle-clipper right">
                <div class="circle"></div>
            </div>
            </div>
        </div>

    </div>
</main>

<%@include file="fragments/general-js.html" %>
<script src="<c:url value="/resources/js/form.js"/>"></script>

</body>