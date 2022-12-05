import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The type High scores table.
 */
class HighScoresTable implements Serializable {
    private int size;
    private List<ScoreInfo> highScore;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.size = size;
        highScore = new ArrayList<ScoreInfo>();
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        int rank = this.getRank(score.getScore());
        if (this.highScore.isEmpty()) {
            highScore.add(score);
        } else {
            if (rank <= this.size) {
                this.highScore.add(rank - 1, score);
            }
        }
        while (this.highScore.size() > this.size) {
            this.highScore.remove(this.highScore.size() - 1);
        }
    }

    /**
     * Size int.
     *
     * @return the int
     */
// Return table size.
    public int size() {
        return this.size;
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.highScore;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        int rank = 1;
        for (int i = 0; i < this.highScore.size(); i++) {
            if (score < this.highScore.get(i).getScore()) {
                rank++;
            }
        }
        return rank;
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.highScore.clear();
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename.getName()));
            HighScoresTable highScoresTable = (HighScoresTable) objectInputStream.readObject();
            //     highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.highScore = highScoresTable.highScore;
            this.size = highScoresTable.size;
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file: " + filename.getName());
            HighScoresTable emptyTable = new HighScoresTable(5);
            emptyTable.save(filename);
            this.highScore = emptyTable.highScore;
            this.size = emptyTable.size;
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find class for object in file: "
                    + filename.getName());
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: "
                        + filename.getName());
            }
        }
    }

    /**
     * this method saves table data to the specified file.
     *
     * @param filename the given filename.
     * @throws IOException if there was an error with writing to the file.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    new FileOutputStream(filename.getName()));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: "
                        + filename.getName());
            }
        }
    }

    /**
     * this methods reads a table from file and return it.
     *
     * @param filename the given filename.
     * @return a new table with the values according to the data in the file.
     * f the file does not exist, or there is a problem with reading it, an empty table is returned.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable emptyTable = new HighScoresTable(5);
        try {
            if (!filename.exists()) {
                return emptyTable;
            }
            emptyTable.load(filename);
        } catch (IOException e) {
            System.err.println("Failed closing file: " + filename.getName());
            return new HighScoresTable(5);
        }
        return emptyTable;
    }
}