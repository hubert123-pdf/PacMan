package src;
import resources.GameConsts;
import resources.Colors;
import src.ghosts.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Scene extends GameConsts implements ActionListener
{
    private JFrame frame;
    private MainPanel mainPanel;
    private JPanel scorePanel;
    private JLabel scoreLabel;

    private Player player;
    private Blinky blinky;
    private Clyde clyde;
    private Inky inky;
    private Pinky pinky;
    private Timer timer;
    private int highestScore;
    private int score;
    private int sumScore;     
    private int lives;
   
    private boolean isRunning;
    private char direction;

    public Scene() throws IOException
    {
        sumScore = 0;
        lives = LIVES;
        FileReader file = new FileReader("resources/highest_score.txt");
        BufferedReader buffereReader = new BufferedReader(file); 
        String line = buffereReader.readLine();
        highestScore = Integer.parseInt(line);
        file.close();
    }

    public void setScene() 
    {
        isRunning = false;
        
        setPanels();
    }

    private void startGame()throws IOException
    {
        isRunning = true;
        direction = 'R';
        timer = new Timer(DELAY, this);
        player = new Player();
        blinky = new Blinky();
        clyde = new Clyde();
        inky = new Inky();
        pinky = new Pinky();
        timer.start();
    }

    private boolean checkCollision()
    {
        if(player.getPositionX() == blinky.getPositionX() && player.getPositionY() == blinky.getPositionY()
        || player.getPositionX() == clyde.getPositionX() && player.getPositionY() == clyde.getPositionY()
        || player.getPositionX() == inky.getPositionX() && player.getPositionY() == inky.getPositionY()
        || player.getPositionX() == pinky.getPositionX() && player.getPositionY() == pinky.getPositionY())
            {
                return true;
            }
        return false;
    }

    private void setPanels()
    {
        scoreLabel = new JLabel();
        scoreLabel.setText("<html>Score: " + Integer.toString(score) +
                           "<br/>Your sum score: " + Integer.toString(sumScore) +
                           "<br/>Highest score: " + Integer.toString(highestScore) +
                           "<br/>Lives left: " + Integer.toString(lives) +
                           "</html>");
        scorePanel = new JPanel();  
        scorePanel.setPreferredSize(new Dimension(200, 0));
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
        frame.setSize(DIMENSION.width + 200, DIMENSION.height + NUM_OF_BLOCKS_Y + 3);
        frame.setLayout(new BorderLayout());
        frame.add(scorePanel,BorderLayout.EAST);
        frame.add(mainPanel,BorderLayout.WEST);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(isRunning)
        {
            player.move(direction);
            blinky.move(player.getPositionX(),player.getPositionY());
            pinky.move(player.getPositionX(),player.getPositionY());
            clyde.move(player.getPositionX(),player.getPositionY());
            inky.move(player.getPositionX(),player.getPositionY());
            if(POINTS_LOCATION[player.getPositionY()][player.getPositionX()] == 1)
            {
                score++;
                sumScore++;
                POINTS_LOCATION[player.getPositionY()][player.getPositionX()] = 0;
                scoreLabel.setText("<html>Score: " + Integer.toString(score) +
                           "<br/>Your sum score: " + Integer.toString(sumScore) +
                           "<br/>Highest score: " + Integer.toString(highestScore) +
                           "<br/>Lives left: " + Integer.toString(lives) +
                           "</html>");
            }
            if(checkCollision())
            {
                score = 0;
                lives--;
                isRunning = false;
                DELAY = Integer.MAX_VALUE;
                POINTS_LOCATION = Arrays.stream(MAP).map(int[]::clone).toArray(int[][]::new);
                if(lives == 0) 
                {
                    if(sumScore > highestScore)
                    {
                        highestScore = sumScore;
                        FileWriter file;
                        try 
                        {
                            file = new FileWriter("resources/highest_score.txt");
                            file.write(Integer.toString(sumScore));
                            file.close();
                        } 
                        catch (IOException e1)
                        {

                        }
                    }                    
                }
            }
            mainPanel.repaint();
        }
    }

    private class MyKeyAdapter extends KeyAdapter
    {
        @Override public void keyPressed(KeyEvent e)
        {
            if(!isRunning && e.getKeyChar() == ' ')
            {
                try 
                {
                    startGame();
                } 
                catch (IOException e1) 
                {
                    e1.printStackTrace();
                }
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
                if(lives == 0 )
                {
                    graphics.drawString("Your score: " + Integer.toString(sumScore),SCREEN_WIDTH / 2 - 250, SCREEN_HEIGHT / 2 - 100);
                    graphics.drawString("Press space to play again...",SCREEN_WIDTH / 2 - 250, SCREEN_HEIGHT / 2);
                    lives = 3;
                    sumScore = 0;
                    score = 0;
                }
                else
                {
                    graphics.drawString("Press space to start new round...",SCREEN_WIDTH / 2 - 320, SCREEN_HEIGHT / 2);
                }
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
                graphics.drawImage(blinky.getImage(), blinky.getPositionX() * BLOCK_SIZE, blinky.getPositionY() * BLOCK_SIZE, this);
                graphics.drawImage(clyde.getImage(), clyde.getPositionX() * BLOCK_SIZE, clyde.getPositionY() * BLOCK_SIZE, this);
                graphics.drawImage(inky.getImage(), inky.getPositionX() * BLOCK_SIZE, inky.getPositionY() * BLOCK_SIZE, this);
                graphics.drawImage(pinky.getImage(), pinky.getPositionX() * BLOCK_SIZE, pinky.getPositionY() * BLOCK_SIZE, this);

            }
        }
    }
}
