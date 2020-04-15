/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.bills;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class BillDTO implements Serializable{
    private int idBill;
    private String username;
    private Timestamp buyDate;
    private int numOfItems;
    private int total;

    public BillDTO() {
    }

    public BillDTO(int idBill, String username, Timestamp buyDate, int numOfItems, int total) {
        this.idBill = idBill;
        this.username = username;
        this.buyDate = buyDate;
        this.numOfItems = numOfItems;
        this.total = total;
    }

    /**
     * @return the idBill
     */
    public int getIdBill() {
        return idBill;
    }

    /**
     * @param idBill the idBill to set
     */
    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the buyDate
     */
    public Timestamp getBuyDate() {
        return buyDate;
    }

    /**
     * @param buyDate the buyDate to set
     */
    public void setBuyDate(Timestamp buyDate) {
        this.buyDate = buyDate;
    }

    /**
     * @return the numOfItems
     */
    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     * @param numOfItems the numOfItems to set
     */
    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
