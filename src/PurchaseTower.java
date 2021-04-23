import bagel.*;

/**
 * The four PurchaseItem basic information
 */

public class PurchaseTower {
    private int price;
    private Image image;


    /**
     * Constructor for setting values
     * @param price
     * @param image
     */
    public PurchaseTower(int price, Image image) {
        this.price = price;
        this.image = image;
    }

    /**
     * Get the image of PurchaseItem
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * Load the image of PurchaseItem
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Get the price of PurchaseItem
     * @return
     */
    public int getPrice() {
        return price;
    }

}
