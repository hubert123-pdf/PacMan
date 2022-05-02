package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

public class Blinky extends Ghost
{
    private int chaseMoves, retreatMoves;
    boolean chaseOrRetreat;
    private Threader thread;
    private int playerPositionX, playerPositionY;

    public Blinky() throws IOException 
    {
        chaseMoves = 0;
        retreatMoves = 0;
        chaseOrRetreat = true; //chase
        x = GameConsts.NUM_OF_BLOCKS_X / 2 + 1;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 - 4;
        paintGhost();
    }

    @Override
    public void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/blinky.png"));
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
            if(chaseOrRetreat)
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
                chaseMoves++;
                if(chaseMoves == 13)
                {
                    chaseOrRetreat = false;
                    chaseMoves = 0;
                }
            }
            else
            {
                if(calculateDistanceToPlayer(x + 1, y) > currentDistanceToPlayer && GameConsts.MAP[y][x + 1] == 1)
                {
                    x++;
                }
                else if(calculateDistanceToPlayer(x - 1, y) > currentDistanceToPlayer && GameConsts.MAP[y][x - 1] == 1)
                {
                    x--;
                }
                else if(calculateDistanceToPlayer(x, y + 1) > currentDistanceToPlayer && GameConsts.MAP[y + 1][x] == 1)
                {
                    y++;
                }
                else if(calculateDistanceToPlayer(x, y - 1) > currentDistanceToPlayer && GameConsts.MAP[y - 1][x] == 1)
                {
                    y--;
                }
                retreatMoves++;
                if(retreatMoves == 3)
                {
                    chaseOrRetreat = true;
                    retreatMoves = 0;
                }
            }
        }
    }
    private double calculateDistanceToPlayer(int posX, int posY)
    {
        return Math.sqrt(Math.pow((posX - playerPositionX), 2) + Math.pow((posY - playerPositionY), 2));   
    }
}
