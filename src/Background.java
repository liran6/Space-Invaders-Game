import java.awt.Color;
import java.awt.Image;
import biuoop.DrawSurface;

/**
 * this class represents a background object.
 */
public class Background implements Sprite {
    private Image image;
    private Color color;
    private boolean isImage;
    /**
     * construct a background object from a given image.
     * @param image the given image.
     */
    public Background(Image image) {
        this.image = image;
        this.isImage = true;
    }
    /**
     * construct a background object from a given color.
     * @param color the given color.
     */
    public Background(Color color) {
        this.color = color;
        this.isImage = false;
    }
    /**
     * this method draws the sprite object to the screen.
     * @param d a draw surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        if (this.isImage) {
            d.drawImage(0, 0, this.image);
        } else {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        }
    }
    /**
     * this method notifies the sprite object that a time unit has passed.
     * @param dt the dt value of this game.
     */
    public void timePassed(double dt) {
    }

}
