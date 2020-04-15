/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.shoppingcart;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Admin
 */
public class ShoppingCartDTO implements Serializable{
    private String username;
    private String nameItem;
    private Timestamp buyDate;
    private int amount;
    private int price;
    private int total;
    private int idBill;

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(String username, String nameItem, Timestamp buyDate, int amount, int price, int total, int idBill) {
        this.username = username;
        this.nameItem = nameItem;
        this.buyDate = buyDate;
        this.amount = amount;
        this.price = price;
        this.total = total;
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
     * @return the nameItem
     */
    public String getNameItem() {
        return nameItem;
    }

    /**
     * @param nameItem the nameItem to set
     */
    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
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
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
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
    
    // check syntax of date yyyy-MM-dd
    public boolean checkDateFormat(String date) throws ParseException{
        boolean check = false;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);        
        try {
            df.parse(date);
            check = true;
        } catch (ParseException e) {
            check = false;
        }
        return check;
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
}
