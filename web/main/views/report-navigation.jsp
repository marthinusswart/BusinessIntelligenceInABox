<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/07/27
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="rounded-border normal-margin">
    <div class="normal-margin">
    <h2>Reports</h2>
        </div>

    <div class="normal-margin">
        <div id="report1" class="fill text-left" ng-click="loadReport('report1')">Report1 aaaa bbbb cccc dddd eeee ffff gggg</div>
    </div>
    <div class="normal-margin">
        <div id="report2" class="fill text-left" ng-click="loadReport('report2')">Report2 aaaa bbbb cccc dddd eeee ffff gggg</div>
    </div>
</div>

<initialise comment="Initialise all the different controls that need the DOM to be available first">
    <jq-button button-id="report1"></jq-button>
    <jq-button button-id="report2"></jq-button>
</initialise>