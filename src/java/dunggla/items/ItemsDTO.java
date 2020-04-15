/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.items;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class ItemsDTO implements Serializable{
    private String nameItem;
    private String image;
    private String description;
    private int price;
    private Timestamp createDate;
    private int amount;
    private int categoryID;
    private int statusID;

    public ItemsDTO() {
    }

    public ItemsDTO(String nameItem, String image, String description, int price, Timestamp createDate, int amount, int categoryID, int statusID) {
        this.nameItem = nameItem;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.amount = amount;
        this.categoryID = categoryID;
        this.statusID = statusID;
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
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the statusID
     */
    public int getStatusID() {
        return statusID;
    }

    /**
     * @param statusID the statusID to set
     */
    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
    
    
}
