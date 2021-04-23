import bagel.Image;
import bagel.util.Point;

/**
 * The tank Towers inherit from Tower and set it specific attributes
 */

public class Tank extends Tower{
    private double CoolDown = 1.0;
    private int Radius = 100;
    private Image image= new Image("res/images/tank.png");

    /**
     * Constructor of the tank to set values and record the position to rendered at map
     * @param position the render position on map
     */
    public Tank(Point position) {
        super(position);
        setImage(image);
        setCoolDown(CoolDown);
        setRadius(Radius);
        setRectangle(getImage().getBoundingBoxAt(position));
    }
}
