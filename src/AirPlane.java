import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Vector2;
import bagel.util.Point;
import java.util.ArrayList;

/**
 * Creating airplane to drop explosive
 */
public class AirPlane {
    private static final int FPS =60;
    private static final int UPPER_BOND = 3;
    private static final int LOWER_BOND = 0;
    private static final int AIR_PLANE_SPEED = 3;
    private static final int INITIAL_DROP = 0;
    private static final double HORIZONTAL_CORRECTION= 1.56;
    private static final double VERTICAL_CORRECTION= 3.15;
    private Image image;
    private Point position;
    private double speed;
    private Vector2 velocity;
    private static final int LEFT = 0;
    private static final int TOP = 0;
    private double rotation;
    private int lastDrop;
    private int dropTime;

    /**
     * Set the attributes for air plane, and determine the changing direction depend on the time of using airplane
     * @param placePosition
     */
    public AirPlane(Point placePosition){
        image = new Image("res/images/airsupport.png");
        lastDrop = INITIAL_DROP;
        // Random the dropTime of explosive between 0 to 3
        dropTime = (int)(0+Math.random()*(UPPER_BOND-LOWER_BOND+1))*FPS;
        speed=AIR_PLANE_SPEED;
        PlaceAirPlane(placePosition);

    }

    /**
     * Placing the airplane with different direction
     * @param placePosition place at position
     */

    public void PlaceAirPlane(Point placePosition){
        // If the direction this time is horizontal, change the direction to vertical for next time
        if(ShadowDefend.isMoveHorizontal() == true){
            position = new Point(LEFT, placePosition.y);
            velocity = new Vector2(1.0,0.0);
            // rotate the airplane to horizontal with appropriate degree
            rotation=HORIZONTAL_CORRECTION;
            ShadowDefend.setMoveHorizontal(false);
        } else {
            position =new Point(placePosition.x, TOP);
            velocity= new Vector2(0.0,1.0);
            // Rotate the airplane to vertical with appropriate degree
            rotation=VERTICAL_CORRECTION;
            ShadowDefend.setMoveHorizontal(true);
        }

    }

    /**
     * Updating the airplane location and using the location to deduce the explosive drop point
     * @param explosives
     */
    public void update(ArrayList<Explosive> explosives){
        // Updating the position of the air plane
        this.position = this.position.asVector().add(velocity.mul(ShadowDefend.getTimescale()*speed)).asPoint();
        // Getting the timeCount from frame count and using it compare with the last drop time
        // If it greater the drop time, drop it and change the lastDrop to the current timeCount
        int timeCount = ShadowDefend.getFrameCount();
        if((timeCount - lastDrop) >= dropTime){
            explosives.add(new Explosive(position));
            lastDrop = timeCount;
            // Pick a random number between 0 to 3 for drop time
            dropTime = (int)(0+Math.random()*(UPPER_BOND-LOWER_BOND+1))*FPS;
        }
    }

    /**
     * Render the Airplane on the map with the appropriate rotation
     */
    public void render(){
        getImage().draw(position.x, position.y,new DrawOptions().setRotation(rotation));
    }


    /**
     * Getter for airplane image
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter for airplane Image
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }



}
