package src.scenes;
import src.Scene;
import resources.Colors;
import javax.swing.*;
import java.awt.*;

public class SecondScene extends Scene
{
    private int score;
    @Override
    public void setScene()
    {

        Dimension scoreDimension = new Dimension (200,0);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DIMENSION);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);


        JPanel scorePanel = new JPanel();  
        scorePanel.setPreferredSize(scoreDimension);
        scorePanel.setBackground(Colors.SCORE_BOARD);
        JLabel scoreLabel = new JLabel();
        scoreLabel.setText("Score: " + Integer.toString(score));
        scorePanel.add(scoreLabel);

        frame.add(scorePanel,BorderLayout.EAST);
        frame.setTitle("Pac-Mac");
        frame.getContentPane().setBackground(Colors.BACKGROUND);


    }
}