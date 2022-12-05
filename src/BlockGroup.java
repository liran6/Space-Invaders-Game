

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import biuoop.DrawSurface;


/**
 * this class represents a group of block.
 */
public class BlockGroup implements Sprite, HitListener {
    private GameLevel game;
    private List<Block> blocks;
    private Map<Double, Set<Block>> blockByX;
    private Map<Double, Set<Block>> blockByY;
    private double angle;
    private double initialSpeed;
    private double speed;
    private boolean movedDown;
    private boolean finish;
    private long lastTimeShot;
    private double maxX;
    private double minX;
    private double maxY;
    private double minY;

    /**
     * constructs a BlockGroup object from a gameLevel object and a speed.
     *
     * @param game  the given gameLevel.
     * @param speed the given speed.
     */
    public BlockGroup(GameLevel game, double speed) {
        this.game = game;
        this.blocks = new ArrayList<Block>();
        this.blockByX = new TreeMap<Double, Set<Block>>();
        this.blockByY = new TreeMap<Double, Set<Block>>();
        this.angle = 90;
        this.initialSpeed = speed;
        this.speed = speed;
        this.movedDown = false;
        this.finish = false;
        this.lastTimeShot = 0;
        this.maxX = 0;
        this.minX = 800;
        this.maxY = 0;
    }

    /**
     * this method updates the min/max values according to the group ststus.
     */
    public void checkStatus() {
        this.maxX = 0;
        this.minX = 800;
        this.maxY = 0;
        this.minY = 600;
        for (Double x : this.blockByX.keySet()) {
            if (this.maxX < x) {
                this.maxX = x;
            }
            if (this.minX > x) {
                this.minX = x;
            }
        }
        for (Double y : this.blockByY.keySet()) {
            if (this.maxY < y) {
                this.maxY = y;
            }
            if (this.minY > y) {
                this.minY = y;
            }
        }
        if (this.maxX >= 750) {
            this.angle = 270;
        }
        if (this.minX <= 0) {
            this.angle = 90;
        }
        if ((this.minX <= 0 || this.maxX >= 750) && !this.movedDown) {
            this.angle = 180;
            this.speed *= 1.1;
            this.movedDown = true;
        }
        if (this.angle == 90 || this.angle == 270) {
            this.movedDown = false;
        }
        if (maxY >= 450) {
            this.finish = true;
        }
    }

    /**
     * this method moves the group according to the moving pattern and
     * a given dt value.
     *
     * @param dt the given dt value.
     */
    public void move(double dt) {
        Velocity velocity = null;
        if (this.angle == 180) {
            velocity = Velocity.fromAngleAndSpeed(this.angle, 1000);
        } else {
            velocity = Velocity.fromAngleAndSpeed(this.angle, this.speed);
        }
        for (Block block : this.blocks) {
            block.move(velocity, dt);
        }
    }

    /**
     * this method shoots a random shot according to the shooting logic.
     *
     * @param gameLevel the given game.
     */
    public void shoot(GameLevel gameLevel) {
        if (System.currentTimeMillis() - lastTimeShot > 500) {
            List<Block> lowest = new ArrayList<Block>();
            for (Double x : this.blockByX.keySet()) {
                Set<Block> column = blockByX.get(x);
                if (column.size() == 0) {
                    continue;
                } else {
                    Block lowestBlock = new Block();
                    lowestBlock.setUpperLeft(0, -1);
                    for (Block block : column) {
                        if (block.getCollisionRectangle().getUpperLeft().getY()
                                > lowestBlock.getCollisionRectangle().
                                getUpperLeft().getY()) {
                            lowestBlock = block;
                        }
                    }
                    lowest.add(lowestBlock);
                }
            }
            Random random = new Random();
            int shooterIndex = random.nextInt(lowest.size());
            Ball shot = gameLevel.getEnemyShot(new Point(lowest.get(shooterIndex).
                    getCollisionRectangle().
                    getBottom().middle().getX(), lowest.get(shooterIndex).
                    getCollisionRectangle().
                    getBottom().middle().getY() + 5));
            shot.setVelocity(
                    Velocity.fromAngleAndSpeed(170 + random.nextInt(20), 300));
            shot.addToGame(gameLevel);
            this.lastTimeShot = System.currentTimeMillis();
        }
    }

    /**
     * this method resets the group to it's original location.
     *
     * @param dt the dt value of this game.
     */
    public void reset(double dt) {
        while (this.minX > 20) {
            this.checkStatus();
            this.angle = 270;
            this.move(dt);
            this.updateMaps();
        }
        while (this.minX + this.maxX < 750) {
            this.checkStatus();
            this.angle = 90;
            this.move(dt);
            this.updateMaps();
        }
        while (this.minY > 25) {
            this.checkStatus();
            this.angle = 0;
            this.move(dt);
            this.updateMaps();
        }
        this.angle = 90;
        this.speed = this.initialSpeed;
        this.movedDown = false;
        this.finish = false;
    }

    /**
     * this method returns true if the group reached the shields.
     *
     * @return true if the group reached the shields.
     */
    public boolean reachedShields() {
        return this.finish;
    }

    /**
     * this method adds a given block to the group.
     *
     * @param block the given block.
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
        this.addBlockMapping(block);
    }

    /**
     * this method removes a given block to the group.
     *
     * @param block the given block.
     */
    public void removeBlock(Block block) {
        this.blocks.remove(block);
        this.removeBlockMapping(block);
    }

    /**
     * this method does nothing.
     *
     * @param d the given drawSurface.
     */
    public void drawOn(DrawSurface d) {
    }

    /**
     * this method adds a mapping to a given block
     * according to it's X and Y coordinates.
     *
     * @param block the given block.
     */
    public void addBlockMapping(Block block) {
        if (!this.blockByX.containsKey(
                block.getCollisionRectangle().getUpperLeft().getX())) {
            Set<Block> set = new HashSet<Block>();
            this.blockByX.put(
                    block.getCollisionRectangle().getUpperLeft().getX(), set);
        }
        if (!this.blockByY.containsKey(
                block.getCollisionRectangle().getUpperLeft().getY())) {
            Set<Block> set = new HashSet<Block>();
            this.blockByY.put(
                    block.getCollisionRectangle().getUpperLeft().getY(), set);
        }
        this.blockByX.get(
                block.getCollisionRectangle().getUpperLeft().getX()).add(block);
        this.blockByY.get(
                block.getCollisionRectangle().getUpperLeft().getY()).add(block);
    }

    /**
     * this method removes a mapping to a given block
     * according to it's X and Y coordinates.
     *
     * @param block the given block.
     */
    public void removeBlockMapping(Block block) {
        this.blockByX.get(
                block.getCollisionRectangle().getUpperLeft().getX()).remove(block);
        this.blockByY.get(
                block.getCollisionRectangle().getUpperLeft().getY()).remove(block);
    }

    /**
     * this method updates the mapping of the block in the group.
     */
    public void updateMaps() {
        this.blockByX.clear();
        this.blockByY.clear();
        for (Block block : this.blocks) {
            this.addBlockMapping(block);
        }
    }

    /**
     * this method notifies the sprite object that a time unit has passed.
     *
     * @param dt the dt value of this game.
     */
    public void timePassed(double dt) {
        this.checkStatus();
        this.move(dt);
        this.updateMaps();
        this.shoot(this.game);
    }

    /**
     * this method is called whenever the beingHit object is being hit.
     * the method removes from the game blocks that reach 0 hit-points and
     * updates the number of blocks that left in the game.
     *
     * @param beingHit the block that is being hit.
     * @param hitter   the hitting ball.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.removeBlock(beingHit);
    }

    /**
     * this method does nothing.
     *
     * @param beingHit the paddle.
     * @param hitter   the ball.
     */
    public void hitEvent(Paddle beingHit, Ball hitter) {
    }
}
