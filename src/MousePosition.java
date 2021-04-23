import bagel.Input;

import java.util.ArrayList;
import bagel.map.TiledMap;

/**
 * Test the mouse position is valid or not
 */

public class MousePosition {

    // Correction of the valid range on panel
    private static final int UPPER_PANEL_CORRECTION=75;
    private static final int STATUS_PANEL_CORRECTION=45;

    // The unValid mouse position
    private boolean polylineNotBlocked;
    private boolean UpperPanelNotBlocked;
    private boolean StatusPanelNotBlocked;
    private boolean TowerNotBlocked;

    /**
     * Constructor for MousePosition ,to test the valid position
     * @param input
     * @param towers
     * @param map
     * @param upperPanel
     * @param statusPanel
     */
    public MousePosition(Input input, ArrayList<Tower> towers, TiledMap map, UpperPanel upperPanel,
                         StatusPanel statusPanel) {
        // Not allowed to be placed on the polyline
        this.polylineNotBlocked = !(map.getPropertyBoolean((int) input.getMousePosition().x,
                (int) input.getMousePosition().y, "blocked", false));

        // Not allowed to place on the upper panel
        this.UpperPanelNotBlocked = !(upperPanel.getBUY_PANEL_Y() + UPPER_PANEL_CORRECTION
                > input.getMousePosition().y);

        // Not allowed to be placed on the Status panel
        this.StatusPanelNotBlocked = !(statusPanel.getY_Position() - STATUS_PANEL_CORRECTION
                < input.getMousePosition().y);

        // Not allowed to be placed tower where there are already exists tower
        this.TowerNotBlocked = true;
        for (Tower tower : towers) {
            if (tower.getRectangle().intersects(input.getMousePosition()) == true) {
                this.TowerNotBlocked = false;
                break;
            }
        }
    }


    /**
     * The position is valid only it meets all requirements
     * @return the boolean value whether it is valid or not
     */
    public boolean isValid(){
        if (UpperPanelNotBlocked && polylineNotBlocked && StatusPanelNotBlocked && TowerNotBlocked) {
            return true;
        } else {
            return false;
        }
    }
}
