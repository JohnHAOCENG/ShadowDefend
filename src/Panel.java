import bagel.Image;

/**
 *  the parent class of Buy Panel and Status Panel
 */

public abstract class Panel {

    private Image image;

    /**
     * get the image of Panel
     * @return panel image
     */
    public Image getImage() {
        return image;
    }

    /**
     * load the image of panel
     * @param image  image for loading
     */

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Constructor for for getting
     * @param imagePath set the path of image
     */
    public Panel(String imagePath){
        this.image= new Image(imagePath);
    }
}
