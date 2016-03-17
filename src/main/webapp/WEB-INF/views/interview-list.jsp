<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general_css.html" %>
    <link href="/resources/css/interview-list.css" rel="stylesheet" type="text/css">

    <title>Interview Street - Список анкет</title>
</head>
<body class="grey lighten-3">
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="chip-wrapper">
            <c:if test="${empty chip || (chip eq true)}">
                <div class="chip">
                    Здравствуйте, ${user_initials}, Вы вошли под правами редактора.
                    <i class="material-icons" id="hide-chip-btn">close</i>
                </div>
            </c:if>
        </div>
        <c:forEach var="interview" items="${interviews}">
            <div class="col s12 m6 l4">
                <div class="card darken-1 z-depth-2">
                    <div class="card-content card-content-wrapper grey lighten-4">
                        <i class="material-icons visibility-icon black-text" title="${interview.type.title}">
                        ${interview.type.visibilityIcon}</i>
                        <span class="new badge"></span>
                        <span class="block card-title-wrapper">${interview.name}</span>
                        <div class="divider divider-wrapper teal"></div>
                        <h6>${interview.placementDate}</h6>
                        <h6>Срок действия до: 2015-12-12</h6>
                        <h6 class="card-question">Вопросов: 30
                            <i class="material-icons center activator activator-wrapper right"
                               title="Посмотреть описание">more_vert</i>
                        </h6>
                    </div>
                    <div class="card-reveal card-reveal-wrapper">
                        <span class="card-title"><i class="material-icons right">close</i></span>
                        <span>${interview.description}</span>
                    </div>
                    <div class="card-action card-action-wrapper teal">
                        <div class="left-block">
                            <a href="#" data-interview-id="${interview.id}" class="lock-btn btn-floating btn waves-effect white accent-3">
                                <i class="material-icons black-text" title="${interview.title}">${interview.lockIcon}</i>
                            </a>
                        </div>
                        <div class="right-block">
                            <div class="fixed-action-btn action-btn-position click-to-toggle">
                                <a class="btn-floating btn white accent-3">
                                    <i class="large material-icons black-text" title="Дополнительные операции">dashboard</i>
                                </a>
                                <ul>
                                    <li>
                                        <a href="#" data-interview-id="${interview.id}" class="edit-interview-btn btn-floating orange" title="Редактировать анкету">
                                            <i class="material-icons black-text">mode_edit</i>
                                        </a>
                                    </li>
                                    <li>
                                        <a data-interview-id="${interview.id}" class="delete-btn btn-floating red" title="Удалить анкету">
                                            <i class="material-icons black-text">delete</i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <a href="<c:url value="/designer/${interview.id}"/>" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Список вопросов">subject</i>
                            </a>
                            <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Список респондентов">supervisor_account</i>
                            </a>
                            <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Анализ результатов">equalizer</i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red">
            <i class="large material-icons">dashboard</i>
        </a>
        <ul>
            <li>
                <a href="#" class="btn-floating red">
                    <i class="material-icons" title="Удалить">delete</i>
                </a>
            </li>
            <li>
                <a href="#" class="btn-floating orange darken-1">
                    <i class="material-icons" title="Редактировать">edit</i>
                </a>
            </li>
            <li>
                <a href="#add-edit-interview-modal" class="btn-floating green modal-trigger">
                    <i class="material-icons" title="Добавить">add</i>
                </a>
            </li>
        </ul>
    </div>

    <!--Add and edit interview modal-->
    <div id="add-edit-interview-modal" class="modal">
        <sf:form id="add-interview-form" class="col s12" method="POST" modelAttribute="extendUserInterview">
            <div class="modal-content">
                <h4>Добавление новой анкеты</h4>

                <div class="row">
                    <div class="input-field col s6">
                        <sf:select id="type" path="interview.type.id">
                            <option value="-1" disabled selected>Выбирите тип опроса</option>
                            <option value="1">Открытый</option>
                            <option value="2">Анонимный</option>
                        </sf:select>
                        <label>Тип опроса</label>
                    </div>
                    <div class="input-field col s6">
                        <sf:input id="name" type="text" path="interview.name" value="Новая анкета" length="50"/>
                        <label for="name" class="active">Наименование</label>
                    </div>
                    <div class="input-field col s6">
                        <select id="subdivisions" multiple>
                            <option value="-1" disabled selected>Выберите подразделения
                            </option>
                            <c:forEach var="item" items="${subdivisions}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <label>Подразделение</label>
                    </div>
                    <div class="input-field col s6">
                        <sf:select id="posts" path="posts" multiple="true">
                            <option value="-1" disabled selected>Сначала выберите подразделения</option>
                        </sf:select>
                        <label class="active">Должности</label>
                    </div>
                    <div class="input-field col s12">
                        <sf:input id="description" type="text" path="interview.description" value="Пустая анкета"
                                  length="100"/>
                        <label for="description" class="active">Описание</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a id="reset-form-btn" href="#"
                   class="modal-action modal-close waves-effect waves-red btn-flat ">Отмена</a>
                <a id="save-interview" href="#" class="btn-flat waves-effect waves-green modal-action">
                    Сохранить
                </a>
            </div>
        </sf:form>
    </div>

    <!--Delete modal-->
    <div id="delete-interview-modal" class="modal">
        <div class="modal-content">
            <h4>Удаление анкеты</h4>
            <p> Вы действительно хотите удалить выбранную анкеты? Все собранные ответы по данной анкетй будут
                безвозвратно удалены.</p>
        </div>
        <div class="modal-footer">
            <a class="waves-effect waves-red btn-flat modal-action modal-close" href="#">Нет</a>
            <a id="submit-delete-btn" class="waves-effect waves-green btn-flat modal-action" href="#">Да</a>
        </div>
    </div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/general_js.html" %>
<script src="/resources/js/interview-list.js"></script>
</body>
</html>