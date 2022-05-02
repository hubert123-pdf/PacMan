package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;


public class Clyde extends Ghost
{
    public Clyde() throws IOException 
    {
        x = GameConsts.NUM_OF_BLOCKS_X / 2 - 2;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 - 1;
        paintGhost();
    }
    @Override
    public void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/clyde.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }
    @Override
    public void move(int playerPositionX, int playerPositionY) {
        // TODO Auto-generated method stub
        
    }
    
}
