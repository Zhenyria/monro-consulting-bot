<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Consultation requests</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/consultationRequests.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/consultationRequests.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Consultation requests</h2>
<table class="mainTable">
    <thead>
    <tr>
        <td>Name</td>
        <td>Last name</td>
        <td>Phone number</td>
        <td>Size</td>
        <td>Volume</td>
        <td>Wishes</td>
        <td>Created at</td>
        <td>Options</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${consultationRequests}" var="consultationRequest">
        <tr>
            <td>${consultationRequest.name}</td>
            <td>${consultationRequest.lastName}</td>
            <td>${consultationRequest.phoneNumber}</td>
            <td>${consultationRequest.size}</td>
            <td>${consultationRequest.volume}</td>
            <td>
                <table>
                    <tbody>
                    <c:forEach items="${consultationRequest.wishes}" var="wish">
                        <tr>
                            <td>${wish}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
            <td>${consultationRequest.createdAt}</td>
            <td>
                <button title="Delete" onclick="deleteConsultationRequest('${consultationRequest.id}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
