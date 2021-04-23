import bagel.Image;
import bagel.util.Point;
import java.util.List;

/**
 * Set all the Mega initial attribute for spawning and load the Image of it
 */


public class MegaSlicer extends Slicer{
    private static final int MEGA_SLICER_HEALTH = 2;
    private static final double MEGA_SLICER_SPEED = 1.5;
    private static final int MEGA_SLICER_PENALTY = 4;
    private static final int MEGA_SLICER_REWARD = 10;
    private static final String MEGA_SLICER_IMAGE = "res/images/megaslicer.png";


    // Set values
    private void MegaSlicerInfo(){
        setSlicerPenalty(MEGA_SLICER_PENALTY);
        setSlicerReward(MEGA_SLICER_REWARD);
        setSlicerHealth(MEGA_SLICER_HEALTH);
        setSpeed(MEGA_SLICER_SPEED);
        setImageLocation(MEGA_SLICER_IMAGE);
        setRectangle(new Image(getImageLocation()).getBoundingBoxAt(getCurrentPosition()));
    }

    /**
     * Constructor for creating mega slicer and move on the map
     * @param polyLine The polyline that the slicer must traverse
     */
    public MegaSlicer(List<Point> polyLine) {
        super(polyLine);
        MegaSlicerInfo();
    }

    /**
     * Constructor for creating Mega slicer on the parent slicer dead place and
     * make them can move
     * @param parent The parent Slicer that can spawn new slicer(child)
     */

    public MegaSlicer(Slicer parent){
        super(parent);
        MegaSlicerInfo();
    }

}
