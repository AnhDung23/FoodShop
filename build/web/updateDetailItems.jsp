<%-- 
    Document   : updateDeteilItems
    Created on : Feb 11, 2020, 8:34:10 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Item Page</title>
    </head>
    <body>
        <h1 style="margin-left: 40%">Update Item Information</h1>

        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="dto" value="${sessionScope.DTO}"/>
        <c:set var="listCate" value="${sessionScope.LIST_CATEGORY}"/>

        <c:set var="error" value="${requestScope.ERROR}"/>
        <c:set var="msgSuccess" value="${requestScope.MESSAGE}"/>

        <c:if test="${not empty name}">
            <font color="red" style="margin-left: 70%"> Welcome, ${name} </font><br/>
        </c:if>

        <form action="updateDetail" method="POST" enctype="multipart/form-data">


            <img src="${pageContext.request.contextPath}/image/${dto.image}" width="100" height="100"/> 
            <br/><br/>           

            Choose New Image: <input type="file" name="txtFile"/><br/>

            <c:if test="${not empty error.imageIsNull}">
                <font color="red">
                ${error.imageIsNull}<br/>
                </font>     
            </c:if>
            <c:if test="${not empty error.imageFormatErr}">
                <font color="red">
                ${error.imageFormatErr}<br/>
                </font>     
            </c:if><br/>

            Product: ${dto.nameItem} <br/><br/>
            <input type="hidden" name="txtName" value="${dto.nameItem}" />

            Description: ${dto.description} <br/>

            New Description:<input type="text" name="txtDescription" value="${param.txtDescription}"/><br/>

            <c:if test="${not empty error.descriptionLengthErr}">
                <font color="red">
                ${error.descriptionLengthErr}<br/>
                </font>               
            </c:if><br/>

            Price: ${dto.price}<br/>

            New Price:<input type="text" name="txtPrice" value="${param.txtPrice}"/><br/>

            <c:if test="${not empty error.priceFormatErr}">
                <font color="red">
                ${error.priceFormatErr}<br/>
                </font>
            </c:if><br/>

            Amount: ${dto.amount}<br/>

            New Amount:<input type="text" name="txtAmount" value="${param.txtAmount}"/><br/>

            <c:if test="${not empty error.amountFormatErr}">
                <font color="red">
                ${error.amountFormatErr}<br/>
                </font>               
            </c:if><br/>

            Category: 
            <select name="txtCate">
                <c:forEach var="cate" items="${listCate}">
                    <option 
                        <c:if test="${empty param.txtCate}">
                            <c:if test="${cate.value eq listCate.get(dto.categoryID)}">
                                selected
                            </c:if>    
                        </c:if>
                        <c:if test="${param.txtCate eq cate.value}">
                            selected
                        </c:if>
                        value="${cate.value}">
                        ${cate.value}
                    </option>
                </c:forEach>
            </select>
            <br/><br/>

            <input type="submit" value="Update" name="btAction" <c:if test="${dto.statusID eq 2}">disabled</c:if>/><br/>
            </form>
        <c:if test="${not empty msgSuccess}">
            <font color="green">${msgSuccess}</font><br/>
        </c:if>

        <c:if test="${not empty confirmMsg}">
            <form action="deleteItem" method="POST">
                <input type="hidden" name="txtNameItem" value="${dto.nameItem}" />
                <font color="blue">${confirmMsg}</font>
                <input type="submit" value="Confirm" name="btAction" />
            </form>
        </c:if>

        <c:url var="detailLink" value="showDetail">
            <c:param name="txtName" value="${dto.nameItem}"/>
        </c:url>

        <a href="${detailLink}" style="text-decoration: none">
            Back to show detail
        </a> <br/>
        <a href="showAll" style="text-decoration: none">
            Back to the home page
        </a> <br/>
    </body>
</html>
