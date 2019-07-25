package com.luxoft.jva001p1.basics.part2.xtasks;

import javax.swing.*;
import java.awt.*;

public class TanksMoveToQuadrant extends JPanel
{
    int tankX = 0;
    int tankY = 0;
    int speed = 50;
    int tankDirection = 1;

    void runTheGame()
    {
        moveToQuadrant(5,2 );
        System.out.println(5 +"_"+2);
        sleep(2000);
        moveToQuadrant(3,8 );
        System.out.println(3 +"_"+8);
        sleep(2000);
        moveToQuadrant(4,7 );
        System.out.println(4 +"_"+7);
        sleep(2000);
        moveToQuadrant(8,8 );
        System.out.println(8 +"_"+8);
        sleep(2000);
        moveToQuadrant(6,2 );
        System.out.println(6 +"_"+2);
    }

    /**
     * One method call should smoothly move the tank one quadrant according to given direction.
     *
     * @param direction can be 1 - up, 2 - right, 3 - down, 4 - right
     */

    void move(int direction)
    {
        int step = 1;
        int covered = 0;
        if ((direction==1 && tankY==0) || (direction==2 && tankY >= 512) || (direction==3 && tankX == 0) || (direction==1 && tankX >=512)){
            System.out.println("Illegal move "+ direction);
            return;
        }
        turn(direction);

        while (covered < 64){
            if (direction ==1 ){
                tankY -= step;
            }
            if (direction ==2 ){
                tankY += step;
            }
            if (direction ==4){
                tankX += step;
            }
            if (direction ==3 ){
                tankX -= step;
            }
            covered +=step;
        }
        repaint();
        sleep(speed);

    }

    void moveToQuadrant(int v, int h)
    {
        String coordinates = getQuadrantXY(v,h);
        int separator = coordinates.indexOf("_");
        System.out.println(coordinates );
        int y = Integer.parseInt(coordinates.substring(0,separator));
        int x = Integer.parseInt(coordinates.substring(separator+1));
//        System.out.println(x + separator +y);
        if (tankX < x) {
            while (tankX != x) {
                move(4);
            }
        } else {
            while (tankX != x) {
                move(3);
            }
        }
        if (tankY < y) {
            while (tankY != y) {
                move(2);
            }
        } else{
            while (tankY != y) {
                move(1);
            }
        }
    }

    String getQuadrantXY(int x, int y)
    {
        if ((x > 575 || y > 575)&& (x < 0 || y < 0) ){
            return null;
        }
        return (x-1)*64+"_"+(y-1)*64;
    }

    // Magic bellow. Do not worry about this now, you will understand everything in this course.
    // Please concentrate on your tasks only.

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    public static void main(String[] args) throws Exception
    {
        TanksMoveToQuadrant bf = new TanksMoveToQuadrant();
        bf.runTheGame();
    }

    public TanksMoveToQuadrant() throws Exception
    {
        JFrame frame = new JFrame("MOVE TANK FORWARD");
        frame.setLocation(500, 150);
        frame.setMinimumSize(new Dimension(BF_WIDTH, BF_HEIGHT + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }
    void turn(int direction)
    {
        tankDirection = direction;
        repaint();
    }

    void sleep(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {

        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        paintBF(g);

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tankX, tankY, 64, 64);
    }

    private void paintBF(Graphics g)
    {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++)
        {
            for (int h = 0; h < 9; h++)
            {
                if (i % 2 == 0)
                {
                    cc = new Color(252, 241, 177);
                }
                else
                {
                    cc = new Color(233, 243, 255);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }
    }

}
