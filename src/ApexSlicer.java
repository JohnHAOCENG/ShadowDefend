import bagel.Image;
import bagel.util.Point;
import java.util.List;


/**
 * Set all the Apex initial attribute for spawning and load the Image of it
 *
 */


public class ApexSlicer extends Slicer {
    private static final int APEX_SLICER_HEALTH = 25;
    private static final double APEX_SLICER_SPEED = 0.75;
    private static final int APEX_SLICER_PENALTY = 16;
    private static final int APEX_SLICER_REWARD = 150;
    private static final String APEX_SLICER_IMAGE = "res/images/apexslicer.png";

    // Set values
    private void ApexSlicerInfo(){
        setSlicerHealth(APEX_SLICER_HEALTH);
        setSpeed(APEX_SLICER_SPEED);
        setSlicerPenalty(APEX_SLICER_PENALTY);
        setSlicerReward(APEX_SLICER_REWARD);
        setImageLocation(APEX_SLICER_IMAGE);
        setRectangle(new Image(getImageLocation()).getBoundingBoxAt(getCurrentPosition()));
    }

    /**
     * Constructor for creating Apex slicer and move on the map
     * @param polyLine The polyline that the slicer must traverse
     */
    public ApexSlicer(List<Point> polyLine) {
        super(polyLine);
        ApexSlicerInfo();
    }

}
