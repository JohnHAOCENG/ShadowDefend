import bagel.Image;
import bagel.util.Point;

/**
 * Set the TankProjectile attributes that inherit from the Projectile
 */
public class TankProjectile extends Projectile{
    private static final String imagPath = "res/images/tank_projectile.png";
    private static final int damage = 1;

    /**
     * Set the TankProjectile target slicer and tank position on map and its damage
     * @param target the target slicer
     * @param position the tank position
     */
    public TankProjectile(Slicer target, Point position) {
        super(target, position);
        setImage(new Image(imagPath));
        setDamage(damage);
    }
}
