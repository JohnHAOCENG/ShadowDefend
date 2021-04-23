import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import java.util.ArrayList;


/**
 * Create the basic tower calls to inherit
 */
public class Tower {
    private static final int FPS =60;
    private static final double RADIAN_CORRECT = Math.PI/2;
    private Point position;
    private int radius;
    private Image image;
    private Rectangle rectangle;
    private double coolDown;
    private Slicer target;
    private double rotate;
    // Counts the frame when tank is placed
    private int counts;
    // Counts the projectile emitted based on the cool down of different tank
    private int emitted;
    private int lastShot;
    private int timeCount;

    public Tower(Point position){
        this.position = position;
        this.target=null;
        this.rotate =0.0;
        counts= 0;
        emitted= 0;
        lastShot = 0;
        timeCount = 0;
    }

    /**
     * Update the slicer target for tower to shoot
     * @param slicers the slicer array for spawned
     * @param projectiles the type of projectile shooted
     */
    public void update(ArrayList<Slicer> slicers, ArrayList<Projectile> projectiles){
        target=null;
        // If there are no target currently, select a target
        if(target==null){
            chooseTargetSlicer(slicers);
        }
        // If we got the target
        if(target!=null){
            shotTheTarget(projectiles);
        }
    }

    /**
     * select the available target that tank can shoot
     * @param slicers the slicers array
     */
    public void chooseTargetSlicer(ArrayList<Slicer> slicers){
        for(Slicer slicer: slicers){
            // The target can be selected is the slicer on the map and in the tank attack radius
            if(slicer.onTheMap() && inAttackRange(slicer)){
                target=slicer;
            }
        }
    }

    /**
     * Test the slicer whether in the tank attack range
     * @param slicer the slicer to be tested
     * @return
     */
    public boolean inAttackRange(Slicer slicer){
        // Assume the attack range is a square
        double xRange = Math.abs(slicer.getCurrentPosition().x - getPosition().x);
        double yRange = Math.abs(slicer.getCurrentPosition().y - getPosition().y);
        if(xRange <= radius && yRange <= radius){
            return true;
        }
        return false;
    }

    /**
     * Shot the target when tower got the target
     * @param projectiles
     */
    public void shotTheTarget(ArrayList<Projectile> projectiles){
        // Rotate the tower by determine the location between the tower and the target slicer
        Vector2 targetPos = target.getCurrentPosition().asVector();
        this.rotate = calculateRotation(targetPos,position.asVector())-RADIAN_CORRECT;
        timeCount = ShadowDefend.getFrameCount()/FPS;
        //  At the first shoot, cool down time is not needed to be used
        firstShot(projectiles);
            /* Creating the projectile to shot by determine the tower type and its cool down time
               and the frameCount with last time shot
             */
        normalShot(projectiles);
        // Unselect the target
        target = null;
    }

    /**
     * it is not needed to determine the first shoot time based on tank cool down
     * @param projectiles
     */
    public void firstShot(ArrayList<Projectile> projectiles){
        if(lastShot == 0){
            if(this instanceof Tank){
                lastShot = timeCount;
                Projectile tankProjectile = new TankProjectile(target, position);
                projectiles.add(tankProjectile);
            }
            if(this instanceof SuperTank){
                lastShot =timeCount;
                Projectile superProjectile = new SuperTankProjectile(target, position);
                projectiles.add(superProjectile);
            }
        }
    }

    /**
     * Creating the projectile to shot by determine the tower type and its cool down time
     * and the timeCount with last shot
     * @param projectiles
     */
    public void normalShot(ArrayList<Projectile> projectiles){
        if(this instanceof Tank && (timeCount - lastShot) >= this.getCoolDown()){
            lastShot = timeCount;
            Projectile tankProjectile = new TankProjectile(target, position);
            projectiles.add(tankProjectile);
            // if it shoots now, record as last time shot
        }
        if(this instanceof SuperTank && (timeCount - lastShot) >= this.getCoolDown()){
            lastShot =timeCount;
            Projectile superProjectile = new SuperTankProjectile(target, position);
            projectiles.add(superProjectile);
        }
    }


    /**
     * render the tower on the map base on it location and its rotation
     */
    public void render(){
        image.draw(position.x, position.y,new DrawOptions().setRotation(rotate));
    }

    /**
     * When selected item, render it is on the mouse position without rotation
     */
    public void staticRender(){
        image.draw(position.x, position.y);
    }



    /**
     * Calculate the rotation of the tower
     * @param dest the target position
     * @param loc the tower position
     * @return
     */
    public double calculateRotation(Vector2 dest, Vector2 loc){
        return Math.atan2(loc.y-dest.y,loc.x-dest.x);
    }


    /**
     * Getter for position
     * @return the tower position
     */

    public Point getPosition() {
        return position;
    }

    /**
     * Setter the Attack Radius for Tank
     * @param radius Tank radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Getter for Image
     * @return Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Setter for Image
     * @param image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Getter for Rectangle
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

    /**
     * Getter for CoolDown
     * @return tank attack cool down
     */
    public double getCoolDown() {
        return coolDown;
    }

    /**
     * Setter for CoolDown
     * @param coolDown
     */
    public void setCoolDown(double coolDown) {
        this.coolDown = coolDown;
    }

    /**
     * Getter for target
     * @return
     */
    public Slicer getTarget() {
        return target;
    }
}
