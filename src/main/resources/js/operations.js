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
            $('#employeePostsId')
                .find('option')
                .remove()
                .end();
            $.each(employees, function (index, element) {
                alert(element["post_name"]);
                if (index == 0) {
                    $("#employeePostsId").append("<option value='" + element["post_id"] + "' disabled selected>Должности сотрудников</option>");
                }

                $("#employeePostsId").append("<option value='" + element["post_id"] + "'>" + element["post_name"] + "</option>");
            });

            $('#employeePostsId').material_select();
        }
    });
}

function clearForm(formID) {
    $('#'+formID)[0].reset();
}