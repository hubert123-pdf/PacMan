package src;

import java.awt.*;

abstract public class Scene {
    protected final int SIZE = 3;
    protected final int BLOCK_SIZE = 8;
    protected final int NUM_OF_BLOCKS_X = 28;
    protected final int NUM_OF_BLOCKS_Y = 36; 
    protected final Dimension DIMENSION = new Dimension(NUM_OF_BLOCKS_X * BLOCK_SIZE * SIZE + 300, NUM_OF_BLOCKS_Y * BLOCK_SIZE * SIZE + 100);
    public abstract void setScene();
    public void swapScenes()
    {
        
    }
}
