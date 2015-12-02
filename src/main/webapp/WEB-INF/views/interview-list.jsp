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
            <c:if test="${empty chip || (chip eq true)}">
                <div class="chip">
                    Здравствуйте, ${user_initials}, Вы вошли под правами редактора.
                    <i class="material-icons" onclick="hideChip()">close</i>
                </div>
            </c:if>
        </div>
        <form method="GET" id="tableInterviewForm" name="tableInterviewForm"
              action="<c:url value="/delete-interview"/>">
            <input type="hidden" value="" name="interviewId"/>
            <table class="centered highlight">
                <thead>
                <tr>
                    <th data-field="id"><a href="JavaScript: selectAll('tableInterviewForm')">
                        <i class="material-icons" title="Выбрать все">done_all</i></a>
                    </th>
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
                            <input type="checkbox" value="${interview.id}" id="${interview.id}" name="id"/>
                            <label for="${interview.id}" class="table-checkbox-fix "></label>
                        </td>
                        <td>${interview.name}</td>
                        <td>${interview.description}</td>
                        <td>${interview.placementDate}</td>
                        <c:choose>
                            <c:when test="${interview.hide}">
                                <td><a href="JavaScript:hideInterview('${interview.id}')"><i
                                        class="material-icons table-material-icons-fix"
                                        title="Открыта">visibility</i></a></td>
                            </c:when>
                            <c:otherwise>
                                <td><a href="JavaScript:hideInterview('${interview.id}')"><i
                                        class="material-icons table-material-icons-fix"
                                        title="Скрыта">visibility_off</i></a></td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <a href="<c:url value="/designer/${interview.id}"/>"
                               class="btn-floating cyan darken-1"><i
                                    class="material-icons" title="Список вопросов">subject</i></a>
                        </td>
                        <td>
                            <a class="btn-floating teal accent-4"><i class="material-icons" title="Список респондентов">supervisor_account</i></a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </form>
    </div>
    <div class="fixed-action-btn horizontal" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red">
            <i class="large material-icons">dashboard</i>
        </a>
        <ul>
            <li><a href="JavaScript:checkCbForDelete()" class="btn-floating red"><i class="material-icons"
                                                                                    title="Удалить">delete</i></a></li>
            <li><a href="JavaScript:showEditInterviewModal()" class="btn-floating yellow darken-1"><i
                    class="material-icons" title="Редактировать">edit</i></a>
            </li>
            <li><a href="#addInterviewModal" class="btn-floating green modal-trigger"><i class="material-icons"
                                                                                         title="Добавить">add</i></a>
            </li>

        </ul>
    </div>

    <div id="addInterviewModal" class="modal modal-fixed-footer">
        <sf:form id="interviewForm" class="col s12" method="POST" modelAttribute="extendUserInterview">
            <div class="modal-content">
                <h4>Добавление новой анкеты</h4>

                <div class="row">
                    <div class="input-field col s6">
                        <sf:select path="interview.type.id" id="type">
                            <option value="-1" disabled selected>Выбирите тип опроса</option>
                            <option value="1">Открытый</option>
                            <option value="2">Скрытый</option>
                        </sf:select>
                        <label>Тип опроса</label>
                    </div>
                    <div class="input-field col s6">
                        <sf:input type="text" path="interview.name" length="50" id="name"/>
                        <label for="name">Наименование</label>
                    </div>
                    <div class="input-field col s6">
                        <select multiple="true" onchange='loadEmployeePosts(this)' id="subdivisions">
                            <option value="-1" disabled selected>Подразделения</option>
                            <c:forEach var="item" items="${subdivisions}">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="input-field col s6">
                        <sf:select multiple="true" id="posts" path="posts">
                            <option value="-1" disabled selected>Должности сотрудников</option>
                        </sf:select>
                    </div>
                    <div class="input-field col s12">
                        <sf:input type="text" path="interview.description" length="100" id="description"/>
                        <label for="description">Описание</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <a href="JavaScript:clearForm('interviewForm')"
                   class="modal-action modal-close waves-effect waves-red btn-flat ">Отмена</a>
                <a href="JavaScript:submitInterviewForm()" class="btn-flat waves-effect waves-green modal-action">
                    Сохранить
                </a>
            </div>
        </sf:form>
    </div>
    <%@include file="fragments/delete_modal.jsp" %>
</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/js_imports.html" %>
</body>
</html>
