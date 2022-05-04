package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;


public class Clyde extends Ghost
{
    private int movesInOneDirection;
    private int lastMove;
    Threader  thread;

    public Clyde() throws IOException 
    {   
        lastMove = 0;
        movesInOneDirection = 3;
        x = GameConsts.NUM_OF_BLOCKS_X / 2 + 7;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 - 11;
        paintGhost();
    }

    @Override
    public void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/clyde.png"));
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
            switch (lastMove) {
                case 0:
                    if(GameConsts.MAP[y][x + 1] == 1)
                    {
                        x++;
                    }
                    break;
                case 1:
                    if(GameConsts.MAP[y + 1][x] == 1)
                    {
                        y++;
                    }
                    break;
                case 2:
                    if(GameConsts.MAP[y][x - 1] == 1)
                    {
                        x--;
                    }
                    break;
                case 3:
                    if(GameConsts.MAP[y - 1][x] == 1)
                    {
                        y--;
                    }
                    break;
                default:
                    break;
            }
            movesInOneDirection--;
            if(movesInOneDirection == 0)
            {
                lastMove = getRandomDirection(0, 4);
                movesInOneDirection = 3;
            }
        }
    }
}
