<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shoes | Creating</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/shoesCreating.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/shoesCreating.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<h2>Shoes creating</h2>
<button onclick="redirect('/shoes')">All shoes</button>
<form id="shoesCreatingForm">
    <div class="inputs">
        <div>
            <label for="vendorCode" class="label">Vendor Code:</label>
            <input id="vendorCode" class="input" name="vendorCode" type="text"/>
        </div>
        <div>
            <label for="url" class="label">URL: </label>
            <input id="url" class="input" name="url" type="text"/>
        </div>
        <div>
            <label for="name" class="label">Name: </label>
            <input id="name" class="input" name="name" type="text"/>
        </div>
        <div>
            <label for="description" class="label">Description: </label>
            <input id="description" class="input" name="description" type="text"/>
        </div>
        <div>
            <label for="imageUrl" class="label">Image URL: </label>
            <input id="imageUrl" class="input" name="imageUrl" type="text"/>
        </div>
        <div>
            <label for="scales" class="label">Scales: </label>
            <select name="scales" class="input" id="scales" multiple>
                <c:forEach items="${scales}" var="scale">
                    <option value="${scale.size}-${scale.volume}">${scale.size} ${scale.volume}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label for="seasonName" class="label">Season: </label>
            <select name="seasonName" class="input" id="seasonName">
                <c:forEach items="${seasons}" var="season">
                    <option value="${season.name}">${season.localizedName}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label for="modelName" class="label">Model: </label>
            <select name="modelName" class="input" id="modelName">
                <c:forEach items="${shoesModels}" var="shoesModel">
                    <option value="${shoesModel.name}">${shoesModel.localizedName}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <button onclick="createShoes()">Create</button>
</form>
</body>
</html>
