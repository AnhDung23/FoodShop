<%-- 
    Document   : viewCart
    Created on : Feb 13, 2020, 9:32:13 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
    </head>
    <body>
        <c:set var="name" value="${sessionScope.NAME}"/>
        <c:if test="${not empty name}">
            <h4>
                <font color="red"> Welcome, ${name} </font>
            </h4>

            <c:url var="shoppingHistory" value="showAllHistory">
                <c:param name="btAction" value="Show All"/>
            </c:url>
            <a href="${shoppingHistory}">
                <font color="blue" style="margin-left: 80%"> Shopping history </font>
            </a>
        </c:if>

        <h1 style="margin-left: 40%">Your Cart includes</h1>

        <c:set var="cart" value="${sessionScope.CART}"/>

        <c:if test="${not empty cart}">
            <c:set var="itemsCart" value="${cart.itemsCart}"/>
            <c:if test="${not empty itemsCart}">
                <table border="1" cellspacing="0" style="text-align: center">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Update Quantity</th>
                            <th>Remove</th>                           
                        </tr>
                    </thead>
                    <tbody>
                    <form action="multiProcess" method="POST">
                        <c:set var="cbx_action" value="${requestScope.CBX_ACTION}"/>
                        <c:set var="err_update" value="${requestScope.ERR_UPDATE}"/>
                        <c:set var="row_err" value="${requestScope.ROW_ERR}"/>
                        <c:set var="confirm_err" value="${requestScope.ERROR_CONFIRM}"/>
                        <c:forEach var="item" items="${itemsCart}" varStatus="counter">

                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${item.key}   
                                    <input type="hidden" name="txtItem_${counter.count}" value="${item.key}" />
                                </td>
                                <td>         
                                    <input type="text" name="txtQuantity_${counter.count}" value="${item.value.quantity}" size="5" style="text-align: center"/>
                                    <c:if test="${not empty err_update}">
                                        <c:if test="${row_err eq counter.count}">
                                            <br/><font color="red">${err_update}</font>
                                        </c:if>                                    
                                    </c:if>
                                    <c:if test="${not empty confirm_err}">
                                        <c:if test="${row_err eq counter.count}">
                                            <br/><font color="red">${confirm_err}</font>
                                        </c:if>
                                    </c:if>
                                    <c:set var="amount" value="${item.value.quantity}"/>
                                </td>
                                <td>
                                    ${item.value.price}
                                    <c:set var="price" value="${item.value.price}"/>
                                </td>
                                <td>
                                    ${amount*price}
                                    <c:set var="total" value="${amount*price + total}"/>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btAction_${counter.count}" />
                                </td>
                                <td>
                                    <c:set var="check" value=""/>
                                    <c:forEach var="cbx" items="${cbx_action}">
                                        <c:if test="${cbx eq item.key}">
                                            <c:set var="check" value="true"/> 
                                        </c:if>
                                    </c:forEach>

                                    <input type="checkbox" name="cbxItem" value="${item.key}" 
                                           <c:if test="${check eq 'true'}">
                                               checked
                                           </c:if>
                                           />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="6">
                                Total: ${total}
                            </td> 
                            <td>
                                <c:set var="msg_remove" value="${requestScope.MSG_REMOVE}"/>
                                <c:if test="${not empty msg_remove}">
                                    <font color="blue">
                                    ${msg_remove} 
                                    <input type="checkbox" name="cbxConfirm" value="confirm" /><br/>
                                    </font>
                                    <input type="hidden" name="txtFlagConfirm" value="flagConfirm" />
                                </c:if>
                                <input type="submit" value="Remove" name="btAction" />                                                     
                            </td>
                        </tr>                    
                        </tbody>
                    </form>
                </table>
                <form action="confirmCart" method="POST">
                    <input type="hidden" name="txtTotalBill" value="${total}" />
                    <input type="submit" value="Confirm" name="btAction" /><br/>
                </form>
                                
<!--                <form action="https://sandbox.paypal.com/cgi-bin/webscr" method="POST">
                    
                    <input type="hidden" name="business" value="sb-zdib91088577@business.example.com" />

                    <input type="hidden" name="cmd" value="_xclick" />

                    <input type="hidden" name="item_name" value="Hoa don mua hang" />
                    
                    <input type="hidden" name="amount" value="" />total/23000

                    <input type="hidden" name="currency_code" value="USD" />

                    <input type="hidden" name="return" value="http://localhost:8084/J3.L.P0005_HanaShop/confirmCart" />

                    <input type="hidden" name="cancel_return" value="http://localhost:8084/J3.L.P0005_HanaShop/viewCart.jsp" />

                    <input type="image" value="" name="submit" src="https://www.paypalobjects.com/webstatic/en_US/i/btn/png/btn_buynow_107x26.png" alt="Buy Now"/>

                </form>-->

            </c:if>
        </c:if>       

        <c:set var="checkout_success_msq" value="${requestScope.CHECKOUT_MSG}"/>
        <c:if test="${empty cart.itemsCart}">
            <c:if test="${not empty checkout_success_msq}">
                <font color="green">
                ${checkout_success_msq}<br/>
                </font>
            </c:if>
            <c:if test="${empty checkout_success_msq}">
                <font color="blue">
                Your cart is empty!
                </font><br/>
            </c:if>
        </c:if>
        <a href="showAll">Back to the home page</a>
    </body>
</html>
