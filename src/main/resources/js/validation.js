function invalidPassportData(textbox) {
    if (textbox.validity.patternMismatch) {
        textbox.setCustomValidity('Паспортные данные введены некорректно');
    }
    else {
        textbox.setCustomValidity('');
    }

    return true;
}

function isValidQuestionForm(formId) {
    var form = document.getElementById(formId);
    var question = document.getElementById('questionText' + formId);
    var type = document.getElementsByName('typeId')[0];
    var answers = $("input[name='answerText']");

    var questionText = question.value;
    if (questionText == "undefined" || type == "undefined" || answers == "undefined") {
        location.reload();
    }
    var errors = [];
    if (questionText.length < 1 || questionText.length > 100) {
        errors.push("<li>Длина вопроса должна быть от 1 до 100 символов</li>");
    }
    if (type.value < 1 || type.value > 4) {
        errors.push("Выберите тип вопроса\n");
    }
    if (answers.length < 1) {
        errors.push("<li>Добавьте хотя бы один ответ на вопрос</li>");
    } else {
        for (var i = 0; i < answers.length; i++) {
            if (answers[i].value.length < 1 || answers[i].value.length > 50) {
                errors.push("<li>Длина ответа должна быть от 1 до 50 символов</li>");
                break
            }
        }
    }

    var errorId = "error" + formId;
    var errorCssId = "#error" + formId;

    if (errors.length > 0) {
        var errorBlocks = $(errorCssId);
        if (errorBlocks.length == 0) {
            $("#" + formId)
                .find(".divider")
                .before("<div id='" + errorId + "' class = 'error-alert alert-position-fix'>" + errors.join("") + "</div>");
        } else {
            $(errorBlocks[0])
                .replaceWith("<div id='" + errorId + "' class = 'error-alert alert-position-fix'>" + errors.join("") + "</div>")
        }
        return false;
    }

    return true;
}

function isValidInterviewForm() {
    var form = $('#interviewForm');
    var type = $(form).find("#type");
    var name = $(form).find("#name");
    var posts = $(form).find("#posts");
    var description = $(form).find("#description");

    if (form == "undefined" || type == "undefined" || name == "undefined"
        || posts == "undefined" || description == "undefined") {
        location.reload();
    }

    var errors = [];
    if (name.length < 1 || name.length > 50) {
        errors.push("<li>Наименование должно быть от 1 до 50 символов</li>");
    }

    if (description.length < 1 || description.length > 50) {
        errors.push("<li>Описание должна быть от 1 до 50 символов</li>");
    }

    var typeValue = $(type).val();
    if (typeValue == null || typeValue < 1) {
        errors.push("<li>Выберите тип опроса</li>");
    } else if (typeValue != 2) {
        var postValues = $(posts).val();
        if (postValues.length < 1) {
            errors.push("<li>Выберите должности сотрудников</li>");
        } else {
            $.each(postValues, function (i, elem) {
                if ($(elem).val() < 0) {
                    errors.push("<li>Выберите должности сотрудников</li>");
                    return false;
                }
            });
        }
    }

    if (errors.length > 0) {
        var errorBlocks = $(".error-alert");
        if (errorBlocks.length == 0) {
            $(form)
                .find(".modal-content")
                .append("<div class = 'error-alert modal-alert-fix'>" + errors.join("") + "</div>");
        } else {
            $(errorBlocks[0])
                .replaceWith("<div class = 'error-alert modal-alert-fix'>" + errors.join("") + "</div>")
        }
        return false;
    }

    return true;
}

