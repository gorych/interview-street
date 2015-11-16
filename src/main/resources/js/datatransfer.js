$(document).ready(function () {
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

                $('<a/>', {
                    id: "addAnswerBtn",
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

                $("#addAnswerBtn").append("<i class='material-icons'>add</i>");

                $('<div/>', {
                    class: 'divider divider-margin-fix'
                }).appendTo(questionCssId);

                $('<div/>', {
                    class: 'right-align'
                }).appendTo(questionCssId).append("" +
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

    $(parentCssId).append("<div id='" + answerId + "' class='row'><div class='input-field col l5 m5'>" +
        "<input id='" + textId + "' type='text'>" +
        "<label for='" + textId + "'>Answer type</label>" +
        "</div><div class='input-field col l6 m6'>" +
        "<input id='" + typeId + "' type='text'>" +
        "<label for='" + typeId + "'>Answer text</label>" +
        "</div></div>");

    var elementCssId = "#" + answerId;
    $('<a/>', {
        class: 'btn-floating btn-large waves-effect amber darken-3',
        href:"#"
    }).appendTo(elementCssId).append("<i class='material-icons'>delete</i>").click(function () {
        $(elementCssId).remove();
    });
}