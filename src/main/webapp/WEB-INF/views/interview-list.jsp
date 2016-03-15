<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/css_imports.html" %>
    <title>Interview Street - Список анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box">
            <c:if test="${empty chip || (chip eq true)}">
                <div class="chip">
                    Здравствуйте, ${user_initials}, Вы вошли под правами редактора.
                    <i class="material-icons" id="hide-chip-btn">close</i>
                </div>
            </c:if>
        </div>
        <form id="interview-form">
            <table id="interview-table" class="centered highlight responsive-table">
                <thead>
                <tr>
                    <th data-field="id"><a id="select-all" href="#">
                        <i class="material-icons" title="Выбрать все">done_all</i></a>
                    </th>
                    <th data-field="name">Название анкеты</th>
                    <th data-field="description">Тип</th>
                    <th data-field="description">Описание</th>
                    <th data-field="date_created">Дата создания</th>
                    <th data-field="state">Состояние</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="interview" items="${interviews}">
                    <tr>
                        <td>
                            <input type="checkbox" value="${interview.id}" id="${interview.id}" name="id"/>
                            <label for="${interview.id}" class="table-checkbox-fix "></label>
                        </td>
                        <td>${interview.name}</td>
                        <c:choose>
                            <c:when test="${interview.type.id <= 1}">
                                <td><i
                                        class="material-icons table-material-icons-fix"
                                        title="Неанонимная">visibility</i></td>
                            </c:when>
                            <c:otherwise>
                                <td><i
                                        class="material-icons table-material-icons-fix"
                                        title="Анонимная">visibility_off</i></td>
                            </c:otherwise>
                        </c:choose>
                        <td>${interview.description}</td>
                        <td>${interview.placementDate}</td>
                        <c:choose>
                            <c:when test="${interview.hide}">
                                <td><a data-interview-id="${interview.id}"><i
                                        class="material-icons table-material-icons-fix"
                                        title="Закрыта для прохождения">lock</i></a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a data-interview-id="${interview.id}"><i
                                        class="material-icons table-material-icons-fix"
                                        title="Открыта для прохождения">lock_open</i></a></td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <a href="<c:url value="/designer/${interview.id}"/>"
                               class="btn-floating cyan darken-1"><i
                                    class="material-icons" title="Список вопросов">subject</i></a>
                        </td>
                        <td>
                            <a class="btn-floating teal accent-4">
                                <i class="material-icons" title="Список респондентов">supervisor_account</i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red">
            <i class="large material-icons">dashboard</i>
        </a>
        <ul>
            <li>
                <a id="delete-btn" href="#" class="btn-floating red">
                    <i class="material-icons" title="Удалить">delete</i>
                </a>
            </li>
            <li>
                <a id="edit-interview-btn" href="#" class="btn-floating orange darken-1">
                    <i class="material-icons" title="Редактировать">edit</i>
                </a>
            </li>
            <li>
                <a href="#add-interview-modal" class="btn-floating green modal-trigger">
                    <i class="material-icons" title="Добавить">add</i>
                </a>
            </li>

        </ul>
    </div>

    <div id="add-interview-modal" class="modal modal-fixed-footer">
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

    <div id="delete-interview-modal" class="modal">
        <div class="modal-content">
            <h4>Удаление анкеты</h4>
            <p> Вы действительно хотите удалить выбранные анкеты? Все собранные ответы по данным анкетам будут
                безвозвратно удалены.</p>
        </div>
        <div class="modal-footer">
            <a class="waves-effect waves-red btn-flat modal-action modal-close" href="#">Нет</a>
            <a id="submit-delete-btn" class="waves-effect waves-green btn-flat modal-action" href="#">Да</a>
        </div>
    </div>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
<script src="/resources/js/interview-list.js"></script>
</body>
</html>