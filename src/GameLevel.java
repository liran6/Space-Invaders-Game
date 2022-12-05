import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type GameLevel.
 *
 * @author liran baruch. Implementation of the GameLevel class.
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter score;
    private Counter numberOfLives;
    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private Paddle paddle;
    private LevelInformation level;
    private LifeIndicator lifeIndicator;
    private LevelIndicator levelIndicator;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_Y_POINT = 560;
    private static final int GUI_WITH = 800;
    private int playerShotCounter = 20;
    private List<Ball> ballList;
    private List<Ball> enemyShots;
    private BlockGroup enemyPlatoon;
    private List<Block> blocks;
//    private int horizontalBound;
//    private int verticalBound;



    /**
     * the constructor of the game members.
     *
     * @param gui               the gui
     * @param level             the level
     * @param score             the score
     * @param numOfLives        the num of lives
     * @param keyboardSensor    the keyboard sensor
     * @param runner            the runner
     * @param enemyPlatoonSpeed the enemy platoon speed
     */
    public GameLevel(GUI gui, LevelInformation level, int score, int numOfLives, KeyboardSensor keyboardSensor
            , AnimationRunner runner, int enemyPlatoonSpeed) {
        sprites = new SpriteCollection();
        environment = new GameEnvironment();
        this.gui = gui;
        this.blocksCounter = new Counter();
        this.ballsCounter = new Counter();
        this.score = new Counter(score);
        this.numberOfLives = new Counter(numOfLives);
        this.runner = runner;
        this.level = level;
        this.keyboardSensor = keyboardSensor;
        this.ballList = new ArrayList<>();
        this.enemyShots = new ArrayList<>();
        this.enemyPlatoon = new BlockGroup(this, enemyPlatoonSpeed);
        this.blocks = new ArrayList<>();
        for (Block b : level.blocks()) {
            Block block = new Block(b);
            this.blocks.add(block);
            this.enemyPlatoon.addBlock(block);
        }
        this.sprites.addSprite(enemyPlatoon);
//        this.horizontalBound = dimentions.horizontalBound();
//        this.verticalBound = dimentions.verticalBound();
    }

    /**
     * adds the collidable object to the game environment.
     *
     * @param c the collidable object.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * adds the sprite object to the game environment.
     *
     * @param s the sprite object.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball (and Paddle) and add them to the game.
     */
    public void initialize() {
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        BallRemover ballRemover = new BallRemover(this, this.ballsCounter);
        this.addSprite(this.level.getBackground());
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        Block[] frameBlocks = new Block[3];
        Block frameBlock = new Block(new Rectangle(new Point(0, 0), 800, 20), Color.gray, 0);
        frameBlock.addToGame(this);
        frameBlock.addHitListener(ballRemover);
        frameBlocks[0] = frameBlock;
        frameBlock = new Block(new Rectangle((new Point(0, 20)), 1, 580), Color.gray, 0);
        frameBlock.addToGame(this);
        frameBlock.addHitListener(ballRemover);
        frameBlocks[1] = frameBlock;
        frameBlock = new Block(new Rectangle((new Point(799, 20)), 1, 580), Color.gray, 0);
        frameBlock.addToGame(this);
        frameBlock.addHitListener(ballRemover);
        frameBlocks[2] = frameBlock;
        ScoreIndicator scoreIndicator = new ScoreIndicator(new Rectangle(new Point(0, 0)
                , 800, 20), score);
        lifeIndicator = new LifeIndicator(new Rectangle(new Point(0, 0)
                , 800, 20), numberOfLives);
        levelIndicator = new LevelIndicator(new Rectangle(new Point(0, 0)
                , 800, 20), level);
        levelIndicator.addToGame(this);
        lifeIndicator.addToGame(this);
        scoreIndicator.addToGame(this);
        blockRemover.setCounter(this.blocksCounter);
        //biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        Block paddleBlock = new Block(new Rectangle(new Point(GUI_WITH / 2
                - (this.level.paddleWidth() / 2), PADDLE_Y_POINT), this.level.paddleWidth(), PADDLE_HEIGHT));
        paddleBlock.addHitListener(ballRemover);
        this.paddle = new Paddle(this.keyboardSensor, paddleBlock
                , this.level.paddleSpeed());
        paddle.addToGame(this);


        Block deathRegion = new Block((new Point(20, 599)), 760, 1, Color.white, 0);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);

        Block shieldTemplate = new Block();
        Map<Integer, Color> shieldColor = new TreeMap<Integer, Color>();
        shieldColor.put(1, Color.CYAN);
        shieldTemplate.setColors(shieldColor);
        shieldTemplate.setHits(1);
        shieldTemplate.setWidth(5);
        shieldTemplate.setHeight(5);
        Block[][] leftShield = new Block[3][30];
        Block[][] middleShield = new Block[3][30];
        Block[][] rightShield = new Block[3][30];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 30; j++) {
                leftShield[i][j] = new Block(shieldTemplate);
                leftShield[i][j].setUpperLeft(100 + j * 5, 500 + i * 5);
                leftShield[i][j].addHitListener(ballRemover);
                leftShield[i][j].addHitListener(new BlockRemover(this, new Counter(0)));
                leftShield[i][j].addToGame(this);

                middleShield[i][j] = new Block(shieldTemplate);
                middleShield[i][j].setUpperLeft(300 + j * 5, 500 + i * 5);
                middleShield[i][j].addHitListener(ballRemover);
                middleShield[i][j].addHitListener(new BlockRemover(this, new Counter(0)));
                middleShield[i][j].addToGame(this);

                rightShield[i][j] = new Block(shieldTemplate);
                rightShield[i][j].setUpperLeft(500 + j * 5, 500 + i * 5);
                rightShield[i][j].addHitListener(ballRemover);
                rightShield[i][j].addHitListener(new BlockRemover(this, new Counter(0)));
                rightShield[i][j].addToGame(this);
            }
        }

        for (Block i : this.blocks) {
            i.addToGame(this);
            i.addHitListener(blockRemover);
            i.addHitListener(ballRemover);
            i.addHitListener(scoreTrackingListener);
            i.addHitListener(this.enemyPlatoon);
            this.blocksCounter.increase(1);
        }
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {

        while (this.numberOfLives.getValue() > 0) {

            this.playOneTurn();

        }
        gui.close();
    }

    /**
     * boolean change.
     *
     * @return true or false.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * do one frame.
     *
     * @param d  the drawSurface
     * @param dt the delta time.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        if (this.keyboardSensor.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(
                    new PauseScreen(), this.keyboardSensor, "space"));
        }
        if (this.playerShotCounter > 0) {
            this.playerShotCounter -= 1;
        }
        if (this.playerShotCounter == 0 && this.keyboardSensor.isPressed("space")) {

            double paddleX = this.paddle.getCollisionRectangle().getUpperLeft().getX();
            double paddleY = this.paddle.getCollisionRectangle().getUpperLeft().getY();
            double paddleWidth = this.paddle.getCollisionRectangle().getWidth();
            Ball ball = new Ball(new Point(paddleX + paddleWidth / 2, paddleY - 7 * 5), 5
                    , Color.white, this.environment);
            ball.setVelocity(0, -500);
            ball.addToGame(this);
            this.ballsCounter.increase(1);
            this.playerShotCounter = 20;
            ballList.add(ball);
        }
        if (this.paddle.isPaddleHit()) {
            this.numberOfLives.decrease(1);
            this.running = false;
            this.enemyPlatoon.reset(dt);
            this.removeShotsLeftover();
            this.paddle.setPaddleHit();
            for (int i = 0; i < ballList.size(); i++) {
                this.removeSprite(ballList.get(i));
            }

        }
        if (this.enemyPlatoon.reachedShields()) {
            this.running = false;
            this.enemyPlatoon.reset(dt);
            this.numberOfLives.decrease(1);
            this.removeShotsLeftover();
            this.paddle.setPaddleHit();
            for (int i = 0; i < ballList.size(); i++) {
                this.removeSprite(ballList.get(i));
            }
        }
//        if (this.ballsCounter.getValue() == 0) {
//            this.numberOfLives.decrease(1);
//        }
        // (this.blocksCounter.getValue() > 0 && this.ballsCounter.getValue() > 0) {
        if (!(this.blocksCounter.getValue() > 0)) {
            this.running = false;
        }
        if (this.getBlockCount() == this.level.blocks().size() - this.level.numberOfBlocksToRemove()) {
            this.score.increase(100);
            this.running = false;
        }


        //long startTime = System.currentTimeMillis(); // timing
        //DrawSurface d = gui.getDrawSurface();
        this.sprites.drawAllOn(d);
        //gui.show(d);
        this.sprites.notifyAllTimePassed();
/**
 // timing
 long usedTime = System.currentTimeMillis() - startTime;
 long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
 if (milliSecondLeftToSleep > 0) {
 sleeper.sleepFor(milliSecondLeftToSleep);
 }
 **/


        // this.running = false;
    }

    /**
     * Play one turn.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle(); // or a similar method
        this.runner.run(new CountdownAnimation(6, 3, this.sprites));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }

    /**
     * move the paddle to the start point.
     */
    public void movePaddleToStart() {
        this.paddle.initializePaddleLocation(new Point(GUI_WITH / 2
                - (this.level.paddleWidth() / 2), PADDLE_Y_POINT));
    }

    /**
     * puts the balls above the paddle.
     */
    public void createBallsOnTopOfPaddle() {
        Ball ball;
        this.movePaddleToStart();

        double paddleX = this.paddle.getCollisionRectangle().getUpperLeft().getX();
        double paddleY = this.paddle.getCollisionRectangle().getUpperLeft().getY();
        double paddleWidth = this.paddle.getCollisionRectangle().getWidth();


        for (int i = 0; i < this.level.numberOfBalls(); i++) {
            ball = new Ball(new Point(paddleX + paddleWidth / 2, paddleY - 7 * 5), 5
                    , Color.white, this.environment);
            ball.setVelocity(this.level.initialBallVelocities().get(i));
            this.ballsCounter.increase(1);
            ball.addToGame(this);
        }
        this.ballsCounter.setValue(this.level.numberOfBalls());

    }

    /**
     * returns the counter of the blocks in the game.
     *
     * @return the counter of the blocks in the game.
     */
    public int getBlockCount() {
        return this.blocksCounter.getValue();
    }

    /**
     * returns the counter of number of lives.
     *
     * @return the counter of number of lives.
     */
    public Counter getNumberOfLives() {
        return this.numberOfLives;
    }

    /**
     * returns the counter of score.
     *
     * @return the counter of score
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * this method generates an enemy shot object at the given point.
     *
     * @param point the given point.
     * @return a player shot object.
     */
    public Ball getEnemyShot(Point point) {
        Ball ball = new Ball(point, 5, Color.RED, this.environment);
        this.enemyShots.add(ball);
        return ball;
    }

    /**
     * this method removes all the shots from the screen.
     */
    public void removeShotsLeftover() {
        for (Ball ball : this.enemyShots) {
            ball.removeFromGame(this);
        }
    }
}

