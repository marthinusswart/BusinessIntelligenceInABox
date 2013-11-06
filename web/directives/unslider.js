/**
 * Created with IntelliJ IDEA.
 * User: marthinusswart
 * Date: 2013/11/06
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
var bibUnslider = angular.module("bibUnslider", []);

bibUnslider.directive("unslider", function ()
    {
        return {
            restrict:'E',
            link:function (scope, element, attrs)
            {

                console.info("Activating the slider");

                $(attrs.unsliderClass).unslider(
                    {
                        fluid:true,
                        dots:true,
                        speed:500,
                        delay:5000
                    }
                );


            }
        };
    }
);
