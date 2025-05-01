<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="content">
    <h2>Menu</h2>
    <button onclick="redirect('/shoes')">Shoes</button>
    <button onclick="redirect('/consultation-requests')">Consultation requests</button>
</div>
</body>
