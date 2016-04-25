$(document).ready(function () {

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
            type: 'column',
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

        tooltip: {
            formatter: function () {
                return '<b>' + "Оценка: " + this.series.name + '</b><br/>' +
                    "Ответило человек: " + this.point.y;
            }
        }
    };

    $(".col-chart-btn, .pie-chart-btn").click(function () {
        var $body = $(this).parents(".collapsible-body");
        $body.find("table").addClass("hide");
        $body.find(".chart-container").removeClass("hide");

        chartOptions.data = {
            table: 'datatable',
            endColumn: 1,
            endRow: $(this).parents(".collapsible-body").find("table>tbody>tr").length
        };

        if ($(this).hasClass("pie-chart-btn")) {
            chartOptions.chart.type = "pie";
            chartOptions.data.switchRowsAndColumns = false;

            chartOptions.plotOptions = {
                pie: {
                    allowPointSelect: true,
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                },
                tooltip: {
                    formatter: function () {
                        return '<b>' + "Оценка: " +  this.series.name + '</b><br/>' +
                            "Ответило человек: " + this.point.y;
                    }
                }
            };
        } else {
            chartOptions.chart.type = "column";
            chartOptions.data.switchRowsAndColumns = true;
        }

        $body.find(".chart-container").highcharts(chartOptions);
    });

    $(".table-btn").click(function () {
        var $body = $(this).parents(".collapsible-body");

        $body.find(".chart-container").addClass("hide");
        Materialize.fadeInImage($body.find("table").removeClass("hide"));
    });
});