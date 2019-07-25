package com.luxoft.jva001p1.basics.part2.xtasks;

import javax.swing.*;
import java.awt.*;

public class TanksMove extends JPanel
{
    int tankX = 0;
    int tankY = 0;
    int speed = 225;

    void runTheGame()
    {
        while (true)
        {
            randomMove();
        }
    }

    /**
     * One method call should smoothly move the tank one quadrant according to given direction.
     *
     * @param direction can be 1 - up, 2 - right, 3 - down, 4 - right
     */
    void move(int direction)
    {
        String sDirection = "";
        if (direction ==1 & tankY > 0){
            tankY -= 64;
            sDirection = "Up";
        }
        if (direction ==2 & tankY < BF_HEIGHT-64){
            tankY += 64;
            sDirection = "Down";
        }
        if (direction ==3 & tankX < BF_WIDTH-64){
            tankX += 64;
            sDirection = "Right";
        }
        if (direction ==4 & tankX > 0){
            tankX -= 64;
            sDirection = "Left";
        }
        if (sDirection.length()>0){
            System.out.println(sDirection + " X :"+ tankX+" ; Y:" + tankY);
        }
        repaint();
        sleep(speed);
    }

    void randomMove()
    {
        int direction;
        while (true){
            direction = numRandom();
//            System.out.println(direction);
            if (direction >= 1 || direction <=4) {
                move(direction);
            }
        }
    }
    static Integer numRandom(){
        String time;
        int direction ;
        while (true){
            time = String.valueOf(System.currentTimeMillis());
//            System.out.println(time);
            direction = Integer.parseInt(time.substring(time.length()-2,time.length()-1));
            if (direction > 0 || direction <= 4){
                return direction;
            }
        }
    }


    // Magic bellow. Do not worry about this now, you will understand everything in this course.
    // Please concentrate on your tasks only.

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    public static void main(String[] args) throws Exception
    {
        TanksMove bf = new TanksMove();
        bf.runTheGame();
    }

    public TanksMove() throws Exception
    {
        JFrame frame = new JFrame("MOVE TANK FORWARD");
        frame.setLocation(500, 150);
        frame.setMinimumSize(new Dimension(BF_WIDTH, BF_HEIGHT + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
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
