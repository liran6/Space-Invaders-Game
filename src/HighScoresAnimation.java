import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private HighScoresTable scores;
    private String endKey;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
        this.endKey = endKey;
        this.stop = false;
    }

    /**
     * do one frame.
     *
     * @param d  the draw on surface.
     * @param dt the delta time.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.black);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.white);
        d.drawText(300, 100, "High Scores", 48);
        for (int i = 1; i <= scores.getHighScores().size(); i++) {
            d.drawText(30, 140 + (i * 40), i + ") " + this.scores.getHighScores().get(i - 1).getName(), 30);
            d.drawText(400, 140 + (i * 40), "" + this.scores.getHighScores().get(i - 1).getScore(), 30);
        }
        d.drawText(300, d.getHeight() - 50, " press 'q' to quit", 30);
    }

    /**
     * boolean return true or false to stop the game or not.
     *
     * @return true or false.
     */
    public boolean shouldStop() {
        return false;
    }
}
