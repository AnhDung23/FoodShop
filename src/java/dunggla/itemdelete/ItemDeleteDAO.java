/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.itemdelete;

import dunggla.utils.DBUtilies;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class ItemDeleteDAO implements Serializable{
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    // Close connection with SQL Server
    public void closeConnection() throws SQLException{
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
    
    // Save delete item date
    public boolean recordDeleteDate(String username, String nameItem, Timestamp date)
        throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Insert ItemDelete(username,nameItem,deleteDate) "
                        + "Values(?,?,?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, nameItem);
                stmt.setTimestamp(3, date);
                check =  stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
}
