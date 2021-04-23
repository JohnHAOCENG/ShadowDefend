import bagel.*;
import bagel.util.Colour;

/**
 *  Create the status panel with font size and picture location
 */

public class   StatusPanel extends Panel {
    // Images String for load
    private static final String STATUS_PANEL = "res/images/statuspanel.png";
    private static final String FONT = "res/fonts/DejaVuSans-Bold.ttf";
    private static final double INITIAL_TIME_SCALE = 1.0;
    private static final int FONT_SIZE=24;
    // Green Font Color
    private static final DrawOptions GREEN_COLOR = new DrawOptions().setBlendColour(Colour.GREEN);
    // Four status Strings
    private static final String WINNER_STRING = "Winner !";
    private static final String PLACING_STRING = "Placing";
    private static final String WAVE_IN_PROGRESS_STRING="Wave in Progress";
    private static final String AWAITING_START_STRING= "Awaiting Start";
    private double coordinateCorrection =5.0;
    private double Y_Position = ShadowDefend.getHEIGHT()- coordinateCorrection;
    private static final double WAVE_NUM_X_POSITION= 5.0;
    private static final double LIVES_X_POSITION = 900.0;
    private static final double STATUS_X_POSITION =430.0;
    private static final double TIME_SCALE_X_POSITION = 190.0;

    /**
     * Constructor for inherit of panel
     */
    public StatusPanel() {
        super(STATUS_PANEL);
    }

    /**
     *  Draw the wave num and time scale and status and Lives on the ShadowDefend
     * @param waveNumber the num of Wave
     * @param timeScale the current timescale
     * @param status enum status
     * @param lives the rest lives
     * @param x
     * @param y
     */

    public void render(int waveNumber, double timeScale, Status status,int lives,double x, double y){
        // The information variable of  wave num & lives
        String waveNumInfo = String.format("Wave: %d", waveNumber);
        String liveInfo = String.format("Lives: %d",lives);
        Font font= new Font(FONT,FONT_SIZE);
        getImage().drawFromTopLeft(x,y);

        // Draw the wave num on the map
        font.drawString(waveNumInfo, 0+WAVE_NUM_X_POSITION,0+Y_Position);

        // Draw time scale with different color by deduce the timescale value
        drawTimeScale(timeScale,font);

        // Draw the status
        drawStatus(status,font);

        // Draw lives
        font.drawString(liveInfo,0+LIVES_X_POSITION,0+Y_Position);

    }

    /**
     * draw the Status on the map
     * @param status
     * @param font
     */
    public void drawStatus(Status status, Font font){
        String statusString=null;
        double statusX_Position=STATUS_X_POSITION;
        // The four status for determine
        switch (status){
            case WINNER:
                statusString=WINNER_STRING;
                break;
            case PLACING:
                statusString=PLACING_STRING;
                break;
            case WAVE_IN_PROGRESS:
                statusString=WAVE_IN_PROGRESS_STRING;
                break;
            case AWAITING_START:
                statusString=AWAITING_START_STRING;
                break;
        }
        String statusInfo ="Status: "+ statusString;
        font.drawString(statusInfo,0+statusX_Position,0+Y_Position);
    }


    /**
     * draw the timeScale on the map
     * @param timeScale
     * @param font
     */
    public void drawTimeScale(double timeScale,Font font){
        String timeScaleInfo = String.format("Time Scale: %.1f",timeScale);
        double timeScaleX_Position=TIME_SCALE_X_POSITION;
        if(timeScale>INITIAL_TIME_SCALE){
            font.drawString(timeScaleInfo,0+timeScaleX_Position,0+Y_Position, GREEN_COLOR);
        }else{
            font.drawString(timeScaleInfo,0+timeScaleX_Position,0+Y_Position);
        }
    }


    /**
     * Getter for the Y position of status pannel
     * @return y position
     */

    public double getY_Position() {
        return Y_Position;
    }
}
