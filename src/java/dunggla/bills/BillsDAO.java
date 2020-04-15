/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.bills;

import dunggla.utils.DBUtilies;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Admin
 */
public class BillsDAO implements Serializable{
    
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
    
    // Get max id of bill to add new bill (id new bill = max + 1)
    public int getMaxIDBill() throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        int maxId = 0;
        try {
            if (con != null) {
                String sql = "Select MAX(idBill) as maxId "
                        + "From Bills";
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    maxId = rs.getInt("maxId");
                }
            }
        } finally {
            closeConnection();
        }
        return maxId;
    }
    
    // Check out bill to database
    public boolean checkOutBill(int idBill, String username, Timestamp date, int numOfItems, int total) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Insert Bills(idBill,username,buyDate,numOfItems,total) "
                        + "Values (?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, idBill);
                stmt.setString(2, username);
                stmt.setTimestamp(3, date);
                stmt.setInt(4, numOfItems);
                stmt.setInt(5, total);
                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    
    private List<BillDTO> list;

    public List<BillDTO> getList() {
        return list;
    }
    
    // Show history shopping of user
    public void showAllBills(String user) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select idBill, username, buyDate, numOfItems, total "
                        + "From Bills "
                        + "Where username = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, user);
                rs = stmt.executeQuery();
                while (rs.next()) {                    
                    int idBill = rs.getInt("idBill");
                    String username = rs.getString("username");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int numOfItems = rs.getInt("numOfItems");
                    int total = rs.getInt("total");
                    
                    BillDTO dto = new BillDTO(idBill, username, buyDate, numOfItems, total);
                    
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    
                    this.list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
    
    // Get list bill search by name item
    public void searchNameItemInBill(String valueSearch, String user) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select idBill, username, buyDate, numOfItems, total "
                        + "From Bills "
                        + "Where username = ? and idBill in "
                        + "(Select idBill From ShoppingCart Where nameItem like ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, user);
                stmt.setString(2, "%" + valueSearch + "%");
                rs = stmt.executeQuery();
                while (rs.next()) {                    
                    int idBill = rs.getInt("idBill");
                    String username = rs.getString("username");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int numOfItems = rs.getInt("numOfItems");
                    int total = rs.getInt("total");
                    
                    BillDTO dto = new BillDTO(idBill, username, buyDate, numOfItems, total);
                    
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    
                    this.list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
    
    // Get list bill search by date
    public void searchByDateBill(Timestamp dateFrom, Timestamp dateTo, String user) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select idBill, username, buyDate, numOfItems, total "
                        + "From Bills "
                        + "Where buyDate >= ? and buyDate <= ? and username = ?";
                stmt = con.prepareStatement(sql);
                stmt.setTimestamp(1, dateFrom);
                stmt.setTimestamp(2, dateTo);
                stmt.setString(3, user);
                rs = stmt.executeQuery();
                while (rs.next()) {                    
                    int idBill = rs.getInt("idBill");
                    String username = rs.getString("username");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int numOfItems = rs.getInt("numOfItems");
                    int total = rs.getInt("total");
                    
                    BillDTO dto = new BillDTO(idBill, username, buyDate, numOfItems, total);
                    
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    
                    this.list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
    
    // Get list bill search by date and name item
    public void searchByNameItemAndDateBill(String nameItemSearch, Timestamp dateFrom, Timestamp dateTo, String user) 
            throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select idBill, username, buyDate, numOfItems, total "
                        + "From Bills "
                        + "Where username = ? and buyDate >= ? and buyDate <= ? and idBill in "
                        + "(Select idBill From ShoppingCart Where nameItem like ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, user);
                stmt.setTimestamp(2, dateFrom);
                stmt.setTimestamp(3, dateTo);
                stmt.setString(4, "%" + nameItemSearch + "%");
                rs = stmt.executeQuery();
                while (rs.next()) {                    
                    int idBill = rs.getInt("idBill");
                    String username = rs.getString("username");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int numOfItems = rs.getInt("numOfItems");
                    int total = rs.getInt("total");
                    
                    BillDTO dto = new BillDTO(idBill, username, buyDate, numOfItems, total);
                    
                    if (this.list == null) {
                        this.list = new ArrayList<>();
                    }
                    
                    this.list.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
    }
}
