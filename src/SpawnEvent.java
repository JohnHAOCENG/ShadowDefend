/**
 * Set the spawnEvent attributes
 */
public class SpawnEvent extends Event{
    private int numToSpawn;
    private String typeToSpawn;
    private int spawnDelayTime;
    private int spawnCount;
    private static final int FPS = 60;
    private static final int CONVERT_TO_SECONDS = 1000;
    private static final int INITIAL_COUNT = 0;
    private static final int FIRST_SLICER = 1;

    /**
     * Constructor for read the csv files and enter the information of the spawn event
     * @param waveNum extract the wave num information on the csv file
     * @param numToSpawn extract the num to spawn information on the csv file
     * @param typeToSpawn extract the wave type to spawn on the csv file
     * @param spawnDelayTime extract the spawn delay time information on the csv file
     */

    public SpawnEvent(int waveNum, int numToSpawn, String typeToSpawn, int spawnDelayTime) {
        super(waveNum);
        this.numToSpawn = numToSpawn;
        this.typeToSpawn = typeToSpawn;
        this.spawnDelayTime = spawnDelayTime;
        spawnCount = INITIAL_COUNT;
    }

    /**
     * Calculating the time spent to spawn the slicers
     */

    public void start(){
        setBeginTime(ShadowDefend.getFrameCount());
        // Record the delay time need to make a new slicer
        int spentTime= (numToSpawn-FIRST_SLICER)*(spawnDelayTime *FPS/CONVERT_TO_SECONDS);
        setEndTime(getBeginTime()+spentTime);
        setStarted(true);
    }

    /**
     * Get the numToSpawn
     * @return numToSpawn
     */

    public int getNumToSpawn() {
        return numToSpawn;
    }

    /**
     * Get the TypeToSpawn
     * @return typeToSpawn
     */
    public String getTypeToSpawn() {
        return typeToSpawn;
    }

    /**
     * Get the SpawnDelayTime
     * @return spawnDelayTime
     */
    public int getSpawnDelayTime() {
        return spawnDelayTime;
    }

    /**
     * Get the SpawnCount
     * @return spawnCount
     */

    public int getSpawnCount() {
        return spawnCount;
    }

    /**
     * Set the SpawnCount
     * @param spawnCount
     */
    public void setSpawnCount(int spawnCount) {
        this.spawnCount = spawnCount;
    }

}
