<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/06/18
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="loginModule">
<head>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>

    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/main/css/default.css" type="text/css">
    <link rel="stylesheet" href="/css/carbon.css" type="text/css">
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/main/controllers/login.js"></script>


    <title>Business Intelligence in a Box</title>
</head>
<body class="background-style">


<h1>Business Intelligence in a Box</h1>

<form method="post" action="main" id="login-form">
    <div ng-controller="loginController" class="centre rounded-border"
         style="width: 300px;height: 150px;border-color: gray;">
        <div class="normal-margin">
            <div class="normal-margin">Email</div>
            <div class="normal-margin"><input type="text" ng-model="email" class="fill" name="email" id="email"></div>
            <div></div>
            <div class="normal-margin">Password</div>
            <div class="normal-margin"><input type="password" ng-model="password" class="fill" name="password"
                                              id="password"></div>
            <div class="align-right">
                <button type="submit" ng-click="doLogin();" id="loginButton">Login</button>
            </div>
            <div class="align-left">
                <button type="button" ng-click="changePassword();" id="changePwdButton">Change Password</button>
            </div>

        </div>
    </div>
    <input type="hidden" value="login" name="action" id="action">
</form>
<initialise comment="Initialise all the different controls that need the DOM to be available first">
    <jq-button button-id="loginButton"></jq-button>
    <jq-button button-id="changePwdButton"></jq-button>
</initialise>
</body>
</html>