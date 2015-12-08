function loadInterviews(select) {
    var type = select.value;
    $.ajax({
        url: "/load-interviews/" + type,
        method: 'GET',
        success: (function (response) {
            if (response == "error") {
                location.reload();
                return;
            }
            var interviews = JSON.parse(response);
            if (response.length > 0) {
                $("#interviews")
                    .find('option')
                    .remove()
                    .end();
                $.each(interviews, function (index, element) {
                    if (index == 0) {
                        $("#interviews").append("<option value='' disabled selected>Выберите анкету</option>");
                    }
                    $("#interviews").append("<option value='" + element["id"] + "'>" + element["name"] + "</option>");
                });

                $("#interviews").material_select();
            }
        }),
        error: function () {
            location.reload();
        }
    });
}

function loadInterviewQuestions(select) {
    $.ajax({
        url: "/load-questions/" + select.value,
        method: 'GET',
        success: (function (response) {
            if (response == "error") {
                location.reload();
                return;
            }
            var questions = JSON.parse(response);
            $("#questions").empty();
            if (questions.length > 0) {
                $.each(questions, function (index, question) {
                    $("#questions").append("<p><input name='questions' type='radio' id='" + question["id"] + "' " +
                        "value='" + question["id"] + "'/>" +
                        "<label for= '" + question["id"] + "'>" + question["text"] + "</label></p>");
                });
            } else {
                $("#questions").append("В данной анкете нет вопросов");
            }
            loadSubdivisions(select);
        }),
        error: function () {
            location.reload();
        }
    });
}

function loadSubdivisions(select) {
    $.ajax({
        url: "/load-subdivisions/" + select.value,
        method: 'GET',
        success: (function (response) {
            if (response == "error") {
                location.reload();
                return;
            }
            var employees = JSON.parse(response);
            if (employees.length > 0) {
                $('#subdivisions')
                    .find('option')
                    .remove()
                    .end();

                $.each(employees, function (j, empl) {
                    var subdivisions = empl["subdivisions"].split(',');
                    var subdvsnNames = empl["subdvsn_names"].split(',');

                    $.each(subdivisions, function (i, id) {
                        if (i == 0) {
                            $("#subdivisions").append("<option value='' disabled selected>Выберите подразделение</option>");
                        }
                        $("#subdivisions").append("<option value='" + id + "'>" + subdvsnNames[i] + "</option>");
                    });
                });
            } else {
                $('#subdivisions').attr("disabled", true);
            }
            $('#subdivisions').material_select();
        }),
        error: function () {
            location.reload();
        }
    });
}