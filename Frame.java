package Assignment1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class Frame extends JPanel {
//    private JLabel countLabel = new JLabel();
//    private JLabel timeLabel = new JLabel();
    private Ball[] balls = new Ball[50];
    //Repaint the scene with the current balls list.
    @Override
    public void paintComponent(Graphics g) {
        this.setBackground(Color.black);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Ball b : balls) {    //Paints each ball according to its current position, color, etc.
            if(b != null) {
                Ellipse2D circle = new Ellipse2D.Double(b.getx(), b.gety(), 20, 20);
                g2.setPaint(b.getColor());
                g2.fill(circle);
            }
        }
    }

    public void refresh(){
        //Frame toRefresh = this;

        this.repaint();
    }

    public void updateBallList(Ball[] b){
        this.balls = b;
    }

//    public void updateTotalLabel(int t, int r, int g, int b){
//        //This label shows the total number of balls left and how many of each color.
//        countLabel.setText("Count total: " + t + " r:" + r + " g:" + g + " b:" + b);
//        countLabel.setFont(new Font("Verdana", 1, 20));
//        countLabel.setForeground(Color.white);
//        countLabel.setSize(countLabel.getPreferredSize());
//        countLabel.setLocation(0, 0);
//        countLabel.setBackground(Color.black);
//        this.add(countLabel);
//    }
//    public void updateTimeLabel(int t){
//        timeLabel.setText("Time: " + (int) t);
//        timeLabel.setFont(new Font("Verdana", 1, 20));
//        timeLabel.setForeground(Color.white);
//        timeLabel.setSize(timeLabel.getPreferredSize());
//        timeLabel.setLocation(0, 30);
//        timeLabel.setBackground(Color.black);
//        this.add(timeLabel);
//    }



}//end frame
