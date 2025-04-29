<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shoes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/shoes.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/shoes.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Shoes</h2>
<button onclick="redirect('/shoes/creating')">Create shoes</button>
<table class="mainTable">
    <thead>
    <tr>
        <td>ID</td>
        <td>Image</td>
        <td>Vendor code</td>
        <td>Name</td>
        <td>Description</td>
        <td>Season</td>
        <td>Model</td>
        <td>Page</td>
        <td>Sizes</td>
        <td>Volume</td>
        <td>Options</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${shoes}" var="shoesItem">
        <tr>
            <td><img class="shoesImage" src="${shoesItem.imageUrl}" alt="${shoesItem.name}"/></td>
            <td>${shoesItem.id}</td>
            <td>${shoesItem.vendorCode}</td>
            <td>${shoesItem.name}</td>
            <td>${shoesItem.description}</td>
            <td>${shoesItem.seasonName}</td>
            <td>${shoesItem.modelName}</td>
            <td><a href="${shoesItem.url}">${shoesItem.url}</a></td>
            <td>
                <table>
                    <tbody>
                    <tr>
                        <c:forEach items="${shoesItem.sizes}" var="size">
                            <td>${size}</td>
                        </c:forEach>
                    </tr>
                    </tbody>
                </table>
            </td>
            <td>${shoesItem.volume}</td>
            <td>
                <button title="Delete" onclick="deleteShoes('${shoesItem.id}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
