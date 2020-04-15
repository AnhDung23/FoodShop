/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartObj implements Serializable {

    private String username;
    private Map<String, ItemInCartDTO> itemsCart;
    

    public CartObj() {
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
     * @return the itemsCart
     */
    public Map<String, ItemInCartDTO> getItemsCart() {
        return itemsCart;
    }

    // Add item to cart
    public void addItemsToCart(ItemInCartDTO dto) {
        if (this.itemsCart == null) {
            this.itemsCart = new HashMap<>();
        }

        String key = dto.getNameItem();
        
        if (this.itemsCart.containsKey(key)) {
            int oldQuantity = this.itemsCart.get(key).getQuantity();
            int quantityAdd = dto.getQuantity();
            this.itemsCart.get(key).setQuantity(oldQuantity + quantityAdd);
        } else {
            this.itemsCart.put(key, dto);
        }

        
    }

    // Remove item from cart
    public void removeItemsFromCart(String nameItem) {
        if (this.itemsCart == null) {
            return;
        }

        if (this.itemsCart.containsKey(nameItem)) {
            this.itemsCart.remove(nameItem);
            if (this.itemsCart.isEmpty()) {
                this.itemsCart = null;
            }
        }
    }
    
    // Update amount item in cart
    public void updateAmount(String nameItem, int amountUpdate){
        if (this.itemsCart == null) {
            return;
        }
        
        if (this.itemsCart.containsKey(nameItem)) {
            this.itemsCart.get(nameItem).setQuantity(amountUpdate);
        }
    }
   
}
