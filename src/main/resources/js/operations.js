$(document).ready(function () {

    $('#deleteInterviewBtn').click(function () {
        var nodes = $("table").find("input");
        var flag = isCheckedValues(nodes);
        if (flag == true) {
            $("#deleteInterviewModal").openModal();
        } else {
            Materialize.toast("Не выбрана анкета для удаления", 2000);
        }
    });

    $("#deleteInterviewModal").find("button").click(function () {
        var form = $("table").parent('form');
        var data = $(form).serialize();
        $.ajax({
            url: "/delete-interview",
            method: 'GET',
            data: data,
            success: (function (response) {
                if (response == "success") {
                    var inputs = $("table").find("input:checked").each(function (i, elem) {
                        var td = $(this).parent('td');
                        $(td).parent('tr').remove();
                    });

                    $("#deleteInterviewModal").closeModal();
                    if (inputs.length < 2) {
                        Materialize.toast("Анкета успешно удалена", 2000);
                    } else {
                        Materialize.toast("Анкеты успешно удалены", 2000);
                    }
                } else {
                    location.reload();
                }
            }),
            error: (function () {
                location.reload();
            })
        });
    });

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

function loadEmployeePosts(select) {
    var result = [];

    var options = select.options;
    for (var i = 1; i < options.length; i++) {
        if (options[i].selected) {
            result.push(options[i].value);
            options[i].selected = true;
        }
    }

    $.ajax({
        url: '/load-posts',
        method: 'GET',
        data: {"data": result.join()}
    }).done(function (data) {
        var employees = JSON.parse(data);
        if (data.length > 0) {
            $('#posts')
                .find('option')
                .remove()
                .end();
            $.each(employees, function (index, element) {
                if (index == 0) {
                    $("#posts").append("<option value='" + -1 + "' disabled selected>Выберите должности</option>");
                    $("#posts").append("<option value='" + 0 + "'>Вcе</option>");
                }

                $("#posts").append("<option value='" + element["post_id"] + "'>" + element["post_name"] + "</option>");
            });

            $('#posts').material_select();
        }
    });
}

function isCheckedValues(nodes) {
    var flag = false;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].type == "checkbox" && nodes[i].checked == true) {
            flag = true;
            break;
        }
    }
    return flag;
}

function submitQuestionForm(formId) {
    if (!isValidQuestionForm(formId)) {
        return;
    }

    var data = $("#" + formId).serialize();
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
                if (!isValidQuestionForm(formId)) {
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

function submitInterviewForm() {
    if (!isValidInterviewForm()) {
        return;
    }
    $.ajax({
        url: "/create-interview",
        data: $('#interviewForm').serialize(),
        type: "POST",
        success: (function (response) {
            if (response != "error") {
                var values = JSON.parse(response);
                $.each(values, function (i, value) {
                    var id = value["id"];
                    var url = "/designer/" + value["interview_id"];

                    <!--Hide rows if there more than 3-->
                    var newRows = $("tr.brown");
                    if (newRows.length >= 3) {
                        $.each(newRows, function (j, elem) {
                            $(elem).removeAttr("class");
                        });
                    }

                    <!--Build new row-->
                    $("table").prepend(
                        "<tr class='brown lighten-5'>" +
                        "<td><input type='checkbox' value='" + id + "' id='" + id + "' name='id'/><label for='" + id + "' class='table-checkbox-fix'></label></td>" +
                        "<td>" + value["name"] + "</td>" +
                        "<td><i class='material-icons table-material-icons-fix'>" + value["type"] + "</i></td>" +
                        "<td>" + value["description"] + "</td>" +
                        "<td>" + value["date"] + "</td>" +
                        "<td><a onclick='hideInterview(" + value["interview_id"] + "," + 'this' + ")'><i class='material-icons table-material-icons-fix' title='Скрыта для прохождения'>" + value["hide"] + "</i></a></td>" +
                        "<td><a href='" + url + "' class='btn-floating cyan darken-1'><i class='material-icons' title='Список вопросов'>subject</i></a></td>" +
                        "<td><a class='btn-floating teal accent-4'><i class='material-icons' title='Список респондентов'>supervisor_account</i></a></td>" +
                        "</tr>");
                });
                $("#addInterviewModal").closeModal();
                clearForm('interviewForm');
            } else {
                if (!isValidInterviewForm()) {
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

function showEditInterviewModal() {
    var nodes = document.getElementsByTagName("input");
    var id = -1;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].type == "checkbox" && nodes[i].checked == true) {
            id = nodes[i].value;
            break;
        }
    }
    if (id > 0) {
        $.ajax({
            url: '/edit-interview',
            method: 'GET',
            data: {"interviewId": id}
        }).done(function (data) {
            var values = JSON.parse(data);
            if (data.length > 1) {
                $.each(values, function (index, value) {
                    $('#name').addClass("active").val(value["name"]);
                    $('#description').addClass("active").val(value["description"]);

                    $("label[for='name']").addClass("active");
                    $("label[for='description']").addClass("active");

                    $('#type').val(value["type"]).material_select();

                    var subdivisionIds = value['subdivisions'].split(",");

                    $('#addInterviewModal').openModal();
                    $("#subdivisions").val(["Ректорат", "Информационные технологии"]);
                    $("#subdivisions").material_select();


                });
            }
        });
    } else {
        Materialize.toast("Не выбрана анкета для редактирования", 2000)
    }

}

var isChecked = true;
function selectAll() {
    var form = $("table").parent('form');
    var nodes = form[0].getElementsByTagName("input");
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].type == "checkbox")
            nodes[i].checked = isChecked;
    }
    isChecked = !isChecked;
}

function hideInterview(interviewId, element) {
    $.ajax({
        url: "/hide-interview/" + interviewId,
        method: 'GET',
        success: (function (response) {
            if (response == "success") {
                var icon = $(element).find("i");
                var text = $(icon).text();
                if (text == "lock_open") {
                    $(icon).html("lock");
                    Materialize.toast("Анкета скрыта", 2000)
                } else {
                    $(icon).html("lock_open");
                    Materialize.toast("Акета открыта", 2000)
                }
            }
        }),
        error: function () {
            location.reload();
        }
    });
}

function clearForm(formID) {
    var form = document.getElementById(formID);
    $(form).find(".error-alert").remove();
    $(form).find(".info-alert").remove();
    form.reset();
}

function deleteQuestion(questionId) {
    $.ajax({
        url: "/delete-question/" + questionId,
        method: 'GET',
        success: (function (response) {
            if (response == "success") {
                $("#" + questionId).parent('section').remove();
                $("#" + questionId).remove();
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

function hideChip() {
    $.ajax({
        url: "/hide-chip",
        method: 'GET',
        success: (function (response) {
            if (response != "success") {
                location.reload();
            }
        }),
        error: (function () {
            location.reload();
        })
    });
}

function hideElements() {
    var type = $('#type').val();
    if (type == null) {
        return;
    }
    if (type == 1) {
        $('#subdivisions').removeAttr('disabled').material_select();
        $('#posts').removeAttr('disabled').material_select();
        $("#interviewForm")
            .find(".modal-content")
            .find(".info-alert")
            .remove();
    } else {
        $('#subdivisions').attr('disabled', true).material_select();
        $('#posts').attr('disabled', true).material_select();

        var modalContent = $("#interviewForm").find(".modal-content");
        $(modalContent)
            .find(".error-alert")
            .remove();
        $(modalContent)
            .append("<div class = 'info-alert modal-alert-fix'>Данная анкета будет доступна только для анонимных пользователей</div>");
    }
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
