import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import java.util.ArrayList;

/**
 * Create the projectile class for tank to shot
 */
public class Projectile {
    private Image image;

    // get the rectangle bounding box to deducing whether hit the slicer
    private Rectangle projectileRectangle;

    // the projectile location
    private Point position;

    // the target slicer
    private Slicer targetSlicer;
    private int speed ;
    private Vector2 velocity;
    private boolean hited;
    private int damage;
    private final static int PROJECTILE_SPEED= 10;
    private static final int APEX_CHILD_NUM = 4;

    /**
     * Constructor for set the attributes of projectile
     * @param targetSlicer
     * @param position
     */
    public Projectile(Slicer targetSlicer, Point position) {
        this.targetSlicer = targetSlicer;
        this.position = position;
        this.speed= PROJECTILE_SPEED;
        // Calculate the normalized velocity for projectile
        Vector2 targetSlicerPosition = targetSlicer.getCurrentPosition().asVector();
        Vector2 projectilePosition = position.asVector();
        this.velocity= targetSlicerPosition.sub(projectilePosition).normalised();
        hited =false;
    }

    /**
     * Update the projectile on the map and test it whether it hit the target slicer
     * and the outcomes of the slicer is destroyed
     * @param slicers the slicers array
     */

    public void update(ArrayList<Slicer> slicers){
        // Updating the projectile position based on it the timescale
        int currentTimescale = ShadowDefend.getTimescale();
        this.position =this.position.asVector().add(velocity.mul(currentTimescale*speed)).asPoint();

        // Get the bounding box of the projectile for determine whether hit the target or not
        this.projectileRectangle =getImage().getBoundingBoxAt(position);

        // Test the target slicer is still on the map or not
        slicerExists(slicers);

    }

    /**
     * Test the slicer is on the map or not
     * @param slicers
     */

    public void slicerExists(ArrayList<Slicer> slicers){
        if(targetSlicer.onTheMap()){
            // If hit the target slicer reduce the slicer health
            if(projectileRectangle.intersects(targetSlicer.getRectangle()) && !hited){
                hited =true;
                // If the slicer is still alive, hit it
                if(targetSlicer.getSlicerHealth() > 0){
                    targetSlicer.setSlicerHealth(targetSlicer.getSlicerHealth()-damage);
                }

                // If the slicer dead, update the player money, and spawn it child slicer
                if(targetSlicer.getSlicerHealth() <= 0) {
                    targetSlicer.setDestroyed(true);
                    int playerCurrentMoney = ShadowDefend.getPlayer().getMoney();
                    int slicerDeathReward = targetSlicer.getSlicerReward();
                    ShadowDefend.getPlayer().setMoney(playerCurrentMoney+slicerDeathReward);
                    addingChildSlicer(slicers);
                }
            }
        }else{
            hited = true;
        }
    }


    /**
     * Updating the spawn child slicers
     * @param slicers
     */
    public void addingChildSlicer(ArrayList<Slicer> slicers){
        if (targetSlicer instanceof SuperSlicer) {
            slicers.add(new NormalSlicer(targetSlicer));
            slicers.add(new NormalSlicer(targetSlicer));
        } else if (targetSlicer instanceof MegaSlicer) {
            slicers.add(new SuperSlicer(targetSlicer));
            slicers.add(new SuperSlicer(targetSlicer));
        } else if (targetSlicer instanceof ApexSlicer) {
            for(int i=0; i<APEX_CHILD_NUM; i++){
                slicers.add(new MegaSlicer(targetSlicer));
            }
        }
    }

    /**
     * Draw the projectile on the map
     */
    public void render(){
        getImage().draw(position.x, position.y);
    }


    /**
     * Getter for the image
     * @return the projectile image
     */

    public Image getImage() {
        return image;
    }

    /**
     * Setter for image
     * @param image
     */

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Setter for Damage of each projectile
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Getter for target
     * @return
     */
    public Slicer getTargetSlicer() {
        return targetSlicer;
    }

    public boolean isHited() {
        return hited;
    }
}
