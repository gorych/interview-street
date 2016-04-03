<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--JS Render template. Used on the designer page --%>
<script id="question-template" type="text/x-jsrender">
    <div class="section">

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
                    <i class="right material-icons teal-text text-lighten-2" title="{{:answerType.title}}">{{:answerType.icon}}</i>
                </nav>
            </div>
        </div>

        <%--Form body--%>
        <div class="row narrow-row">
            <div class="input-field col l12 m12 s12">
                <input data-question="{{:question.id}}" value="{{:question.text}}" type="text" length="250"/>
                <label class="active">Текст вопроса</label>
            </div>

            {{if answerType.name === "radio" || answerType.name === "checkbox"}}
                {{for answers}}
                    <div class="input-field  col offset-l2 l7 m11 s11">
                        <input data-answer="{{:id}}" type="text" length="100" value="{{:text}} {{:#index+1}}"/>
                        <label class="active">Введите ответ</label>
                    </div>
                    <div class=" col icon-col l1 m1 s1">
                        <i class="small material-icons red-text text-lighten-1" title="Удалить ответ">delete_forever</i>
                    </div>
                {{/for}}
            {{else answerType.name === "rating" }}
                <div class="input-field col offset-l2 l8 m12 s12">
                     <input data-answer="{{:id}}" type="number" min = "3" max = "10" value="{{:answerType.defaultValue}}"/>
                     <label class="active">Количество звезд</label>
                </div>
                <div class="col offset-l2 col l8 m12 s12 rating center">
                    <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
                    <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
                    <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
                    <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
                    <i class="small material-icons red-text text-lighten-1 hoverable">star_rate</i>
                </div>
            {{else}}
                <div class="input-field  col offset-l2 l8 m11 s11">
                    <input disabled type="text" length="100"/>
                    <label>Нет ответов для выбора</label>
                </div>
            {{/if}}
        </div>

        <%--Form footer--%>
        <div class="divider grey lighten-1"></div>

        <a class="btn-floating btn-large add-quest-btn waves-effect waves-light blue-grey lighten-2 hoverable"
           title="Добавить вопрос"><i class="material-icons">add</i></a>
    </div>

</script>
