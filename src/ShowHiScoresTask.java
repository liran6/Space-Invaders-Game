
import biuoop.KeyboardSensor;

/**
 * this class represents a ShowHiScoresTask object.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private HighScoresTable table;

    /**
     * constructs a ShowHiScoresTask object from an animationRunner,
     * a keyboardSensor and a highScoresTable.
     *
     * @param animationRunner the given AnimationRunner.
     * @param keyboardSensor  the given KeyboardSensor.
     * @param table           the given HighScoresTable.
     */
    public ShowHiScoresTask(AnimationRunner animationRunner,
                            KeyboardSensor keyboardSensor,
                            HighScoresTable table) {
        this.animationRunner = animationRunner;
        this.keyboardSensor = keyboardSensor;
        this.table = table;
    }

    /**
     * this method runs this task.
     *
     * @return unimplemented option.
     */
    public Void run() {
        this.animationRunner.run(new KeyPressStoppableAnimation(
                new HighScoresAnimation(table), this.keyboardSensor, "q"));
        return null;
    }
}

