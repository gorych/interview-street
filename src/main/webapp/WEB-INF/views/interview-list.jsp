<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="fragments/css_imports.html" %>
    <title>Список анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row">
        <div class="box">
            <div class="chip">
                Здравствуйте, ${user_initials}, Вы вошли под правами редактора.
                <i class="material-icons">close</i>
            </div>
        </div>
        <table class="centered highlight">
            <thead>
            <tr>
                <th data-field="id"><a href="#"><i class="material-icons" title="Выбрать все">done_all</i></a></th>
                <th data-field="name">Название анкеты</th>
                <th data-field="description">Дата создания</th>
                <th data-field="description">Состояние</th>
                <th data-field="respondentCount">Респондентов</th>
                <th data-field="questionCount">Вопросов</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <input type="checkbox" id="test5"/>
                    <label for="test5"></label>
                </td>
                <td>Название анкеты 1</td>
                <td>15.10.2015</td>
                <td><i class="material-icons" title="Открыта">visibility</i></td>
                <td>30</td>
                <td>30</td>
                <td>
                    <a href="<c:url value="/questions-editor"/>" class="btn-floating cyan darken-1"><i
                            class="material-icons" title="Список вопросов">subject</i></a>
                </td>
                <td>
                    <a class="btn-floating teal accent-4"><i class="material-icons" title="Список респондентов">supervisor_account</i></a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="fixed-action-btn horizontal" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red">
            <i class="large material-icons">dashboard</i>
        </a>
        <ul>
            <li><a class="btn-floating red"><i class="material-icons" title="Удалить">delete</i></a></li>
            <li><a class="btn-floating yellow darken-1"><i class="material-icons" title="Редактировать">edit</i></a>
            </li>
            <li><a href="#addInterviewModal" class="btn-floating green modal-trigger"><i class="material-icons"
                                                                                         title="Добавить">add</i></a>
            </li>
            <li><a class="btn-floating blue"><i class="material-icons" title="Скрыть">visibility_off</i></a></li>
        </ul>
    </div>

    <div id="addInterviewModal" class="modal">
        <sf:form class="col s12" method="POST" modelAttribute="interview" action="/create-interview">
            <div class="modal-content">
                <h4>Добавление новой анкеты</h4>

                <div class="row">
                    <div class="input-field col s6">
                        <sf:select path="type.id">
                            <option value="1" disabled selected>Тип опроса</option>
                            <option value="1">Открытый</option>
                            <option value="2">Скрытый</option>
                        </sf:select>
                    </div>
                    <div class="input-field col s6">
                        <sf:input type="text" path="name"/>
                        <label for="name">Наименование</label>
                    </div>
                    <div class="input-field col s6">
                        <select multiple onchange="getSelectValues(this)">
                            <option value="${subdivisions[0].id}" disabled selected>${subdivisions[0].name}</option>
                            <c:forEach var="item" items="${subdivisions}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <label>Подразделения</label>
                    </div>
                    <div class="input-field col s6">
                        <select multiple>
                            <option value="${employees[0].post.id}" disabled selected>${employees[0].post.name}</option>
                            <c:forEach var="employee" items="${employees}">
                                <option value="${employee.post.id}">${employee.post.name}</option>
                            </c:forEach>
                        </select>
                        <label>Должности сотрудников</label>
                    </div>
                    <div class="input-field col s12">
                        <sf:input type="text" path="description"/>
                        <label for="description">Описание</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="#" class="modal-action modal-close waves-effect waves-red btn-flat ">Отмена</a>
                <button class="btn-flat waves-effect waves-green modal-action" type="submit" name="action">
                    Сохранить
                </button>

            </div>
        </sf:form>
    </div>
</main>
<%@include file="fragments/small_footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
