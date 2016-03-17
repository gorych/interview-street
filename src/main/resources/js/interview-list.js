;(function () {

    addResetFormEvent();
    addHideChipEvent();
    addSaveInterviewEvent();
    addEventToSubmitDeleteBtn();

    addHideInterviewEventToAllButtons();
    addDeleteInterviewEventToAllButtons();
    addEditInterviewEventToAllButtons();

    addEventToSubdivisionSelect();
    addEventToInterviewTypeSelect();

}());

function addEventToSubmitDeleteBtn() {
    document.querySelector("#submit-delete-btn").onclick = function () {
        $.ajax({
            url: "/delete-interview",
            method: 'GET',
            data: {id: this.getAttribute("id")},
            success: (function (response) {
                if (response == "success") {
                    var delRows = $("table").find("input[name='id']:checked").each(function (i, row) {
                        var td = $(this).parent('td');
                        $(td).parent('tr').remove();
                    });

                    $("#delete-interview-modal").closeModal();

                    var msgHead = delRows > 1 ? "Анкеты" : "Анкета";
                    var msgBody = " успешно ";
                    var msgEnd = delRows > 1 ? "удалены" : "удалена";

                    interview.id = -1;
                    Materialize.toast(msgHead + msgBody + msgEnd, 2000);
                    console.log("Interviews deleted successfully.");

                }
            }),
            error: (function () {
                console.log("Can't delete interviews. Invalid response from server.");
                location.reload();
                interview.id = -1;
            })
        });
    };
}

function addEventToSubdivisionSelect() {
    document.querySelector("#subdivisions").onchange = function () {
        var values = $(this).val() || [];
        $.ajax({
            url: '/load-posts',
            method: 'GET',
            data: {"data": values.join()}
        }).done(function (response) {
            var posts = JSON.parse(response);
            if (response.length > 0) {
                var postSelect = document.querySelector("#posts");
                fillPostSelect(postSelect, posts);
                $('#posts').material_select();
            }
        });
    };
}

function addEventToInterviewTypeSelect() {
    document.querySelector("#type").onchange = function () {
        var interviewType = $(this).val() || 1;

        var subdvsnSelect = document.querySelector("#subdivisions") || {};
        var postSelect = document.querySelector("#posts") || {};
        var modalContent = document.querySelector(".modal-content") || {};

        if (interviewType == 1) {
            $(subdvsnSelect).removeAttr('disabled').material_select();
            $(postSelect).removeAttr('disabled').material_select();
            $(modalContent)
                .find(".info-alert").remove();
        } else {
            $(subdvsnSelect).attr('disabled', "").material_select();
            $(postSelect).attr('disabled', "").material_select();

            $(modalContent)
                .find(".error-alert").remove();
            $(modalContent)
                .append("<div class = 'info-alert modal-alert-fix'>Данная анкета будет доступна только для анонимных пользователей</div>");
        }
    };
}

function addResetFormEvent() {
    document.querySelector("#reset-form-btn").onclick = function () {
        clearForm("#add-interview-form");
    };
}

function addSaveInterviewEvent() {
    document.querySelector("#save-interview").onclick = function () {
        if (!isValidInterviewForm()) {
            console.log("Incorrect input data on the modal form.");
            return;
        }

        var form = document.querySelector("#add-interview-form");
        $.ajax({
            url: "/create-interview",
            data: $(form).serialize(),
            type: "POST"
        }).done(function (response) {
            if (response === "error") {
                console.log("Invalid response from server.");
                if (!isValidInterviewForm()) {
                    console.log("Incorrect input data on the modal form.");
                    return;
                }
                location.reload();
            }

            var values = JSON.parse(response);
            $.each(values, function (i, value) {
                var id = value["id"];
                var url = "/designer/" + value["interview_id"];

                <!--Hide rows if there more than 3-->
                hideOldRows(3);
                buildNewRow(id, value.interview_id, value.name, value.type, value.description, value.date, value.hide, url);
            });
            $("#add-edit-interview-modal").closeModal();
            clearForm('#add-interview-form');
        });
    };
}

function addEditInterviewEvent(btn, interviewId) {
    btn.onclick = function () {
        if (interviewId > 0) {
            var subdvsnSelect = document.querySelector("#subdivisions");
            var postSelect = document.querySelector("#posts");

            $.ajax({
                url: '/edit-interview',
                method: 'GET',
                data: {"interviewId": interviewId}
            }).done(function (response) {
                var data = JSON.parse(response);

                var postValues = data.posts;
                var subdivisionValues = data.subdivisions;
                var interview = data.interview;

                $.ajax({
                    url: '/load-posts',
                    method: 'GET',
                    data: {"data": subdivisionValues.join()}
                }).done(function (response) {
                    var posts = JSON.parse(response);
                    if (response.length > 0) {
                        fillPostSelect(postSelect, posts);
                        $(postSelect).material_select();
                        $(postSelect).val(postValues).material_select();
                        console.log("Add data to post select.");
                    }
                });

                $('#name').addClass("active").val(interview.name);
                $('#description').addClass("active").val(interview.description);

                $("label[for='name']").addClass("active");
                $("label[for='description']").addClass("active");

                $('#type').val(interview.type.id).material_select();
                $(subdvsnSelect).val(subdivisionValues).material_select();

                console.log("Add data to update-modal.");

                $("#add-edit-interview-modal").openModal();
            });
        } else {
            Materialize.toast("Не выбрана анкета для редактирования", 2000);
            console.log("Interview isn't selected for editing.");
        }
    };
}

function addHideInterviewEventToAllButtons() {
    var buttons = document.querySelectorAll('.lock-btn') || [];
    for (var i = 0; i < buttons.length; i++) {
        var btn = buttons[i];
        addHideInterviewEvent(btn, btn.getAttribute("data-interview-id"));
    }
}

function addDeleteInterviewEventToAllButtons() {
    var buttons = document.querySelectorAll('.delete-btn') || [];
    var i = 0;
    var len = buttons.length;
    for (; i < len; i++) {
        var btn = buttons[i];
        btn.onclick = function () {
            var interviewId = this.getAttribute("data-interview-id");
            $("#submit-delete-btn").attr("id", interviewId);
            $("#delete-interview-modal").openModal();
        }
    }
}

function addEditInterviewEventToAllButtons() {
    var buttons = document.querySelectorAll('.edit-interview-btn') || [];
    var i = 0;
    var len = buttons.length;
    for (; i < len; i++) {
        var btn = buttons[i];
        addEditInterviewEvent(btn, btn.getAttribute("data-interview-id"));
    }
}

function addHideInterviewEvent(btn, interviewId) {
    btn.onclick = function () {
        $.ajax({
            url: "/hide-interview/" + interviewId,
            method: 'GET'
        }).done(function (response) {
            if (response == "success") {
                var icon = $(btn).find("i");
                var text = $(icon).text();

                var msg;
                if (text == "lock_open") {
                    $(icon).html("lock");
                    msg = "Анкета закрыта для прохождения";
                } else {
                    $(icon).html("lock_open");
                    msg = "Анкета открыта для прохождения";
                }
                $(icon).attr("title", msg);
                Materialize.toast(msg, 2000)
            }
        });
    }
}

function addHideChipEvent() {
    document.querySelector("#hide-chip-btn").onclick = function () {
        $.ajax({
            url: "/hide-chip",
            method: 'GET'
        }).done(function (response) {
            if (response !== "success") {
                console.log("Unable to hide chip.");
                location.reload();
            }
        });
    };
}

function buildNewRow(elemId, interviewId, name, type, description, date, hideValue, url) {
    var table = document.querySelector("#interview-table");

    var body = table.tBodies[0] || table.appendChild(document.createElement("body"));
    var row = body.insertRow(0);
    row.setAttribute("class", "brown lighten-5");

    var cells = [];
    var cellCount = 8;
    for (var j = 0; j < cellCount; j++) {
        cells[j] = row.insertCell(j);
    }

    var input = document.createElement("input");
    $(input).attr({
        "type": "checkbox",
        "id": elemId,
        "value": elemId,
        "name": "id"
    });

    var label = document.createElement("label");
    $(label).attr({
        "for": elemId,
        "class": "table-checkbox-fix"
    });

    var typeIcon = document.createElement("i");
    $(typeIcon)
        .attr({
            "class": "material-icons table-material-icons-fix"
        })
        .html(type);

    var linkIcon = document.createElement("i");
    $(linkIcon)
        .attr({
            "title": "Список вопросов",
            "class": "material-icons"
        })
        .html("subject");

    var hideIcon = document.createElement("i");
    $(hideIcon)
        .attr({
            "title": "Скрыта для прохождения",
            "class": "material-icons table-material-icons-fix"
        })
        .html(hideValue);

    var hideLink = document.createElement("a");
    $(hideLink).append(hideIcon);
    addHideInterviewEvent(hideLink, interviewId);

    var questLink = document.createElement("a");
    $(questLink)
        .attr({
            "href": url,
            "class": "btn-floating cyan darken-1"
        })
        .click(null)
        .html(linkIcon);

    cells[0].appendChild(input);
    cells[0].appendChild(label);

    cells[1].innerHTML = name;
    cells[2].appendChild(typeIcon);
    cells[3].innerHTML = description;
    cells[4].innerHTML = date;

    cells[5].appendChild(hideLink);

    cells[6].appendChild(questLink);
}

function hideOldRows(rowCount) {
    var newRows = $("tr.brown");
    if (newRows.length >= rowCount) {
        $.each(newRows, function (j, elem) {
            $(elem).removeAttr("class");
        });
    }
}

function clearForm(formId) {
    var form = document.querySelector(formId) || {};
    $(form).find(".error-alert").remove();
    $(form).find(".info-alert").remove();
    $(form)[0].reset();
}

function fillPostSelect(postSelect, posts) {
    $(postSelect)
        .find('option')
        .remove()
        .end();
    $.each(posts, function (index, post) {
        var option = document.createElement("option");
        if (index == 0) {
            $(postSelect).append("<option value='-1' disabled selected>Выберите должности</option>");
        }
        option.setAttribute("value", post.id);
        option.innerHTML = post.name;

        $(postSelect).append(option);
    });
}

/*
 Validation for interview-list page fields
 */
function isValidInterviewForm() {
    var form = $('#add-interview-form');
    var type = $(form).find("#type");
    var name = $(form).find("#name");
    var posts = $(form).find("#posts");
    var description = $(form).find("#description");

    if (form == "undefined" || type == "undefined" || name == "undefined"
        || posts == "undefined" || description == "undefined") {
        location.reload();
    }

    var errors = [];
    if (name.length < 1 || name.length > 50) {
        errors.push("<li>Наименование должно быть от 1 до 50 символов</li>");
    }

    if (description.length < 1 || description.length > 50) {
        errors.push("<li>Описание должна быть от 1 до 50 символов</li>");
    }

    var typeValue = $(type).val();
    if (typeValue == null || typeValue < 1) {
        errors.push("<li>Выберите тип опроса</li>");
    }
    if (typeValue != 2) {
        var postValues = $(posts).val();
        if (postValues == null || postValues.length < 1) {
            errors.push("<li>Выберите должности сотрудников</li>");
        } else {
            $.each(postValues, function (i, elem) {
                if ($(elem).val() < 0) {
                    errors.push("<li>Выберите должности сотрудников</li>");
                    return false;
                }
            });
        }
    }

    if (errors.length > 0) {
        var errorBlocks = $(".error-alert");
        if (errorBlocks.length == 0) {
            $(form)
                .find(".modal-content")
                .append("<div class = 'error-alert modal-alert-fix'>" + errors.join("") + "</div>");
        } else {
            $(errorBlocks[0])
                .replaceWith("<div class = 'error-alert modal-alert-fix'>" + errors.join("") + "</div>")
        }
        return false;
    }

    return true;
}
