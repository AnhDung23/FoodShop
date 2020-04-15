/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.itemdelete.ItemDeleteDAO;
import dunggla.items.ItemsDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public class DeleteMultiRowServlet extends HttpServlet {

    private final String ERROR_PAGE = "error.jsp";

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
        boolean check = true;
        boolean checkRecordDelete = true;
        boolean changeStatus = false;

        try {
            String flagConfirm = request.getParameter("txtFlagConfirm");

            // Send request confirm delete
            if (flagConfirm == null) {
                String statusUpdate[];
                statusUpdate = request.getParameterValues("txtStatusUpdate");

                List<String> listDelete = null;
                if (statusUpdate != null) {
                    for (String statusNew : statusUpdate) {
                        int lastIndex = statusNew.lastIndexOf("-");
                        String statusInactive = statusNew.substring(lastIndex + 1);
                        if (statusInactive.equals("Inactive")) {
                            changeStatus = true;
                            String nameItemsDelete = statusNew.substring(0, lastIndex);
                            if (listDelete == null) {
                                listDelete = new ArrayList<>();
                            }
                            listDelete.add(nameItemsDelete);
                        }
                    }
                }
                if (changeStatus) {
                    request.setAttribute("CONFIRM", "check confirm to delete");
                    request.setAttribute("LIST_NAME_DELETE", listDelete);
                }
            } else {
                String confirm = request.getParameter("cbxConfirm");
                // Confirm delete
                if (confirm != null && confirm.equals("confirm")) {
                    String nameDelete = request.getParameter("listNameDelete");
                    int lastIndex = nameDelete.lastIndexOf("]");
                    String stringNames = nameDelete.substring(1, lastIndex);
                    String[] listNameDelete = stringNames.split(",");

                    // Get current date
                    long millis = System.currentTimeMillis();
                    Timestamp dateDelete = new Timestamp(millis);

                    String username = "";
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        username = (String) session.getAttribute("USERNAME");
                    }

                    ItemsDAO dao = new ItemsDAO();
                    ItemDeleteDAO daoRecord = new ItemDeleteDAO();
                    int statusID = 2;
                    // Save date delete
                    for (String name : listNameDelete) {
                        check = dao.deleteItems(name.trim(), statusID);
                        checkRecordDelete = daoRecord.recordDeleteDate(username, name.trim(), dateDelete);
                    }
                }
            }

            if (check && checkRecordDelete) {
                String searchValue = request.getParameter("txtSearch");
                String rangeMoney = request.getParameter("txtRangeMoney");
                String cate = request.getParameter("txtCateSearch");
                String pageNum = request.getParameter("txtPage");
                if (pageNum.trim().equals("")) {
                    pageNum = "1";
                }
                if (searchValue.equals("") && rangeMoney.equals("") && cate.equals("")) {
                    url = "ShowAllServlet?&txtPage=" + pageNum;
                } else {
                    url = "SearchServlet?&txtSearch=" + searchValue + "&txtRangeMoney=" + rangeMoney + "&txtCateSearch=" + cate + "&txtPage=" + pageNum;
                }
            }
        } catch (NamingException e) {
            log("UpdateServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("UpdateServlet_SQLException " + e.getMessage());
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
