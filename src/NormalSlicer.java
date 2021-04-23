import bagel.Image;
import bagel.util.Point;
import java.util.List;

/**
 * Set all the Regular initial attribute for spawning and load the Image of it
 *
 */

public class NormalSlicer extends Slicer {
    private static final int NORMAL_SLICER_HEALTH = 1;
    private static final double NORMAL_SLICER_SPEED = 2.0;
    private static final int NORMAL_SLICER_PENALTY = 1;
    private static final int NORMAL_SLICER_REWARD = 2;
    private static final String NORMAL_SLICER_IMAGE = "res/images/slicer.png";

    // Set values
    private void RegularSlicerInfo(){
        setSlicerPenalty(NORMAL_SLICER_PENALTY);
        setSlicerReward(NORMAL_SLICER_REWARD);
        setSlicerHealth(NORMAL_SLICER_HEALTH);
        setSpeed(NORMAL_SLICER_SPEED);
        setImageLocation(NORMAL_SLICER_IMAGE);
        setRectangle(new Image(getImageLocation()).getBoundingBoxAt(getCurrentPosition()));
    }

    /**
     * Constructor for creating Regular slicer and move on the map
     * @param polyLine The polyline that the slicer must traverse
     */

    public NormalSlicer(List<Point> polyLine) {
        super(polyLine);
        RegularSlicerInfo();
    }

    /**
     * Constructor for creating Regular slicer on the parent slicer dead place and
     * make them can move
     * @param parent The parent Slicer that can spawn new slicer(child)
     */
    public NormalSlicer(Slicer parent){
        super(parent);
        RegularSlicerInfo();
    }

}
