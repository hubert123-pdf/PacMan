package src;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Ghost 
{
    protected BufferedImage ghostImage;
    protected Image resizedGhostImage;
    protected int x, y; 
    protected int playerPositionX, playerPositionY;
    protected abstract void paintGhost() throws IOException;
    public abstract void move(int playerPositionX, int playerPositionY);
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
    public Image getImage()
    {
        return resizedGhostImage;
    }
    public double calculateDistanceToPlayer(int posX, int posY)
    {
        return Math.sqrt(Math.pow((posX - playerPositionX), 2) + Math.pow((posY - playerPositionY), 2));   
    }
}
