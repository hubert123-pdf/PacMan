package src;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public abstract class Ghost 
{
    protected BufferedImage ghostImage;
    protected Image resizedGhostImage;
    protected int x, y; 
    protected int playerPositionX, playerPositionY;
    protected abstract void paintGhost() throws IOException;
    protected abstract void move(int playerPositionX, int playerPositionY);
    public int getPositionX()
    {
        return x;
    }

    public int getPositionY()
    {
        return y;
    }

    public void setPositionX(int x)
    {
        this.x = x;
    }

    public void setPositionY(int y)
    {
        this.y = y;
    }
    protected Image getImage()
    {
        return resizedGhostImage;
    }

    protected double calculateDistanceToPlayer(int posX, int posY)
    {
        return Math.sqrt(Math.pow((posX - playerPositionX), 2) + Math.pow((posY - playerPositionY), 2));   
    }

    protected int getRandomDirection(int min, int max)
    { 
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    
}
