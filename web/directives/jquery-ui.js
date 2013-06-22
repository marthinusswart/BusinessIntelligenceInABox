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

jqComponents.directive("jqTabs", function ()
    {
        return {
            restrict:'E',
            scope:{
                "beforeActivateCallback":"&"
            } ,
            link:function (scope, element, attrs)
            {

                var beforeActivateCallback = scope.beforeActivateCallback();

                if (typeof beforeActivateCallback != "function")
                {

                    beforeActivateCallback = function(event,ui){return true;};

                }



                // make it a jQuery Tabs
                $("#"+attrs.tabsId).tabs(
                    {
                       beforeActivate:beforeActivateCallback

                    });


            }
        };
    }
);







