import bagel.Image;
import bagel.util.Point;

/**
 * The super tank Towers inherit from Tower and set it specific attributes
 */

public class SuperTank extends Tower{
    private double CoolDown = 0.5;
    private int Radius = 150;
    private Image image= new Image("res/images/supertank.png");

    /**
     * Constructor of the super tank to set values and record the location to rendered at map
     * @param position the render location on map
     */
    public SuperTank(Point position) {
        super(position);
        setImage(image);
        setCoolDown(CoolDown);
        setRadius(Radius);
        setRectangle(getImage().getBoundingBoxAt(position));
    }
}
