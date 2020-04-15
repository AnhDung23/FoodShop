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
public class SuggessPopularServlet extends HttpServlet {

    private final String ERROR_PAGE = "pageNotFound.html";
    private final String SUGGESS_PAGE = "homePage.jsp";
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

        String url = SUGGESS_PAGE;
        String numpage = request.getParameter("txtPage");
        int numOfPages = 0;
        int numOfRows = 0;
        int firstIndex = 0;
        try {

            if (numpage == null) {
                numpage = "1";
            }
            
            // compute numof page to paging
            ItemsDAO dao = new ItemsDAO();
            numOfRows = dao.getNumOfRowsShowAllByMember("Active");
            PagingTable paging = new PagingTable();
            paging.setNumberOfRows(numOfRows);
            numOfPages = paging.findNumOfPages(SIZE_OF_PAGE);
            firstIndex = Integer.parseInt(numpage) * SIZE_OF_PAGE - SIZE_OF_PAGE;
            
            dao.suggessPopular("Active", firstIndex, SIZE_OF_PAGE);
            
            List<ItemsDTO> list = dao.getList();
            request.setAttribute("LIST", list);
            request.setAttribute("FLAG_POPULAR", "flagPopular");
            
            HttpSession session = request.getSession();
            session.setAttribute("NUM_OF_PAGES", numOfPages);
            request.setAttribute("SIZE_OF_PAGE", SIZE_OF_PAGE);
            
        } catch (NamingException e) {
            log("SuggessPopularServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("SuggessPopularServlet_SQLException " + e.getMessage());
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
