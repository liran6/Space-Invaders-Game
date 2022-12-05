

import java.util.Map;
import java.util.TreeMap;

/**
 * this class is a factory that produces blocks from symbols.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Block> blocks;
    private Map<String, Integer> spacerWidths;

    /**
     * constructs a BlocksFromSymbolsFactory object from two maps:
     * first map: string -> block.
     * second map: string -> integer.
     *
     * @param blocks       the first map.
     * @param spacerWidths the second map.
     */
    public BlocksFromSymbolsFactory(Map<String, Block> blocks,
                                    Map<String, Integer> spacerWidths) {
        this.blocks = blocks;
        this.spacerWidths = spacerWidths;
    }

    /**
     * constructs a new BlocksFromSymbolsFactory object.
     */
    public BlocksFromSymbolsFactory() {
        this.blocks = new TreeMap<String, Block>();
        this.spacerWidths = new TreeMap<String, Integer>();
    }

    /**
     * this method gets a string and checks if
     * there is a mapping from it to a spacer.
     *
     * @param s the given string.
     * @return true if there is a mapping.
     */
    public boolean isSpaceSymbol(String s) {
        return spacerWidths.containsKey(s);
    }

    /**
     * this method gets a string and checks if
     * there is a mapping from it to a block.
     *
     * @param s the given string.
     * @return true if there is a mapping.
     */
    public boolean isBlockSymbol(String s) {
        return blocks.containsKey(s);
    }

    /**
     * this method gets a string and return the corresponding spacer width.
     *
     * @param s the given string.
     * @return the spacer width.
     */
    public int getSpacerWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * this method gets a string and two coordinates
     * and return the corresponding block.
     *
     * @param s the given string.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the corresponding block.
     */
    public Block getBlock(String s, int x, int y) {
        Block block = new Block(this.blocks.get(s));
        block.setUpperLeft(x, y);
        return block;
    }

    /**
     * this method gets a string and a block and put them in the blocks map.
     *
     * @param string the given string.
     * @param block  the given block.
     */
    public void putBlock(String string, Block block) {
        this.blocks.put(string, block);
    }

    /**
     * this method gets a string and a spacer width
     * and put them in the spacers map.
     *
     * @param string the given string.
     * @param num    the given spacer width.
     */
    public void putSpaceWidth(String string, Integer num) {
        this.spacerWidths.put(string, num);
    }
}
