$(document).ready(function () {

    var $subs = $("#subs");
    var $publish = $("#publish-id");
    var $selects = $("#subs, #publish-id");

    $.templates({
        statisticsTmpl: "#statistics-template"
    });

    $(document).ready(function () {
        if ($("#interviews").val()) {
            $selects.removeAttr("disabled").val(0);
        } else {
            $selects.attr("disabled");
        }

        $subs.material_select();
        $publish.material_select();

        $subs.trigger('change');
    });

    $(document).on('click', ".user-answer", function () {
        var data = {
            hash: $("#interviews").val(),
            answerText: $(this).html()
        };
        $.get(global.rewriteUrl("/statistics/load-respondents"), data, global.ajaxCallback)
            .done(function (response) {
                var resultList = JSON.parse(response);

                var formatResult = "";
                $.each(resultList, function (index, val) {
                    formatResult += val + "<br/>";
                });

                var $respondentList = $('#respondent-list');
                $respondentList.find('.list-body').html(formatResult);
                $respondentList.openModal();
            });
    });

    $("#interviews").change(function () {
        $selects
            .removeAttr("disabled")
            .val(0); //All accessible subdivisions for interview

        $("#interview-name")
            .html($(this).find(":selected:last").text())
            .next().addClass("hide");

        $selects.find("option[value!='0']").remove();

        $subs.material_select();
        $publish.material_select();

        $subs.trigger('change', $(this).find(":selected").not(":disabled").attr("data-type"));
    });

    $selects.on('change', function (ev, type) {
        var data = {
            hash: $("#interviews").val(),
            publishId: $publish.val() || 0,
            subId: $subs.val() || 0
        };

        function fillSelectFacade(inData, select, prop1, prop2) {
            if (inData && inData.length) {
                fillSelect(select, inData, prop1, prop2);
            } else if (type && type !== "open") {
                $(select).attr("disabled", "disabled");
                $(select).material_select();
            }
        }

        $.get(global.rewriteUrl("/statistics/load-data"), data, global.ajaxCallback)
            .done(function (response) {
                var data = JSON.parse(response);

                fillSelectFacade(data.subdivisions, $subs, "id", "name");
                fillSelectFacade(data.published_dates, $publish, "id", "format-date");

                var $statistics = $("#statistics-container");
                $statistics.empty();

                if (data.statistics) {
                    $statistics
                        .removeClass("hide")
                        .append($.render.statisticsTmpl(data));
                    $statistics.collapsible();
                } else {
                    $statistics.addClass("hide");
                }
            });
    });

    function fillSelect(select, data, prop1, prop2) {
        $(select).find("option[value!='0']").remove();

        $.each(data, function (i, val) {
            $(select).append($('<option>', {
                value: val[prop1],
                text: val[prop2]
            }));
        });

        $(select).material_select();
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