

import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;


/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private Sleeper sleeper;
    private boolean running;
    private int downCounting;

    /**
     * constructor.
     *
     * @param numOfSeconds number of seconds to display the caountdown
     * @param countFrom    the number from which the countdown starts
     * @param gameScreen   the sprites of the screen to be drawn underneath the countdoun
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds / countFrom;
        this.countFrom = countFrom;
        this.downCounting = countFrom + 1;
        this.gameScreen = gameScreen;
        this.running = true;
        this.sleeper = new Sleeper();

    }

    /**
     * Do one fram of the animation.
     *
     * @param d  the Drawface on which the animtaion is to be drawn
     * @param dt the delta time.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        if (this.downCounting != (this.countFrom + 1)) {
            this.sleeper.sleepFor((int) this.numOfSeconds * 400);
        }

        if (this.downCounting == 0) {
            this.running = false;
            return;
        }
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.white);
        if (this.downCounting == 1) {
            d.drawText(240, d.getHeight() / 2, " Let's Go!!", 60);

        } else {
            d.drawText(350, d.getHeight() / 2, Integer.toString(downCounting - 1), 100);
        }
        this.downCounting--;


    }

    /**
     * This mathod returns true if the animation should be stopes and false otherwise.
     *
     * @return true if the animation should be stopes and false otherwise
     */
    public boolean shouldStop() {
        return !this.running;
    }
}
