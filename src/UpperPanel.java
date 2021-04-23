import bagel.*;
import bagel.util.Colour;

/**
 * Set the attribute of UpperPanel and draw them on the map
 */


public class UpperPanel extends Panel {
    // The position attribute
    private static final int TOP_OFFSET = 64;
    private static final int BELOW = 50;
    private static final int LEFT_OFFSET=64;
    private static final int RIGHT_OFFSET = 200;
    private static final int ABOVE_CENTER=10;
    private static final int GAP = 120;
    // The resources for loading
    private static final String BUY_PANEL = "res/images/buypanel.png";
    private static final String TANK_IMAGE = "res/images/tank.png";
    private static final String SUPER_TANK_IMAGE ="res/images/supertank.png";
    private static final String AIR_SUPPORT_IMAGE ="res/images/airsupport.png";
    public static final String FONT = "res/fonts/DejaVusans-Bold.ttf";
    // The red Color and Green Color
    private static final DrawOptions GREEN_COLOR = new DrawOptions().setBlendColour(Colour.GREEN);
    private static final DrawOptions RED_COLOR = new DrawOptions().setBlendColour(Colour.RED);
    // The price of the buy items
    private static final int TANK_PRICE = 250;
    private static final int SUPER_TANK_PRICE =600;
    private static final int AIR_SUPPORT_PRICE =500;
    // The sizes of the items and key binds and money
    private static final int BUY_FONT_SIZE=20;
    private static final int KEY_FONT_SIZE=14;
    private static final int MONEY_FONT_SIZE=48;
    // Create the instance of three PurchaseItem
    private PurchaseTower tank = new PurchaseTower(TANK_PRICE, new Image(TANK_IMAGE));
    private PurchaseTower superTank = new PurchaseTower(SUPER_TANK_PRICE, new Image(SUPER_TANK_IMAGE));
    private PurchaseTower plane = new PurchaseTower(AIR_SUPPORT_PRICE,new Image(AIR_SUPPORT_IMAGE));
    // The Key binds string information
    private static final String INFO_1 = "Key binds";
    private static final String INFO_2 = "S - Start Wave";
    private static final String INFO_3 = "L - Increase Timescale";
    private static final String INFO_4 = "K - Decrease Timescale";
    // The X and Y location of Panel and purchaseItem in the map
    private final double BUY_PANEL_Y= getImage().getHeight()/2-ABOVE_CENTER;
    private final double TANK_X = LEFT_OFFSET;
    private final double SUPER_TANK_X =LEFT_OFFSET+GAP;
    private final double AIR_SUPPORT_X =LEFT_OFFSET+GAP*2;

    /**
     * Constructor for UpperPanel
     */
    public UpperPanel() {
        super(BUY_PANEL);
    }

    /**
     * draw the purchase item and key binds and money part on the map
     * @param xCoordinate
     * @param yCoordinate
     * @param money
     */
    public void render(double xCoordinate, double yCoordinate, int money){
        // Set the the xCoordinate and yCoordinate location of the UpPanel
        getImage().drawFromTopLeft(xCoordinate,yCoordinate);
        final double INFO_X =450;
        final double INFO_Y1 =20;
        final double INFO_Y2 =50;
        final double INFO_Y3 =65;
        final double INFO_Y4 =80;
        final double MONEY_X = ShadowDefend.getWIDTH()-RIGHT_OFFSET;
        final double MONEY_Y = TOP_OFFSET;
        final String MONEY_INFO = String.format("$%d",money);

        // Draw the tank and superTank and plane
        drawBuyItem(0+TANK_X, 0+BUY_PANEL_Y,tank,money);
        drawBuyItem(0+SUPER_TANK_X, 0+BUY_PANEL_Y,superTank,money);
        drawBuyItem(0+AIR_SUPPORT_X,0+BUY_PANEL_Y,plane,money);

        // Draw the information of the key binds
        Font keyFont = new Font(FONT,KEY_FONT_SIZE);
        keyFont.drawString(INFO_1,0+INFO_X,0+INFO_Y1);
        keyFont.drawString(INFO_2,0+INFO_X,0+INFO_Y2);
        keyFont.drawString(INFO_3,0+INFO_X,0+INFO_Y3);
        keyFont.drawString(INFO_4,0+INFO_X,0+INFO_Y4);

        // Draw the information of the money
        Font moneyFont = new Font(FONT,MONEY_FONT_SIZE);
        moneyFont.drawString(MONEY_INFO,0+MONEY_X,0+MONEY_Y);
    }


    /**
     * Draw the purchase item
     * @param x
     * @param y
     * @param towers
     * @param money
     */
    public void drawBuyItem(double x, double y, PurchaseTower towers, int money){
        // Draw tower
        towers.getImage().draw(x,y);
        Font towerFont = new Font(FONT,BUY_FONT_SIZE);
        String price = String.format("$%d",towers.getPrice());
        double xPosition = x-towerFont.getWidth(price)/2;
        double yPosition = y+BELOW;
        // Draw Cost of each purchaseItem and deduce the color by current money
        if(towers.getPrice()<= money){
            towerFont.drawString(price,0+xPosition,0+yPosition, GREEN_COLOR);
        }else{
            towerFont.drawString(price,0+xPosition,0+yPosition, RED_COLOR);
        }
    }

    /**
     * BUY_PANEL_Y getter
     * @return Y location of Buy panel
     */
    public double getBUY_PANEL_Y() {
        return BUY_PANEL_Y;
    }

    /**
     * TANK_X getter
     * @return X location of Tank
     */
    public double getTANK_X() {
        return TANK_X;
    }

    /**
     * SUPER_TANK_X getter
     * @return X location of Super Tank
     */
    public double getSUPER_TANK_X() {
        return SUPER_TANK_X;
    }
    /**
     * AIR_X getter
     * @return X location of plane
     */
    public double getAIR_SUPPORT_X() {
        return AIR_SUPPORT_X;
    }
    /**
     * Tank getter
     * @return Tank
     */
    public PurchaseTower getTank() {
        return tank;
    }

    /**
     * SuperTank getter
     * @return superTank
     */
    public PurchaseTower getSuperTank() {
        return superTank;
    }

    /**
     * Plane getter
     * @return plane
     */
    public PurchaseTower getPlane() {
        return plane;
    }


}
