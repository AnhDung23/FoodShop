<%-- 
    Document   : homePage
    Created on : Feb 6, 2020, 5:42:38 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hana Shop</title>
    </head>
    <body>
        <h1>Hana Shop</h1>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:set var="role" value="${sessionScope.ROLE}"/>
        <c:set var="listCate" value="${sessionScope.LIST_CATEGORY}"/>
        <c:set var="flag_popular" value="${requestScope.FLAG_POPULAR}"/>
        
        <c:if test="${not empty name}">
            <h4>
                <font color="red"> Welcome, ${name} </font>
            </h4>

            <a href="viewCart.jsp">
                <font color="blue" style="margin-left: 70%"> View Cart </font>
            </a>          

            <c:url var="shoppingHistory" value="showAllHistory">
                <c:param name="btAction" value="Show All"/>
            </c:url>
            <a href="${shoppingHistory}">
                <font color="blue" style="margin-left: 5px"> Shopping history </font>
            </a>

            <c:url var="logoutLink" value="logout">
                <c:param name="btAction" value="Logout"/>
            </c:url>
            <a href="${logoutLink}" >
                <font color="blue" style="margin-left: 5px"> Logout </font>
            </a>

        </c:if>

        <c:if test="${empty name}">
            <a href="login.jsp" style="margin-left: 80%"">
                <font color="blue"> Login </font>
            </a>
        </c:if>

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
        &ensp;<a href="suggessPopular">Popular</a>

        <c:set var="list" value="${requestScope.LIST}"/>

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
                        <th>Buy (Quantity)</th>
                    </tr>
                </thead>
                <tbody>

                    <c:set var="numPage" value="${param.txtPage}"/>
                    <c:set var="sizePage" value="${requestScope.SIZE_OF_PAGE}"/>
                    <c:set var="confirmMsg" value="${requestScope.CONFIRM}"/>
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                    <form action="addToCart" method="POST">
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
                                <input type="hidden" name="txtPrice" value="${dto.price}" />
                            </td>
                            <td>
                                ${dto.createDate}
                            </td>
                            <td>
                                ${dto.amount}
                                <input type="hidden" name="txtAmountInStock" value="${dto.amount}" />
                            </td>
                            <td>
                                ${listCate.get(dto.categoryID)}                                    
                            </td>
                            <td>
                                <c:if test="${dto.statusID eq 1}">
                                    Active
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not empty flag_popular}">
                                    <input type="hidden" name="txtFlagSuggessPage" value="suggessPage" />
                                </c:if>
                                
                                <c:if test="${dto.amount ne 0}">
                                    <input type="text" name="txtQuantity" value="1" size="5" style="text-align: right"/> 
                                    <input type="submit" value="Add to cart" name="btAction" 
                                           <c:if test="${role ne 'member'}">disabled</c:if>
                                               /><br/>
                                    <c:if test="${dto.nameItem eq param.txtName}">
                                        <c:set var="err_quantity" value="${requestScope.ERROR_QUANTITY}"/>
                                        <c:set var="add_success_msg" value="${requestScope.MSG_ADD_SUCCESS}"/>
                                        <c:if test="${not empty err_quantity}">
                                            <font color="red">
                                            ${err_quantity}
                                            </font>                                    
                                        </c:if>
                                        <c:if test="${not empty add_success_msg}">
                                            <font color="green">
                                            ${add_success_msg}
                                            </font>                                    
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${dto.amount eq 0}">
                                    Out of stock
                                </c:if>
                            </td>
                        </tr>
                        <input type="hidden" name="txtSearch" value="${param.txtSearch}" />
                        <input type="hidden" name="txtRangeMoney" value="${param.txtRangeMoney}" />
                        <input type="hidden" name="txtCateSearch" value="${param.txtCateSearch}" />
                        <input type="hidden" name="txtPage" value="${param.txtPage}" />
                    </form>
                </c:forEach>

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

        
        <c:if test="${empty flag_popular}">
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
        </c:if>
        <c:if test="${not empty flag_popular}">
            <form action="suggessPopular" method="POST">                
                <c:forEach var="numPage" begin="1" end="${numberOfPages}">                    
                    <c:url var="pagingPage" value="suggessPopular">
                        <c:param name="txtPage" value="${numPage}"/>
                    </c:url>
                    <a href="${pagingPage}">${numPage}</a>
                </c:forEach>
            </form>
        </c:if>

    </c:if><br/>
    <c:if test="${empty list}">
        <font color="red">No record is match!!!</font>
    </c:if><br/>
</body>
</html>
