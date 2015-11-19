function loadEmployeePosts(select) {
    var result = [];
    var options = select && select.options;

    for (var i = 1; i < options.length; i++) {
        if (options[i].selected) {
            result.push(options[i].value);
        }
    }

    $.ajax({
        url: '/load-posts',
        method: 'GET',
        data: {"data": result.join()}
    }).done(function (data) {
        var employees = JSON.parse(data);
        if (data.length > 0) {
            $('#selectId')
                .find('option')
                .remove()
                .end();
            $.each(employees, function (index, element) {
                if (index == 0) {
                    $("#selectId").append("<option value='" + element["post_id"] + "' disabled selected>" + element["post_name"] + "</option>");
                }

                $("#selectId").append("<option value='" + element["post_id"] + "'>" + element["post_name"] + "</option>");
            });

            $('#selectId').material_select();
        }
    });
}