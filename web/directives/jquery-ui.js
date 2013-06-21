var jqComponents = angular.module("jqComponents", []);

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



