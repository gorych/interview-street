<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id ="card-template" class="col s12 m6 l4 hide">
    <div class="card darken-1 z-depth-2">
        <div class="card-content card-content-wrapper grey lighten-4">
            <i class="material-icons visibility-icon black-text" title=""></i>
            <span class="badge new"></span>
            <span class="fixed-block card-title-wrapper"></span>
            <div class="divider divider-wrapper teal"></div>
            <h6 class="placement-date"></h6>
            <h6 class="end-date"></h6>
            <h6 class="card-question">0
                <i class="material-icons center activator activator-wrapper right"
                   title="Посмотреть описание">more_vert</i>
            </h6>
        </div>
        <div class="card-reveal card-reveal-wrapper">
            <span class="card-title"><i class="material-icons right">close</i></span>
            <p class="goal"></p>
            <p class="audience"></p>
            <p class="description"></p>
        </div>
        <div class="card-action card-action-wrapper teal">
            <div class="left-block">
                <a href="#" data-interview-id="" class="lock-btn btn-floating btn waves-effect white accent-3">
                    <i class="material-icons black-text" title="Анкета закрыта для прохождения">lock</i>
                </a>
            </div>
            <div class="right-block">
                <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                    <i class="material-icons black-text" title="Список вопросов">subject</i>
                </a>
                <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                    <i class="material-icons black-text" title="Список респондентов">supervisor_account</i>
                </a>
                <a href="#" class="btn-floating float-btn-wrapper waves-effect grey lighten-4">
                    <i class="material-icons black-text" title="Анализ результатов">equalizer</i>
                </a>
            </div>
            <div class="fixed-action-btn action-btn-position click-to-toggle">
                <a class="btn-floating btn white accent-3">
                    <i class="large material-icons black-text" title="Нажмите для выбора операции">dashboard</i>
                </a>
                <ul>
                    <li>
                        <a href="#" data-interview-id="" class="edit-interview-btn btn-floating orange"
                           title="Редактировать анкету">
                            <i class="material-icons black-text">mode_edit</i>
                        </a>
                    </li>
                    <li>
                        <a data-interview-id="" class="delete-btn btn-floating red" title="Удалить анкету">
                            <i class="material-icons black-text">delete</i>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>