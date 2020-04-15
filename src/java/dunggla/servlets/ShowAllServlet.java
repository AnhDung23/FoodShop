/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.items.ItemsDAO;
import dunggla.items.ItemsDTO;
import dunggla.paging.PagingTable;
import java.io.IOException;
import java.sql.SQLException;
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
public class ShowAllServlet extends HttpServlet {

    private final String ERROR_PAGE = "pageNotFound.html";
    private final String MEMBER_PAGE = "homePage.jsp";
    private final String ADMIN_PAGE = "admin.jsp";
    private final int SIZE_OF_PAGE = 3;

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

        String url = MEMBER_PAGE;
        String numPage = request.getParameter("txtPage");
        int numOfRows = 0;
        int numOfPages = 0;
        int firstIndex = 0;
        String status = "";
        try {

            if (numPage == null) {
                numPage = "1";
            }

            ItemsDAO dao = new ItemsDAO();

            HttpSession session = request.getSession();
            //Role admin or member to set status item
            String role = (String) session.getAttribute("ROLE");
            if (role == null || role.equals("member")) {
                status = "Active";
                numOfRows = dao.getNumOfRowsShowAllByMember(status);
                url = MEMBER_PAGE;
            } else if (role.equals("admin")) {
                url = ADMIN_PAGE;
                numOfRows = dao.getNumOfRowsShowAllByAdmin();
            }

            PagingTable paging = new PagingTable();
            paging.setNumberOfRows(numOfRows);
            numOfPages = paging.findNumOfPages(SIZE_OF_PAGE);
            firstIndex = Integer.parseInt(numPage) * SIZE_OF_PAGE - SIZE_OF_PAGE;

            if (status.equals("Active")) {
                dao.showAllByMember(status, firstIndex, SIZE_OF_PAGE);
            } else {
                dao.showAllByAdmin(firstIndex, SIZE_OF_PAGE);
            }

            List<ItemsDTO> list = dao.getList();
            request.setAttribute("LIST", list);

            session.setAttribute("NUM_OF_PAGES", numOfPages);
            request.setAttribute("SIZE_OF_PAGE", SIZE_OF_PAGE);
        } catch (NumberFormatException e) {
            log("ShowAllServlet_NumberFormatException " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("ShowAllServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("ShowAllServlet_SQLException " + e.getMessage());
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
