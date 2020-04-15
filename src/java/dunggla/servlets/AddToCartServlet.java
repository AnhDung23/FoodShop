/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.cart.CartObj;
import dunggla.cart.ItemInCartDTO;
import java.io.IOException;
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
public class AddToCartServlet extends HttpServlet {

    private final String SHOPPING_PAGE = "homePage.jsp";

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
        String url = SHOPPING_PAGE;
        try {
            String nameItem = request.getParameter("txtName");
            String quantity = request.getParameter("txtQuantity");
            int price = Integer.parseInt(request.getParameter("txtPrice"));
            int amountInStock = Integer.parseInt(request.getParameter("txtAmountInStock"));

            HttpSession session = request.getSession();

            CartObj cart = (CartObj) session.getAttribute("CART");

            if (cart == null) {
                cart = new CartObj();
            }

            // Error amount fiel format
            if (!quantity.matches("\\d{1,9}")) {
                request.setAttribute("ERROR_QUANTITY", "Quantity is integer from 1 to 9 numerals");
            } else {
                int quantityAdd = Integer.parseInt(quantity);
                if (quantityAdd == 0) {
                    request.setAttribute("ERROR_QUANTITY", "Quantity is integer greater than 0");
                } else {
                    ItemInCartDTO dto = new ItemInCartDTO(nameItem, price, quantityAdd, amountInStock);
                    cart.addItemsToCart(dto);
                    session.setAttribute("CART", cart);
                    request.setAttribute("MSG_ADD_SUCCESS", "Added " + quantity + " " + nameItem + "(s) to your cart");
                }
            }
            request.setAttribute("ITEM_ADD", nameItem);

            // Rewriting search page
            String searchValue = request.getParameter("txtSearch");
            String rangeMoney = request.getParameter("txtRangeMoney");
            String cate = request.getParameter("txtCateSearch");
            String pageNum = request.getParameter("txtPage");
            if (pageNum.trim().equals("")) {
                pageNum = "1";
            }

            boolean suggessPage = false;
            if (request.getParameter("txtFlagSuggessPage") == null) {
                suggessPage = false;
            } else if (request.getParameter("txtFlagSuggessPage").equals("suggessPage")) {
                suggessPage = true;
            }

            if (!suggessPage) {
                if (searchValue.equals("") && rangeMoney.equals("") && cate.equals("")) {
                    url = "ShowAllServlet?&txtPage=" + pageNum;
                } else {
                    url = "SearchServlet?&txtSearch=" + searchValue + "&txtRangeMoney=" + rangeMoney + "&txtCateSearch=" + cate + "&txtPage=" + pageNum;
                }
            } else {
                url = "SuggessPopularServlet?&txtPage=" + pageNum;
            }

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
