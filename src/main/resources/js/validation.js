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
    var question = document.getElementById('questionText');
    var type = document.getElementsByName('typeId')[0];
    var answers = $("input[name='answerText']");

    var questionText = question.value;
    if (questionText == "undefined" || type == "undefined" || answers == "undefined") {
        location.reload();
    }
    var errors =[];
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

    if(errors.length > 0){
        var errorBlocks = $(".error-alert");
        if (errorBlocks.length == 0) {
            $('.divider').before("<div class = 'error-alert alert-position-fix'>" + errors.join("")+ "</div>");
        } else {
            $(errorBlocks[0]).replaceWith("<div class = 'error-alert alert-position-fix'>" + errors.join("") + "</div>")
        }
        return false;
    }

    return true;
}

