/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.shoppingcart;

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
public class ShoppingCartDAO implements Serializable {

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

    // Check out cart to database
    public boolean checkOutCart(String username, String nameItem, Timestamp buyDate, int amount, int price, int total, int idBill)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        boolean check = true;
        try {
            if (con != null) {
                String sql = "Insert ShoppingCart(username, nameItem, buyDate, amount, price, total, idBill) "
                        + "values(?,?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, nameItem);
                stmt.setTimestamp(3, buyDate);
                stmt.setInt(4, amount);
                stmt.setInt(5, price);
                stmt.setInt(6, total);
                stmt.setInt(7, idBill);
                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    private List<ShoppingCartDTO> list;

    public List<ShoppingCartDTO> getList() {
        return list;
    }

    // show bill detail
    public void showAllItemInBill(int idBill) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select username, nameItem, buyDate, amount, price, total, idBill "
                        + "From ShoppingCart "
                        + "Where idBill = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, idBill);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String nameItem = rs.getString("nameItem");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int amount = rs.getInt("amount");
                    int price = rs.getInt("price");
                    int total = rs.getInt("total");
                    int id = rs.getInt("idBill");

                    ShoppingCartDTO dto = new ShoppingCartDTO(username, nameItem, buyDate, amount, price, total, id);

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

    // Get list item bought search by name item
    public void searchNameItemHistory(String valueSearch, String user, int idBill) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select username, nameItem, buyDate, amount, price, total, idBill "
                        + "From ShoppingCart "
                        + "Where nameItem like ? and username = ? and idBill = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + valueSearch + "%");
                stmt.setString(2, user);
                stmt.setInt(3, idBill);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String nameItem = rs.getString("nameItem");
                    Timestamp buyDate = rs.getTimestamp("buyDate");
                    int amount = rs.getInt("amount");
                    int price = rs.getInt("price");
                    int total = rs.getInt("total");
                    int id = rs.getInt("idBill");

                    ShoppingCartDTO dto = new ShoppingCartDTO(username, nameItem, buyDate, amount, price, total, id);
                    
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
