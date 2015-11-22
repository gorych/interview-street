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
                <th data-field="description">Описание</th>
                <th data-field="date_created">Дата создания</th>
                <th data-field="state">Состояние</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="interview" items="${interviews}">
                <tr>
                    <td>
                        <input type="checkbox" id="${interview.id}"/>
                        <label for="${interview.id}" class="table-checkbox-fix "></label>
                    </td>
                    <td>${interview.name}</td>
                    <td>${interview.description}</td>
                    <td>${interview.placementDate}</td>
                    <c:choose>
                        <c:when test="${interview.hide}">
                            <td><i class="material-icons" title="Открыта">visibility</i></td>
                        </c:when>
                        <c:otherwise>
                            <td><i class="material-icons" title="Скрыта">visibility_off</i></td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <a href="<c:url value="/questions-editor"/>" class="btn-floating cyan darken-1"><i
                                class="material-icons" title="Список вопросов">subject</i></a>
                    </td>
                    <td>
                        <a class="btn-floating teal accent-4"><i class="material-icons" title="Список респондентов">supervisor_account</i></a>
                    </td>
                </tr>
            </c:forEach>
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
        <sf:form id="interviewForm" class="col s12" method="POST" modelAttribute="user_interview_helper"
                 action="/create-interview">
            <div class="modal-content">
                <h4>Добавление новой анкеты</h4>

                <div class="row">
                    <div class="input-field col s6">
                        <sf:select path="interview.type.id">
                            <option value="1" disabled selected>Открытый</option>
                            <option value="1">Открытый</option>
                            <option value="2">Скрытый</option>
                        </sf:select>
                        <label>Тип опроса</label>
                    </div>
                    <div class="input-field col s6">
                        <sf:input type="text" path="interview.name" length="50"/>
                        <label for="interview.name">Наименование</label>
                    </div>
                    <div class="input-field col s6">
                        <select multiple="true" onchange='loadEmployeePosts(this)'>
                            <option value="-1" disabled selected>Подразделения</option>
                            <c:forEach var="item" items="${subdivisions}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-field col s6">
                        <sf:select multiple="true" id="employeePostsId" path="posts">
                            <option value="-1" disabled selected>Должности сотрудников</option>
                        </sf:select>
                    </div>
                    <div class="input-field col s12">
                        <sf:input type="text" path="interview.description" length="100"/>
                        <label for="interview.description">Описание</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="JavaScript:clearForm('interviewForm')"
                   class="modal-action modal-close waves-effect waves-red btn-flat ">Отмена</a>
                <a href="JavaScript:submitInterviewForm()" class="btn-flat waves-effect waves-green modal-action"
                   type="submit" name="action">
                    Сохранить
                </a>
            </div>
        </sf:form>
    </div>
</main>
<%@include file="fragments/small_footer.html" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
