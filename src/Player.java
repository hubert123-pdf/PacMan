package src;

import resources.GameConsts;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
/**
 * PacMan layout and movement implementation. 
 */
public class Player {
    private int x, y;
    private BufferedImage pacManImage;
    private Image pacManResized;

    /**
     * Constructs PacMan with his layout and start position.
     * @throws IOException
     */
    public Player() throws IOException
    {
        this.x = 1;
        this.y = 1;
        pacManImage = ImageIO.read(new File("resources/images/pac_man.png"));
        pacManResized = pacManImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

    /** 
     * Returns x position of PacMan.
     */
    public int getPositionX()
    {
        return x;
    }

    /** 
     * Returns y position of PacMan.
     */
    public int getPositionY()
    {
        return y;
    }

    /** 
     * Sets new x position of PacMan.
     * @param x Position to put x of PacMan in 2D map.
     */
    public void setPositionX(int x)
    {
        this.x = x;
    }

    /** 
     * Sets new y position of PacMan.
     * @param y Position to put y of PacMan in 2D map.
     */
    public void setPositionY(int y)
    {
        this.y = y;
    }

    /**
     * Returns PacMan image, in size of a single map block. 
     */
    public Image getImage()
    {
        return pacManResized;
    }

    /**
     * This method sets new position of PacMan in the next tick of a game clock.
     * @param direction Direction in witch PacMan will be moving - Up, Down, Left or Right.
     */
    public void move(char direction)
    {
        {
            switch (direction) 
            {
                case 'U':
                    if(GameConsts.MAP[y - 1][x] == 1)
                    {
                        y = y - 1;
                    }
                    break;
    
                case 'D':
                    if(Scene.MAP[y + 1][x] == 1)
                    {
                        y = y + 1;
                    }
                    break;
    
                case 'L':
                    if(Scene.MAP[y][x - 1] == 1)
                    {
                        x = x - 1;
                    }
                    break;
    
                case 'R':
                    if(Scene.MAP[y][x + 1] == 1)
                    {
                        x = x + 1;
                    }
                    break;
    
                default:
                    break;
            }
        }
    }
}
