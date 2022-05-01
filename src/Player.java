package src;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player {
    private int x, y;
    private BufferedImage pacManImage;
    private Image pacManResized;

    public Player(int blockSize) throws IOException
    {
        this.x = 1;
        this.y = 1;
        pacManImage = ImageIO.read(new File("resources/images/pac_man.png"));
        pacManResized = pacManImage.getScaledInstance(blockSize, blockSize, Image.SCALE_DEFAULT);
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
}
