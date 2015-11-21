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
    var POSTS_SELECT_DEFAULT_VALUE = "Подразделения";
    var ERROR_MSG = ["Наименование анкеты менее 5 символов",
        "Не выбраны подразделения", "Не выбраны должности сотрудников", "Описание анкеты менее 5 символов"];

    var form = document.getElementById("interviewForm");
    var inputs = form.getElementsByTagName('input');

    var errors = [];
    for (var i = 1; i < inputs.length; i++) {
        var element = inputs[i];
        if ($(element).hasClass("select-dropdown")) {
            if (element.value == SUBDIVISIONS_SELECT_DEFAULT_VALUE ||
                element.value == POSTS_SELECT_DEFAULT_VALUE)
                errors.push(ERROR_MSG[i - 1]);
            continue;
        }

        if (element.type.toLowerCase() == 'text') {
            if (element.value.length <= 0) {
                errors.push(ERROR_MSG[i - 1]);
            }
        }
    }

    var modalContent = form.getElementsByClassName("modal-content");
    if (errors.length > 0) {
        var msg = "";
        for (var j = 0; j < errors.length; j++) {
            msg += ERROR_MSG[j] + "<br/>";
        }
        $(modalContent).append("<div class='error-alert modal-alert-error-fix'>" + msg + "</div>");
    } else {
        form.submit();
    }
}