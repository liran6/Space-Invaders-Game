

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;

/**
 * this class represent a SpaceInvaders Level Information object.
 */
public class Level1 implements LevelInformation {
    /**
     * returns the level name.
     * @return the level name.
     */
    public String levelName() {
        return "Level no. ";
    }
    /**
     * returns the level background.
     * @return the level background.
     */
    public Sprite getBackground() {
        return new Background(Color.BLACK);
    }
    /**
     * returns 0.
     * @return the number of balls.
     */
    public int numberOfBalls() {
        return 0;
    }
    /**
     * returns the number of blocks.
     * @return the number of blocks.
     */
    public int numberOfBlocksToRemove() {
        return 50;
    }
    /**
     * returns the level's blocks list.
     * @return the level's blocks list.
     */
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<Block>();
        Image image = null;
        try {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().
                    getResourceAsStream("block_images/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Integer, Image> map = new TreeMap<Integer, Image>();
        map.put(1, image);
        Block block = new Block();
        block.setImages(map);
        block.setHits(1);
        block.setWidth(40);
        block.setHeight(30);
        int y = 20;
        for (int i = 0; i < 5; i++) {
            int x = 150;
            for (int j = 0; j < 10; j++) {
                Block b = new Block(block);
                b.setUpperLeft(x, y);
                blocks.add(b);
                x += 50;
            }
            y += 40;
        }
        return blocks;
    }
    /**
     * returns an empty list.
     * @return the level's velocities list.
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> lst = new ArrayList<Velocity>();
        return lst;
    }
    /**
     * returns the paddle speed.
     * @return the paddle speed.
     */
    public int paddleSpeed() {
        return 500;
    }
    /**
     * returns the paddle width.
     * @return the paddle width.
     */
    public int paddleWidth() {
        return 150;
    }

    /**
     * returns the paddle height.
     *
     * @return the paddle height.
     */
    public int paddleHeight() {
        return 15;
    }

}
