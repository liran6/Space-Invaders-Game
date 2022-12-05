import biuoop.DrawSurface;

/**
 * The interface Sprite.
 *
 * @author liran baruch. Implementation of the Sprite interface.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface interface.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the dt
     */
    void timePassed(double dt);
}