/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dunggla.paging;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class PagingTable implements Serializable {

    private int numberOfPages;
    private int numberOfRows;

    public PagingTable() {
    }

    public PagingTable(int numberOfPages, int numberOfRows) {
        this.numberOfPages = numberOfPages;
        this.numberOfRows = numberOfRows;
    }

    // Compute num of page for paging
    public int findNumOfPages(int sizeOfPage) {
        this.setNumberOfPages(this.numberOfRows / sizeOfPage);
        int mod = this.numberOfRows % sizeOfPage;
        if (mod == 0) {
            return this.getNumberOfPages();
        } else {
            return this.getNumberOfPages() + 1;
        }
    }

    /**
     * @return the numberOfPages
     */
    public int getNumberOfPages() {
        return numberOfPages;
    }

    /**
     * @param numberOfPages the numberOfPages to set
     */
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    /**
     * @return the numberOfRows
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * @param numberOfRows the numberOfRows to set
     */
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
}
