import java.util.ArrayList;
import java.util.List;

/**
 * this class represent a level object.
 */
public class Level implements LevelInformation {
    private String levelName;
    private Sprite background;
    private Integer numberOfBalls;
    private Integer numberOfBlocksToRemove;
    private List<Block> blocks;
    private List<Velocity> initialBallVelocities;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Integer paddleHeight;
    private Integer blocksStartX;
    private Integer blocksStartY;
    private Integer rowHeight;
    /**
     * construct a basic level with default parameters.
     */
    public Level() {
        this.levelName = null;
        this.background = null;
        this.numberOfBalls = null;
        this.numberOfBlocksToRemove = null;
        this.blocks = new ArrayList<Block>();
        this.initialBallVelocities = new ArrayList<Velocity>();
        this.paddleSpeed = null;
        this.paddleWidth = null;
        this.paddleHeight = null;
        this.blocksStartX = null;
        this.blocksStartY = null;
        this.rowHeight = null;
    }
    /**
     * returns the level name.
     * @return the level name.
     */
    public String levelName() {
        return this.levelName;
    }
    /**
     * this method sets the level's name.
     * @param name the given name.
     */
    public void setLevelName(String name) {
        this.levelName = name;
    }
    /**
     * returns the level background.
     * @return the level background.
     */
    public Sprite getBackground() {
        return this.background;
    }
    /**
     * this method sets the level's background.
     * @param s the given sprite.
     */
    public void setBackground(Sprite s) {
        this.background = s;
    }
    /**
     * returns the number of balls.
     * @return the number of balls.
     */
    public int numberOfBalls() {
        return this.numberOfBalls;
    }
    /**
     * this method sets the level's number of balls.
     * @param numOfBalls the number of balls.
     */
    public void setNumberOfBalls(int numOfBalls) {
        this.numberOfBalls = numOfBalls;
    }
    /**
     * returns the number of blocks.
     * @return the number of blocks.
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
    /**
     * this method sets the level's number of blocks.
     * @param numOfBlocks the number of blocks.
     */
    public void setNumberOfBlocksToRemove(int numOfBlocks) {
        this.numberOfBlocksToRemove = numOfBlocks;
    }
    /**
     * returns the level's blocks list.
     * @return the level's blocks list.
     */
    public List<Block> blocks() {
        return this.blocks;
    }
    /**
     * this method get a list of blocks and saves it as the level's blocks list.
     * @param blocksList the given list.
     */
    public void setBlocks(List<Block> blocksList) {
        this.blocks = blocksList;
    }
    /**
     * this method adds a given block to the blocks list.
     * @param block the given block.
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }
    /**
     * returns the level's velocities list.
     * @return the level's velocities list.
     */
    public List<Velocity> initialBallVelocities() {
        return this.initialBallVelocities;
    }
    /**
     * this method get a list of velocities
     * and saves it as the level's velocities list.
     * @param velocities the given velocities.
     */
    public void setInitialBallVelocities(List<Velocity> velocities) {
        this.initialBallVelocities = velocities;
    }
    /**
     * this method adds a given velocity to the velocities list.
     * @param velocity the given velocity.
     */
    public void addInitialBallVelocity(Velocity velocity) {
        this.initialBallVelocities.add(velocity);
    }
    /**
     * returns the paddle speed.
     * @return the paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }
    /**
     * this method sets the level's paddle speed.
     * @param speed the given speed.
     */
    public void setPaddleSpeed(int speed) {
        this.paddleSpeed = speed;
    }
    /**
     * returns the paddle width.
     * @return the paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }
    /**
     * this method sets the level's paddle width.
     * @param width the given width.
     */
    public void setPaddleWidth(int width) {
        this.paddleWidth = width;
    }
    /**
     * returns the paddle height.
     * @return the paddle height.
     */
    public int paddleHeight() {
        return this.paddleHeight;
    }
    /**
     * this method sets the level's paddle height.
     * @param height the given height.
     */
    public void setPaddleHeight(int height) {
        this.paddleHeight = height;
    }
    /**
     * returns the initial x coordinate for locating the first block.
     * @return the x coordinate..
     */
    public int blocksStartX() {
        return this.blocksStartX;
    }
    /**
     * this method sets the initial x coordinate for locating the first block.
     * @param x the x coordinate.
     */
    public void setBlocksStartX(int x) {
        this.blocksStartX = x;
    }
    /**
     * returns the initial y coordinate for locating the first block.
     * @return the y coordinate..
     */
    public int blocksStartY() {
        return this.blocksStartY;
    }
    /**
     * this method sets the initial y coordinate for locating the first block.
     * @param y the y coordinate.
     */
    public void setBlocksStartY(int y) {
        this.blocksStartY = y;
    }
    /**
     * returns the height of a row.
     * @return the height of a row.
     */
    public int rowHeight() {
        return this.rowHeight;
    }
    /**
     * this method sets the height of a row.
     * @param height the given height.
     */
    public void setRowHeight(int height) {
        this.rowHeight = height;
    }
}
