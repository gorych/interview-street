<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="fragments/meta.html" %>
    <%@include file="fragments/general-css.jsp" %>
    <link href="<c:url value="/resources/css/interview-list.css"/>" rel="stylesheet" type="text/css">
    <title>Interview Street - Список анкет</title>
</head>
<body>
<%@include file="fragments/header.jsp" %>
<main class="container">
    <div class="row card-container">
        <%@include file="fragments/chip.jsp" %>

        <c:forEach var="interview" items="${interviews}">
            <div class="col s12 m6 l4">
                <div class="card darken-1 z-depth-2">

                    <div class="card-content card-content-wrapper grey lighten-4">
                        <i class="material-icons visibility-icon black-text" title="${interview.type.title}">
                                ${interview.type.visibilityIcon}</i>

                        <c:if test="${interview.isNew}">
                            <span class="badge new"></span>
                        </c:if>

                        <span class="fixed-block card-title-wrapper">${interview.name}</span>
                        <div class="divider divider-wrapper teal"></div>
                        <h6 class="placement-date">${interview.formatPlacementDate}</h6>
                        <h6 class="end-date">${interview.formatEndDate}</h6>
                        <h6 class="card-question">${interview.questions.size()}
                            <i class="material-icons center activator activator-wrapper right"
                               title="Посмотреть описание">more_vert</i>
                        </h6>
                    </div>

                    <div class="card-reveal card-reveal-wrapper">
                        <span class="card-title"><i class="material-icons right">close</i></span>
                        <c:if test="${interview.type.name == 'close'}">
                            <p class="goal">${interview.goal}</p>
                            <p class="audience">${interview.description}</p>
                        </c:if>
                        <p class="description">${interview.description}</p>
                    </div>

                    <div class="card-action card-action-wrapper teal">

                        <div class="left-block">
                            <a href="#" data-interview-id="${interview.id}"
                               class="lock-btn btn-floating btn waves-effect white accent-3">
                                <i class="material-icons black-text"
                                   title="${interview.title}">${interview.lockIcon}</i>
                            </a>
                        </div>

                        <div class="right-block">
                            <a href="<c:url value="/editor/${interview.hash}/designer"/>"
                               class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Список вопросов">subject</i>
                            </a>
                            <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Список респондентов">supervisor_account</i>
                            </a>
                            <a href="<c:url value="/viewer/${interview.hash}/statistics"/>" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                                <i class="material-icons black-text" title="Анализ результатов">equalizer</i>
                            </a>
                        </div>

                        <div class="fixed-action-btn action-btn-position click-to-toggle">
                            <a class="btn-floating btn white accent-3">
                                <i class="large material-icons black-text"
                                   title="Нажмите для выбора операции">dashboard</i>
                            </a>
                            <ul>
                                <li>
                                    <a href="<c:url value="/interview/form?id=${interview.id}"/>"
                                       class="btn-floating orange" title="Редактировать анкету">
                                        <i class="material-icons black-text">mode_edit</i>
                                    </a>
                                </li>
                                <li>
                                    <a data-interview-id="${interview.id}" class="delete-btn btn-floating red"
                                       title="Удалить анкету">
                                        <i class="material-icons black-text">delete</i>
                                    </a>
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>
            </div>
        </c:forEach>

        <c:if test="${page_count > 1}">
            <div class="col s12 right-align">
                <ul class="pagination">
                    <li class="prev-page">
                        <c:if test="${active_page > 1}">
                            <a href="<c:url value="/editor/interview-list?pageNumber=${active_page - 1}"/>">
                                <i class="material-icons">chevron_left</i>
                            </a>
                        </c:if>
                    </li>
                    <c:forEach var="i" begin="${start_page}" end="${last_page}">
                        <c:choose>
                            <c:when test="${active_page == i}">
                                <li class="active brown lighten-2">
                                    <a href="<c:url value="/editor/interview-list?pageNumber=${i}"/>">${i}</a>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li>
                                    <a href="<c:url value="/editor/interview-list?pageNumber=${i}"/>">${i}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <li class="next-page">
                        <c:if test="${active_page < page_count}">
                            <a href="<c:url value="/editor/interview-list?pageNumber=${active_page + 1}"/>">
                                <i class="material-icons">chevron_right</i>
                            </a>
                        </c:if>
                    </li>
                </ul>
            </div>
        </c:if>

    </div><!--end card container-->

    <div class="fixed-action-btn" style="bottom: 45px; right: 24px;">
        <a class="btn-floating btn-large red lighten-1">
            <i class="large material-icons">dashboard</i>
        </a>
        <ul>
            <li>
                <a href="<c:url value="/interview/form"/>" class="btn-floating green modal-trigger">
                    <i class="material-icons" title="Добавить">add</i>
                </a>
            </li>
        </ul>
    </div>

    <!--Delete modal-->
    <div id="delete-interview-modal" class="modal">
        <div class="modal-content">
            <h4>Удаление анкеты</h4>
            <p>
                Вы действительно хотите удалить выбранную анкету? Все собранные ответы по данной анкете будут
                безвозвратно удалены.
            </p>
        </div>
        <div class="modal-footer">
            <a class="waves-effect waves-red btn-flat modal-action modal-close" href="#">Нет</a>
            <a href="#" id="submit-delete-btn" class="waves-effect waves-green btn-flat modal-action">Да</a>
        </div>
    </div>

</main>
<%@include file="fragments/footer.jsp" %>
<%@include file="fragments/general-js.jsp" %>

<script src="<c:url value="/resources/vendors/js-render/jsrender.js"/>"></script>
<script src="<c:url value="/resources/js/interview-list.js"/>"></script>
</body>
</html>