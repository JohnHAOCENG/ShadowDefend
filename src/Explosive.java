import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.util.ArrayList;

/**
 * Creating Explosive object for airplane to drop
 */
public class Explosive {
    private static final int EXPLOSIVE_DAMAGE = 500;
    private static final int DAMAGE_RADIUS = 200;
    private static final int WAIT_SECONDS = 2;
    private static final int FPS = 60;
    private static final int APEX_CHILD_NUM = 4;
    private static final String FILE_LOCATION = "res/images/explosive.png";
    private Image image;
    private Point location;
    private int damage;
    private int damageRadius;
    private int waitSeconds;
    private Rectangle rectangle;
    private ArrayList<Slicer> slicers;
    private Slicer slicer;
    private int timeCount;

    /**
     * Constructor for Airplane to set the initial attributes for the explosive
     * @param location
     */

    public Explosive(Point location) {
        timeCount = 0;
        this.slicer =null;
        this.slicers = ShadowDefend.getSlicers();
        damage = EXPLOSIVE_DAMAGE;
        damageRadius = DAMAGE_RADIUS;
        waitSeconds = WAIT_SECONDS;
        this.location=location;
        image = new Image(FILE_LOCATION);
        rectangle = getImage().getBoundingBoxAt(location);
    }


    /**
     * Test if the slicer is still live and in the range of explosive,
     * if so cause the damage to them and update it
     */
    public void update() {
        // Updating the waiting time for drop the bomb when after two seconds, apply the damage
        timeCount++;
        // Set a temporary Slicer arraylist to adding new child slicers when their parent slicer dead
        ArrayList<Slicer> spawnSlicers = new ArrayList<Slicer>();
        // When time count become two seconds, it is time to explosive and apply damage
        if(timeCount == waitSeconds*FPS) {
            // Go through the whole array list
            for (int i = 0; i < slicers.size(); i++) {
                this.slicer = slicers.get(i);
                // If slicer is still live and in range, put damage on
                if (slicer.onTheMap() && inExplosiveRange(slicer)) {
                    slicer.setSlicerHealth(slicer.getSlicerHealth() - damage);
                    // If the slicer is destroyed update the player money
                    if (slicer.getSlicerHealth() <= 0) {
                        slicer.setDestroyed(true);
                        // Updating the player money
                        int playerCurrentMoney = ShadowDefend.getPlayer().getMoney();
                        int slicerDeathReward = slicer.getSlicerReward();
                        ShadowDefend.getPlayer().setMoney(playerCurrentMoney + slicerDeathReward);
                        // Also updating the spawn child slicers
                        addingChildSlicer(spawnSlicers);
                    }
                }
            }
            // adding all the temporary slices into slicers arraylist
            slicers.addAll(spawnSlicers);
        }
    }

    /**
     * Updating the spawn child slicers
     */
    public void addingChildSlicer(ArrayList<Slicer> spawnSlicers){
        if (slicer instanceof SuperSlicer) {
            spawnSlicers.add(new NormalSlicer(slicer));
            spawnSlicers.add(new NormalSlicer(slicer));
        } else if (slicer instanceof MegaSlicer) {
            spawnSlicers.add(new SuperSlicer(slicer));
            spawnSlicers.add(new SuperSlicer(slicer));
        } else if (slicer instanceof ApexSlicer) {
            for(int i=0; i< APEX_CHILD_NUM; i++){
                spawnSlicers.add(new MegaSlicer(slicer));
            }
        }
    }

    /**
     * Check the choose slicer whether in the range of explosive
     * @param slicer
     * @return
     */
    public boolean inExplosiveRange(Slicer slicer){
        // Assume the attack range is a square
        double xRange = Math.abs(slicer.getCurrentPosition().x - this.getLocation().x);
        double yRange = Math.abs(slicer.getCurrentPosition().y - this.getLocation().y);
        if(xRange <=getDamageRadius() && yRange <= getDamageRadius()){
            return true;
        }
        return false;
    }

    /**
     * Draw the explosive on the map
     */
    public void render(Explosive explosive,ArrayList<Explosive> explosives) {
        // Record the time of explosive begin, it will only last for two seconds(120 frames)
        if(timeCount < waitSeconds*FPS){
            getImage().draw(location.x, location.y);
        }else {
            // If exceeds 2 seconds, remove it from the arraylist
            explosives.remove(explosive);
        }
    }

    /**
     * Getter for image
     * @return
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
     * Getter for location
     * @return
     */

    public Point getLocation() {
        return location;
    }

    /**
     * Getter for damage radius
     * @return
     */

    public int getDamageRadius() {
        return damageRadius;
    }
}
