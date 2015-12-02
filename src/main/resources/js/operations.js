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
                    $("#posts").append("<option value='" + element["post_id"] + "' disabled selected>Должности сотрудников</option>");
                }

                $("#posts").append("<option value='" + element["post_id"] + "'>" + element["post_name"] + "</option>");
            });

            $('#posts').material_select();
        }
    });
}

function checkCbForDelete() {
    var nodes = document.getElementsByTagName("input");
    var flag = isCheckedValues(nodes);
    if (flag == true) {
        $('#deleteModal').openModal();
    } else {
        Materialize.toast("Не выбрана анкета для удаления", 2000)
    }
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

function deleteInterview() {
    var form = document.getElementById("tableInterviewForm");
    form.submit();
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
    var form = $('#interviewForm');
    $.ajax({
        url: "/create-interview",
        data: form.serialize(),
        type: "POST",
        success: function (response) {
            if (response == "success") {
                location.reload();
            } else {
                var errors = $(".error-alert");
                if (errors.length == 0) {
                    var content = $(form).find(".modal-content");
                    $(content).append("<div class = 'error-alert modal-alert-error-fix'>" + response + "</div>");
                } else {
                    $(errors[0]).replaceWith("<div class = 'error-alert modal-alert-error-fix'>" + response + "</div>")
                }
            }
        },
        error: function () {
            location.reload();
        }
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
                    var subSel = document.getElementById("subdivisions");

                    /*var options = subSel.options;
                     for (var k = 0; k < subdivisionIds.length; k++) {
                     for (var i = 0; i < options.length; i++) {
                     if (options[i].value == subdivisionIds[k]) {
                     $(options[i]).attr("selected", true);
                     }
                     }
                     }*/
                    $('#subdivisions').material_select();
                });

                $('#addInterviewModal').openModal();
            }
        });
    } else {
        Materialize.toast("Не выбрана анкета для редактирования", 2000)
    }

}

var isChecked = true;
function selectAll(formId) {
    var form = document.getElementById(formId);
    var nodes = form.getElementsByTagName("input");
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].type == "checkbox")
            nodes[i].checked = isChecked;
    }
    isChecked = !isChecked;
}

function hideInterview(interviewId) {
    $.ajax({
        url: "/hide-interview/" + interviewId,
        method: 'GET'
    }).done(function (response) {
        if (response == "success") {
            location.reload();
        }
    });
}

function clearForm(formID) {
    var form = document.getElementById(formID);
    var errors = form.getElementsByClassName("error-alert modal-alert-error-fix");
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
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
    /*for( key in kv ){
     value = kv[key];

     $('input[type="checkbox"][name="'+ key +'"][value="'+ value +'"],input[type="radio"][name="'+ key +'"][value="'+ value +'"]', element).prop('checked', true);
     $('select[name="'+ key +'"],input[type="text"][name="'+ key +'"],input[type="password"][name="'+ key +'"],input[type="hidden"][name="'+ key +'"],textarea[name="'+ key +'"]', element).val(value);
     }*/
}
