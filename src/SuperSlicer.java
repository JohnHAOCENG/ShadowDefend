import bagel.Image;
import bagel.util.Point;
import java.util.List;


/**
 * Set all the Super initial attribute for spawning and load the Image of it
 *
 */


public class SuperSlicer extends Slicer{
    private static final int SUPER_SLICER_HEALTH = 1;
    private static final double SUPER_SLICER_SPEED = 1.5;
    private static final int SUPER_SLICER_PENALTY = 2;
    private static final int SUPER_SLICER_REWARD = 15;
    private static final String SUPER_SLICER_IMAGE = "res/images/superslicer.png";

    // Set values
    private void SuperSlicerInfo(){
        setSlicerHealth(SUPER_SLICER_HEALTH);
        setSpeed(SUPER_SLICER_SPEED);
        setSlicerPenalty(SUPER_SLICER_PENALTY);
        setSlicerReward(SUPER_SLICER_REWARD);
        setImageLocation(SUPER_SLICER_IMAGE);
        setRectangle(new Image(getImageLocation()).getBoundingBoxAt(getCurrentPosition()));
    }

    /**
     * Constructor for creating Super slicer and move on the map
     * @param polyLine The polyline that the slicer must traverse
     */
    public SuperSlicer(List<Point> polyLine) {
        super(polyLine);
        SuperSlicerInfo();
    }

    /**
     * Constructor for creating Super slicer on the parent slicer dead place and
     * make them can move
     * @param parent The parent Slicer that can spawn new slicer(child)
     */
    public SuperSlicer(Slicer parent){
        super(parent);
        SuperSlicerInfo();
    }


}
