package src;
import resources.GameConsts;
import resources.Colors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Scene extends GameConsts implements ActionListener
{
    private JFrame frame;
    private MainPanel mainPanel;
    private JPanel scorePanel;
    private JLabel scoreLabel;
    private Player player;
    private Timer timer;
    private int score;        
    private boolean isRunning;
    private char direction;

    public void setScene() throws IOException
    {
        isRunning = false;
        player = new Player(BLOCK_SIZE);
        setPanels();
    }

    private void startGame()
    {
        isRunning = true;
        direction = 'R';
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(isRunning)
        {
            move();
            mainPanel.repaint();
        }
        else
        {

        }
    }

    private void move()
    {
        switch (direction) {
            case 'U':
                if(MAP[player.getPositionY() - 1][player.getPositionX()] == 1)
                {
                    player.setPositionY(player.getPositionY() - 1);
                }
                break;

            case 'D':
                if(MAP[player.getPositionY() + 1][player.getPositionX()] == 1)
                {
                    player.setPositionY(player.getPositionY() + 1);
                }
                break;

            case 'L':
                if(MAP[player.getPositionY()][player.getPositionX() - 1] == 1)
                {
                    player.setPositionX(player.getPositionX() - 1);
                }
                break;

            case 'R':
                if(MAP[player.getPositionY()][player.getPositionX() + 1] == 1)
                {
                    player.setPositionX(player.getPositionX() + 1);
                }
                break;

            default:
                break;
        }
        if(POINTS_LOCATION[player.getPositionY()][player.getPositionX()] == 1)
        {
            score++;
            POINTS_LOCATION[player.getPositionY()][player.getPositionX()] = 0;
            scoreLabel.setText("Score: " + Integer.toString(score));
        }
    }

    private void setPanels()
    {
        scoreLabel = new JLabel();
        scoreLabel.setText("Score: " + Integer.toString(score));
        scorePanel = new JPanel();  
        scorePanel.setPreferredSize(new Dimension(100, 0));
        scorePanel.setBackground(Colors.SCORE_BOARD);
        scorePanel.add(scoreLabel);

        mainPanel = new MainPanel();
        mainPanel.setPreferredSize(DIMENSION);
        mainPanel.setBackground(Colors.BACKGROUND);

        frame = new JFrame();
        frame.setFocusable(true);
        frame.addKeyListener(new MyKeyAdapter());
        frame.setTitle("Pac-Mac");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DIMENSION.width + 100, DIMENSION.height + NUM_OF_BLOCKS_Y + 3);
        frame.setLayout(new BorderLayout());
        frame.add(scorePanel,BorderLayout.EAST);
        frame.add(mainPanel,BorderLayout.WEST);
        frame.setVisible(true);
    }

    private class MyKeyAdapter extends KeyAdapter
    {
        @Override public void keyPressed(KeyEvent e)
        {
            if(!isRunning && e.getKeyChar() == ' ')
            {
                startGame();
                System.out.println(e.getKeyChar());
            }
            else if(isRunning)
            {
                if(e.getKeyChar() == 's')
                {
                    direction = 'D';
                }
                else if(e.getKeyChar() == 'w')
                {
                    direction = 'U';
                }
                else if(e.getKeyChar() == 'd')
                {
                    direction = 'R';
                }
                else if(e.getKeyChar() == 'a')
                {
                    direction = 'L';
                }
            }
        }
    }


    private class MainPanel extends JPanel 
    {   
        @Override
        public void paint(Graphics graphics) 
        {
            super.paintComponent(graphics);     
            BufferedImage image;
            try 
            {
                image = ImageIO.read(new File("resources/images/pacman_layout.png"));
                Image newImage = image.getScaledInstance(DIMENSION.width, DIMENSION.height, Image.SCALE_DEFAULT);
                graphics.drawImage(newImage, 0, 0, this);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            if(!isRunning)
            {
                graphics.setFont(new Font("Dialog", Font.PLAIN, 40)); 
                graphics.setColor(Color.WHITE);
                graphics.drawString("Press space to start...",SCREEN_WIDTH / 2 - 200, SCREEN_HEIGHT / 2);
            }
            else
            {
                graphics.setColor(Colors.PACMAN);
                for(int i = 0; i < POINTS_LOCATION.length; i++)
                {
                    for(int j = 0; j < POINTS_LOCATION[i].length; j++)
                    {
                        if(POINTS_LOCATION[i][j] == 1)
                        {
                            graphics.fillOval(BLOCK_SIZE * j + 8, BLOCK_SIZE * i + 8, BLOCK_SIZE / 2, BLOCK_SIZE / 2);
                        }
                    }
                }
                graphics.drawImage(player.getImage(), player.getPositionX() * BLOCK_SIZE, player.getPositionY() * BLOCK_SIZE, this);
            }
        }
    }
}
