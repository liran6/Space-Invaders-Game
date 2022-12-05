
import java.io.File;
import java.io.IOException;
import java.util.List;
import biuoop.GUI;

/**
 * this class represents a StartGameTask object.
 */
public class StartGameTask implements Task<Void> {
    private GUI gui;
    private AnimationRunner animationRunner;
    private HighScoresTable table;
    private List<LevelInformation> levels;
    private int lives;
    private File highScoresFile;

    /**
     * construct a StartGameTask from a GUI, an animationRunner,
     * a highScoresTable, a levelInformation list,
     * a number of lives and a highScoresFile.
     *
     * @param gui             the given GUI.
     * @param animationRunner the given animationRunner.
     * @param table           the given highScoresTable.
     * @param levels          the given levelInformation list.
     * @param lives           the number of lives.
     * @param highScoresFile  the given highScores file.
     */
    public StartGameTask(GUI gui, AnimationRunner animationRunner,
                         HighScoresTable table, List<LevelInformation> levels,
                         int lives, File highScoresFile) {
        this.gui = gui;
        this.animationRunner = animationRunner;
        this.table = table;
        this.levels = levels;
        this.lives = lives;
        this.highScoresFile = highScoresFile;
    }

    /**
     * this method runs this task.
     *
     * @return unimplemented option.
     */

    public Void run() {
//        File fileToBe = new File("resources/level_sets.txt");
//        InputStreamReader inputStreamReader;
//        try {
//            inputStreamReader= new InputStreamReader( // bytes to characters wrapper
//                            new FileInputStream(fileToBe)); // binary file stream
//            LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
//            List<LevelInformation> list = levelSpecificationReader.fromReader(inputStreamReader);
        GameFlow game = new GameFlow(this.gui, this.animationRunner,
                this.gui.getKeyboardSensor(), this.lives,
                this.table, this.gui.getDialogManager(), this.highScoresFile);
        game.runLevels(this.levels);
        try {
            table.save(this.highScoresFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        } catch (IOException e) {
//            System.out.println("exception caught");
//        } catch (ErrorException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
