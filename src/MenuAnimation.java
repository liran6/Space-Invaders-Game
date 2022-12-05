
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

//import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private String menuTitle;
    private List<String> keys;
    private List<String> messages;
    private List<T> options;
    private List<Menu<T>> subMenus;
    private List<Boolean> isOption;
    private T status;
    private boolean close;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;

    /**
     * construct a menu animation object from a menuTitle,
     * a keyboardSensor and an animationRunner.
     *
     * @param menuTitle       the given menuTitle.
     * @param keyboardSensor  the given keyboardSensor.
     * @param animationRunner the given animationRunner.
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor,
                         AnimationRunner animationRunner) {
        this.menuTitle = menuTitle;
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        this.options = new ArrayList<T>();
        this.subMenus = new ArrayList<Menu<T>>();
        this.isOption = new ArrayList<Boolean>();
        this.close = false;
        this.keyboardSensor = keyboardSensor;
        this.animationRunner = animationRunner;
    }

    /**
     * this method draws the current state of
     * the animation object to the screen.
     *
     * @param d  a draw surface to draw on.
     * @param dt the dt value of this game.
     */
    public void doOneFrame(DrawSurface d, double dt) {
//        d.setColor(Color.black);
//        d.fillRectangle(0, 0, 800, 600);
//        d.setColor(Color.white);
//        d.drawText(300,  50, "Arknoid Game", 48);
//        for (int i = 1; i <= scores.getHighScores().size();i++){
//            d.drawText(30, 140+ (i * 40), i + ") " + this.scores.getHighScores().get(i-1).getName(),30 );
//            d.drawText(400, 140+ (i * 40),""+this.scores.getHighScores().get(i-1).getScore(),30 );
//        }
//
//        d.drawText(300,d.getHeight() - 50," press 'q' to quite", 30);
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        //d.setColor(Color.decode("#1e7f00"));
        //d.fillRectangle(0, d.getHeight() / 2 - 160, d.getWidth(), 90);
        d.setColor(Color.BLACK);
        d.fillRectangle(0, d.getHeight() / 2 - 160, d.getWidth(), 3);
        d.drawText(260, d.getHeight() / 2 - 90, this.menuTitle, 70);
        d.fillRectangle(0, d.getHeight() / 2 - 70, d.getWidth(), 3);
        for (int i = 0; i < this.keys.size(); i++) {
            d.drawText(260, 200 + 40 * (i + 2), "("
                    + this.keys.get(i) + ") " + this.messages.get(i), 30);
        }
        for (int i = 0; i < this.keys.size(); i++) {
            if (this.keyboardSensor.isPressed(this.keys.get(i))) {
                if (this.isOption.get(i)) {
                    this.status = this.options.get(i);
                    this.close = true;
                } else {
                    this.animationRunner.run(this.subMenus.get(i));
                    this.status = this.subMenus.get(i).getStatus();
                    this.subMenus.get(i).reset();
                    this.close = true;
                    break;
                }
            }
        }
    }

    /**
     * this method adds a selection to this menu.
     *
     * @param key       the key of the selection.
     * @param message   the name of the selection.
     * @param returnVal the selection value according to the menu type.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.options.add(returnVal);
        this.subMenus.add(null);
        this.isOption.add(true);
    }

    /**
     * this method returns the selected value after the occurrence
     * of a selection event, according to the menu type.
     *
     * @return the selected value.
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * boolean function to close the running.
     * @return true or false.
     */
    public boolean shouldStop() {
        return this.close;
    }

    /**
     * reset the boolean variables.
     */
    public void reset() {
        this.status = null;
        this.close = false;
    }

    /**
     * adding the sub menu.
     * @param key     the key
     * @param message the message
     * @param subMenu the sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.subMenus.add(subMenu);
        this.options.add(null);
        this.isOption.add(false);
    }
}
