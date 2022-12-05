import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Rectangle rec;
    private Counter score;

    /**
     * Instantiates a new Score indicator.
     *
     * @param rec   the rec
     * @param score the score
     */
    public ScoreIndicator(Rectangle rec, Counter score) {
        this.rec = rec;
        this.score = score;
    }

    /**
     * sets the counter.
     *
     * @param c the new counter.
     */
    public void setScoreCounter(Counter c) {
        this.score = c;
    }

    /**
     * drawOn.
     * @param d the draw surface interface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText((int) Math.round(this.rec.getUpperLeft().getX())
                        + (int) Math.round(this.rec.getWidth()) / 3
                , ((int) Math.round(this.rec.getUpperLeft().getY()
                        + (int) Math.round(this.rec.getHeight()) / 2 + 10)),
                "Score: " + Integer.toString(this.score.getValue()), 20);
    }

    /**
     * does nothing.
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
