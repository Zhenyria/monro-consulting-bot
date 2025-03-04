<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shoes | Creating</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/shoesCreating.js"></script>
</head>
<body>
<h1>Shoes creating</h1>
<button onclick="redirect('/shoes')">All shoes</button>
<form id="shoesCreatingForm">
    <div>
        <label for="vendorCode">Vendor Code:</label>
        <input id="vendorCode" name="vendorCode" type="text"/>
    </div>
    <div>
        <label for="url">URL: </label>
        <input id="url" name="url" type="text"/>
    </div>
    <div>
        <label for="name">Name: </label>
        <input id="name" name="name" type="text"/>
    </div>
    <div>
        <label for="description">Description: </label>
        <input id="description" name="description" type="text"/>
    </div>
    <div>
        <label for="imageUrl">Image URL: </label>
        <input id="imageUrl" name="imageUrl" type="text"/>
    </div>
    <div>
        <label for="scales">Scales: </label>
        <select name="scales" id="scales" multiple>
            <c:forEach items="${scales}" var="scale">
                <option value="${scale.size}-${scale.volume}">${scale.size} ${scale.volume}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="seasonName">Season: </label>
        <select name="seasonName" id="seasonName">
            <c:forEach items="${seasons}" var="season">
                <option value="${season.name}">${season.localizedName}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="modelName">Model: </label>
        <select name="modelName" id="modelName">
            <c:forEach items="${shoesModels}" var="shoesModel">
                <option value="${shoesModel.name}">${shoesModel.localizedName}</option>
            </c:forEach>
        </select>
    </div>
    <button onclick="createShoes()">Create</button>
</form>
</body>
</html>
