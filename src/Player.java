/**
 * create the player attribute for game
 */
public class Player {
     private static final int INITIAL_MONEY=500;
     private static final int INITIAL_LIVES=100;
     private int money;
     private int lives;
     private static Player instance;

     // Set the initial money and lives;
     private Player(){
         money=INITIAL_MONEY;
         lives=INITIAL_LIVES;
     }

    /**
     * use the singleton pattern to make sure there are only on player instance
     * @return the unique instance of player
     */

    public static Player getInstance(){
         if(instance==null){
             instance=new Player();
         }
         return instance;
     }

    /**
     * Money getters
     * @return money
     */

    public int getMoney() {
        return money;
    }

    /**
     * Money setters
     * @param money money
     */

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Lives getters
     * @return live
     */
    public int getLives() {
        return lives;
    }

    /**
     * lives setters
     * @param lives lives
     */

    public void setLives(int lives) {
        this.lives = lives;
    }
}
