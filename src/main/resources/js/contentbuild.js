function buildForm(interviewId) {
    $.ajax({
        url: '/create-question',
        method: 'GET'
    }).done(function (questionId) {
        if (questionId > -1) {
            var answersBoxId = "answers" + questionId;
            var questionCssId = "#" + questionId;

            $('<form/>', {
                id: questionId,
                class: 'question',
                method: 'POST',
                action: '/create-new-form'
            }).appendTo('#interview_questions');

            $(questionCssId).append("<div class='row'><div class='input-field col s12'>" +
                "<input id='questionText' type='text' length='200' name='questionName'>" +
                "<label for='questionText'>Question</label></div></div>").
                append("<input type='hidden' name='questionId' value='" + questionId + "'/>");

            $('<div/>', {
                id: answersBoxId
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

            var str = "JavaScript:deleteQuestion('" + questionId + "')";

            $('<div/>', {
                class: 'right-align'
            }).appendTo(questionCssId).append(
                "<input type='submit' class='waves-effect waves-green btn-flat' value='Save'/>" +
                '<a href="' + str + '" class="waves-effect waves-red btn-flat">Delete</a>');
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
            var typeId = "type" + answerId;
            var textId = "text" + answerId;

            $('<div/>', {
                id: answerId,
                class: 'row'
            }).appendTo(parentCssId);

            var elementCssId = "#" + answerId;

            $('<div/>', {
                class: 'input-field col l5 input-field-margin-fix'
            }).appendTo(elementCssId).append("<select name='answerType' id='" + typeId + "'>" +
                "<option value='' disabled selected>Choose answer type</option>" +
                "<option value='1'>Text field</option>" +
                "<option value='2'>Radio Button</option>" +
                "<option value='3'>Checkbox</option>" +
                "<option value='4'>Slider</option></select>");

            $('<div/>', {
                class: 'input-field col l6 input-field-margin-fix'
            }).appendTo(elementCssId).append("<input id='" + textId + "' type='text' name='answerText'>" +
                "<label for='" + textId + "'>Answer text</label></div>")
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

            $('select').material_select();
        }
    });
}
