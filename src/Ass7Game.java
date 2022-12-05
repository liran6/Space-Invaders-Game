import biuoop.DialogManager;
import biuoop.GUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Ass 7 game.
 *
 * @author liran baruch. Implementation of the Ass5 class.
 */


public class Ass7Game {
    private static final int FRAME_WITH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_PER_SECOND = 60;
    private static final int NUM_OF_LIFE = 7;

    /**
     * This is the main method that runs the game.
     *
     * @param args the commandline input- the levels of the game to be played.
     * @throws FileNotFoundException when the file can not be read.
     */
    public static void main(String[] args) throws FileNotFoundException {


        GUI gui = new GUI("Space Invaders", FRAME_WITH, FRAME_HEIGHT);
        DialogManager dialog = gui.getDialogManager();
        AnimationRunner ar = new AnimationRunner(gui, FRAME_PER_SECOND);
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        levels.add(new Level1());
        HighScoresTable highScoresTable = new HighScoresTable(5);
        File file = new File("highscores");
        if (file.exists()) {
            try {
                highScoresTable.load(file);
            } catch (IOException ex) {
                Logger.getLogger(Ass7Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Ass7Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>(
                "Space Invaders", gui.getKeyboardSensor(), ar);
        menu.addSelection("s", "start game", new StartGameTask(gui, ar, highScoresTable, levels
                , NUM_OF_LIFE, file));
        menu.addSelection("h", "High Scores", new ShowHiScoresTask(
                ar, gui.getKeyboardSensor(), highScoresTable));
        menu.addSelection("e", "Exit", new ExitTask(gui));
        while (true) {
            ar.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.reset();

        }
    }
}
