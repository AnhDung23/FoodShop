/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.registration;

import dunggla.utils.DBUtilies;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class RegistrationDAO implements Serializable{
    
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    // Close connection with SQL Server
    private void closeConnection() throws SQLException{
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
    
    // Check login user
    public String checkLogin(String username, String password) 
        throws SQLException, NamingException{
        con = DBUtilies.makeConnection();
        String role = "failed";
        try {
            if (con != null) {
                String sql = "Select role "
                        + "From Registration "
                        + "Where username = ? and password = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    role = rs.getString("role");
                }
            }
        } finally {
            closeConnection();
        }
        return role;
    }
    
    // Get name user
    public String getName(String username, String password) throws SQLException, NamingException{
        con = DBUtilies.makeConnection();
        String name = "";
        try {
            if (con != null) {
                String sql = "Select name "
                        + "From Registration "
                        + "Where username = ? and password = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    name = rs.getString("name");
                }
            }
        } finally {
            closeConnection();
        }
        return name;
    }
}
