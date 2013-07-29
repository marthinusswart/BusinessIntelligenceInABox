<%--
  Created by IntelliJ IDEA.
  User: marthinus
  Date: 2013/07/27
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<!DOCTYPE html>
<html ng-app="uploadModule">
<head >
    <script src="/js/jquery.min.js"></script>
    <script src="/js/jquery-ui.min.js"></script>
    <script src="/js/angular.min.js"></script>

    <link rel="stylesheet" href="/css/ui-lightness/jquery-ui.css"
          type="text/css">
    <link rel="stylesheet" href="/main/css/default.css" type="text/css">
    <link rel="stylesheet" href="/css/carbon.css" type="text/css">
    <script type="text/javascript" src="/directives/jquery-ui.js"></script>
    <script type="text/javascript" src="/main/controllers/upload.js"></script>

    <title>Business Intelligence in a Box Data Upload</title>
</head>
<body class="background-style">

<h1>Business Intelligence in a Box Data Upload</h1>

<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
    <div class="centre rounded-border"
         style="width: 500px;height: 100px;border-color: gray;" ng-controller="uploadController">

        <input id="fileupload" type="file" name="companyFile">
        <p></p>

    <input type="submit" id="submitButton" value="Upload">


        </div>
</form>
<jq-button button-id="submitButton"></jq-button>

</body>
</html>