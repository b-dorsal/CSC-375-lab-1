package Assignment1;//package assignment1;
//Thread object

import java.awt.Color;
import java.util.concurrent.CountDownLatch;

public class BallThread implements Runnable{

    //Ball attributes.
    private Ball[] balls = new Ball[49];
    private Color color;
    private int id;
    private int x, y;
    private int velx, vely;
    private boolean isDead = false;

    private CountDownLatch latch;

    @Override
    public void run() {
        this.move();        //move the ball.
        this.setBall();     //set the value back to the array.
        this.checkCollisions();//check if its colliding.
        latch.countDown();
    }

    //Simple method to set the variables for this ball.
    public void setVars(Ball b) {
        this.id = b.id;
        this.x = b.getx();
        this.y = b.gety();
        this.velx = b.getVx();
        this.vely = b.getVy();
        this.color = b.getColor();
    }

    public void setBalls(Ball[] b, CountDownLatch latch){
        this.balls = b;
        this.latch = latch;
    }

    //Move method updates the next position and velocity values for this ball.
    public void move() {
        if (this.x < 0 || this.x > 600 - 20) {
            this.velx = -this.velx;
            this.swapColor();
        }
        if (this.y < 0 || this.y > 400 - 40) {
            this.vely = -this.vely;
            this.swapColor();
        }
        this.x += this.velx;
        this.y += this.vely;

        if(this.velx == 0){
            this.color = Color.white;
        }


        this.latch = latch;
    }



    //This method checks this ball against another ball and reports collisions.
    private synchronized boolean checkCollide(Ball check) {
            if (this.id != check.id && this.isDead == false && check.getDead() == false) {
                int axmax = this.x + 8;
                int axmin = this.x - 8;
                int aymax = this.y + 8;
                int aymin = this.y - 8;

                int bxmax = check.getx() + 10;
                int bxmin = check.getx() - 10;
                int bymax = check.gety() + 10;
                int bymin = check.gety() - 10;


                boolean didcross = false;

                if (bxmax > axmin && bxmax < axmax) {
                    if (bymax > aymin && bymax < aymax) {
                        didcross = true;
                    } else if (bymin > aymin && bymin < aymax) {
                        didcross = true;
                    }else{
                        didcross = false;
                    }
                }else if(bxmin > axmin && bxmin < axmax){
                    if (bymax > aymin && bymax < aymax) {
                        didcross = true;
                    } else if (bymin > aymin && bymin < aymax) {
                        didcross = true;
                    }else{
                        didcross =  false;
                    }
                }else{
                    didcross =  false;
                }


                if(didcross == true){
                    Color checkCol = check.getColor();
                    if(this.color == Color.red && checkCol != Color.red){
                        if(checkCol == Color.green){
                            return true;
                        }else{
                            return false;
                        }
                    }else if(this.color == Color.green && checkCol != Color.green){
                        if(checkCol == Color.blue){
                            return true;
                        }else{
                            return false;
                        }
                    }else if(this.color == Color.blue && checkCol != Color.blue){
                        if(checkCol == Color.red){
                            return true;
                        }else{
                            return false;
                        }
                    }else{
                        return false;
                    }
                }

            } else {
                return false;
            }
            return false;
    }


    //This method checks this ball against all the others and checks for collision.
    public synchronized void checkCollisions() {
          for(Ball b : balls){
              if(checkCollide(b) == true){
                  this.color = Color.white;
                  this.velx = 0;
                  this.vely = 0;
                  this.isDead = true;

                  b.setDead();
                  b.move(b.getx(), b.gety(), 0, 0);
                  b.setColor(Color.white);
                  break;
              }
          }
    }

    //Toggles the colors between red green and blue.
    private void swapColor() {
        if (this.color == Color.red) {
            this.color = Color.green;
        } else if (this.color == Color.green) {
            this.color = Color.blue;
        } else {
            this.color = Color.red;
        }
    }

    //Sets the Assignment1.BallThread values to the ball object it came from.
    private void setBall() {
        //balls[this.id].move(this.x, this.y, this.velx, this.vely, this.color, this.isDead);
        balls[this.id].move(this.x, this.y, this.velx, this.vely);
        if(this.isDead == true){balls[this.id].setDead();}
        balls[this.id].setColor(this.color);

    }

}//end ballThread