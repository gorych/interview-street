function invalidPassportData(textbox) {
    if (textbox.validity.patternMismatch) {
        textbox.setCustomValidity('Паспортные данные введены некорректно');
    }
    else {
        textbox.setCustomValidity('');
    }

    return true;
}

function submitInterviewForm() {
    var SUBDIVISIONS_SELECT_DEFAULT_VALUE = "Подразделения";
    var POSTS_SELECT_DEFAULT_VALUE = "Должности сотрудников";
    var TYPE_SELECT_DEFAULT_VALUE = "Тип опроса";
    var ERROR_MSG = ["Не выбран тип опроса", "Наименование анкеты менее 5 символов",
        "Не выбраны подразделения", "Не выбраны должности сотрудников",
        "Описание анкеты менее 5 символов"];

    var form = document.getElementById("interviewForm");
    var inputs = form.getElementsByTagName('input');

    var errors = [];
    for (var i = 0; i < inputs.length; i++) {
        var element = inputs[i];
        if ($(element).hasClass("select-dropdown")) {
            if (element.value == SUBDIVISIONS_SELECT_DEFAULT_VALUE ||
                element.value == POSTS_SELECT_DEFAULT_VALUE ||
                element.value == TYPE_SELECT_DEFAULT_VALUE)
                errors.push(ERROR_MSG[i]);
            continue;
        }

        if (element.type.toLowerCase() == 'text') {
            if (element.value.length < 5) {
                errors.push(ERROR_MSG[i]);
            }
        }
    }

    var modalContent = form.getElementsByClassName("modal-content");
    if (errors.length > 0) {
        var msg = "";
        for (var j = 0; j < errors.length; j++) {
            msg += ERROR_MSG[j] + "<br/>";
        }
        var elements = document.getElementsByClassName("error-alert modal-alert-error-fix");
        if (elements.length > 0) {
            $(elements[0]).replaceWith("<div class='error-alert modal-alert-error-fix'>" + msg + "</div>");
        } else {
            $(modalContent).append("<div class='error-alert modal-alert-error-fix'>" + msg + "</div>");
        }
    }
    else {
        form.submit();
    }
}