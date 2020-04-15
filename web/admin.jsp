<%-- 
    Document   : admin
    Created on : Feb 6, 2020, 5:42:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:if test="${not empty name}">
            <h4>
                <font color="red"> Welcome, ${name} </font>
            </h4>
        </c:if>

        <a href="addItems.jsp" 
           <font color="blue" style="margin-left: 75%">Add new items</font>
        </a>

        <c:url var="logoutLink" value="logout">
            <c:param name="btAction" value="Logout"/>
        </c:url>
        <a href="${logoutLink}" style="margin-left: 5px">
            <font color="blue"> Logout </font>
        </a>

        <form action="search" method="POST">
            Product: <input type="text" name="txtSearch" value="${param.txtSearch}" style="margin-left: 2%"/><br/>
            Price: <select name="txtRangeMoney" style="margin-left: 3.5%">
                <option 
                    <c:if test="${param.txtRangeMoney eq 'All Price'}">
                        selected
                    </c:if>
                    value="All Price">All Price</option>
                <option
                    <c:if test="${param.txtRangeMoney eq 'Smaller 100.000 VND'}">
                        selected
                    </c:if>
                    value="Smaller 100.000 VND">Smaller 100.000 VND</option>
                <option
                    <c:if test="${param.txtRangeMoney eq 'From 100.000 VND to 500.000 VND'}">
                        selected
                    </c:if>
                    value="From 100.000 VND to 500.000 VND">From 100.000 VND to 500.000 VND</option>
                <option
                    <c:if test="${param.txtRangeMoney eq 'From 500.000 VND to 1.000.000 VND'}">
                        selected
                    </c:if>
                    value="From 500.000 VND to 1.000.000 VND">From 500.000 VND to 1.000.000 VND</option>
                <option
                    <c:if test="${param.txtRangeMoney eq 'Greater 1.000.000 VND'}">
                        selected
                    </c:if>
                    value="Greater 1.000.000 VND">Greater 1.000.000 VND</option>
            </select>
            <input type="submit" value="Search" name="btAction" style="margin-left: 10%"/><br/>

            <c:set var="listCate" value="${sessionScope.LIST_CATEGORY}"/>
            Category: 
            <select name="txtCateSearch">
                <option
                    <c:if test="${empty param.txtCateSearch}">
                        selected
                    </c:if>
                    <c:if test="${param.txtCateSearch eq 'All'}">
                        selected
                    </c:if>/>All
                </option>
                <c:forEach var="cate" items="${listCate}">
                    <option 
                        <c:if test="${param.txtCateSearch eq cate.value}">
                            selected
                        </c:if>
                        >${cate.value}
                    </option>
                </c:forEach>
            </select><br/>       
        </form><br/>

        <a href="showAll">Show All</a>
        <c:set var="list" value="${requestScope.LIST}"/>
        <c:set var="listNameDelete" value="${requestScope.LIST_NAME_DELETE}"/>
        <c:if test="${not empty list}">
            <table border="1" cellspacing="0" style="text-align: center">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Product</th>
                        <th>Image</th>
                        <th>Description</th>
                        <th>Price (VND)</th>
                        <th>Create Date</th>
                        <th>Amount</th>
                        <th>Category</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <form action="deleteMultiRow" method="POST">
                    <c:set var="numPage" value="${param.txtPage}"/>
                    <c:set var="sizePage" value="${requestScope.SIZE_OF_PAGE}"/>
                    <c:set var="confirmMsg" value="${requestScope.CONFIRM}"/>
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                        <tr>
                            <td>
                                <c:if test="${empty numPage}">
                                    <c:set var="numPage" value="1"/>
                                </c:if>
                                ${counter.count + (numPage-1)*sizePage}
                            </td>
                            <td>                              
                                <c:url var="detailLink" value="showDetail">
                                    <c:param name="txtName" value="${dto.nameItem}"/>
                                </c:url>

                                <a href="${detailLink}" style="text-decoration: none">
                                    <font color="black">${dto.nameItem}</font>
                                </a>
                                <input type="hidden" name="txtName" value="${dto.nameItem}" />
                            </td>
                            <td>
                                <a href="${detailLink}" style="text-decoration: none">
                                    <img src="${pageContext.request.contextPath}/image/${dto.image}" width="100" height="100"/>
                                </a>                                
                            </td>
                            <td>
                                ${dto.description}
                            </td>
                            <td>
                                ${dto.price}
                            </td>
                            <td>
                                ${dto.createDate}
                            </td>
                            <td>
                                ${dto.amount}
                            </td>
                            <td>
                                 ${listCate.get(dto.categoryID)}                                           
                            </td>

                            <td>
                                <c:if test="${empty listNameDelete}">
                                    <select name="txtStatusUpdate" <c:if test="${dto.statusID eq 2}">disabled</c:if> >
                                        <option <c:if test="${dto.statusID eq 1}">selected</c:if> value="Active">
                                                Active
                                            </option>
                                            <option <c:if test="${dto.statusID eq 2}">selected</c:if> value="${dto.nameItem}-Inactive">
                                                Inactive
                                            </option>
                                        </select>
                                </c:if>

                                <c:if test="${not empty listNameDelete}">
                                    <c:if test="${dto.statusID eq 2}">
                                        <select name="txtStatusUpdate" disabled >
                                            <option selected value="Inactive">
                                                Inactive
                                            </option>
                                        </select>
                                    </c:if>
                                    <c:if test="${dto.statusID ne 2}">
                                        <select name="txtStatusUpdate">
                                            <c:set var="status" value="Active"/>

                                            <c:forEach var="nameDelete" items="${listNameDelete}">
                                                <c:if test="${nameDelete eq dto.nameItem}">
                                                    <c:set var="status" value="Inactive"/>
                                                </c:if>
                                            </c:forEach>

                                            <option
                                                <c:if test="${status eq 'Active'}"> selected </c:if> value="Active">                                                                                                
                                                    Active
                                                </option>
                                                <option
                                                <c:if test="${status eq 'Inactive'}"> selected </c:if> value="${dto.nameItem}-Inactive">                                                                                               
                                                    Inactive
                                                </option>
                                            </select>
                                    </c:if>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <input type="hidden" name="txtSearch" value="${param.txtSearch}" />
                    <input type="hidden" name="txtRangeMoney" value="${param.txtRangeMoney}" />
                    <input type="hidden" name="txtCateSearch" value="${param.txtCateSearch}" />
                    <input type="hidden" name="txtPage" value="${param.txtPage}" />
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <c:if test="${not empty confirmMsg}">
                                <font color="blue">
                                ${confirmMsg} 
                                <input type="checkbox" name="cbxConfirm" value="confirm" /><br/>
                                </font>
                                <input type="hidden" name="listNameDelete" value="${listNameDelete}" />
                                <input type="hidden" name="txtFlagConfirm" value="flagConfirm" />
                            </c:if>
                            <input type="submit" value="Delete" name="btAction" />
                        </td>
                    </tr>
                </form>
            </tbody>
        </table>

        <c:set var="numberOfPages" value="${sessionScope.NUM_OF_PAGES}"/>

        <c:set var="linkActionNumPage" value="search"/>
        <c:if test="${empty param.txtSearch}">
            <c:if test="${param.txtRangeMoney eq 'All Price'}">
                <c:if test="${param.txtCateSearch eq 'All'}">
                    <c:set var="linkActionNumPage" value="showAll"/>
                </c:if>
            </c:if>
            <c:if test="${empty param.txtRangeMoney}">
                <c:if test="${empty param.txtCateSearch}">
                    <c:set var="linkActionNumPage" value="showAll"/>
                </c:if>
            </c:if>
        </c:if>


        <form action="${linkActionNumPage}" method="POST">                
            <c:forEach var="numPage" begin="1" end="${numberOfPages}">                    
                <c:url var="pagingPage" value="${linkActionNumPage}">
                    <c:param name="txtPage" value="${numPage}"/>

                    <c:if test="${linkActionNumPage eq 'search'}">
                        <c:param name="txtSearch" value="${param.txtSearch}"/>
                        <c:param name="txtRangeMoney" value="${param.txtRangeMoney}"/>
                        <c:param name="txtCateSearch" value="${param.txtCateSearch}"/>
                    </c:if>

                </c:url>
                <a href="${pagingPage}">${numPage}</a>
            </c:forEach>
        </form>

    </c:if><br/>
    <c:if test="${empty list}">
        <font color="red">No record is match!!!</font>
    </c:if><br/>
</body>
</html>
