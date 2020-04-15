/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.bills.BillDTO;
import dunggla.bills.BillsDAO;
import dunggla.shoppingcart.ShoppingCartDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
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
public class SearchHistoryServlet extends HttpServlet {

    private final String HISTORY_PAGE = "shoppingHistory.jsp";
    private final String SEARCH_PAGE = "shoppingHistory.jsp";

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
        String url = HISTORY_PAGE;
        try {
            String nameItemSearch = request.getParameter("txtSearchHistory");

            String dayFrom = request.getParameter("txtDDFrom");
            String monthFrom = request.getParameter("txtMMFrom");
            String yearFrom = request.getParameter("txtYYFrom");
            String dayTo = request.getParameter("txtDDTo");
            String monthTo = request.getParameter("txtMMTo");
            String yearTo = request.getParameter("txtYYTo");

            // Format date: yyyy-MM-dd hh:mm:ss.sss
            String dateFrom = yearFrom.trim() + "-" + monthFrom.trim() + "-" + dayFrom.trim() + " " + "00:00:00.000";
            String dateTo = yearTo.trim() + "-" + monthTo.trim() + "-" + dayTo.trim() + " " + "23:59:59.999";

            HttpSession session = request.getSession(false);
            if (session != null) {
                String user = (String) session.getAttribute("USERNAME");

                ShoppingCartDTO dto = new ShoppingCartDTO();
                BillsDAO dao = new BillsDAO();
                List<BillDTO> list = null;

                // Fiels are empty
                if (dayFrom.trim().equals("") && monthFrom.trim().equals("") && yearFrom.trim().equals("") && dayTo.trim().equals("") && monthTo.trim().equals("") && yearTo.trim().equals("")) {
                    if (!nameItemSearch.trim().equals("")) {
                        dao.searchNameItemInBill(nameItemSearch.trim(), user);
                    }
                } else {
                    // check date format
                    if (dto.checkDateFormat(dateFrom) && dto.checkDateFormat(dateTo)) {
                        Timestamp from = Timestamp.valueOf(dateFrom);
                        Timestamp to = Timestamp.valueOf(dateTo);

                        // from date earlier than to date
                        if (from.compareTo(to) < 0) {
                            if (!nameItemSearch.trim().equals("")) {
                                dao.searchByNameItemAndDateBill(nameItemSearch.trim(), from, to, user);
                            } else {
                                dao.searchByDateBill(from, to, user);
                            }
                        } else if (from.compareTo(to) > 0) {
                            request.setAttribute("ERR_DATE", "The start date must be earlier than the end date");
                        }
                    } else {
                        // invalid date format
                        request.setAttribute("ERR_DATE", "Invalid date");
                    }
                }

                list = dao.getList();
                request.setAttribute("LIST", list);
                url = SEARCH_PAGE;
            }

        } catch (ParseException e) {
            log("ParseException " + e.getMessage());
        } catch (NamingException e) {
            log("SearchHistoryServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("SearchHistoryServlet_SQLException " + e.getMessage());
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
