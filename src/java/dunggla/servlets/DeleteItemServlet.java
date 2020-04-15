/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.itemdelete.ItemDeleteDAO;
import dunggla.items.ItemsDAO;
import dunggla.items.ItemsDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class DeleteItemServlet extends HttpServlet {

    private final String DETAIL_PAGE = "detailItem.jsp";
    private final String DELETE_PAGE = "detailItem.jsp";

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
        String url = DETAIL_PAGE;
        boolean check = false;
        try {
            String status = request.getParameter("txtStatus");

            if (status.equals("Inactive")) {
                // send request ci=onfirm delete
                request.setAttribute("CONFIRM", "Click confirm button to Delete");

                String checkConfirm = request.getParameter("cbxConfirm");
                // Case confirm
                if (checkConfirm != null && checkConfirm.equals("confirm")) {
                    String name = request.getParameter("txtNameItem");
                    int statusID = 2;
                    ItemsDAO dao = new ItemsDAO();
                    check = dao.deleteItems(name, statusID);
                    if (check) {
                        long millis = System.currentTimeMillis();
                        Timestamp date = new Timestamp(millis);

                        ItemsDTO dto = dao.getItemByName(name);
                        HttpSession session = request.getSession();
                        session.setAttribute("DTO", dto);
                        String username = (String) session.getAttribute("USERNAME");

                        ItemDeleteDAO recordDao = new ItemDeleteDAO();
                        boolean checkRecord = recordDao.recordDeleteDate(username, name, date);

                        if (checkRecord) {
                            url = DELETE_PAGE;
                        }
                    }
                }
            }

        } catch (NamingException e) {
            log("DeleteItemServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("DeleteItemServlet_SQLException " + e.getMessage());
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
