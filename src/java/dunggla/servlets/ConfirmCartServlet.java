/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.bills.BillsDAO;
import dunggla.cart.CartObj;
import dunggla.cart.ItemInCartDTO;
import dunggla.items.ItemsDAO;
import dunggla.shoppingcart.ShoppingCartDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class ConfirmCartServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";
    private final String CONFIRM_PAGE = "viewCart.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = ERROR_PAGE;

        boolean checkConfirm = true;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                // Get user buy item
                String username = (String) session.getAttribute("USERNAME");
                CartObj cart = (CartObj) session.getAttribute("CART");

                if (cart != null) {
                    cart.setUsername(username);
                    Map<String, ItemInCartDTO> items = cart.getItemsCart();
                    if (items != null) {
                        int row = 1;
                        for (String itemName : items.keySet()) {
                            int quantityBuy = items.get(itemName).getQuantity();
                            int quantityInStock = items.get(itemName).getAmountOfStock();
                            
                            // Check amount out of stock
                            if (quantityBuy > quantityInStock) {
                                checkConfirm = false;
                                request.setAttribute("ROW_ERR", row);
                                request.setAttribute("ERROR_CONFIRM", "Quantity of " + itemName + " is out of stock (" + quantityInStock + " items)");
                                url = CONFIRM_PAGE;
                                break;
                            }
                        }

                        if (checkConfirm) {
                            ShoppingCartDAO dao = new ShoppingCartDAO();
                            ItemsDAO daoItem = new ItemsDAO();
                            boolean checkOut = true;

                            // Get current date
                            long millis = System.currentTimeMillis();
                            Timestamp dateBuy = new Timestamp(millis);

                            BillsDAO billDao = new BillsDAO();
                            int idBill = billDao.getMaxIDBill() + 1;
                            String total = request.getParameter("txtTotalBill");
                            int totalBill = Integer.parseInt(total);

                            // save bill to database
                            checkOut = billDao.checkOutBill(idBill, username, dateBuy, items.size(), totalBill);

                            if (checkOut) {
                                // Save bill detail
                                for (String itemName : items.keySet()) {
                                    int amount = items.get(itemName).getQuantity();
                                    int price = items.get(itemName).getPrice();
                                    int totalPrice = amount * price;
                                    checkOut = dao.checkOutCart(username, itemName, dateBuy, amount, price, totalPrice, idBill);
                                    int amountInStock = items.get(itemName).getAmountOfStock() - amount;
                                    items.get(itemName).setAmountOfStock(amountInStock);
                                    checkOut = daoItem.updateQuantityInStock(itemName, amountInStock);
                                    if (!checkOut) {
                                        break;
                                    }
                                }
                                if (checkOut) {
                                    cart = null;
                                    session.setAttribute("CART", cart);
                                    request.setAttribute("CHECKOUT_MSG", "Check out successful!!");
                                    url = CONFIRM_PAGE;
                                }
                            }

                        }
                    }
                }
            }

        } catch (NamingException e) {
            log("ConfirmCartServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("ConfirmCartServlet_SQLException " + e.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
