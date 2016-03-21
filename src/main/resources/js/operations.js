$(document).ready(function () {

    $("#sendInterview").click(function () {
        if (!isValidUserAnswers()) {
            return;
        }
        var form = $(this).parent('form');
        var data = $(form).serialize();
        var interviewId = $(form).find('input[name="interviewId"]').val();
        $.ajax({
            url: "/send-interview/" + interviewId,
            method: 'POST',
            data: data,
            success: (function (response) {
                if (response == "success") {
                    location.href = "/interviews";
                    return;
                }
                if (response == "error") {
                    if (isValidUserAnswers()) {
                        location.reload();
                    }
                }
            }),
            error: (function () {
                location.reload();
            })
        });
    });
});

function submitQuestionForm(questionId) {
    if (!isValidQuestionForm(questionId)) {
        return;
    }
    var formId = "form" + questionId;
    var formCssId = "#form" + questionId;
    var data = $(formCssId).serialize();
    $.ajax({
        url: "/send-form",
        method: 'POST',
        data: data,
        success: (function (response) {
            if (response == "success") {
                var decodedData = deserialize(data);
                showQuestionSection(formId, decodedData);
                return;
            }
            if (response == "error") {
                if (!isValidQuestionForm(questionId)) {
                    return;
                }

                location.reload();
            }
        }),
        error: (function () {
            location.reload();
        })
    });
}

function deleteQuestion(questionId) {
    $.ajax({
        url: "/delete-question/" + questionId,
        method: 'GET',
        success: (function (response) {
            if (response == "success") {
                var form = $("#form" + questionId);
                if (form != null && form.length > 0) {
                    $(form).remove();
                } else {
                    $("#" + questionId)
                        .parent('section')
                        .remove();
                }
                updateBadges();
                Materialize.toast("Вопрос успешно удален", 2000)
            } else {
                location.reload();
            }
        }),
        error: (function () {
            location.reload();
        })
    });
}

function deserialize(data) {
    var splits = decodeURIComponent(data).split('&'),
        i = 0,
        split = null,
        key = null,
        value = null,
        splitParts = null;

    var kv = {};
    while (split = splits[i++]) {
        splitParts = split.split('=');
        key = splitParts[0] || '';
        value = (splitParts[1] || '').replace(/\+/g, ' ');

        if (key != '') {
            if (key in kv) {
                if ($.type(kv[key]) !== 'array')
                    kv[key] = [kv[key]];

                kv[key].push(value);
            } else
                kv[key] = value;
        }
    }

    return kv;
}