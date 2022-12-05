import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Instantiates a new Pause screen.
     */
    public PauseScreen() {
        // this.keyboard = k;
        this.stop = false;
    }

    /**
     * do one frame.
     *
     * @param d  the draw on surface.
     * @param dt the delta time.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(140, d.getHeight() / 2, "paused -- press space to continue", 32);
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