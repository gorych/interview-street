function getSelectValues(select) {
    var result = [];
    var options = select && select.options;

    for (var i = 1; i < options.length; i++) {
        if (options[i].selected) {
            result.push(options[i].value);
        }
    }

    $.ajax({
        url: '/load-posts',
        method: 'POST',
        data: {"ids": result}
    });
}