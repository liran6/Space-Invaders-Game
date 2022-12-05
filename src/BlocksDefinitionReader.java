
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

/**
 * this class is a block definition reader.
 */
public class BlocksDefinitionReader {
    /**
     * this method gets a reader object and returns
     * a new BlocksFromSymbolsFactory object.
     *
     * @param reader the given reader.
     * @return a new BlocksFromSymbolsFactory object.
     * @throws Exception if an error occurs.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader)
            throws Exception {
        BlocksFromSymbolsFactory blockFactory = new BlocksFromSymbolsFactory();
        LineNumberReader lineNumberReader = new LineNumberReader(reader);
        String line;
        int lineNum = lineNumberReader.getLineNumber();
        try {
            Map<String, String> blockMap = new TreeMap<String, String>();
            Map<String, String> defaultMap = new TreeMap<String, String>();
            Map<String, String> tmpMap = new TreeMap<String, String>();
            while ((line = lineNumberReader.readLine()) != null) {
                line = line.trim();
                if (!line.equals("") && !line.startsWith("#")) {
                    if (line.startsWith("default")) {
                        line = line.substring("default".length()).trim();
                        defaultMap = parseLine(line, lineNum);
                    } else if (line.startsWith("bdef")) {
                        line = line.substring("bdef".length()).trim();
                        tmpMap.clear();
                        tmpMap = parseLine(line, lineNum);
                        blockMap.clear();
                        blockMap.putAll(defaultMap);
                        blockMap.putAll(tmpMap);
                        if (blockMap.containsKey("symbol") && blockMap.containsKey("width")
                                && blockMap.containsKey("height") && blockMap.containsKey("hit_points")) {
                            String symbol = blockMap.get("symbol");
                            Block block = new Block();
                            try {
                                if (Integer.valueOf(blockMap.get("width")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                if (Integer.valueOf(blockMap.get("height")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                if (Integer.valueOf(blockMap.get("hit_points")) <= 0) {
                                    throw new NumberFormatException();
                                }
                                block.setWidth(Integer.valueOf(blockMap.get("width")));
                                block.setHeight(Integer.valueOf(blockMap.get("height")));
                                block.setHits(Integer.valueOf(blockMap.get("hit_points")));
                            } catch (Exception e) {
                                        System.out.println("Error in reading file In line " + lineNum + ": " + line);
                            }
                            if (blockMap.containsKey("stroke")) {
                                block.setBorderColor(ColorParser.colorFromString(blockMap.get("stroke"), lineNum));
                                block.setBorder(true);
                            } else {
                                block.setBorder(false);
                            }
                            Map<Integer, Color> colors = new TreeMap<Integer, Color>();
                            Map<Integer, Image> images = new TreeMap<Integer, Image>();
                            if (blockMap.containsKey("fill")) {
                                blockMap.put("fill-0", blockMap.get("fill"));
                                blockMap.remove("fill");
                            }
                            for (int i = 0; i <= Integer.valueOf(
                                    blockMap.get("hit_points")); i++) {
                                if (blockMap.containsKey("fill-" + i)) {
                                    String fillString = blockMap.get("fill-" + i);
                                    if (fillString.startsWith("image(")) {
                                        fillString = fillString.substring("image(".length());
                                        fillString = fillString.replace(")", "");
                                        Image image = ImageIO.read(ClassLoader.getSystemClassLoader()
                                                .getResourceAsStream(fillString));
                                        images.put(i, image);
                                    } else if (fillString.startsWith("color(")) {
                                        Color color = ColorParser.colorFromString(fillString, lineNum);
                                        colors.put(i, color);
                                    } else {
                                        throw new Exception("Error in reading file At line " + lineNum + ": " + line);
                                    }
                                }
                            }
                            block.setColors(colors);
                            block.setImages(images);
                            blockFactory.putBlock(symbol, block);
                        } else {
                            throw new Exception("Error in reading file");
                        }
                    } else if (line.startsWith("sdef")) {
                        line = line.substring("sdef".length()).trim();
                        Map<String, String> spacerMap = parseLine(line, lineNum);
                        if (spacerMap.containsKey("symbol") && spacerMap.containsKey("width")) {
                            String symbol = spacerMap.get("symbol");
                            Integer width = Integer.valueOf(spacerMap.get("width"));
                            blockFactory.putSpaceWidth(symbol, width);
                        } else {
                            throw new Exception("Error in reading file");
                        }
                    } else {
                        throw new Exception(
                                "Error in reading file At line " + lineNum + ": " + line + "Invallid line");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error in reading file");
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (IOException e) {
                    System.out.println("Error in closing file");
                }
            }
        }
        return blockFactory;
    }

    /**
     * this method gets a string and a line number and returns
     * the information organized as a map.
     *
     * @param line    the given line.
     * @param lineNum the line number.
     * @return an organized map with the line information.
     * @throws Exception if the line is not valid.
     */
    private static Map<String, String> parseLine(String line, int lineNum)
            throws Exception {
        Map<String, String> returnVal = new TreeMap<String, String>();

        String[] pairs = line.split(" ");
        for (String pair : pairs) {
            String[] parts = pair.split(":");
            if (parts.length != 2) {
                throw new Exception(
                        "Error in reading file At line " + lineNum + ": " + line + "Parts must be 2");
            }
            returnVal.put(parts[0], parts[1]);
        }
        return returnVal;
    }

}
