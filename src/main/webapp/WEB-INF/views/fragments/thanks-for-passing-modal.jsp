<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Thanks modal. Show after successfully passing interview -->
<div id="thanks-modal" class="modal">
    <div class="modal-content">
        <h5 class="teal-text">Успешное прохождение анкеты</h5>
        <p>Благодарим Вас за заполнение анкеты.</p>
        <h6><i>C уважением, <strong>Interview Street</strong>.</i></h6>
    </div>
    <div class="modal-footer">
        <a href="<c:url value="/j_spring_security_logout"/>"
           class="modal-action modal-close waves-effect waves-red btn-flat">Выход</a>
        <a href="<c:url value="/respondent/dashboard"/>"
           class="modal-action modal-close waves-effect waves-green btn-flat">На главную</a>

    </div>
</div>