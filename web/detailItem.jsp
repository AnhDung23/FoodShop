<%-- 
    Document   : detailItem
    Created on : Feb 25, 2020, 11:30:05 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Item Information</title>
    </head>
    <body>
        <h1 style="margin-left: 40%">Item Information</h1>

        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="dto" value="${sessionScope.DTO}"/>
        <c:set var="listCate" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="confirmMsg" value="${requestScope.CONFIRM}"/>


        <c:if test="${not empty name}">
            <font color="red" style="margin-left: 70%"> Welcome, ${name} </font><br/>
        </c:if>


        <img src="${pageContext.request.contextPath}/image/${dto.image}" width="100" height="100"/><br/><br/>
        Product: ${dto.nameItem} <br/><br/>
        Description: ${dto.description} <br/><br/>
        Price: ${dto.price}<br/><br/>
        Amount: ${dto.amount}<br/><br/>
        Category: 
        <select name="txtCate" disabled>
            <option selected value="${listCate.get(dto.categoryID)}">
                ${listCate.get(dto.categoryID)}
            </option>
        </select><br/><br/>

        <c:if test="${role ne 'admin'}">
            Status:
            <select name="txtStatus" disabled >
                <option selected>
                    <c:if test="${dto.statusID eq 1}">Active</c:if>
                    <c:if test="${dto.statusID eq 2}">Inactive</c:if>
                    </option>
                </select><br/><br/>
        </c:if>


        <c:if test="${role eq 'admin'}">
            <form action="deleteItem" method="POST">
                Status:
                <select name="txtStatus" 
                        <c:if test="${dto.statusID eq 2}">
                            disabled
                        </c:if> 
                        >
                    <option 
                        <c:if test="${dto.statusID eq 1}">
                            selected
                        </c:if> 
                        <c:if test="${param.txtStatus eq 'Active'}">
                            selected
                        </c:if>
                        value="Active">
                        Active
                    </option>
                    <option 
                        <c:if test="${dto.statusID eq 2}">
                            selected
                        </c:if> 
                        <c:if test="${param.txtStatus eq 'Inactive'}">
                            selected
                        </c:if>
                        value="Inactive">
                        Inactive
                    </option>
                </select><br/>

                <c:if test="${dto.statusID eq 1}">
                    <c:if test="${not empty confirmMsg}">
                        <font color="blue">
                        ${confirmMsg} 
                        <input type="checkbox" name="cbxConfirm" value="confirm" /><br/>
                        </font>
                        <input type="hidden" name="txtFlagConfirm" value="flagConfirm" />
                    </c:if>

                    <input type="submit" value="Delete" name="btAction" />
                    <input type="hidden" name="txtNameItem" value="${dto.nameItem}" />
                </c:if>

            </form>

            <c:if test="${dto.statusID ne 2}">
                <c:url var="updateItem" value="updateDetailItems.jsp">
                    <c:param name="txtNameItem" value="${dto.nameItem}"/>
                    <c:param name="txtDescription" value="${dto.description}"/>
                    <c:param name="txtPrice" value="${dto.price}"/>
                    <c:param name="txtAmount" value="${dto.amount}"/>
                    <c:param name="txtCate" value="${param.txtCate}"/>
                    <c:param name="txtStatus" value="${param.txtStatus}"/>
                    <c:param name="flag" value="updateInfo"/>
                </c:url>
                <c:if test="${role eq 'admin'}">
                    <c:if test="${empty flagUpdate}">
                        <a href="${updateItem}">Change Item Information</a><br/>
                    </c:if>
                </c:if>
            </c:if>
        </c:if>
        <a href="showAll">Back to the home page</a>
    </body>
</html>
