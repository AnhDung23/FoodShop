/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.category;

import dunggla.utils.DBUtilies;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class CategoryDAO implements Serializable {

    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    // Close connection with SQL Server
    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    // Get all category (ID-name)
    public Map<Integer, String> getListCategory() throws SQLException, NamingException{
        con = DBUtilies.makeConnection();
        Map<Integer, String> listCate = null;
        try {
            if (con != null) {
                String sql = "Select categoryID, nameCategory "
                        + "From categories";
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) {   
                    int cateID = rs.getInt("categoryID");
                    String name = rs.getString("nameCategory");
                    if (listCate == null) {
                        listCate = new HashMap<>();
                    }
                    listCate.put(cateID, name);
                }
            }
        } finally {
            closeConnection();
        }
        return listCate;
    }
    
    // Get id category
    public int getCateID(String cateName) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        int cateID = 0;
        try {
            if (con != null) {
                String sql = "Select categoryID from Categories "
                        + "Where nameCategory = ?";
                stmt =con.prepareStatement(sql);
                stmt.setString(1, cateName);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    cateID = rs.getInt("categoryID");
                }
            }
        } finally {
            closeConnection();
        }
        return cateID;
    }
}
