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
                class: 'question',
                method: 'POST',
                action: '/create-new-form'
            }).appendTo('#interview_questions');

            $(questionCssId).prepend("<div class='row'><div class='input-field col l8 m8 s12'>" +
                "<input id='questionText' type='text' name='questionText' length='200'/>" +
                "<label for='questionText'>Текст вопроса</label></div>" +
                "<div class='input-field col l4 m4 s12'>" +
                "<select name='typeId'>" +
                "<option value='' disabled selected>Тип вопроса</option>" +
                "<option value='1'>Текстовое поле</option>" +
                "<option value='2'>Один из списка</option>" +
                "<option value='3'>Несколько из списка</option>" +
                "<option value='4'>Шкала</option>" +
                "</select></div></div>").
                append("<input type='hidden' name='questionId' value='" + questionId + "'/>");

            $('select').material_select();

            $('<div/>', {
                id: answersBoxId,
                class: "row"
            }).appendTo(questionCssId);

            var answerBtnId = "addAnswerBtn" + questionId;

            $('<a/>', {
                id: answerBtnId,
                class: 'btn-floating btn-middle waves-effect waves-light green'
            }).appendTo(questionCssId);

            $("#" + answerBtnId)
                .attr("href", "JavaScript:addAnswer('" + answersBoxId + "','" + interviewId + "','" + questionId + "')")
                .append("<i class='material-icons'>add</i>");

            $('<div/>', {
                class: 'divider divider-margin-fix'
            }).appendTo(questionCssId);

            var del = "JavaScript:deleteQuestion('" + questionId + "')";
            var submit = "JavaScript:submitQuestionForm('" + questionId + "')";

            $('<div/>', {
                class: 'right-align'
            }).appendTo(questionCssId).append(
                '<a href="' + submit + '" class="waves-effect waves-green btn-flat">Сохранить</a>' +
                '<a href="' + del + '" class="waves-effect waves-red btn-flat">Удалить</a>');
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

            $("#" + questionId).html("<form method='POST' action='/create-new-form' id='" + formId + "'></form>");

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

            var submit = "JavaScript:submitQuestionForm('" + formId + "')";

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

function submitQuestionForm(formCssId) {
    var form = document.getElementById(formCssId);
    form.submit();
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
            }).appendTo(elementCssId).append("<input id='" + textId + "' type='text' name='answerText'>" +
                "<label for='" + textId + "'>Текст ответа</label></div>")
                .append("<input type='hidden' name='answerId' value='" + answerId + "'/>");

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
