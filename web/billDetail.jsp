<%-- 
    Document   : billDetail
    Created on : Feb 28, 2020, 10:19:45 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bill Detail</title>
    </head>
    <body>
        <h1>Bill Detail</h1>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:if test="${not empty name}">
            <h4>
                <font color="red"> Welcome, ${name} </font>
            </h4>
        </c:if>

        <c:set var="list" value="${requestScope.LIST}"/>  
        <form action="searchDetailInBill" method="POST">
            Product:&ensp;&ensp; <input type="text" name="txtSearchFood" value="${param.txtSearchFood}" style="margin-left: 2%"/>
            &ensp;&ensp;&ensp;<input type="submit" value="Search" name="btAction" /><br/>     
        </form>
             
        <c:if test="${not empty list}">
            <table border="1" cellspacing="0" style="text-align: center">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Username</th>
                        <th>Name Item</th>
                        <th>Buy Date</th>
                        <th>Amount</th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                        <tr>       
                            <td>
                                ${counter.count}
                            </td>
                            <td>
                                ${dto.username}
                            </td>
                            <td>
                                ${dto.nameItem}
                            </td>
                            <td>
                                ${dto.buyDate}
                            </td>
                            <td>
                                ${dto.amount}
                            </td>                      
                            <td>
                                ${dto.price}
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
        <a href="showAllHistory">Back page</a><br/>
        <a href="showAll">Back to the home page</a> 

    </body>
</html>
