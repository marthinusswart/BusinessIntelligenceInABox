var jqComponents = angular.module("jqComponents", []);
/*jqComponents.directive("jqButton", function ()
    {
        return {
            restrict:'E',
            replace:true,
            template:'<button ng-click="jqbClick()"></button>',
            scope:{
                'jqbClick':'&',
                'text':'@'
            },
            link:function (scope, element, attrs)
            {
                // make it a jQuery UI button
                element.button();

                // if the text change, update the label of the button
                scope.$watch('text', function (value)
                {
                    element.button('option', 'label', value);
                });


            }
        };
    }
);  */

jqComponents.directive("jqButton", function ()
    {
        return {
            restrict:'E',
            link:function (scope, element, attrs)
            {
                // make it a jQuery UI button
                $("#"+attrs.buttonId).button();

            }
        };
    }
);



