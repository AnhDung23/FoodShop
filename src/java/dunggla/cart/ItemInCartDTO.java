/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.cart;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ItemInCartDTO implements Serializable{
    private String nameItem;
    private int price;
    private int quantity;
    private int amountOfStock;

    public ItemInCartDTO() {
    }

    public ItemInCartDTO(String nameItem, int price, int quantity, int amountOfStock) {
        this.nameItem = nameItem;
        this.price = price;
        this.quantity = quantity;
        this.amountOfStock = amountOfStock;
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
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the amountOfStock
     */
    public int getAmountOfStock() {
        return amountOfStock;
    }

    /**
     * @param amountOfStock the amountOfStock to set
     */
    public void setAmountOfStock(int amountOfStock) {
        this.amountOfStock = amountOfStock;
    }

}
