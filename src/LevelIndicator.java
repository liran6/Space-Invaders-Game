import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Level indicator.
 */
public class LevelIndicator implements Sprite {
    private Rectangle rec;
    private LevelInformation levelInformation;

    /**
     * Instantiates a new Level indicator.
     *
     * @param rec              the rec
     * @param levelInformation the level information
     */
    public LevelIndicator(Rectangle rec, LevelInformation levelInformation) {
        this.levelInformation = levelInformation;
        this.rec = rec;
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the draw surface interface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText((int) Math.round(this.rec.getUpperLeft().getX()) + (int) Math.round(this.rec.getWidth()) / 2
                        + (int) Math.round(this.rec.getWidth()) / 8
                , ((int) Math.round(this.rec.getUpperLeft().getY()
                        + (int) Math.round(this.rec.getHeight()) / 2 + 10)),
                (this.levelInformation.levelName()) + "1", 20);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the delta time.
     */
    public void timePassed(double dt) {

    }

    /**
     * Add to game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}

