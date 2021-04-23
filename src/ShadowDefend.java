import bagel.*;
import bagel.map.TiledMap;
import bagel.Input;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * ShadowDefend, a tower defence game.
 */

public class ShadowDefend extends AbstractGame {


    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;

    // Player attribute
    private static Player player;
    private static final int INITIAL_MONEY=500;
    private static final int INITIAL_LIVES=100;

    // Waves csv file information
    private static final String DELAY_EVENT = "delay";
    private static final String APEX_SLICER = "apexslicer";
    private static final String MEGA_SLICER = "megaslicer";
    private static final String SUPER_SLICER = "superslicer";
    private static final String SLICER = "slicer";

    // Levels and waves and images for loading
    private static final String LEVEL1 = "res/levels/1.tmx";
    private static final String LEVEL2 = "res/levels/2.tmx";
    private static final String WAVES = "res/levels/waves.txt";
    private static final String TANK_IMAGE ="res/images/tank.png";
    private static final String SUP_IMAGE = "res/images/supertank.png";
    private static final String AIR_PLANE_IMAGE = "res/images/airsupport.png";
    private static final int INITIAL_LEVEL = 0;
    private static final int INITIAL_FRAME_COUNT = 0;
    private static final int INITIAL_CURRENT_WAVE = 0;
    private static final int WAVE_NUM = 0;
    private static final int EVENT = 1;
    private static final int NUM_TO_SPAWN = 2;
    private static final int TYPE_TO_SPAWN = 3;
    private static final int SPAWN_DELAY = 4;
    private static final int FIRST_WAVE = 1;

    // Selecting different items, 0 means unselected, 1->Tank, 2->SuperTank, 3->AirPlane
    private int selectedItem= 0;
    private static final int UNSELECTED = 0;
    private static final int TANK = 1;
    private static final int SUPER_TANK= 2;
    private static final int AIR_PLANE = 3;

    // Test whether the place position is available to be placed
    private boolean validPositionForSetting;

    // Price of all towers
    private static final int TANK_PRICE = 250;
    private static final int SUPER_TANK_PRICE=600;
    private static final int AIR_PLANE_PRICE = 500;

    // Tower Image
    Image tankImage = new Image(TANK_IMAGE);
    Image supImage = new Image(SUP_IMAGE);
    Image planeImage = new Image(AIR_PLANE_IMAGE);

    // Levels and waves arraylist with their index and the money reward
    private ArrayList<String> levels = new ArrayList<String>(Arrays.asList(LEVEL1,LEVEL2));
    private ArrayList<Wave> waves;
    private int currentLevel;
    private int currentWave;
    private int currentEvent;
    private static TiledMap map;
    private boolean waveStarted;
    public static final int MONEY_REWARD=150;
    public static final int WAVE_REWARD =100;

    // Panel objects
    private Status status;
    private StatusPanel statusPanel;
    private UpperPanel upperPanel;

    /* Timescale is made static because it is a universal property of the game and the specification
     * says everything in the game is affected by this
     */
    private static int timescale;
    private static final int INITIAL_TIMESCALE = 1;
    private static final int MAX_TIMESCALE = 5;
    public static final int SPAWN_FPS =60;
    public static final int CONVERT_SECONDS = 1000;
    private static int frameCount;


    // The arraylist of following classes is for updating the games on the map
    private static ArrayList<Slicer> slicers;
    private ArrayList<Tower> towers;
    private ArrayList<AirPlane> airPlanes;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Explosive> explosives;

    // For deducing the direction of airplane
    private static boolean moveHorizontal = true;

    /**
     * Creates a new instance of the ShadowDefend game
     */

    public ShadowDefend() {
        super(WIDTH, HEIGHT, "ShadowDefend");
        currentLevel=INITIAL_LEVEL;
        frameCount=INITIAL_FRAME_COUNT;
        timescale=INITIAL_TIMESCALE;
        waveStarted = false;
        status = Status.AWAITING_START;
        player = Player.getInstance();

        // Initialize all new class objects in ShadowDefend
        InitializeInstance();

        // Update the fist level map
        loadMapLevel();
    }

    /**
     * Create the instance of other classes instance,
     * so they can be used and update in ShadowDefend
     */

    public void InitializeInstance(){
        slicers = new ArrayList<Slicer>();
        towers = new ArrayList<Tower>();
        airPlanes = new ArrayList<AirPlane>();
        projectiles = new ArrayList<Projectile>();
        explosives = new ArrayList<Explosive>();
        statusPanel = new StatusPanel();
        upperPanel = new UpperPanel();
    }

    /**
     * load the different maps depend on the current level
     */

    public void loadMapLevel(){
        map = new TiledMap(levels.get(currentLevel));
        // Then load the level following waves
        loadWaves();
    }

    /**
     * Read the waves from the csv files in res, then adding the different waves and events
     * to the respond ArrayList
     */

    private void loadWaves(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(WAVES))) {
            ArrayList<Wave> waves = new ArrayList<Wave>();
            ArrayList<Event> events = new ArrayList<Event>();
            String line = null;

            // Using the buffered reader to read the information from the files
            while((line = bufferedReader.readLine())!=null){

                // Separate them by the "," , and stored in an array
                String[] tokens = line.split(",");

                // Read the event part to determine the load type of event
                if(tokens[EVENT].equals(DELAY_EVENT)){
                    loadDelayEvents(tokens,events);
                }else{
                   loadSpawnEvents(tokens,events);
                }
            }

            // Add waves into waves with same waveNum
            AddWaves(events,waves);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Adding the delay events information into the events array
     * @param tokens the array contain all the information of delay events
     * @param events events array for loading event
     */

    public void loadDelayEvents(String[] tokens,ArrayList<Event> events){
        int waveNumber = Integer.parseInt(tokens[WAVE_NUM]);
        int delay = Integer.parseInt(tokens[NUM_TO_SPAWN]);
        events.add(new DelayEvent(waveNumber,delay));
    }

    /**
     * Adding the spawn events information into the events array
     * @param tokens the array contain all the information of spawn events
     * @param events events array for loading event
     */

    public void loadSpawnEvents(String[] tokens,ArrayList<Event> events){
        int waveNumber = Integer.parseInt(tokens[WAVE_NUM]);
        int numToSpawn = Integer.parseInt(tokens[NUM_TO_SPAWN]);
        String typeToSpawn = tokens[TYPE_TO_SPAWN];
        int spawnDelay = Integer.parseInt(tokens[SPAWN_DELAY]);
        events.add(new SpawnEvent(waveNumber,numToSpawn,typeToSpawn,spawnDelay));
    }

    /**
     * After all events are loaded into the events list, then loading the same wave num events into
     * the waves array
     * @param events  events array list
     * @param waves waves array list
     */

    public void AddWaves(ArrayList<Event> events, ArrayList<Wave> waves){
        int index=FIRST_WAVE;

        // Create a new wave object
        Wave wave = new Wave(index);

        // Go through the events arraylist, adding the events to the wave object with same wave num
        // when events wave num changed(next wave), index increase by 1
        for(Event event: events){
            if (event.getWaveNUm() != index) {
                waves.add(wave);
                index ++;
                wave = new Wave(index);
            }
            wave.getWaveEvents().add(event);
        }
        // Adding the wave object into waves arraylist in order
        waves.add(wave);
        this.waves=waves;
        this.currentWave=INITIAL_CURRENT_WAVE;

    }

    /**
     * The entry-point for the game
     *
     * @param args Optional command-line arguments
     */
    public static void main(String[] args) {
        new ShadowDefend().run();
    }


    /**
     * Getter for timescale
     * @return
     */

    public static int getTimescale() {
        return timescale;
    }

    /**
     * Increases the timescale but doesn't above the max timescale
     */

    private void increaseTimescale() {
        if(timescale < MAX_TIMESCALE){
            timescale++;
        }
    }

    /**
     * Decreases the timescale but doesn't go below the base timescale
     */
    private void decreaseTimescale() {
        if (timescale > INITIAL_TIMESCALE) {
            timescale--;
        }
    }

    /**
     * Update the whole games for ShadowDefend to run
     * @param input input for mouse and keyboard
     */
    @Override
    protected void update(Input input) {
        // Draw map from the top left of the window
        map.draw(0, 0, 0, 0, WIDTH, HEIGHT);

        // Update panel
        updatePanel();

        // Updating game Objects
        updatingGameObject();

        // Control the games
        controlGame(input);

        // Render tower on mouse position
        renderTower(input);

        // Increase the frame counter by the current timescale
        frameCount += getTimescale();

        // When lives below zero, games over and close windows
        if(player.getLives()<=0){
            Window.close();
        }
    }

    /**
     * Update the panel information
     */
    public void updatePanel(){
        int statusWaveNum = currentWave+1;
        int statusLives = player.getLives();
        double StatusYPos = HEIGHT - statusPanel.getImage().getHeight();

        // Draw the Panel
        upperPanel.render(0.0,0.0,player.getMoney());
        statusPanel.render(statusWaveNum, timescale,status,statusLives,0.0,StatusYPos);

    }

    /**
     * Updating everything on the map
     */
    public void updatingGameObject(){
        updateLevel();
        updateWave();
        updatingSlicer();
        updatingTower();
        updatingProjectiles();
        updatingPlanes();
        updatingExplosive();
        updatingStatus();

    }

    /**
     * Using keyboard and mouse to control the game
     * @param input
     */
    public void controlGame(Input input){
        // Control the game by using the keyboard

        // S to control new Waves
        if(!waveStarted&&input.wasPressed(Keys.S)&& currentWave<waves.size()){
            waves.get(currentWave).start();
            waveStarted = true;
        }

        // L to increasing timescale
        if(input.wasPressed(Keys.L)){
            increaseTimescale();
        }

        // K to decreasing timescale
        if(input.wasPressed(Keys.K)){
            decreaseTimescale();
        }

        // Control the game by using the mouse
        MouseControl(input);
    }


    /**
     * Control the game by the mouse
     * @param input
     */
    public void MouseControl(Input input){
        // Left mouse buttons to select item
        if(input.wasPressed(MouseButtons.LEFT)){
            // If not selected, select the item based on the mouse position
            if(selectedItem==UNSELECTED){
                selectedItem= chooseTower(input);
            }else {
                // If selected, make sure it can only set in the valid position
                validPositionForSetting = mousePositionValid(input);
                if(validPositionForSetting){
                    setTower(input,selectedItem);
                }
            }
        }

        // Right mouse buttons to unselect the item
        if(input.wasPressed(MouseButtons.RIGHT)){
            selectedItem = UNSELECTED;
        }
    }

    /**
     * Select the item based on the mouse position
     * @param input
     * @return
     */
    public int chooseTower(Input input){
        // Get the position of all towers in the map
        Point tankPosition = new Point(0+ upperPanel.getTANK_X(), 0+ upperPanel.getBUY_PANEL_Y());
        Point superTankPosition = new Point(0+ upperPanel.getSUPER_TANK_X(), 0+ upperPanel.getBUY_PANEL_Y());
        Point airPlanePosition = new Point(0+ upperPanel.getAIR_SUPPORT_X(), 0+ upperPanel.getBUY_PANEL_Y());

        // Get their bounding box
        Rectangle tankRectangle = upperPanel.getTank().getImage().getBoundingBoxAt(tankPosition);
        Rectangle superTankRectangle = upperPanel.getTank().getImage().getBoundingBoxAt(superTankPosition);
        Rectangle airPlaneRectangle = upperPanel.getTank().getImage().getBoundingBoxAt(airPlanePosition);

        // Test whether the tower bounding box intersect with the mouse position
        boolean tankSelected = tankRectangle.intersects(input.getMousePosition());
        boolean superTankSelected = superTankRectangle.intersects(input.getMousePosition());
        boolean planeSelected = airPlaneRectangle.intersects(input.getMousePosition());

        // Based on the selected towers return the tower to be placed
        if(tankSelected){
            return TANK;
        }else if(superTankSelected){
            return SUPER_TANK;
        }else if(planeSelected){
            return AIR_PLANE;
        }else {
            return UNSELECTED;
        }

    }

    /**
     * Make sure the towers only can be placed on the valid position
     * @param input
     * @return
     */
    public boolean mousePositionValid(Input input) {
        MousePosition mousePosition = new MousePosition(input,towers,map,upperPanel,statusPanel);
        return mousePosition.isValid();

    }


    /**
     * Set the Tower on the map, and make sure the player has enough money to buy it
     * @param input
     * @param selectedItem
     */
    public void setTower(Input input, int selectedItem) {

        if(selectedItem == TANK){
            if(player.getMoney()>=TANK_PRICE){
                towers.add(new Tank(input.getMousePosition()));
                player.setMoney(player.getMoney()-TANK_PRICE);
            }
        }else if (selectedItem == SUPER_TANK){
            if(player.getMoney()>=SUPER_TANK_PRICE){
                towers.add(new SuperTank(input.getMousePosition()));
                player.setMoney(player.getMoney()-SUPER_TANK_PRICE);
            }
        }else if(selectedItem == AIR_PLANE ){
            if(player.getMoney()>=AIR_PLANE_PRICE){
                airPlanes.add(new AirPlane(input.getMousePosition()));
                player.setMoney(player.getMoney()-AIR_PLANE_PRICE);
            }
        }
        // After set the tower, change to unselected
        this.selectedItem = UNSELECTED;
    }

    /**
     * If the all the waves in current level are finished
     * go to the next level and reset all attributes
     */
    public void updateLevel(){
        if(currentWave>=waves.size()){
            currentLevel++;
            if(currentLevel<levels.size()){

                // Load new level map and reset all attributes
                loadMapLevel();
                resetting();
            }
        }
    }

    /**
     * reset all exists existing stuffs in the map
     */
    public void resetting(){
        player.setMoney(INITIAL_MONEY);
        player.setLives(INITIAL_LIVES);
        status=Status.AWAITING_START;
        waveStarted=false;
        slicers.clear();
        towers.clear();
        projectiles.clear();
        airPlanes.clear();
        explosives.clear();
    }

    /**
     * Updating the wave slicers on the map
     */
    public void updateWave(){

        // When S pressed wave started
        if(waveStarted){
            Wave wave = waves.get(currentWave);

            // If current wave finished, wait for the next wave and adding wave reward to the player
            if(currentEvent>=wave.getWaveEvents().size() && noSlicerOnMap()){
                waveFinished();

                // If the current wave is available, updating the slicers on the map
                // and waiting time based on the current wave event type
            }else if(currentEvent<wave.getWaveEvents().size()){
                waveContinue(wave);
            }
        }
    }

    /**
     * Wait for the next wave and adding wave reward to the player
     */
    public void waveFinished(){
        waveStarted=false;
        currentEvent=0;
        slicers.clear();
        currentWave++;
        int currentMoney = player.getMoney();
        int waveReward = MONEY_REWARD + currentWave*WAVE_REWARD;
        player.setMoney(currentMoney+waveReward);

    }

    /**
     * Get the next wave event
     * @param wave waves for getting the currentEvent
     */

    public void waveContinue(Wave wave){

        Event event =wave.getWaveEvents().get(currentEvent);

        if(!event.isStarted()){
            event.start();
        }
        if(event instanceof SpawnEvent){
            spawnSlicers(event);
        }else if(event instanceof DelayEvent){

        }

        // When current event times up, load next event
        if(frameCount>=event.getEndTime()){
            currentEvent++;
        }
    }


    /**
     * Make sure there are no slicer on the map
     * @return
     */

    public boolean noSlicerOnMap(){
        for(Slicer slicer:slicers){
            // There only two ways slicer will disappear: reach end or being destroyed
            if(slicer.isReachTheEnd() == true|| slicer.isDestroyed() == true ){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * Spawn the new slicer to the map based on the type of slicer and delay time
     * @param event the spawn event
     */
    public void spawnSlicers(Event event){
        int spawnCount = ((SpawnEvent) event).getSpawnCount();
        int numToSpawn = ((SpawnEvent) event).getNumToSpawn();
        int spawnDelay = ((SpawnEvent) event).getSpawnDelayTime();
        int startTime = event.getBeginTime();
        int frame = SPAWN_FPS;
        int convertToSeconds = CONVERT_SECONDS;

        // When updating slicers smaller than the slicers it needs to spawn
        // and the satisfy the time delay, updating a new slicer
        if(spawnCount<numToSpawn && ((frameCount-startTime)/(spawnDelay*frame/convertToSeconds)==spawnCount)){
            // Different slicer to be created
            CreatingNewSlicers(event);
        }

    }

    /**
     * Creating the new slicers based on its slicer type and add it into the slicers array
     * @param event the spawn event
     */

    public void CreatingNewSlicers(Event event){
        Slicer newSlicer =null;
        String slicerType =((SpawnEvent) event).getTypeToSpawn();
        if(slicerType.equals(APEX_SLICER)){
            newSlicer = new ApexSlicer(map.getAllPolylines().get(0));
        }else if(slicerType.equals(MEGA_SLICER)){
            newSlicer = new MegaSlicer(map.getAllPolylines().get(0));
        }else if(slicerType.equals(SUPER_SLICER)){
            newSlicer = new SuperSlicer(map.getAllPolylines().get(0));
        }else if(slicerType.equals(SLICER)){
            newSlicer = new NormalSlicer(map.getAllPolylines().get(0));
        }

        // Add to the slicers array and  update the count
        slicers.add(newSlicer);
        int currentCount = ((SpawnEvent) event).getSpawnCount();
        ((SpawnEvent) event).setSpawnCount(currentCount+1);
    }

    /**
     * updating the on map slicer and draw it in each update
     */

    public void updatingSlicer(){
        for(Slicer slicer : slicers){
            if(slicer.onTheMap() == true){
                slicer.update();
                slicer.render();
            }
        }
    }

    /**
     * updating the towers and draw it in each update
     */

    public void updatingTower(){
        for(Tower tower : towers){
            tower.update(slicers,projectiles);
            tower.render();
        }
    }

    /**
     * updating the projectiles and draw it in each update
     */

    public void updatingProjectiles(){
        for (Projectile projectile: projectiles){
            if(projectile.getTargetSlicer().onTheMap() == true && projectile.isHited() == false) {
                projectile.update(slicers);
                projectile.render();
            }
        }
    }


    /**
     * updating the Airplane and draw it in each update
     */
    public void updatingPlanes(){
        for(AirPlane airPlane : airPlanes) {
            airPlane.update(explosives);
            airPlane.render();
        }
    }


    /**
     * updating the explosive and draw it in each update
     */

    public void updatingExplosive(){
        for(int i=0; i<explosives.size(); i++){
            Explosive explosive = explosives.get(i);
            explosive.update();
            explosive.render(explosive,explosives);
        }
    }

    /**
     * Updating the Status in each update
     */

    public void updatingStatus(){
        if(!waveStarted){
            status=Status.AWAITING_START;
        }else{
            status=Status.WAVE_IN_PROGRESS;
        }

        if(selectedItem != UNSELECTED){
            status=Status.PLACING;
        }

        if(currentWave>=waves.size()&&currentLevel>=levels.size()){
            status=Status.WINNER;
        }

    }

    /**
     * Render the towers in mouse position and make sure it can not be rendered in polyline
     * @param input
     */
    public void renderTower(Input input){
        try {
            // Render the tower on the map and make sure the money is enough
            if (selectedItem == TANK && player.getMoney() >= TANK_PRICE) {
                // Only render the item in valid mouse position
                if (mousePositionValid(input)) {
                    tankImage.draw(input.getMousePosition().x,input.getMousePosition().y);
                }
            } else if (selectedItem == SUPER_TANK && player.getMoney() >= SUPER_TANK_PRICE) {
                if (mousePositionValid(input)) {
                    supImage.draw(input.getMousePosition().x,input.getMousePosition().y);
                }
            } else if (selectedItem == AIR_PLANE && player.getMoney() >= AIR_PLANE_PRICE) {
                if(mousePositionValid(input)) {
                    planeImage.draw(input.getMousePosition().x,input.getMousePosition().y);
                }
            } else {
                selectedItem = UNSELECTED;
            }
        }catch (Exception NullPointerException){
            // If move the mouse out of the map, it may cause the null pointer
        }
    }


    /**
     * Getter for FrameCount
     * @return
     */
    public static int getFrameCount() {
        return frameCount;
    }

    /**
     * Getter for Height
     * @return
     */

    public static int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * Getter for Width
     * @return
     */

    public static int getWIDTH() {
        return WIDTH;
    }

    /**
     * Getter for map
     * @return
     */

    public static TiledMap getMap() {
        return map;
    }

    /**
     * Setter for map Image
     * @param map
     */
    public static void setMap(TiledMap map) {
        ShadowDefend.map = map;
    }

    /**
     * Getter for player (singleton)
     * @return
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Getter for slicer arrayList
     * @return
     */

    public static ArrayList<Slicer> getSlicers() {
        return slicers;
    }

    /**
     * Getter for Horizontal
     * @return
     */
    public static boolean isMoveHorizontal() {
        return moveHorizontal;
    }

    /**
     * Setter for Horizontal
     * @param moveHorizontal
     */
    public static void setMoveHorizontal(boolean moveHorizontal) {
        ShadowDefend.moveHorizontal = moveHorizontal;
    }

}
