/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.items;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ItemsError implements Serializable {

    private String imageIsNull;
    private String nameLengthErr;
    private String nameExistedErr;
    private String descriptionLengthErr;
    private String priceFormatErr;
    private String amountFormatErr;
    private String imageFormatErr;

    public ItemsError() {
    }

    public ItemsError(String imageIsNull, String nameLengthErr, String nameExistedErr, String descriptionLengthErr, String priceFormatErr, String amountFormatErr, String imageFormatErr) {
        this.imageIsNull = imageIsNull;
        this.nameLengthErr = nameLengthErr;
        this.nameExistedErr = nameExistedErr;
        this.descriptionLengthErr = descriptionLengthErr;
        this.priceFormatErr = priceFormatErr;
        this.amountFormatErr = amountFormatErr;
        this.imageFormatErr = imageFormatErr;
    }

    

    /**
     * @return the imageIsNull
     */
    public String getImageIsNull() {
        return imageIsNull;
    }

    /**
     * @param imageIsNull the imageIsNull to set
     */
    public void setImageIsNull(String imageIsNull) {
        this.imageIsNull = imageIsNull;
    }

    /**
     * @return the nameLengthErr
     */
    public String getNameLengthErr() {
        return nameLengthErr;
    }

    /**
     * @param nameLengthErr the nameLengthErr to set
     */
    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    /**
     * @return the descriptionLengthErr
     */
    public String getDescriptionLengthErr() {
        return descriptionLengthErr;
    }

    /**
     * @param descriptionLengthErr the descriptionLengthErr to set
     */
    public void setDescriptionLengthErr(String descriptionLengthErr) {
        this.descriptionLengthErr = descriptionLengthErr;
    }

    /**
     * @return the priceFormatErr
     */
    public String getPriceFormatErr() {
        return priceFormatErr;
    }

    /**
     * @param priceFormatErr the priceFormatErr to set
     */
    public void setPriceFormatErr(String priceFormatErr) {
        this.priceFormatErr = priceFormatErr;
    }

    /**
     * @return the amountFormatErr
     */
    public String getAmountFormatErr() {
        return amountFormatErr;
    }

    /**
     * @param amountFormatErr the amountFormatErr to set
     */
    public void setAmountFormatErr(String amountFormatErr) {
        this.amountFormatErr = amountFormatErr;
    }

    /**
     * @return the nameExistedErr
     */
    public String getNameExistedErr() {
        return nameExistedErr;
    }

    /**
     * @param nameExistedErr the nameExistedErr to set
     */
    public void setNameExistedErr(String nameExistedErr) {
        this.nameExistedErr = nameExistedErr;
    }

    /**
     * @return the imageFormatErr
     */
    public String getImageFormatErr() {
        return imageFormatErr;
    }

    /**
     * @param imageFormatErr the imageFormatErr to set
     */
    public void setImageFormatErr(String imageFormatErr) {
        this.imageFormatErr = imageFormatErr;
    }
     
    public boolean checkFormatPrice(String price) {
        return price.matches("\\d{1,9}");
    }

    public boolean checkAmountFormat(String amount) {
        return amount.matches("\\d{1,9}");
    }

}
