<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="deleteModal" class="modal">
    <div class="modal-content">
        <h4>Удаление записей</h4>

        <p> Вы действительно хотите удалить запись(и)?</p>
    </div>
    <div class="modal-footer">
        <a class="waves-effect waves-red btn-flat modal-action modal-close">Нет</a>
        <button onclick="deleteInterview()"
                class="waves-effect waves-green btn-flat modal-action">Да
        </button>
    </div>
</div>