<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--JS Render template. Used on the designer page --%>
<script id="question-template" type="text/x-jsrender">
    <div class="section" data-question="{{:id}}">

        <%--Form navigation--%>
        <div class="row">
            <div class="col l12 m12 s12">
                <nav>
                    <div class="left number">1</div>
                    <ul>
                        <li><a><i class="material-icons move-down" title="Переместить вниз">arrow_downward</i></a></li>
                        <li><a><i class="material-icons move-up" title="Переместить вверх">arrow_upward</i></a></li>
                        <li><a><i class="material-icons duplicate"
                                  title="Дублировать вопрос">control_point_duplicate</i></a></li>
                        <li><a><i class="material-icons del-quest" title="Удалить вопрос">delete</i></a></li>
                    </ul>
                    <i class="right material-icons teal-text text-lighten-2" title="{{:type.title}}">{{:type.icon}}</i>
                </nav>
            </div>
        </div>

        <%--Form body--%>
        <div class="row narrow-row">
            <div class="input-field col l12 m12 s12">
                <input value="{{:text}}" type="text" length="250" title="Текст вопроса"/>
                <label class="active">Текст вопроса</label>
            </div>
            {{setId -1/}}
            {{if type.name === "radio" || type.name === "checkbox"}}
                {{for answers}}
                    {{if type.name === "text"}}
                        {{setId id/}}
                    {{else}}
                        {{include tmpl="multiAnswTmpl"/}}
                    {{/if}}
                {{/for}}
                <div class="col wide-col offset-l2 l8 left-align">
                    <i class="add-answer small material-icons green-text text-accent-4" title="Добавить ответ">add</i>
                    {{if ~getId() <= 0}}
                        <i class="add-text-answer small material-icons deep-orange-text"
                               title="Добавить текстовый ответ">playlist_add</i>
                    {{/if}}
                </div>
                {{if ~getId() > 0}}
                    {{:~printOptionalAnswer()}}
                {{/if}}
            {{else type.name=== "rating" }}
                {{include tmpl="rateAnswTmpl"/}}
            {{else}}
                {{include tmpl="textAnswTmpl"/}}
            {{/if}}
        </div>

        <%--Form footer--%>
        <div class="col s12 divider grey lighten-1"></div>

        <div class="row narrow-row center">
            <div class="col s12">
                <a class="btn-floating btn-large add-quest-btn waves-effect waves-light blue-grey lighten-2 hoverable"
                title="Добавить вопрос"><i class="material-icons">add</i></a>
            </div>
        </div>
    </div>
</script>
