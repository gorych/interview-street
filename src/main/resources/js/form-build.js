function buildForm(interviewId) {
    $.ajax({
        url: '/create-question',
        method: 'GET'
    }).done(function (questionId) {
        if (questionId > -1) {
            $('html, body').animate({scrollTop: $(document).height()}, 'slow');

            var answersBoxId = "answers" + questionId;
            var questionCssId = "#" + questionId;

            $('<form/>', {
                id: questionId,
                class: 'question add-question-form',
                method: 'POST',
                action: '/send-form'
            }).appendTo('#interview-questions');

            var questionTextId = "questionText" + questionId;
            var questionTextCssId = "#" + questionTextId;

            $(questionCssId)
                .prepend("<div class='row'><div class='input-field col l8 m8 s12'>" +
                "<input id='" + questionTextId + "' type='text' name='questionText' length='200'/>" +
                "<label for='" + questionTextId + "'>Текст вопроса</label></div>" +
                "<div class='input-field col l4 m4 s12'>" +
                "<select name='typeId'>" +
                "<option value='1'>Текстовое поле</option>" +
                "<option value='2'>Один из списка</option>" +
                "<option value='3'>Несколько из списка</option>" +
                "<option value='4'>Шкала</option>" +
                "</select><label>Тип вопроса</label></div></div>")
                .append("<input type='hidden' name='questionId' value='" + questionId + "'/>")
                .append("<input type='hidden' name='interviewId' value='" + interviewId + "'/>");

            $("label[for='" + questionTextId + "']").addClass("active");
            $(questionTextCssId).val('Новый вопрос');
            $("#typeId").val('1');

            $('select').material_select();

            $('<div/>', {
                id: answersBoxId,
                class: "row"
            }).appendTo(questionCssId);

            var answerBtnId = "addAnswerBtn" + questionId;

            $('<a/>', {
                id: answerBtnId,
                class: 'btn-floating btn-middle waves-effect waves-light green'
            }).appendTo(questionCssId).click(function () {
                addAnswer(answersBoxId, interviewId, questionId)
            });

            $("#" + answerBtnId)
                .append("<i class='material-icons'>add</i>");

            $('<div/>', {
                class: 'divider divider-margin-fix'
            }).appendTo(questionCssId);

            var rightBox = 'rightBox' + questionId;
            var addQuestion = 'addQuestion' + questionId;
            var delQuestion = 'delQuestion' + questionId;

            $('<div/>', {
                class: 'right-align',
                id: rightBox
            }).appendTo(questionCssId);

            $("#" + rightBox)
                .append("<a id='" + addQuestion + "' class='waves-effect waves-green btn-flat'>Сохранить</a>")
                .append("<a id='" + delQuestion + "' class='waves-effect waves-green btn-flat'>Удалить</a>");

            $("#" + addQuestion).click(function () {
                submitQuestionForm(questionId);
            });

            $("#" + delQuestion).click(function () {
                deleteQuestion(questionId);
            });
        }
    });
}

function editForm(questionId, interviewId) {
    $.ajax({
        url: "/load-question/" + questionId,
        method: 'GET',
        success: function (response) {
            if (response == "") {
                location.reload();
                return false;
            }
            var values = JSON.parse(response);
            var formId = "form" + questionId;
            var formCssId = "#" + formId;
            var answersBoxId = "answers" + questionId;

            $("#" + questionId).html("<form method='POST' action='/send-form' id='" + formId + "'></form>");

            $(formCssId).prepend("<div class='row'><div class='input-field col l8 m8 s12'>" +
                "<input id='questionText' type='text' name='questionText' length='200'/>" +
                "<label for='questionText'>Текст вопроса</label></div>" +
                "<div class='input-field col l4 m4 s12'>" +
                "<select name='typeId' id='type'>" +
                "<option value='' disabled selected>Тип вопроса</option>" +
                "<option value='1'>Текстовое поле</option>" +
                "<option value='2'>Один из списка</option>" +
                "<option value='3'>Несколько из списка</option>" +
                "<option value='4'>Шкала</option>" +
                "</select></div></div>").
                append("<input type='hidden' name='questionId' value='" + questionId + "'/>");

            $('<div/>', {
                id: answersBoxId,
                class: "row"
            }).appendTo(formCssId);

            $.each(values, function (index, value) {
                $('#questionText').val(value["question"]);
                $('#type').val(value["type"]);

                $("label[for='questionText']").addClass("active");

                var answers = value["answers"].split("\n");
                var ids = value["answer_ids"].split("\n");

                $.each(answers, function (i, val) {
                    var answerId = ids[i];
                    var textId = "text" + answerId;

                    $('<div/>', {
                        id: answerId,
                        class: 'input-field col l6 m6 s12 input-field-margin-fix'
                    }).appendTo("#" + answersBoxId);

                    var elementCssId = "#" + answerId;

                    $('<div/>', {
                        class: 'col l10 m10 s10 col-fix'
                    }).appendTo(elementCssId).append("<input id='" + textId + "' type='text' name='answerText'>" +
                        "<label for='" + textId + "'>Текст ответа</label></div>")
                        .append("<input type='hidden' name='answerId' value='" + answerId + "'/>");

                    $('#' + textId).val(val);
                    $("label[for='" + textId + "']").addClass("active");

                    $('<a/>', {
                        class: 'btn-floating btn waves-effect red delete-answer-lotion-btn-fix',
                        href: "#"
                    }).appendTo(elementCssId).append("<i class='material-icons'>delete</i>").click(function () {
                        $(elementCssId).remove();
                        $.ajax({
                            url: "/delete-answer/" + answerId,
                            method: 'GET'
                        }).done(function (response) {
                            if (response == "success") {
                                console.log("The answer successfully removed.");
                            }
                        });
                    });
                });
            });
            $('select').material_select();

            var answerBtnId = "addAnswerBtn" + questionId;
            $('<a/>', {
                id: answerBtnId,
                class: 'btn-floating btn-middle waves-effect waves-light green'
            }).appendTo(formCssId);

            $("#" + answerBtnId)
                .attr("href", "JavaScript:addAnswer('" + answersBoxId + "','" + interviewId + "','" + questionId + "')")
                .append("<i class='material-icons'>add</i>");

            $('<div/>', {
                class: 'divider divider-margin-fix'
            }).appendTo(formCssId);

            var submit = "JavaScript:submitQuestionForm('" + formCssId + "')";

            $('<div/>', {
                class: 'right-align'
            }).appendTo(formCssId).append(
                '<a href="' + submit + '" class="waves-effect waves-green btn-flat">Сохранить</a>' +
                '<a onclick="location.reload()" class="waves-effect waves-red btn-flat">Отменить</a>');
        },
        error: function () {
            location.reload();
        }
    });
}

function addAnswer(parentId, interviewId, questionId) {
    var parentCssId = "#" + parentId;
    var url = "/create-answer/" + interviewId + "/" + questionId;
    $.ajax({
        url: url,
        method: 'GET'
    }).done(function (answerId) {
        if (answerId > -1) {
            var textId = "text" + answerId;

            $('<div/>', {
                id: answerId,
                class: 'input-field col l6 m6 s12 input-field-margin-fix'
            }).appendTo(parentCssId);

            var elementCssId = "#" + answerId;

            $('<div/>', {
                class: 'col l10 m10 s10 col-fix'
            }).appendTo(elementCssId)
                .append("<input id='" + textId + "' type='text' name='answerText'><label for='" + textId + "'>Текст ответа</label></div>")
                .append("<input type='hidden' name='answerId' value='" + answerId + "'/>");

            $("#" + textId).val('Новый ответ');
            $("label[for='" + textId + "']").addClass("active");

            $('<a/>', {
                class: 'btn-floating btn waves-effect red delete-answer-lotion-btn-fix',
                href: "#"
            }).appendTo(elementCssId).append("<i class='material-icons'>delete</i>").click(function () {
                $(elementCssId).remove();
                $.ajax({
                    url: "/delete-answer/" + answerId,
                    method: 'GET'
                }).done(function (response) {
                    if (response == "success") {
                        console.log("The answer successfully removed.");
                    }
                });
            });
        }
    });
}

function updateBadges() {
    var badges = $(".badge");

    if (badges.length > 0) {
        $(badges).each(function (i, element) {
            $(element).find(".text").html(i + 1);
        });
        $(".box h6:last-child").html("Вопросов: " + badges.length);
    } else {
        $(".box h6:last-child").html("В данной анкете пока нет ни одного вопроса.");
    }


}

function showQuestionSection(formId, formData) {
    $("#" + formId).remove();
    var questionId = formData["questionId"];
    var questionCssId = "#" + questionId;

    $("<section><div class='badge teal valign-wrapper'><h6 class='valig text'></h6></div>" +
        "<div class='question' id=" + questionId + ">" +
        "<h5 class='header black-text'>" + formData["questionText"] + "</h5></div></section>")
        .insertBefore("#interview-questions");

    updateBadges();

    var answerTexts = formData["answerText"];
    var answerIds = formData["answerId"];
    var answers = [];
    var type = formData["typeId"];

    if (!$.isArray(answerTexts)) {
        switch (type) {
            case "1":
                answers.push("<div class='input-field input-field-fix col empty-padding l12 s12 m12'>" +
                    "<input id='" + answerIds + "' type='text'><label for='" + answerIds + "'>" + answerTexts +
                    "</label></div>");
                break;
            case "2":
                answers.push("<p><input name='1' type='radio' id='" + answerIds + "'/>" +
                    "<label for='" + answerIds + "'>" + answerTexts + "</label></p>");
                break;
            case "3":
                answers.push("<p><input type='checkbox' id='" + answerIds + "'/>" +
                    "<label for='" + answerIds + "'>" + answerTexts + "</label></p>");
                break;
            case "4":
                answers.push("<p class='range-field'><input type='range' id='" + answerIds + "'/>" +
                    "<label for='" + answerIds + "'>" + answerTexts + "</label></p>");
                break;
        }
    } else {
        for (var i = 0; i < answerTexts.length; i++) {
            switch (type) {
                case "1":
                    if (i == answerTexts.length - 1 && i % 2 == 0) {
                        answers.push("<div class='input-field input-field-fix col empty-padding l12 s12 m12'>" +
                            "<input id='" + answerIds[i] + "' type='text'><label for='" + answerIds[i] + "'>" + answerTexts[i] +
                            "</label></div>");
                        break;
                    }
                    answers.push("<div class='input-field input-field-fix col empty-padding l6 s12 m6'>" +
                        "<input id='" + answerIds[i] + "' type='text'><label for='" + answerIds[i] + "'>" + answerTexts[i] +
                        "</label></div>");
                    break;
                case "2":
                    answers.push("<p><input name='1' type='radio' id='" + answerIds[i] + "'/>" +
                        "<label for='" + answerIds[i] + "'>" + answerTexts[i] + "</label></p>");
                    break;
                case "3":
                    answers.push("<p><input type='checkbox' id='" + answerIds[i] + "'/>" +
                        "<label for='" + answerIds[i] + "'>" + answerTexts[i] + "</label></p>");
                    break;
                case "4":
                    answers.push("<p class='range-field'><input type='range' id='" + answerIds[i] + "'/>" +
                        "<label for='" + answerIds[i] + "'>" + answerTexts[i] + "</label></p>");
                    break;
            }
        }
    }
    $(questionCssId)
        .append("<div class='answers'>" + answers.join("") + "</div>")
        .append("<div class='divider'></div><div class='right-align'>");

    var rightAlign = $(questionCssId).find(".right-align");

    $('<a/>', {
        class: 'waves-effect waves-green btn-flat',
        html: 'Изменить'
    }).appendTo(rightAlign).click(function () {
        var interviewId = formData["interviewId"];
        editForm(questionId, interviewId);
    });

    $('<a/>', {
        class: 'waves-effect waves-red btn-flat',
        html: 'Удалить'
    }).appendTo(rightAlign).click(function () {
        deleteQuestion(questionId);
    });
}
