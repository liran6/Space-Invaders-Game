

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is a level set reader.
 */
public class LevelSetsReader {
    /**
     * this method gets a reader object and returns a list of
     * MenuSelection objects.
     * @param reader a reader to read from.
     * @return a list of LevelInformation objects.
     * @throws Exception if an error occurs.
     */
    public static List<MenuScreenOptions> fromReader(Reader reader)
    throws Exception {
        List<MenuScreenOptions> returnVal = new ArrayList<MenuScreenOptions>();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        int lineNum = lineNumberReader.getLineNumber();
        List<LevelInformation> levelSet;
        try {
            String key = null;
            String message = null;
            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() % 2 != 0) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        key = parts[0];
                        message = parts[1];
                    } else {
                        throw new Exception(
                                "Error in reading file At line " + lineNum + ": " + line + "Parts must be 2");
                    }
                } else {
                    Reader levelsReader = new InputStreamReader(ClassLoader.getSystemClassLoader().
                            getResourceAsStream(line));
//                            new FileReader(line);
                    levelSet =
                            LevelSpecificationReader.fromReader(levelsReader);
                    returnVal.add(new MenuScreenOptions(key, message, levelSet));
                }
            }
        } catch (IOException e) {
            throw new Exception(
                    "Error in reading file LevelSetsReader");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new Exception(
                            "Error in closing file LevelSetsReader");
                }
            }
        }
        return returnVal;
    }
}
