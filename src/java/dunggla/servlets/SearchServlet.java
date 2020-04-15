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
public class SearchServlet extends HttpServlet {

    private final String ERROR_PAGE = "pageNotFound.html";
    private final String SEARCH_PAGE_ADMIN = "admin.jsp";
    private final String SEARCH_PAGE_MEMBER = "homePage.jsp";
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

        String url = SEARCH_PAGE_MEMBER;
        String numPage = request.getParameter("txtPage");
        int numOfPages = 0;
        int numOfRows = 0;
        int firstIndex = 0;

        int minRangeMoney = 0;
        int maxRangeMoney = 0;
        String status = "";
        try {

            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("ROLE");
            if (role == null || role.equals("member")) {
                status = "Active";
                url = SEARCH_PAGE_MEMBER;
            } else if (role.equals("admin")) {
                url = SEARCH_PAGE_ADMIN;
            }

            if (numPage == null) {
                numPage = "1";
            }

            String searchValue = request.getParameter("txtSearch");
            String rangeMoney = request.getParameter("txtRangeMoney");
            String cateSearch = request.getParameter("txtCateSearch");
            if (rangeMoney == null) {
                rangeMoney = "All Price";
            }
            if (cateSearch == null) {
                cateSearch = "All";
            }

            ItemsDAO dao = new ItemsDAO();

            // Get Range price user want to search min - max
            switch (rangeMoney) {
                case "Smaller 100.000 VND":
                    minRangeMoney = 0;
                    maxRangeMoney = 99999;
                    break;
                case "From 100.000 VND to 500.000 VND":
                    minRangeMoney = 100000;
                    maxRangeMoney = 500000;
                    break;
                case "From 500.000 VND to 1.000.000 VND":
                    minRangeMoney = 500000;
                    maxRangeMoney = 1000000;
                    break;
                case "Greater 1.000.000 VND":
                    minRangeMoney = 1000001;
                    maxRangeMoney = dao.getMaxPrice();
                    if (maxRangeMoney < minRangeMoney) {
                        maxRangeMoney = minRangeMoney;
                    }
                    break;
                default:
                    minRangeMoney = 0;
                    maxRangeMoney = dao.getMaxPrice();
                    break;
            }

            if (searchValue == null) {
                searchValue = "";
            }

            PagingTable paging = new PagingTable();

            searchValue = searchValue.trim();
            // no search cate
            if (cateSearch.equals("All")) {
                
                if (status.equals("Active")) {
                    numOfRows = dao.getNumOfRowsSearchNoCateByMember(minRangeMoney, maxRangeMoney, searchValue, status);
                } else {
                    // no search status
                    numOfRows = dao.getNumOfRowsSearchNoCateByAdmin(minRangeMoney, maxRangeMoney, searchValue);
                }
            } else {
                if (status.equals("Active")) {
                    numOfRows = dao.getNumOfRowsSearchByMember(minRangeMoney, maxRangeMoney, searchValue, cateSearch, status);
                } else {
                    // no search status
                    numOfRows = dao.getNumOfRowsSearchByAdmin(minRangeMoney, maxRangeMoney, searchValue, cateSearch);
                }
            }

            paging.setNumberOfRows(numOfRows);
            numOfPages = paging.findNumOfPages(SIZE_OF_PAGE);
            firstIndex = Integer.parseInt(numPage) * SIZE_OF_PAGE - SIZE_OF_PAGE;

            // no search cate
            if (cateSearch.equals("All")) {
                if (status.equals("Active")) {
                    dao.searchNoCateByMember(minRangeMoney, maxRangeMoney, searchValue, firstIndex, SIZE_OF_PAGE, status);
                } else {
                    // no search status
                    dao.searchNoCateByAdmin(minRangeMoney, maxRangeMoney, searchValue, firstIndex, SIZE_OF_PAGE);
                }
            } else {
                if (status.equals("Active")) {
                    dao.searchFullByMember(minRangeMoney, maxRangeMoney, searchValue, cateSearch, firstIndex, SIZE_OF_PAGE, status);
                } else {
                    // no search status
                    dao.searchFullByAdmin(minRangeMoney, maxRangeMoney, searchValue, cateSearch, firstIndex, SIZE_OF_PAGE);
                }
            }

            List<ItemsDTO> list = dao.getList();
            request.setAttribute("LIST", list);
            session.setAttribute("NUM_OF_PAGES", numOfPages);
            request.setAttribute("SIZE_OF_PAGE", SIZE_OF_PAGE);

            if (status.equals("Active")) {
                if (list != null && list.size() > 0) {
                    for (ItemsDTO dto : list) {
                        int numOfSearch = dao.getNumOfSearch(dto.getNameItem()) + 1;
                        boolean check = dao.updateNumOfSearchPopular(dto.getNameItem(), numOfSearch);
                    }
                }
            }
        } catch (NumberFormatException e) {
            log("SearchServlet_NumberFormatException " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("SearchServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("SearchServlet_SQLException " + e.getMessage());
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
