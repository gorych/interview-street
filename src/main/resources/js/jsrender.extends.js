(function($) {
    "use strict";

    // An extended {{for}} tag: {{range}} inherits from {{for}}, and adds
    // support for iterating over a range (start to end) of items within an array,
    // or for iterating directly over integers from start integer to end integer

    var global = {};

    $.views.tags({
        range: {
            baseTag: "for",
            render: function(val) {
                var array = val,
                    start = this.tagCtx.props.start || 0,
                    end = this.tagCtx.props.end;

                if (start || end) {
                    if (!this.tagCtx.args.length) {
                        array = [];
                        end = end || 0;
                        for (var i = start; i <= end; i++) {
                            array.push(i);
                        }
                    } else if ($.isArray(array)) {
                        array = array.slice(start, end);
                    }
                }
                return this.base(array);
            },
            onArrayChange: function(ev, eventArgs) {
                this.refresh();
            }
        },
        /*save Id to global variable*/
        setId: function(id) {
            global.id = id;
        }
    });

    $.views.helpers({
        /*Print custom template for optional answer*/
        printOptionalAnswer: function() {
            return $.render.optionalAnswTmpl.render({id: global.id});
        },
        /*return id from global variable*/
        getId: function(){
            return global.id;
        }
    })
})(jQuery);
