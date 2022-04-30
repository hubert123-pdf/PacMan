package src.scenes;
import src.Scene;
import resources.Colors;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FirstScene extends Scene
{
    @Override
    public void setScene()
    {
        JLabel title = new JLabel();
        title.setText("Press space to start...");
        title.setForeground(Colors.PACMAN);
        title.setBorder(new EmptyBorder(0, DIMENSION.width / 2 - 75, 0 ,0));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(DIMENSION);
        frame.setLayout(new BorderLayout());
        frame.add(title, BorderLayout.CENTER);
        frame.setTitle("Pac-Mac");
        frame.getContentPane().setBackground(Colors.BACKGROUND);

        JButton jButton = new JButton();
        jButton.setOpaque(false);
        jButton.setContentAreaFilled(false);
        jButton.setBorderPainted(false);
        jButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                frame.setVisible(false);
            }
        });
        frame.add(jButton, BorderLayout.NORTH);
    }
}