<%-- 
    Document   : addItems
    Created on : Feb 6, 2020, 9:14:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New Item</title>
    </head>
    <body>

        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="listCate" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="error" value="${requestScope.ERROR}"/>

        <c:if test="${not empty name}">
            <font color="red"> Welcome, ${name} </font>
        </c:if><br/>
        <h3>Item Information</h3>


        <form action="addNewItem" method="POST" enctype="multipart/form-data">
            Image: <input type="file" name="txtFile"/><br/>
            <c:if test="${not empty error.imageIsNull}">
                <font color="red">
                ${error.imageIsNull}<br/>
                </font>     
            </c:if>
            <c:if test="${not empty error.imageFormatErr}">
                <font color="red">
                ${error.imageFormatErr}<br/>
                </font>     
            </c:if>

            Product: <input type="text" name="txtNameItem" value="" /><br/>
            <c:if test="${not empty error.nameLengthErr}">
                <font color="red">
                ${error.nameLengthErr}<br/>
                </font>                
            </c:if>
            <c:if test="${not empty error.nameExistedErr}">
                <font color="red">
                ${error.nameExistedErr}<br/>
                </font>                
            </c:if>

            Description: <input type="text" name="txtDescription" value="" /><br/>
            <c:if test="${not empty error.descriptionLengthErr}">
                <font color="red">
                ${error.descriptionLengthErr}<br/>
                </font>               
            </c:if>

            Price: <input type="text" name="txtPrice" value="" /><br/>
            <c:if test="${not empty error.priceFormatErr}">
                <font color="red">
                ${error.priceFormatErr}<br/>
                </font>
            </c:if>

            Amount: <input type="text" name="txtAmount" value="" /><br/>
            <c:if test="${not empty error.amountFormatErr}">
                <font color="red">
                ${error.amountFormatErr}<br/>
                </font>               
            </c:if>

            Category: <select name="txtCate">
                <c:forEach var="cate" items="${listCate}">
                    <option 
                        <c:if test="${param.txtCate eq cate.value}">
                            selected
                        </c:if>
                        >${cate.value}</option>
                </c:forEach>
            </select><br/>
            <input type="submit" value="Create" name="btAction" /><br/>
        </form>
        <c:set var="msg" value="${requestScope.MESSAGE}"/>
        <c:if test="${not empty msg}">
            <font color="green">${msg}</font><br/>
        </c:if><br/>
        <a href="showAll">Back to the home page</a>
    </body>
</html>
