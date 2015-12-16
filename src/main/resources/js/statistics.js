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

                if (type == 2) {
                    $("#subdivisions").attr("disabled", true).material_select();
                } else {
                    $("#subdivisions").removeAttr("disabled").material_select();
                }
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
            /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
            $("#questions").removeClass("l2").addClass("l3").empty();
            $(".l10").removeClass("l10").addClass("l9");
            if (questions.length > 0) {
                $.each(questions, function (index, question) {
                    var questionId = question["id"];
                    $("#questions").append("<p><input name='questions' id='" + questionId + "' type='radio'/>" +
                        "<label for= '" + questionId + "'>" + question["text"] + "</label></p>");
                    $("#" + questionId).click(function () {
                        loadAnswers(questionId);
                    });
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
    var val = select.value;
    $.ajax({
        url: "/load-subdivisions/" + val,
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
function loadAnswers(questionId) {
    $.ajax({
        url: "/load-answers/" + questionId,
        method: 'GET',
        success: (function (response) {
            if (response != "error") {
                var values = JSON.parse(response);
                if (response.length > 1) {
                    var arr = [['Да', 'Hours per Day']];
                    $.each(values, function (index, value) {
                        var answers = value['answer'].split(";");
                        var counts = value['counts'].split(";");

                        for (var i = 0; i < answers.length; i++) {
                            var p = [answers[i], parseInt(counts[i])];
                            arr.push(p);
                        }

                        drawChart(arr);
                    });
                }
            } else {
                location.reload();
            }
        }),
        error: (function () {
            location.reload();
        })
    });
}

function drawChart(arr) {
    var data = google.visualization.arrayToDataTable(arr);
    var chart = new google.visualization.PieChart(document.getElementById('piechart'));
    chart.draw(data);
}