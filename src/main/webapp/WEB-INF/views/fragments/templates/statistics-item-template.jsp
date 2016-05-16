<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--JS Render template. Used on the designer page --%>
<script id="statistics-template" type="text/x-jsrender">
   {{for statistics}}
        <li>
            <div class="collapsible-header collapsible-header-fix active">
                {{:#index + 1}}.&nbsp;{{:questionText}}
            </div>
            <div class="collapsible-body">
                {{if questionType !== 'text'}}
                    <div class="row mobile-row center-align hide-on-large-only">
                        <a class="btn-floating table-btn"><i class="material-icons">list</i></a>
                        <a class="btn-floating pie-chart-btn"><i class="material-icons">album</i></a>
                        <a class="btn-floating col-chart-btn"><i
                                class="material-icons">assessment</i></a>
                    </div>
                {{/if}}
                <div class="row collapsible-row valign-wrapper">
                    <div class="col offset-l1 offset-m1 l10 m10 s12">
                        {{if questionType !== 'text'}}

                                <!--Container for chart-->
                                <div class="chart-container"></div>

                                <table id="datatable{{:#getIndex()}}" class="centered">
                                    <thead>
                                    <tr>
                                        {{if questionType === 'rating'}}
                                            <th>Оценка респондента</th>
                                        {{/if}}
                                        {{if questionType !== 'rating'}}
                                            <th>Ответ респондента</th>
                                        {{/if}}
                                        <th>Ответило, чел</th>
                                        <th>Ответило, %</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {{props answerData}}
                                        <tr>
                                            <th class="center-align">{{>key}}</th>
                                            <td>{{>prop[0]}}</td>
                                            <td>{{>prop[1]}}</td>
                                        </tr>
                                    {{/props}}

                                    </tbody>
                                </table>

                            {{if questionType === 'rating'}}
                                <span class="center-align teal-text left-align hide-on-small-only">
                                    <b>Усредненная оценка: {{:maxEstimate}}<br/></b>
                                    Максимально допустимая оценка: {{:maxEstimate}}<br/>
                                    Ответило человек: {{:total}}
                                </span>
                                <div class="center-align teal-text left-align hide-on-med-and-up">
                                    <b>Усредненная оценка: {{:maxEstimate}}<br/></b>
                                    Максимально допустимая оценка: {{:maxEstimate}}<br/>
                                    Ответило человек: {{:total}}
                                </div>
                            {{/if}}
                        {{/if}}
                        {{if questionType === 'text'}}
                            {{props answerData}}
                                <div class="custom-answer">
                                        {{>key}} (x{{>prop[0]}})
                                </div>
                            {{/props}}
                        {{/if}}
                    </div>
                    {{if questionType !== 'text'}}
                        <div class="col l1 valign hide-on-med-and-down">
                            <a class="btn-floating table-btn"><i class="material-icons">list</i></a>
                            <a class="btn-floating pie-chart-btn"><i
                                    class="material-icons">album</i></a>
                            <a class="btn-floating col-chart-btn"><i
                                    class="material-icons">assessment</i></a>
                        </div>
                    {{/if}}
                </div>
            </div>
        </li>
   {{/for}}
</script>
