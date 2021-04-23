
public class Event {
    private boolean started;
    private int waveNUm;
    private int beginTime;
    private int endTime;

    /**
     * Constructor for Event
     * @param waveNum
     */
    public Event(int waveNum){
        started = false;
        this.waveNUm =waveNum;
    }

    /**
     * For sub class to inherit
     */
    public void start(){}

    /**
     * Get the wave num
     * @return waveNumber
     */
    public int getWaveNUm() {
        return waveNUm;
    }

    /**
     * Set the wave num
     * @param waveNUm
     */
    public void setWaveNUm(int waveNUm) {
        this.waveNUm = waveNUm;
    }

    /**
     * Get the start time
     * @return startTime
     */

    public int getBeginTime() {
        return beginTime;
    }

    /**
     * Set the startTime
     * @param beginTime
     */

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * Get the endTime
     * @return endTime
     */

    public int getEndTime() {
        return endTime;
    }

    /**
     * Set the endTime
     * @param endTime
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Get started
     * @return started
     */

    public boolean isStarted() {
        return started;
    }

    /**
     * Set started
     * @param started
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

}
