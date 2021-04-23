/**
 * Set the delayEvent attributes
 */

public class DelayEvent extends Event {
    private int delayTime;
    private static final int FPS = 60;
    private static final int CONVERT_TO_SECONDS = 1000;

    /**
     * Constructor for DelayEvent and set the rank of the wave and read and set the delayTime
     * @param waveNum extract the wave num information on the csv file
     * @param delayTime extract the delay time information on the csv file
     */
    public DelayEvent(int waveNum, int delayTime) {
        super(waveNum);
        this.delayTime=delayTime;
    }

    /**
     * Calculating the time spent to delay
     */
    public void start(){
        setBeginTime(ShadowDefend.getFrameCount());
        // Record the time need to delay the event
        int spentTime= (delayTime*FPS)/(CONVERT_TO_SECONDS);
        setEndTime(getBeginTime()+spentTime);
        setStarted(true);
    }


}
