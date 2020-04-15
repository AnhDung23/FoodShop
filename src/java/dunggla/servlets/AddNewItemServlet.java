/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.servlets;

import dunggla.category.CategoryDAO;
import dunggla.items.ItemsDAO;
import dunggla.items.ItemsError;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author Admin
 */
public class AddNewItemServlet extends HttpServlet {

    private final String ERROR_PAGE = "addItems.jsp";
    private final String ADD_ITEM_PAGE = "addItems.jsp";

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

        ItemsError error = new ItemsError();
        boolean checkErr = false;

        try {
            // Get request to check this multipart is what we have just process
            boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
            if (!isMultiPart) {

            } else {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                // if this is multipart, get all data and change to list
                try {
                    items = upload.parseRequest(new ServletRequestContext(request));
                } catch (FileUploadException e) {
                    log("AddNewItemServlet_FileUploadException " + e.getMessage());
                }

                Iterator iter = items.iterator();
                // Get para pass to control in form (except file)
                Hashtable params = new Hashtable();
                String filename = null;

                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
                        // Get para pass to control in form (except file)
                        params.put(item.getFieldName(), item.getString());
                    } else {
                        try {
                            // Get name of file, create path and save to image file
                            String itemName = item.getName();
                            filename = itemName.substring(itemName.lastIndexOf("\\") + 1);
                            if (filename.trim().equals("")) {
                                error.setImageIsNull("Image is requied");
                                checkErr = true;
                            } else {
                                if (!filename.contains(".jpg") && !filename.contains(".png")) {
                                    error.setImageFormatErr("Invalid extension");
                                    checkErr = true;
                                }
                            }

                            if (!checkErr) {
                                String realPath = getServletContext().getRealPath("/");

                                int index = realPath.indexOf("\\build");
                                String buildPath = realPath.substring(0, index) + realPath.substring(index + 6) + "image\\" + filename;
                                File savedFile = new File(buildPath);
                                item.write(savedFile);
                            }

                        } catch (Exception e) {
                            log("AddNewItemServlet_Exception " + e.getMessage());
                        }
                    }
                }

                // Get para
                String nameItem = (String) params.get("txtNameItem");
                String imgName = filename;
                String description = (String) params.get("txtDescription");
                String priceTxt = (String) params.get("txtPrice");
                String amountTxt = (String) params.get("txtAmount");
                String category = (String) params.get("txtCate");
                int status = 1;

                // Get current date
                long millis = System.currentTimeMillis();
                Timestamp createDate = new Timestamp(millis);

                CategoryDAO daoCate = new CategoryDAO();
                int categoryID = daoCate.getCateID(category);

                if (imgName.trim().equals("")) {
                    error.setImageIsNull("Image is requied");
                    checkErr = true;
                } else {
                    if (!imgName.contains(".jpg") && !imgName.contains(".png")) {
                        error.setImageFormatErr("Invalid extension");
                        checkErr = true;
                    }
                }
                if (nameItem.trim().length() < 2 || nameItem.trim().length() > 50) {
                    error.setNameLengthErr("Name length must be from 2 to 50 chars");
                    checkErr = true;
                }
                if (description.trim().length() < 2 || nameItem.trim().length() > 50) {
                    error.setDescriptionLengthErr("Description length must be from 2 to 50 chars");
                    checkErr = true;
                }
                if (!error.checkFormatPrice(priceTxt.trim())) {
                    error.setPriceFormatErr("Price is integer from 1 to 999.999.999");
                    checkErr = true;
                }
                if (!error.checkAmountFormat(amountTxt.trim())) {
                    error.setAmountFormatErr("Amount is integer from 1 to 999.999.999");
                    checkErr = true;
                }

                if (checkErr) {
                    request.setAttribute("ERROR", error);
                } else {
                    int price = Integer.parseInt(priceTxt);
                    int amount = Integer.parseInt(amountTxt);
                    ItemsDAO dao = new ItemsDAO();
                    boolean check = dao.addItem(nameItem, imgName, description, price, createDate, amount, categoryID, status);
                    if (check) {
                        request.setAttribute("MESSAGE", "Create item successful");
                        url = ADD_ITEM_PAGE;
                    }
                }
            }
        } catch (NamingException e) {
            log("AddNewItemServlet_NamingException " + e.getMessage());
        } catch (SQLException e) {
            log("AddNewItemServlet_SQLException " + e.getMessage());
            if (e.getMessage().contains("duplicate")) {
                error.setNameExistedErr("Product is existed");
                request.setAttribute("ERROR", error);
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
