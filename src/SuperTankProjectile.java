import bagel.Image;
import bagel.util.Point;

/**
 * Set the SuperTankProjectile attributes that inherit from the Projectile
 */
public class SuperTankProjectile extends Projectile {
    private static final String imagPath = "res/images/supertank_projectile.png";
    private static final int damage = 3;


    /**
     * Set the SuperTankProjectile target slicer and tank position on map and its damage
     * @param target the target slicer
     * @param position the tank position
     */
    public SuperTankProjectile(Slicer target, Point position) {
        super(target, position);
        setImage(new Image(imagPath));
        setDamage(damage);
    }
}
