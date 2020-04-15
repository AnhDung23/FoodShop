<%-- 
    Document   : shoppingHistory
    Created on : Feb 17, 2020, 5:41:42 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping history</title>
    </head>
    <body>
        <h1>Shopping history</h1>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:if test="${not empty name}">
            <h4>
                <font color="red"> Welcome, ${name} </font>
            </h4>

            <a href="viewCart.jsp">
                <font color="blue" style="margin-left: 80%"> View Cart </font>
            </a>
        </c:if>

        <c:set var="err_date" value="${requestScope.ERR_DATE}"/>
        <form action="searchHistory" method="POST">
            Product:&ensp;&ensp; <input type="text" name="txtSearchHistory" value="${param.txtSearchHistory}" style="margin-left: 2%"/>
            &ensp;&ensp;&ensp;<input type="submit" value="Search" name="btAction" /><br/>
            Date range (dd/mm/yyyy): <br/>
            &ensp;&ensp;&ensp;From: <input type="text" name="txtDDFrom" value="${param.txtDDFrom}" size="1" />/<input type="text" name="txtMMFrom" value="${param.txtMMFrom}" size="1" />/<input type="text" name="txtYYFrom" value="${param.txtYYFrom}" size="1" />
            &ensp;To: <input type="text" name="txtDDTo" value="${param.txtDDTo}" size="1"/>/<input type="text" name="txtMMTo" value="${param.txtMMTo}" size="1" />/<input type="text" name="txtYYTo" value="${param.txtYYTo}" size="1" />

            <c:if test="${not empty err_date}">
                <font color="red">
                &ensp;&ensp;${err_date}<br/>
                </font>
            </c:if>
        </form>

        <a href="showAllHistory">Show All</a><br/>
        <c:set var="list" value="${requestScope.LIST}"/>        
        <c:if test="${not empty list}">
            <table border="1" cellspacing="0" style="text-align: center">
                <thead>
                    <tr>
                        <th>Bill ID</th>
                        <th>Username</th>
                        <th>Buy Date</th>
                        <th>Number of Items</th>
                        <th>Total Bill</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}" >
                        <tr>       
                            <td>
                                <c:url var="billDetail" value="showBillDetail">
                                    <c:param name="txtIdBill" value="${dto.idBill}"/>
                                </c:url>
                                <a href="${billDetail}">${dto.idBill}</a>
                            </td>
                            <td>
                                ${dto.username}
                            </td>
                            <td>
                                ${dto.buyDate}
                            </td>
                            <td>
                                ${dto.numOfItems}
                            </td>
                            <td>
                                ${dto.total}
                            </td>                      
                        </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty list}">
        <font color="red">No record is match!!!</font><br/>
    </c:if>
    <a href="showAll">Back to the home page</a>
</body>
</html>
