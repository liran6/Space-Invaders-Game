import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Block.
 *
 * @author liran baruch. Implementation of the Block class.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // private Point upperLeft;
    //private double width;
    //private double height;
    private Rectangle rect;
    private java.awt.Color color;
    private List<HitListener> hitListeners;
    private int life;
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;
    private Color borderColor;
    private boolean hasBorder;

    /**
     * Block constructor.
     *
     * @param upperLeft the upperLeft vertex of the block.
     * @param width     the width of the block.
     * @param height    the height of the block.
     * @param color     the color of the block.
     * @param life    the number of hits left written on the block.
     */
    /**
     * public Block(Point upperLeft, double width, double height, java.awt.Color color, int life) {
     * this.upperLeft = upperLeft;
     * this.width = width;
     * this.height = height;
     * this.color = color;
     * this.hitListeners = new ArrayList<HitListener>();
     * this.life = life;
     * }
     * /**
     * constructor.
     *
     * @param rect the rectangle of the block.
     */
    public Block(Rectangle rect) {
        this.rect = rect;
        this.color = new Color(113, 89, 128);
        this.life = 3;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor.
     *
     * @param rect  the rectangle of the block
     * @param color the color of the block
     * @param life  the hits of the block
     */
    public Block(Rectangle rect, Color color, int life) {
        this.rect = rect;
        this.color = color;
        this.life = life;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * constructor.
     *
     * @param upperLeft the x and y value of the upper point.
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     * @param lifes     the life of the block.
     */
    public Block(Point upperLeft, int width, double height, Color color, int lifes) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.life = lifes;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * construct a basic block with default parameters,
     * located in the upper left corner of the screen (coordinates: x=0, y=0).
     */
    public Block() {
        this.rect = new Rectangle(new Point(0, 0), 1, 1);
        this.colors = new TreeMap<>();
        this.images = new TreeMap<>();
        this.borderColor = Color.BLACK;
        this.hasBorder = false;
        this.life = 0;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * construct a new block from a given block according to its' parameters,
     * a new hitListener object is generated anyway.
     *
     * @param other the given block.
     */
    public Block(Block other) {
        this.rect = other.rect;
        this.colors = other.colors;
        this.images = other.images;
        this.borderColor = other.borderColor;
        this.hasBorder = other.hasBorder;
        this.life = other.life;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * create a rectangle with the variables given by the block constructor.
     *
     * @return rectangle.
     */

    public Rectangle getCollisionRectangle() {
        rect = new Rectangle(this.rect.getUpperLeft(), this.rect.getWidth(), this.rect.getHeight());
        return rect;
    }

    /**
     * Get the color of the current Block.
     *
     * @return - integer - color of the Block.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Get the string number written on the current Block.
     *
     * @return - string - the number on the Block.
     */
    public int getLife() {
        return this.life;
    }

    /**
     * @param surface surface.
     */
    public void drawOn(DrawSurface surface) {
//        surface.setColor(this.getColor());
//        surface.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY()
//                , (int) this.rect.getWidth(), (int) this.rect.getHeight());
//        surface.setColor(Color.black);
//        surface.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY()
//                , (int) this.rect.getWidth(), (int) this.rect.getHeight());
//        surface.setColor(Color.white);
//        //surface.drawText((int) (this.upperLeft.getX() + this.width / 2) - 4
//        //      , (int) (4 + this.upperLeft.getY() + this.height / 2), this.getNumber(), 17);
        if (this.images == null && this.colors == null) {
//            surface.setColor(this.getColor());
//            surface.fillRectangle(
//                    (int) Math.round(this.rect.getUpperLeft().getX()),
//                    (int) Math.round(this.rect.getUpperLeft().getY()),
//                    (int) Math.round(this.rect.getWidth()),
//                    (int) Math.round(this.rect.getHeight()));
            surface.setColor(this.getColor());
            surface.fillRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY()
                    , (int) this.rect.getWidth(), (int) this.rect.getHeight());
            surface.setColor(Color.black);
            surface.drawRectangle((int) this.rect.getUpperLeft().getX(), (int) this.rect.getUpperLeft().getY()
                    , (int) this.rect.getWidth(), (int) this.rect.getHeight());
            surface.setColor(Color.white);
            //surface.drawText((int) (this.upperLeft.getX() + this.width / 2) - 4
            //      , (int) (4 + this.upperLeft.getY() + this.height / 2), this.getNumber(), 17);
        } else if (this.images.containsKey(this.life)) {
            surface.drawImage(
                    (int) Math.round(this.rect.getUpperLeft().getX()),
                    (int) Math.round(this.rect.getUpperLeft().getY()),
                    this.images.get(this.life));
        } else if (this.colors.containsKey(this.life)) {
            surface.setColor(this.colors.get(this.life));
            surface.fillRectangle(
                    (int) Math.round(this.rect.getUpperLeft().getX()),
                    (int) Math.round(this.rect.getUpperLeft().getY()),
                    (int) Math.round(this.rect.getWidth()),
                    (int) Math.round(this.rect.getHeight()));
        } else if (this.images.containsKey(0)) {
            surface.drawImage(
                    (int) Math.round(this.rect.getUpperLeft().getX()),
                    (int) Math.round(this.rect.getUpperLeft().getY()),
                    this.images.get(0));
        } else if (this.colors.containsKey(0)) {
            surface.setColor(this.colors.get(0));
            surface.fillRectangle(
                    (int) Math.round(this.rect.getUpperLeft().getX()),
                    (int) Math.round(this.rect.getUpperLeft().getY()),
                    (int) Math.round(this.rect.getWidth()),
                    (int) Math.round(this.rect.getHeight()));
        }
        if (this.hasBorder) {
            surface.setColor(this.borderColor);
            surface.drawRectangle(
                    (int) Math.round(this.rect.getUpperLeft().getX()),
                    (int) Math.round(this.rect.getUpperLeft().getY()),
                    (int) Math.round(this.rect.getWidth()),
                    (int) Math.round(this.rect.getHeight()));
        }

    }

    /**
     * create a list of hit listener.
     *
     * @param hitter the hitting ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl Hit Listener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * // Remove hl from the list of listeners to hit events.
     *
     * @param hl the Hit listener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  the trajectory and rectangle collision point.
     * @param hitter          the hitting ball.
     * @param currentVelocity the current velocity.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity velocity = new Velocity(currentVelocity.getDx(), currentVelocity.getDy());
        double x = rect.getUpperLeft().getX();
        double y = rect.getUpperLeft().getY();
        Point upperLeft1 = new Point(x, y);
        Point upperRight = new Point(x + rect.getWidth(), y);
        Point lowerLeft = new Point(x, y + rect.getHeight());
        Point lowerRight = new Point(x + rect.getWidth(), y + rect.getHeight());
        Line upperLine = new Line(upperLeft1, upperRight);
        Line leftLine = new Line(upperLeft1, lowerLeft);
        Line rightLine = new Line(upperRight, lowerRight);
        Line bottomLine = new Line(lowerLeft, lowerRight);
        if (collisionPoint.getY() == upperLeft1.getY() && collisionPoint.getX() < upperRight.getX()
                && collisionPoint.getX() > upperLeft1.getX()) {
            velocity = new Velocity(velocity.getDx(), velocity.getDy() * -1);
        }
        if (collisionPoint.getY() > upperLeft1.getY() && collisionPoint.getY() < lowerLeft.getY()
                && collisionPoint.getX() == upperLeft1.getX()) {
            velocity = new Velocity(velocity.getDx() * -1, velocity.getDy());
        }
        if (collisionPoint.getY() > upperLeft1.getY() && collisionPoint.getY() < lowerLeft.getY()
                && collisionPoint.getX() == upperRight.getX()) {
            velocity = new Velocity(velocity.getDx() * -1, velocity.getDy());
        }
        if (collisionPoint.getY() == lowerLeft.getY() && collisionPoint.getX() > lowerLeft.getX()
                && collisionPoint.getX() < lowerRight.getX()) {
            velocity = new Velocity(velocity.getDx(), velocity.getDy() * -1);
        }

        if (collisionPoint.equals(upperLeft1) || collisionPoint.equals(upperRight)
                || collisionPoint.equals(lowerLeft) || collisionPoint.equals(lowerRight)) {
            velocity = new Velocity(velocity.getDx() * -1, velocity.getDy() * -1);
        }
        if (this.life > 0) {
            this.life--;
        }
        this.notifyHit(hitter);
        return velocity;
    }

    /**
     * set the upper Left point of the rectangle.
     *
     * @param x x value of the upper left point.
     * @param y y value of the upper left point.
     */
    public void setUpperLeft(double x, double y) {
//        this.rect.setUpperLeft(x, y);
        Point upperLeft = new Point(x, y);
        this.setUpperLeft(upperLeft);
    }

    /**
     * Sets upper left.
     *
     * @param upperLeft the upper left
     */
    public void setUpperLeft(Point upperLeft) {
        this.rect = new Rectangle(upperLeft, this.rect.getWidth(),
                this.rect.getHeight());
    }

    /**
     * does nothing for now.
     * @param dt the delta time.
     */
    public void timePassed(double dt) {

    }

    /**
     * add the sprite and collidable to the gameLevel.
     *
     * @param gameLevel the gameLevel class.
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }

    /**
     * remove the sprite and collidable from the gameLevel.
     *
     * @param gameLevel the gameLevel class.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * this method sets the width of this block.
     *
     * @param width the new width.
     */
    public void setWidth(int width) {
        this.rect = new Rectangle(this.rect.getUpperLeft(),
                width, this.rect.getHeight());
    }

    /**
     * this method sets the height of this block.
     *
     * @param height the new height.
     */
    public void setHeight(int height) {
        this.rect = new Rectangle(this.rect.getUpperLeft(),
                this.rect.getWidth(), height);
    }

    /**
     * this method sets the number of hits of this block.
     *
     * @param numberOfLives the number of hits.
     */
    public void setHits(int numberOfLives) {
        this.life = numberOfLives;
    }

    /**
     * this method sets the color of this block's borders.
     *
     * @param bordersColor the given color.
     */
    public void setBorderColor(Color bordersColor) {
        this.borderColor = bordersColor;
    }

    /**
     * this method sets the available colors of this block's filling.
     *
     * @param filling the given colors.
     */
    public void setColors(Map<Integer, Color> filling) {
        this.colors = filling;
    }

    /**
     * this method sets the available images of this block's filling.
     *
     * @param filling the given images.
     */
    public void setImages(Map<Integer, Image> filling) {
        this.images = filling;
    }

    /**
     * this method determines if this block has borders or not.
     *
     * @param isHasBorder the wanted option.
     */
    public void setBorder(boolean isHasBorder) {
        this.hasBorder = isHasBorder;
    }

    /**
     * Move.
     *
     * @param velocity the velocity
     * @param dt       the dt
     */
    public void move(Velocity velocity, double dt) {
        Velocity v = velocity.setSpeed(dt);
        double dx = Math.round(v.getDx());
        double dy = Math.round(v.getDy());
        int newX = (int) (this.rect.getUpperLeft().getX() + dx);
        int newY = (int) (this.rect.getUpperLeft().getY() + dy);
        this.setUpperLeft(newX, newY);
    }
}