package Assignment1;

//CSC-375 Assignment 1
//Brian Dorsey - 2016
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.*;

public class Assignment1 {

    private static Ball[] balls = new Ball[50];
    private static CountDownLatch latch = new CountDownLatch(50);


    //Maintenence stuff
    public void printBallList() {
        for (Ball b : balls) {
            if (b == null) {
                break;
            }
            System.out.println("id: " + b.id
                    + " x: " + b.getx()
                    + " y: " + b.gety()
                    + " col: " + b.getColor()
                    + " vel: " + b.getVx());
        }
    }

    //Method to automatically and randomly populate the array of balls.
    public static void autoPopulate(int n) {
        for (int r = 0; r < n; r++) {
            Ball btemp = new Ball(r, randX(), randY(), randCol(), randVel(), randVel());
            balls[r] = btemp;
        }
    }

    //method to choose a random x position.
    private static int randX() {
        Random rand = new Random();
        int randomNum = rand.nextInt((600 - 20) + 1);
        return randomNum;
    }

    //method to choose a random y position.
    private static int randY() {
        Random rand = new Random();
        int randomNum = rand.nextInt((400 - 40) + 1);
        return randomNum;
    }

    //Method to choose a random velocity.
    private static int randVel() {
        Random rand = new Random();
        int randomNum = 0;
        while (randomNum == 0) {
            randomNum = rand.nextInt(3);
        }
        return randomNum;
    }

    //Method to choose a random color.
    private static Color randCol() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1);

        Color returnCol;
        if (randomNum == 1) {
            returnCol = Color.red;
        } else if (randomNum == 2) {
            returnCol = Color.blue;
        } else {
            returnCol = Color.green;
        }
        return returnCol;
    }

    //Finds out how many of this color ball are on the map and alive.
    private static int findCount(Color color) {
        int count = 0;
        for (Ball b : balls) {
            if (b.getColor() == color && b.getDead() != true) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws InterruptedException {

        long tStart = System.currentTimeMillis();
        double time = 0;
        int total = 50;

        Frame s = new Frame();
        JFrame f = new JFrame();
        f.setResizable(false); //No resizing.

        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Moving Balls: R>G : G>B : B>R");//tite.
        f.add(s);
        autoPopulate(50);

        f.setVisible(true);//show the JFrame
        //s.printBallList();
        s.updateBallList(balls);
        SwingUtilities.invokeLater(() -> s.repaint());


        //JOptionPane.showMessageDialog(f, "Ready?", "Simulation Ready.", JOptionPane.WARNING_MESSAGE);

        for (int c = 0;; c++) {
            long tS = System.currentTimeMillis();


            refreshPos();
            try {
                latch.await();  // wait untill latch counted down to 0
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s.updateBallList(balls);
            SwingUtilities.invokeLater(() -> s.repaint());
            //s.refresh();

            if (total <= 1) {
                break;
            }
            long tE = System.currentTimeMillis();
            long tD = tE - tS;
            while (tD < 4) {
                //Thread.sleep(1);
                tE = System.currentTimeMillis();
                tD = tE - tS;
            }

            //Set counts for each ball still on screen.
            int rcount = findCount(Color.red);
            int gcount = findCount(Color.green);
            int bcount = findCount(Color.blue);

            total = rcount + gcount + bcount; // total balls.

            //This part lets the program display the elapsed time in seconds on the screen.
            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000;
            time = elapsedSeconds;


//            s.updateTimeLabel((int) time);
//            s.updateTotalLabel(total, rcount, gcount, bcount);

        }
        JOptionPane.showMessageDialog(f, "Winner: " + getWinner() + "\ntime: " + (int) time + "s", "Simulation Over", JOptionPane.WARNING_MESSAGE);


        f.setVisible(false);
        f.remove(s);
        System.exit(0);
    }

    //Method refreshes the ball position, the display labels, and the object counts.
    public static void refreshPos() throws InterruptedException {
        //refresh ball location, color, collision, etc.
        for (Ball b : balls) {
//            if (b.getDead() != true) {
                BallThread b1 = new BallThread();
                b1.setVars(b);
                b1.setBalls(balls, latch);
                Thread t1 = new Thread(b1);
                t1.start();
//            }else{
//                b.setColor(Color.white);
//            }
        }
    }

    public static String getWinner(){
        String col = "";
        for(Ball b : balls){
            if(b.getColor() == Color.red){
                col = "Red";
            }else if(b.getColor() == Color.blue){
                col = "Blue";
            }else if(b.getColor() == Color.green){
                col = "Green";
            }
        }
        return col;
    }

}
