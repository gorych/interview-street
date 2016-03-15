function invalidPassportData(textbox) {
    if (textbox.validity.patternMismatch) {
        textbox.setCustomValidity('Паспортные данные введены некорректно');
    }
    else {
        textbox.setCustomValidity('');
    }
    return true;
}

function isValidQuestionForm(questionId) {
    var form = document.getElementById("form" + questionId);
    var question = document.getElementById('questionText' + questionId);
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

    var errorId = "error" + questionId;
    var errorCssId = "#error" + questionId;

    if (errors.length > 0) {
        var errorBlocks = $(errorCssId);
        if (errorBlocks.length == 0) {
            $("#form" + questionId)
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

function isValidUserAnswers() {
    var sections = $(document)
        .find('form')
        .find('section');

    var errors = [];
    $.each(sections, function (i, section) {
        var inputs = $(section)
            .find('.answers')
            .find('input');
        var type = $(inputs[0]).attr('type');
        var questionId = $(section).find(".question").attr("id");

        var isValid = true;
        switch (type) {
            case "text":
                isValid = isValidInputs(inputs);
                break;
            case "radio":
            case "checkbox":
                isValid = isValidRadioAndCheckboxes(inputs);
                break;
            case "range":
                isValid = isValidRange(inputs);
                break;
        }
        var errorBlocks = $(section).find(".danger-alert");
        if (!isValid) {
            errors.push("error");
            if (errorBlocks.length == 0) {
                $(section).find(".answers").append("<div class = 'danger-alert z-depth-1'>" +
                    "Вы не ответили на этот вопрос или на его часть. " +
                    "Посмотрите вопрос снова и завершите свой ответ, пожалуйста.</div>");
            } else {
                $(errorBlocks[0])
                    .replaceWith("<div class = 'danger-alert z-depth-1'>" +
                    "Вы не ответили на этот вопрос или на его часть. " +
                    "Посмотрите вопрос снова и завершите свой ответ, пожалуйста.</div>")
            }
        } else {
            errorBlocks.remove().end();
        }
    });

    return errors.length <= 0;
}

function isValidInputs(inputs) {
    var isError = false;
    $.each(inputs, function (i, input) {
        var text = input.value;
        if (text.length < 1) {
            isError = true;
            return false;
        }
    });
    return !isError;
}

function isValidRadioAndCheckboxes(element) {
    var isError = true;
    $.each(element, function (i, el) {
        if (el.checked == true) {
            isError = false;
            return false;
        }
    });
    return !isError;
}

function isValidRange(element) {
    var isError = false;
    $.each(element, function (i, el) {
        if (el.value == "" || el <= 0) {
            isError = true;
            return false;
        }
    });
    return !isError;
}