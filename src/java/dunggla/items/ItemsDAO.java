/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.items;

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
public class ItemsDAO implements Serializable {

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

    // Add new item
    public boolean addItem(String name, String img, String description, int price, Timestamp createDate, int amount, int cateID, int statusID)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Insert Items(nameItem, image, description, price, createDate, amount, categoryID, statusID) "
                        + "Values(?,?,?,?,?,?,?,?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, img);
                stmt.setString(3, description);
                stmt.setInt(4, price);
                stmt.setTimestamp(5, createDate);
                stmt.setInt(6, amount);
                stmt.setInt(7, cateID);
                stmt.setInt(8, statusID);

                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    private List<ItemsDTO> list;

    public List<ItemsDTO> getList() {
        return list;
    }

    // Get num of rows to paging (member show all)
    public int getNumOfRowsShowAllByMember(String status) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items Where amount > 0 and statusID = "
                        + "(Select statusID From Status where statusName = ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, status);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }

    // Get num of rows to paging (admin show all)
    public int getNumOfRowsShowAllByAdmin() throws SQLException, NamingException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items ";
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }

    // Get num of rows to paging (admin search no category)
    public int getNumOfRowsSearchNoCateByAdmin(int minRange, int maxRange, String name) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? ";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }
    
    // Get num of rows to paging (member search no category)
    public int getNumOfRowsSearchNoCateByMember(int minRange, int maxRange, String name, String status) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and amount > 0 and statusID = "
                        + "(Select statusID From Status where statusName = ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, status);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }

    // Get num of rows to paging (admin search all info)
    public int getNumOfRowsSearchByAdmin(int minRange, int maxRange, String name, String cate)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and categoryID = "
                        + "(Select categoryID from Categories Where nameCategory = ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, cate);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }
    
    // Get num of rows to paging (member search all info)
    public int getNumOfRowsSearchByMember(int minRange, int maxRange, String name, String cate, String status)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        int numOfRows = 0;
        try {
            if (con != null) {
                String sql = "Select COUNT(nameItem) as numOfRows "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and amount > 0 and categoryID = "
                        + "(Select categoryID from Categories Where nameCategory = ?) and statusID = "
                        + "(Select statusID From Status where statusName = ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, cate);
                stmt.setString(5, status);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    numOfRows = rs.getInt("numOfRows");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfRows;
    }

    // Get list all item by admin
    public void showAllByAdmin(int firstIndex, int pageSize) throws SQLException, NamingException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, firstIndex);
                stmt.setInt(2, pageSize);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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

    // Get list all item by member
    public void showAllByMember(String status, int firstIndex, int pageSize) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items Where amount > 0 and statusID = "
                        + "(Select statusID From Status where statusName = ?) "
                        + "Order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, status);
                stmt.setInt(2, firstIndex);
                stmt.setInt(3, pageSize);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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

    // Get max price of items
    public int getMaxPrice() throws SQLException, NamingException {
        con = DBUtilies.makeConnection();
        int maxPrice = 0;
        try {
            if (con != null) {
                String sql = "Select MAX(price) as maxPrice "
                        + "From Items";
                stmt = con.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    maxPrice = rs.getInt("maxPrice");
                }
            }
        } finally {
            closeConnection();
        }
        return maxPrice;
    }

    // Get list item search no category by admin
    public void searchNoCateByAdmin(int minRange, int maxRange, String name, int firstIndex, int sizePage)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? "
                        + "order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setInt(4, firstIndex);
                stmt.setInt(5, sizePage);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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
    
    // Get list item search no category by member
    public void searchNoCateByMember(int minRange, int maxRange, String name, int firstIndex, int sizePage, String status)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and amount > 0 and statusID = "
                        + "(Select statusID From Status where statusName = ?) "
                        + "order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, status);
                stmt.setInt(5, firstIndex);
                stmt.setInt(6, sizePage);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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

    // Get list item search all info by admin
    public void searchFullByAdmin(int minRange, int maxRange, String name, String cate, int firstIndex, int sizePage)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and categoryID = "
                        + "(Select categoryID from Categories Where nameCategory = ?) "
                        + "order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, cate);
                stmt.setInt(5, firstIndex);
                stmt.setInt(6, sizePage);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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
    
    // Get list item search all info by member
    public void searchFullByMember(int minRange, int maxRange, String name, String cate, int firstIndex, int sizePage, String status)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Where nameItem like ? and price between ? and ? and amount > 0 and categoryID = "
                        + "(Select categoryID from Categories Where nameCategory = ?) and statusID = "
                        + "(Select statusID From Status where statusName = ?) "
                        + "order by createDate DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, "%" + name + "%");
                stmt.setInt(2, minRange);
                stmt.setInt(3, maxRange);
                stmt.setString(4, cate);
                stmt.setString(5, status);
                stmt.setInt(6, firstIndex);
                stmt.setInt(7, sizePage);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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

    // Delete a item
    public boolean deleteItems(String name, int statusID) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            String sql = "Update Items "
                    + "Set statusID = ? "
                    + "Where nameItem = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, statusID);
            stmt.setString(2, name);

            check = stmt.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    // Get old cate by name item
    public String getOldCateByName(String name) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        String cateName = "";
        try {
            if (con != null) {
                String sql = "Select nameCategory "
                        + "From Categories "
                        + "Where categoryID = "
                        + "(Select categoryID From Items Where nameItem = ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    cateName = rs.getString("nameCategory");
                }
            }
        } finally {
            closeConnection();
        }
        return cateName;
    }

    // update category
    public boolean updateCate(String name, int newCateID) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Update Items "
                        + "Set categoryID = ? "
                        + "Where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, newCateID);
                stmt.setString(2, name);
                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    // Get item by name item
    public ItemsDTO getItemByName(String name) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        ItemsDTO dto = null;
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID "
                        + "From Items "
                        + "Where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, name);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");

                    dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);
                }
            }
        } finally {
            closeConnection();
        }
        return dto;
    }

    // Update item detail
    public boolean updateItemDetail(String name, String img, String description, int price, Timestamp createDate, int amount, int cateID, int statusID)
            throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Update Items "
                        + "Set image = ?, description = ?, price = ?, createDate = ?, amount = ?, categoryID = ?, statusID = ? "
                        + "Where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, img);
                stmt.setString(2, description);
                stmt.setInt(3, price);
                stmt.setTimestamp(4, createDate);
                stmt.setInt(5, amount);
                stmt.setInt(6, cateID);
                stmt.setInt(7, statusID);
                stmt.setString(8, name);

                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    
    // update amount in stock after buy
    public boolean updateQuantityInStock(String nameItem, int quantity) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        boolean check = true;
        try {
            if (con != null) {
                String sql = "Update Items "
                        + "Set amount = ? "
                        + "Where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, quantity);
                stmt.setString(2, nameItem);
                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    
    // suggess item popular
    public void suggessPopular(String status, int firstIndex, int pageSize) throws NamingException, SQLException {
        con = DBUtilies.makeConnection();
        try {
            if (con != null) {
                String sql = "Select nameItem, image, description, price, createDate, amount, categoryID, statusID, numOfSearch "
                        + "From Items Where amount > 0 and statusID = "
                        + "(Select statusID From Status where statusName = ?) "
                        + "Order by numOfSearch DESC "
                        + "offset ? rows "
                        + "fetch next ? rows only";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, status);
                stmt.setInt(2, firstIndex);
                stmt.setInt(3, pageSize);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String nameItem = rs.getString("nameItem");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int price = rs.getInt("price");
                    Timestamp createDate = rs.getTimestamp("createDate");
                    int amount = rs.getInt("amount");
                    int categoryID = rs.getInt("categoryID");
                    int statusID = rs.getInt("statusID");
                    ItemsDTO dto = new ItemsDTO(nameItem, image, description, price, createDate, amount, categoryID, statusID);

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
    
    // Get num of search of a item to check popular
    public int getNumOfSearch(String nameItem) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        int numOfSearch = 0;
        try {
            if (con != null) {
                String sql = "Select numOfSearch "
                        + "From Items "
                        + "Where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, nameItem);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    numOfSearch = rs.getInt("numOfSearch");
                }
            }
        } finally {
            closeConnection();
        }
        return numOfSearch;
    }
    
    // Update number of search to check popular
    public boolean updateNumOfSearchPopular(String nameItem, int numOfSearch) throws NamingException, SQLException{
        con = DBUtilies.makeConnection();
        boolean check = false;
        try {
            if (con != null) {
                String sql = "Update Items set numOfSearch = ? where nameItem = ?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, numOfSearch);
                stmt.setString(2, nameItem);
                
                check = stmt.executeUpdate() > 0;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
}
