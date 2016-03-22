;(function () {

    /*Constructor for interview*/
    function Interview() {
        this.id = null;
        this.name = document.querySelector("#name").value || "Новая анкета";
        this.description = document.querySelector("#description").value || "Пустая анкета";
        this.goal = document.querySelector("#goal").text || "";
        this.audience = document.querySelector("#audience").text || "";
        this.isHide = true;
        this.endDate = document.querySelector("#end-date").value || getFormattedDate(new Date);
        this.type = {
            id: document.querySelector("#type").value || 1
        };
    }

    $('.modal-trigger').leanModal({
            ready: function () {
                var hiddenInputs = document.querySelectorAll(".select-dropdown");

                /*Add onblur events to modal selects*/
                var i = 0;
                var len = hiddenInputs.length;
                for (; i < len; i++) {
                    hiddenInputs[i].onblur = function () {
                        var selectWrapper = $(this).parent(".select-wrapper");
                        var select = $(selectWrapper).find("select");
                        var values = $(select).val() || [];
                        modalSelectsValidation(this, values);
                    };
                }
            },
            complete: clearForm("#add-interview-form")
        }
    );

    addInputsValidationListener();
    addHideChipListener();

    addSaveOrEditInterviewListener();
    addHideInterviewListenerToAllButtons();
    addDeleteInterviewListenersToAllButtons();
    addEditInterviewListenerToAllButtons();

    addListenerToSubmitDeleteBtn();
    addListenerToSubdivisionSelect();
    addListenerToInterviewTypeSelect();

    //region Listeners
    function addHideChipListener() {
        var chip = document.querySelector("#hide-chip-btn") || {};
        chip.onclick = function () {
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

    function addSaveOrEditInterviewListener() {
        document.querySelector("#save-interview").onclick = function () {
            var interview = new Interview();
            var postIds = $("#posts").val();

            var data = JSON.stringify([interview, postIds]);

            $.ajax({
                url: "/create-interview",
                data: data,
                type: "POST"
            }).done(function (interviewId) {
                if (interviewId !== null && interviewId > 0) {
                    interview.id = interviewId;
                    buildNewCard(interview);

                    $("#add-edit-interview-modal").closeModal();
                    clearForm('#add-interview-form');
                } else {
                    console.log("Interview is not added to the database. Json string: {}", interview);
                    location.reload();
                }
            });
        };
    }

    function addHideInterviewListenerToAllButtons() {
        var buttons = document.querySelectorAll('.lock-btn') || [];
        var i = 0;
        var len = buttons.length;
        for (; i < len; i++) {
            var btn = buttons[i];
            addHideInterviewListener(btn, btn.getAttribute("data-interview-id"));
        }
    }

    function addHideInterviewListener(btn, interviewId) {
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

    function addDeleteInterviewListenersToAllButtons() {
        var buttons = document.querySelectorAll('.delete-btn') || [];
        var i = 0;
        var len = buttons.length;
        for (; i < len; i++) {
            addDeleteInterviewListener(buttons[i]);
        }
    }

    function addDeleteInterviewListener(button) {
        button.onclick = function () {
            var interviewId = this.getAttribute("data-interview-id");
            var submitBtn = $("#submit-delete-btn");
            var card = $(this).parent(".card");

            $(submitBtn).attr("id", interviewId);
            submitBtn.parentNode = card;

            $("#delete-interview-modal").openModal();
        };
    }

    function addEditInterviewListenerToAllButtons() {
        var buttons = document.querySelectorAll('.edit-interview-btn') || [];
        var i = 0;
        var len = buttons.length;
        for (; i < len; i++) {
            var btn = buttons[i];
            addEditInterviewListener(btn, btn.getAttribute("data-interview-id"));
        }
    }

    function addEditInterviewListener(btn, interviewId) {
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
                    console.log(JSON.stringify(data));

                    var postValues = data.posts;
                    var subdivisionValues = data.subdivisions;
                    var interview = data.interview;

                    $.ajax({
                        url: '/load-posts',
                        method: 'GET',
                        data: {"data": JSON.stringify(subdivisionValues)}
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

    function addListenerToSubmitDeleteBtn() {
        document.querySelector("#submit-delete-btn").onclick = function () {
            var btn = this;
            $.ajax({
                url: "/delete-interview",
                method: 'GET',
                data: {data: JSON.stringify({id: btn.getAttribute("id") || 0})},
                success: (function (response) {
                    if (response === "success") {

                        var parent = btn.parentNode;
                        $(parent).remove();

                        $("#delete-interview-modal").closeModal();

                        Materialize.toast("Анкета успешно удалена", 2000);
                        console.log("Interview deleted successfully.");
                    }
                }),
                error: (function () {
                    console.log("Can't delete interviews. Invalid response from server.");
                    location.reload();
                })
            });
        };
    }

    function addListenerToSubdivisionSelect() {
        document.querySelector("#subdivisions").onchange = function () {
            var values = $(this).val() || [];
            $.ajax({
                url: '/load-posts',
                method: 'POST',
                data: JSON.stringify(values)
            }).done(function (response) {
                var data = JSON.parse(response);
                if (response.length > 0) {
                    var postSelect = document.querySelector("#posts");
                    fillPostSelect(postSelect, data);
                    $('#posts').material_select();
                }
            });
        };
    }

    function addListenerToInterviewTypeSelect() {
        document.querySelector("#type").onchange = function () {
            var interviewType = $(this).val() || 1;

            if (interviewType == 1) {
                resetAddFormSelects();
            } else {
                rebuildAddForm();
            }
        };
    }

    //endregion

    //region Helper functions
    function getFormattedDate(date) {
        var month = date.getMonth() + 1;
        return date.getFullYear() + "-" + (month < 10 ? "0" : "") + month + "-" + date.getDate();
    }

    function buildNewCard(interview) {
        var cardTemplate = document.querySelector("#card-template");
        var card = cardTemplate.cloneNode(true);

        var title = card.querySelector(".card-title-wrapper");
        var placementDate = card.querySelector(".placement-date");
        var endDate = card.querySelector(".end-date");
        var goal = card.querySelector(".goal");
        var audience = card.querySelector(".audience");
        var visibilityIcon = card.querySelector(".visibility-icon");
        var description = card.querySelector(".description");

        title.textContent = interview.name;
        placementDate.textContent = getFormattedDate(new Date());
        endDate.textContent = interview.endDate;

        /*if interview type is closed*/
        if (interview.type.id > 1) {
            goal.textContent = interview.goal;
            audience.textContent = interview.audience;
            visibilityIcon.textContent = "visibility_off";
        } else {
            goal.classList.add("hide");
            audience.classList.add("hide");
            visibilityIcon.textContent = "visibility";
        }
        description.textContent = interview.description;

        var dataIds = card.querySelectorAll("[data-interview-id]");
        var i = 0;
        var len = dataIds.length;
        for (; i < len; i++) {
            dataIds[i] = interview.id;
        }

        card.classList.remove("hide");
        card.removeAttribute("id");

        var cardContainer = document.querySelector(".card-container");
        cardContainer.appendChild(card);
    }

    function clearForm(formId) {
        var form = document.querySelector(formId) || {};
        $(form).find(".error-alert").remove();
        $(form).find(".info-alert").remove();

        $(form).find(".invalid").remove().end();
        $(form).find(".invalid-select").remove().end();

        var label = $("label[for='end-date']") || {};
        $(label).removeClass("active");

        resetAddFormSelects();

        $(form)[0].reset();
    }

    function resetAddFormSelects() {
        var subdvsnSelect = $("#subdivisions") || {};
        var postSelect = $("#posts") || {};

        $(subdvsnSelect).find("option:selected").removeAttr("selected");
        $(postSelect).find("option:selected").removeAttr("selected");

        $("#goal").remove();
        $("#audience").remove();

        $("label[for='goal']").replaceWith("<label for='subdivisions'>Подразделения</label>");
        $("label[for='audience']").replaceWith("<label for='posts'>Должности</label>");

        $("label[for='type']").removeClass("info-badge");

        $(subdvsnSelect).material_select();
        $(postSelect).material_select();
    }

    function rebuildAddForm() {
        var subdvsnSelect = $("#subdivisions") || {};
        var postSelect = $("#posts") || {};

        $(subdvsnSelect).material_select('destroy');
        $(postSelect).material_select('destroy');

        $("label[for='subdivisions']").replaceWith("<label for='goal'>Цель опроса</label>");
        $("label[for='posts']").replaceWith("<label for='audience'>Целевая аудитория</label>");

        $(subdvsnSelect).before("<input id='goal' class='validate' type='text' length='65'/>");
        $(postSelect).before("<input id='audience' class='validate' type='text' length='25'/>");

        $('input#goal, input#audience').characterCounter();

        $("label[for='type']").addClass("info-badge");
    }

    function fillPostSelect(postSelect, data) {
        $(postSelect)
            .find('option')
            .remove()
            .end();
        $.each(data, function (index, entry) {
            $.each(entry, function (j, post) {
                var option = document.createElement("option");
                if (index == 0) {
                    $(postSelect).append("<option value='-1' disabled selected>Выберите должности</option>");
                }
                option.setAttribute("value", post.id);
                option.textContent = post.name;

                $(postSelect).append(option);
            });
        });
    }

    //endregion

    //region Validation

    function addInputsValidationListener() {
        var form = document.querySelector("#add-interview-form");
        var inputs = form.querySelectorAll("input");

        var i = 0;
        var len = inputs.length;
        var value;
        for (; i < len; i++) {
            inputs[i].onblur = function () {
                value = this.value;
                if (!value || this.validity.patternMismatch) {
                    this.classList.add("invalid");
                } else {
                    this.classList.remove("invalid");
                }
            };
        }
    }

    function modalSelectsValidation(hiddenInput, values) {
        var len = values.length;
        if (len < 1) {
            hiddenInput.classList.add("invalid", "invalid-select");
            return
        }

        var i = 0;
        var value;
        for (; i < len; i++) {
            value = values[i];
            if (!value || value < 1) {
                hiddenInput.classList.add("invalid", "invalid-select");
                return
            }
            hiddenInput.classList.remove("invalid", "invalid-select");
        }
    }

    //endregion

}());




