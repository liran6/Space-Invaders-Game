
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.io.File;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {
    private GUI gui;
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private int numOfLives;
    private int score;
    private boolean won;
    private HighScoresTable scores;
    private DialogManager dialog;
    private File file;
    private int horizontalBound;
    private int verticalBound;


    /**
     * Instantiates a new Game flow.
     *
     * @param gui        the gui
     * @param ar         the ar
     * @param ks         the ks
     * @param numOfLives the num of lives
     * @param scores     the scores
     * @param dialog     the dialog
     * @param file       the file
     */
    public GameFlow(GUI gui, AnimationRunner ar, KeyboardSensor ks, int numOfLives, HighScoresTable scores
            , DialogManager dialog, File file) {
        this.ar = ar;
        this.ks = ks;
        this.numOfLives = numOfLives;
        this.score = 0;
        this.won = true;
        this.scores = scores;
        this.dialog = dialog;
        this.file = file;
        this.horizontalBound = gui.getDrawSurface().getWidth();
        this.verticalBound = gui.getDrawSurface().getHeight();

    }

    /**
     * This mathod runs the levels of the game.
     *
     * @param levels the levels to be run
     */
    public void runLevels(List<LevelInformation> levels) {
        int enemyPlatoonSpeed = 100;
        int levelNumber = 1;
        // for (LevelInformation levelInfo : levels) {
        while (true) {

            GameLevel level = new GameLevel(gui, levels.get(0), this.score, this.numOfLives, this.ks, this.ar
                    , enemyPlatoonSpeed);
//            new Dimensions(this.horizontalBound, this.verticalBound)
            level.initialize();
            this.ar.run(level);
            this.score = level.getScore().getValue();
            this.numOfLives = level.getNumberOfLives().getValue();

            while (level.getNumberOfLives().getValue() > 0) {
//                if (level.getBlockCount() > levelInfo.blocks().size() - levelInfo.numberOfBlocksToRemove()) {
                level.playOneTurn();
//                }
                if (level.getNumberOfLives().getValue() == 0) {
                    this.won = false;
                    this.score = level.getScore().getValue();
                    break;
                }
                if (level.getBlockCount() == 0) {
                    this.score = level.getScore().getValue();
                    this.numOfLives = level.getNumberOfLives().getValue();
                    enemyPlatoonSpeed += 10;
                    break;

                }

                // if (level.getNumberOfLives().getValue() == 0) {
                //   this.won = false;
                // this.score = level.getScore().getValue();
                //break;
                //}


            }
            if (level.getNumberOfLives().getValue() == 0) {
                this.won = false;
                this.score = level.getScore().getValue();
                break;
            }
        }

        this.ar.run(new KeyPressStoppableAnimation(new EndScreen(this.won, this.score), this.ks, "space"));
        if (this.scores.getRank(this.score) <= this.scores.size()) {
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, this.score);
            this.scores.add(scoreInfo);
            try {
                this.scores.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.ar.run(new KeyPressStoppableAnimation(new HighScoresAnimation(scores), this.ks, "q"));

    }
}
