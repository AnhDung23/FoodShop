/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.listener;

import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class UpdateQuantityServletListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        sre.getServletRequest().removeAttribute("ERR_UPDATE");
        sre.getServletRequest().removeAttribute("ROW_ERR");
        sre.getServletRequest().removeAttribute("txtItem");
        sre.getServletRequest().removeAttribute("txtQuantity");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        String button = request.getParameter("btAction");

        if (button == null) {
            // Get all request
            Enumeration params = request.getParameterNames();
            boolean next = true;

            while (params.hasMoreElements() && next) {
                String paramName = (String) params.nextElement();

                // Check request get name start with name btAction_
                if (paramName.startsWith("btAction_")) {
                    next = false;
                    button = request.getParameter(paramName);
                    // Cut name request 
                    StringTokenizer stk = new StringTokenizer(paramName, "_");
                    stk.nextToken(); 
                    String suffix = stk.nextToken();

                    String itemName = request.getParameter("txtItem_" + suffix);
                    String quantity = request.getParameter("txtQuantity_" + suffix);

                    request.setAttribute("txtItem", itemName);

                    int amountUpdate = 0;
                    if (!quantity.matches("\\d{1,9}")) {
                        request.setAttribute("ERR_UPDATE", "Quantity is integer from 1 to 9 numerals and greater then 0");
                        request.setAttribute("ROW_ERR", suffix);
                    } else {
                        amountUpdate = Integer.parseInt(quantity);
                        if (amountUpdate == 0) {
                            request.setAttribute("ERR_UPDATE", "Quantity is integer from 1 to 9 numerals and greater then 0");
                            request.setAttribute("ROW_ERR", suffix);
                        } else {
                            request.setAttribute("txtQuantity", amountUpdate);
                        }
                    }
                }

            }
        }
    }
}
