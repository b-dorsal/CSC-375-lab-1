package Assignment1;

import java.awt.Color;

public class Ball {

    public final int id;  //Ball identifier
    private int x, y;   //x and y position.
    private int velx, vely;//x and y velocities.
    private Color color;     //ball fill color.
    private boolean isDead;  //if its been hit and killed.

    //Constructor for ball class.
    public Ball(int id, int x, int y, Color col, int velx, int vely) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = col;
        this.velx = velx;
        this.vely = vely;
    }

    //Method to update this ball's variables,
    //synchronized to prevent 2 threads from changing this ball at the same time.
    public synchronized void move(int x, int y, int velx, int vely) {
        this.x = x;
        this.y = y;

        this.velx = velx;
        this.vely = vely;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    //Synchronized Get Methods
    public synchronized void setDead() {
        this.isDead = true;
    }

    public synchronized int getx() {
        return this.x;
    }

    public synchronized int gety() {
        return this.y;
    }

    public synchronized int getVx() {
        return this.velx;
    }

    public synchronized int getVy() {
        return this.vely;
    }

    public synchronized Color getColor() {
        return this.color;
    }

    public synchronized boolean getDead() {
        return this.isDead;
    }

}
