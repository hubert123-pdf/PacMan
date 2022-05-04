package src;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
/**
 * Abstract class from which every type of ghost will inherit.
 * 
 */
public abstract class Ghost 
{
    protected BufferedImage ghostImage;
    protected Image resizedGhostImage;
    protected int x, y; 
    protected int playerPositionX, playerPositionY;
    /**
     * Sets specific layout to the ghost.
     * @throws IOException
     */
    protected abstract void paintGhost() throws IOException;
    /**
     * Moves ghost to the new position and starts new thread. 
     */
    protected abstract void move(int playerPositionX, int playerPositionY);

    /**
     * 
     * Returns x position of a ghost.
     */
    public int getPositionX()
    {
        return x;
    }

    /**
     * 
     * Returns y position of a ghost.
     */
    public int getPositionY()
    {
        return y;
    }

    /** 
     * Sets new x position of the ghost.
     * @param x Position to put x of the ghost in 2D map.
     */
    public void setPositionX(int x)
    {
        this.x = x;
    }

    /** 
     * Sets new y position of the ghost.
     * @param y Position to put y of the ghost in 2D map.
     */
    public void setPositionY(int y)
    {
        this.y = y;
    }

    /** 
     * Returns ghost image in size of a single map block.
     */
    protected Image getImage()
    {
        return resizedGhostImage;
    }

    /**
     * Returns distance from ghost to player.
     * @param posX Position x of the ghost.
     * @param posY Position y of the ghost.
     */
    protected double calculateDistanceToPlayer(int posX, int posY)
    {
        return Math.sqrt(Math.pow((posX - playerPositionX), 2) + Math.pow((posY - playerPositionY), 2));   
    }

    /**
     * Randomly returns one of four directions as int. 
     */
    protected int getRandomDirection(int min, int max)
    { 
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }    
}
