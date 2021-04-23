import java.util.ArrayList;

/**
 * Set the attribute for Waves
 */
public class Wave {
    private int waveNumber;
    private ArrayList<Event> waveEvents;
    private int currentEvent;
    private int startTime;

    /**
     * Constructor for set the initial values of the waves
     * @param waveNumber extract the wave num information on the csv file
     */
    public Wave(int waveNumber) {
        // Record the wave number
        this.waveNumber = waveNumber;
        // Record the events in this specific number wave
        this.waveEvents = new ArrayList<Event>();
        currentEvent=0;
    }

    /**
     * Record the begin time of the wave
     */
    void start(){
        startTime=ShadowDefend.getFrameCount();
    }

    /**
     * Getter for waveEvents
     * @return the wave events list
     */
    public ArrayList<Event> getWaveEvents() {
        return waveEvents;
    }


}
