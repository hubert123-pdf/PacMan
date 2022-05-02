package src;

import resources.GameConsts;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player {
    private int x, y;
    private BufferedImage pacManImage;
    private Image pacManResized;

    public Player() throws IOException
    {
        this.x = 1;
        this.y = 1;
        pacManImage = ImageIO.read(new File("resources/images/pac_man.png"));
        pacManResized = pacManImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

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
        return pacManResized;
    }

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
