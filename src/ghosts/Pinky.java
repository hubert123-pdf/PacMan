package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

public class Pinky extends Ghost
{
    private int chaseMoves, waitMoves;
    private Threader thread;
    public Pinky() throws IOException 
    {
        chaseMoves = 20;
        waitMoves = 5;
        x = GameConsts.NUM_OF_BLOCKS_X / 2 - 5;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 + 4;
        paintGhost();
    }

    @Override
    public void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/pinky.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

    @Override
    public void move(int playerPositionX, int playerPositionY) 
    {
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        thread = new Threader();
        thread.start();
    }

    private class Threader extends Thread
    {
        @Override
        public void run()
        {
            double currentDistanceToPlayer = calculateDistanceToPlayer(x, y);
            if(currentDistanceToPlayer < 10 && chaseMoves > 0)
            {
                if(calculateDistanceToPlayer(x + 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x + 1] == 1)
                {
                    x++; 
                }
                else if(calculateDistanceToPlayer(x - 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x - 1] == 1)
                {
                    x--;
                }
                else if(calculateDistanceToPlayer(x, y + 1) < currentDistanceToPlayer && GameConsts.MAP[y + 1][x] == 1)
                {
                    y++;
                }
                else if(calculateDistanceToPlayer(x, y - 1) < currentDistanceToPlayer && GameConsts.MAP[y - 1][x] == 1)
                {
                    y--;
                }
                chaseMoves--;
            }
            else
            {
                waitMoves--;
                if(waitMoves < 0)
                {
                    chaseMoves = 20;
                    waitMoves = 5;
                }
            }

        }
    }
}
