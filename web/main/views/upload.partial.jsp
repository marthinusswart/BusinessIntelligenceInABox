<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/07/27
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="rounded-border normal-margin">
    <div class="normal-margin">
        <h2>Upload Data</h2>
    </div>

    <div class="normal-margin">
        <div id="data1" class="fill text-left" ng-click="uploadData()">Data Upload 1</div>
    </div>

</div>

<initialise comment="Initialise all the different controls that need the DOM to be available first">
    <jq-button button-id="data1"></jq-button>
    <jq-button button-id="data2"></jq-button>
</initialise>