<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shoes</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/shoes.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/shoes.js"></script>
</head>
<body>
<h1>Shoes</h1>
<button onclick="redirect('/shoes/creating')">Create shoes</button>
<table class="mainTable">
    <thead>
    <tr>
        <td>Image</td>
        <td>Vendor code</td>
        <td>Name</td>
        <td>Description</td>
        <td>Season code</td>
        <td>Model code</td>
        <td>Page</td>
        <td>Scales (size / volume)</td>
        <td>Options</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${shoes}" var="shoesItem">
        <tr>
            <td><img class="shoesImage" src="${shoesItem.imageUrl}" alt="${shoesItem.name}"/></td>
            <td>${shoesItem.vendorCode}</td>
            <td>${shoesItem.name}</td>
            <td>${shoesItem.description}</td>
            <td>${shoesItem.seasonName}</td>
            <td>${shoesItem.modelName}</td>
            <td><a href="${shoesItem.url}">${shoesItem.url}</a></td>
            <td>
                <table>
                    <tbody>
                    <c:forEach items="${shoesItem.scales}" var="scale">
                        <tr>
                            <td>${scale.size}</td>
                            <td>${scale.volume}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
            <td>
                <button title="Delete" onclick="deleteShoes('${shoesItem.vendorCode}')">Delete</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
