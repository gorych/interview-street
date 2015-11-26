function buildForm(interviewId) {
    $.ajax({
        url: '/create-question',
        method: 'GET'
    }).done(function (questionId) {
        if (questionId > -1) {
            $('html, body').animate({scrollTop:$(document).height()}, 'slow');

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
                "<option value='' disabled selected>Тип ответов</option>" +
                "<option value='1'>Текстовое поле</option>" +
                "<option value='2'>Только один вариант верный</option>" +
                "<option value='3'>Несколько верных вариантов</option>" +
                "<option value='4'>Диапозон значений</option>" +
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

function submitQuestionForm(formId) {
    var form = document.getElementById(formId);
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