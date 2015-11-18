$(document).ready(function () {

    $('select').material_select();

    $('#addQuestionBtn').click(function () {
        $.ajax({
            url: '/create-question',
            method: 'GET'
        }).done(function (questionId) {
            if (questionId > -1) {
                var answersBoxId = "answers" + questionId;
                var questionCssId = "#" + questionId;

                $('<form/>', {
                    id: questionId,
                    class: 'question'
                }).appendTo('#interview_questions');

                $(questionCssId).append("<div class='row'><div class='input-field col s12'>" +
                    "<input id='questionText' type='text' length='200'>" +
                    "<label for='questionText'>Question</label></div></div>");

                $('<div/>', {
                    id: answersBoxId
                }).appendTo(questionCssId);

                var answerBtnId = "addAnswerBtn" + questionId;

                $('<a/>', {
                    id: answerBtnId,
                    class: 'btn-floating btn-middle waves-effect waves-light green'
                }).appendTo(questionCssId).click(function () {
                    $.ajax({
                        url: '/create-answer',
                        method: 'GET'
                    }).done(function (answerId) {
                        if (answerId > -1) {
                            addAnswer(answerId, answersBoxId);
                        }
                    });
                });

                $("#" + answerBtnId).append("<i class='material-icons'>add</i>");

                $('<div/>', {
                    class: 'divider divider-margin-fix'
                }).appendTo(questionCssId);

                $('<div/>', {
                    class: 'right-align'
                }).appendTo(questionCssId).append(
                    "<a href='#' class='modal-action modal-close waves-effect waves-green btn-flat'>Save</a>" +
                    "<a href='#' class='modal-action modal-close waves-effect waves-red btn-flat'>Delete</a>");
            }
        });
    });
});

function addAnswer(answerId, parentId) {
    var parentCssId = "#" + parentId;
    var typeId = "type" + answerId;
    var textId = "text" + answerId;

    $('<div/>', {
        id: answerId,
        class: 'row'
    }).appendTo(parentCssId);

    var elementCssId = "#" + answerId;

    $('<div/>', {
        class: 'input-field col l5 input-field-margin-fix'
    }).appendTo(elementCssId).append("<select><option value='' disabled selected>Choose answer type</option>" +
        "<option value='1'>Text field</option>" +
        "<option value='2'>Radio Button</option>" +
        "<option value='3'>Checkbox</option>" +
        "<option value='4'>Slider</option></select>");

    $('<div/>', {
        class: 'input-field col l6 input-field-margin-fix'
    }).appendTo(elementCssId).append("<input id='" + textId + "' type='text'>" +
        "<label for='" + textId + "'>Answer text</label>" +
        "</div>");

    $('<a/>', {
        class: 'btn-floating btn waves-effect red delete-answer-lotion-btn-fix',
        href: "#"
    }).appendTo(elementCssId).append("<i class='material-icons'>delete</i>").click(function () {
        $(elementCssId).remove();
    });

    $('select').material_select();
}
