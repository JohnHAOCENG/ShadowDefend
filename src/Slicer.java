
import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import java.util.List;

/**
 * The parent class of all slicers
 */
public class Slicer  {

    private final List<Point> polyline;
    // There are only two ways slicers will disappear in the map, reach the end or being destroyed
    private boolean reachTheEnd;
    private boolean destroyed;

    // Set the slicer initial position and destination and use them to calculate slicer moving velocity
    private Point currentPosition;
    private Point targetPosition;
    private Vector2 velocity;;
    private double rotate;

    // the attribute for different slicers to inherit
    private int slicerHealth;
    private int slicerPenalty;
    private int slicerReward;
    private int index;
    private int targetIndex;
    private double speed;
    private String imageLocation;
    private Rectangle rectangle;

    /**
     * Constructor for creating different Slicers
     *
     * @param polyline The polyline that the slicer must traverse (must have at least 1 point)
     */
    public Slicer(List<Point> polyline) {
        this.reachTheEnd =false;
        this.destroyed=false;
        this.index =0;
        this.targetIndex = index+1;
        this.polyline=polyline;
        this.currentPosition =polyline.get(index);
        this.targetPosition =polyline.get(targetIndex);
        this.rotate = calculateTheRotation(currentPosition.asVector(), targetPosition.asVector());
        this.velocity= targetPosition.asVector().sub(currentPosition.asVector()).normalised();
    }

    /**
     * Constructor for creating the parent of the Slicers
     *
     * @param parent  the parent type of the current slicer
     */

    public Slicer(Slicer parent) {
        this.reachTheEnd = parent.reachTheEnd;
        this.velocity = parent.velocity;
        this.rotate = parent.rotate;
        this.index = parent.index;
        this.targetIndex = parent.targetIndex;
        this.polyline = parent.polyline;
        this.currentPosition = parent.currentPosition;
        this.targetPosition = parent.targetPosition;
        this.destroyed = false;
    }

    /**
     * Updates the current state of the slicer. The slicer moves towards its next target point in
     * the polyline at its specified movement rate.
     */

    public void update() {
        double distanceTravel = targetPosition.distanceTo(currentPosition);
        int currentTimeScale = ShadowDefend.getTimescale();
        // If not reach the end
        if(!reachTheEnd){
            // Test location arrive to the destination or not
            if(distanceTravel < currentTimeScale * speed){
                index++;
                // Move to next path in the polyline if possible
                if(index <polyline.size()){
                    moveToNextPath();
                }else {
                    /* When this single slicer reach the end point, reach the end change to true
                     * and player get the penalty on their lives
                     */
                    reachTheEndPoint();
                }
            }
            // Keep going with the specific speed
            this.currentPosition =this.currentPosition.asVector().
                    add(velocity.mul(currentTimeScale*speed)).asPoint();
            Image image = new Image(getImageLocation());
            this.rectangle =image.getBoundingBoxAt(currentPosition);
        }
    }

    /**
     * Move to next path in the polyline
     */
    public void moveToNextPath(){
        targetPosition =polyline.get(index);
        velocity= targetPosition.asVector().sub(currentPosition.asVector()).normalised();
        this.rotate = calculateTheRotation(currentPosition.asVector(), targetPosition.asVector());
    }

    /**
     * Reach the end point, update the penalty
     */

    public void reachTheEndPoint(){
        reachTheEnd =true;
        Player player = ShadowDefend.getPlayer();
        int restLives = ShadowDefend.getPlayer().getLives()- slicerPenalty;
        player.setLives(restLives);
    }


    /**
     * Test weather the slicers is destroyed or reach the end or not
     * @return
     */

    public boolean onTheMap(){
        if(destroyed || reachTheEnd){
            return false;
        }
        return true;
    }

    /**
     * Render the different slicer on the map
     */

    public void render(){
        Image image = new Image(getImageLocation());
        image.draw(currentPosition.x, currentPosition.y,new DrawOptions().setRotation(rotate));
    }

    /**
     * Calculate the angle of velocity for slicer rotation and draw it on the map
     * @param pos the destination of slicer
     * @param des the current pos of slicer
     * @return the rotation value
     */
    public double calculateTheRotation(Vector2 pos, Vector2 des){
        double rotation = Math.atan2(des.y-pos.y,des.x-pos.x);
        return rotation;
    }


    /**
     * Getter ImageLocation
     * @return the image location in res
     */
    public String getImageLocation() {
        return imageLocation;
    }

    /**
     * Setter imageLocation
     * @param imageLocation
     */
    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    /**
     * Getter for health
     * @return
     */
    public int getSlicerHealth() {
        return slicerHealth;
    }

    /**
     * Setter for health
     * @param slicerHealth
     */

    public void setSlicerHealth(int slicerHealth) {
        this.slicerHealth = slicerHealth;
    }

    /**
     * Getter for speed
     * @return
     */

    public double getSpeed() {
        return speed;
    }

    /**
     * Getter for speed
     * @param speed the slicer speed
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Getter for penalty
     * @return penalty for slicer reach the end
     */

    public int getSlicerPenalty() {
        return slicerPenalty;
    }

    /**
     * Setter for penalty
     * @param slicerPenalty
     */

    public void setSlicerPenalty(int slicerPenalty) {
        this.slicerPenalty = slicerPenalty;
    }

    /**
     * Getter for Reward
     * @return the reward for destroyed
     */

    public int getSlicerReward() {
        return slicerReward;
    }

    /**
     * Setter for Reward
     * @param slicerReward
     */

    public void setSlicerReward(int slicerReward) {
        this.slicerReward = slicerReward;
    }

    /**
     * Getter for reach
     * @return get the slicer reach the end or not
     */
    public boolean isReachTheEnd() {
        return reachTheEnd;
    }

    /**
     * Getter for position
     * @return the position
     */

    public Point getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Getter for destroyed
     * @return the destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Setter for destroyed
     * @param destroyed
     */

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Getter for rectangle
     * @return the rectangle
     */

    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Setter for rectangle
     * @param rectangle
     */

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

}
