$(document).ready(function () {

    var $subs = $("#subs");

    $.templates({
        statisticsTmpl: "#statistics-template"
    });

    $(document).ready(function () {
        if ($("#interviews").val()) {
            $subs.removeAttr("disabled").val(0);
        } else {
            $subs.attr("disabled");
        }

        $subs.material_select();
        $subs.trigger('change');
    });

    $("#interviews").change(function () {
        var $selects = $("#subs, #publish-date");

        $selects
            .removeAttr("disabled")
            .val(0); //All accessible subdivisions for interview

        $("#interview-name")
            .html($(this).find(":selected:last").text())
            .next().addClass("hide");

        $selects.find("option[value!='0']").remove();
        //$subs.find("option[value!='0']").remove();

        $subs.material_select();
        $("#publish-date").material_select();

        $subs.trigger('change');
    });

    $subs.on('change', function () {
        var data = {
            hash: $("#interviews").val(),
            publishId: $("#publish-id").val(),
            subId: $subs.val()
        };

        $.get(global.rewriteUrl("/statistics/load-data"), data, global.ajaxCallback)
            .done(function (response) {
                var data = JSON.parse(response);
                console.log(data);

                var $statistics = $("#statistics-container");
                $statistics.empty();

                if (data.statistics.length > 0) {
                    $statistics
                        .removeClass("hide")
                        .append($.render.statisticsTmpl(data));

                    fillSubSelect(data.subdivisions || {});

                    $statistics.collapsible();
                } else {
                    $statistics.addClass("hide");
                    Materialize.toast("По выбранным критериям не найдено ответов.", 3000);
                }
            });
    });

    function fillSubSelect(subs) {
        $.each(subs, function (i, sub) {
            $subs.append($('<option>', {
                value: sub.id,
                text: sub.name
            }));
        });

        $subs.material_select();
    }

    var chartOptions = {
        lang: {
            printChart: 'Распечатать график',
            downloadPNG: 'Скачать в PNG',
            downloadJPEG: 'Скачать в JPEG',
            downloadPDF: 'Скачать в PDF',
            downloadSVG: 'Скачать в SVG',
            contextButtonTitle: 'Контекстное меню'
        },
        chart: {
            backgroundColor: '#f5f5f5'
        },
        credits: {
            enabled: false
        },
        title: {
            text: undefined
        },
        yAxis: {
            allowDecimals: false,
            title: {
                text: 'Ответило, чел'
            }
        },
        xAxis: {
            title: {
                text: 'Ответ респондента'
            },
            labels: {
                enabled: false
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        }
    };

    function collectData(table) {
        var data = [];

        $(table).find("tbody>tr").each(function () {
            var answer = $(this).find('th:first').text();
            var peopleCount = $(this).find('td:nth-child(2)').text();

            data.push({
                "name": answer,
                "y": parseFloat(peopleCount)
            });
        });

        return data;
    }

    $(document).on('click', '.col-chart-btn, .pie-chart-btn', function () {
        var $body = $(this).parents(".collapsible-body");
        var $table = $body.find("table");

        $table.addClass("hide");
        $body.find(".chart-container").removeClass("hide");

        if ($(this).hasClass("pie-chart-btn")) {
            chartOptions.chart.type = "pie";

            delete chartOptions['data'];

            chartOptions.series = [{
                name: 'Ответило',
                colorByPoint: true,
                data: collectData($table)
            }];

            chartOptions.tooltip = {
                pointFormat: '{series.name}: <b>[{point.percentage:.1f}%, {point.y} чел]</b>'
            };
        } else {
            chartOptions.chart.type = "column";

            delete chartOptions['series'];

            chartOptions.data = {
                table: $table.attr("id"),
                endColumn: 1,
                endRow: $(this).parents(".collapsible-body").find("table>tbody>tr").length,
                switchRowsAndColumns: true
            };

            chartOptions.tooltip = {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        "Ответило человек: " + this.point.y;
                }
            };
        }

        $body.find(".chart-container").highcharts(chartOptions);
    });

    $(document).on('click', '.table-btn', function () {
        var $body = $(this).parents(".collapsible-body");

        $body.find(".chart-container").addClass("hide");
        Materialize.fadeInImage($body.find("table").removeClass("hide"));
    });

});