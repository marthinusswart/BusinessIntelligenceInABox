<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/06/28
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>

    <script src="/js/angular.min.js"></script>
    <script src="/js/angular-cookies.min.js"></script>


    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/controlpanel/css/default.css" type="text/css">
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/controlpanel/controllers/change-pwd.js"></script>


    <title>Business Intelligence in a Box Control Panel Login</title>
</head>
<body ng-app="changePasswordModule">


<h1>Change Password</h1>

<form method="post" action="/cpanel" id="password-form" novalidate>
    <div ng-controller="changePasswordController" class="centre rounded-border"
         style="width: 300px;height: 150px;border-color: gray;">
        <div class="normal-margin">
            <div class="normal-margin">New Password</div>
            <div class="normal-margin"><input type="password" ng-model="password" class="fill" name="password1" id="password1"></div>
            <div></div>
            <div class="normal-margin">Retype New Password</div>
            <div class="normal-margin"><input type="password" ng-model="password2" class="fill" name="password2" id="password2"></div>
            <div class="align-right">
                <button type="button" ng-click="doCancel();" id="cancelButton">Cancel</button>
                <button type="button" ng-click="doChange();" id="changeButton">Change</button>
            </div>


        </div>
    </div>
    <input type="hidden" value="newpwd" name="action" id="action">
</form>
<initialise comment="Initialise all the different controls that need the DOM to be available first">
    <jq-button button-id="cancelButton"></jq-button>
    <jq-button button-id="changeButton"></jq-button>
</initialise>
</body>
</html>