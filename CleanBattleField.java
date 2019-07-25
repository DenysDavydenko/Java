package com.luxoft.jva001p1.basics.part3.xtasks;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CleanBattleField extends JPanel
{

    final boolean COLORED_MODE = false;
    final boolean IS_GRID = true;

    final int BF_WIDTH = 576;
    final int BF_HEIGHT = 576;

    // 1 - top, 2 - right, 3 - down, 4 - left
    int tankDirection = 1;

    int tankX = 128;
    int tankY = 512;

    int bulletX = -100;
    int bulletY = -100;

    int tankSpeed = 5;
    int bulletSpeed = 2;

    final String BRICK = "B";
    final String BLANK = " ";
    int speed = 100;

    String[][] battleField = {
            {"B", "B", "B", "B", "B", "B", "B", "B", "B"},
            {" ", " ", " ", " ", " ", " ", " ", " ", "B"},
            {"B", "B", "B", " ", "B", " ", "B", "B", "B"},
            {"B", "B", "B", " ", " ", " ", "B", "B", "B"},
            {"B", "B", "B", " ", "B", " ", "B", "B", "B"},
            {"B", "B", " ", "B", "B", "B", " ", "B", "B"},
            {"B", "B", " ", " ", " ", " ", " ", "B", "B"},
            {"B", " ", " ", "B", "B", "B", " ", " ", "B"},
            {"B", " ", " ", "B", "B", "B", " ", " ", "B"}
    };

    void runTheGame() throws Exception
    {
        printCurrentBattleField();

        clean();
    }

    /**
     *
     *  When method called tank should destroy all the objects on battle field in less then 30 seconds.
     *
     */
    void clean() {
        int v = 0;
        int h = 0;
        while (true) {
            v = numRandom();
            h = numRandom();
            moveToQuadrant(v,h );
            System.out.println(v + "_" + h);
        }

    }

    static Integer numRandom(){
        String time;
        int direction ;
        while (true){
            time = String.valueOf(System.currentTimeMillis());
//            System.out.println(time);
            direction = Integer.parseInt(time.substring(time.length()-2,time.length()-1));
            if (direction > 0 || direction <= 8){
                return direction;
            }
        }
    }

    /**
     *
     * When called tank should produce new bullet.
     * This bullet should smoothly move to the opposite side.
     *
     * Bullet should be destroyed when rich the opposite side.
     *
     * When the bullet shoot something method would clean appropriate quadrant and destroy the bullet.
     */
    void fire()
    {
        bulletX = tankX + 25;
        bulletY = tankY + 25;
        int step = 1;

        while ((bulletX > -14 && bulletX < 590) && (bulletY > -14 && bulletY < 590)){

            if (tankDirection ==1 ){
                bulletY -= step;
            }
            if (tankDirection ==2 ){
                bulletY += step;
            }
            if (tankDirection ==4){
                bulletX += step;
            }
            if (tankDirection ==3 ){
                bulletX -= step;
            }
            if (checkAndProcessInterception()){
                bulletX = -100;
                bulletY = -100;
            }
            repaint();
            sleep(bulletSpeed);

        }

    }

    void move(int direction)
    {
        int step = 1;
        int covered = 0;
        if ((direction==1 && tankY==0) || (direction==2 && tankY >= 512) || (direction==3 && tankX == 0) || (direction==1 && tankX >=512)){
            return;
        }
        turn(direction);
        System.out.println(direction);
        while (covered < 64){
            if (direction ==1 ){
                tankY -= step;
            }
            if (direction ==2 ){
                tankY += step;
            }
            if (direction ==3){
                tankX += step;
            }
            if (direction ==4 ){
                tankX -= step;
            }
            covered +=step;
        }
        repaint();
        sleep(speed);

    }


    void moveToQuadrant(int v, int h)
    {
//        String coordinates = getQuadrant(v,h);
        String coordinates = getQuadrantXY(v,h);
        int separator = coordinates.indexOf("_");
        System.out.println(coordinates );
        int y = Integer.parseInt(coordinates.substring(0,separator));
        int x = Integer.parseInt(coordinates.substring(separator+1));
        System.out.println(x + separator +y);
        if (tankX < x) {
            while (tankX != x) {
                turn(3);
                fire();
                move(3);
            }
        } else {
            while (tankX != x) {
                turn(4);
                fire();
                move(4);
            }
        }
        if (tankY < y) {
            while (tankY != y) {
                turn(2);
                fire();
                move(2);
            }
        } else{
            while (tankY != y) {
                turn(1);
                fire();
                move(1);
            }
        }
    }


    /**
     *
     * Should return true if bullet located in non-empty quadrant.
     *
     */
    private boolean checkAndProcessInterception()
    {
        String coordinates =  getQuadrant(bulletX, bulletY);
        int x = Integer.parseInt(coordinates.split("_")[0]);
        int y = Integer.parseInt(coordinates.split("_")[1]);

        if (y >=0 && y < 9 && x >=0 && x < 9) {
            if (!battleField[y][x].trim().isEmpty()) {
                battleField[y][x] = "";
                return true;
            }
        }
        return false;
    }


//    int[] getQuadrant(int x, int y)
//    {
//        return new int[] {x / 64, y / 64};
//    }

    String getQuadrant(int x, int y)
    {
        return x / 64 + "_" + y / 64;
    }


    private void printCurrentBattleField()
    {
        for (String[] row : battleField)
        {
            System.out.println(Arrays.toString(row));
        }
    }

//    int[] getQuadrant(int x, int y)
//    {
//        return new int[] {x / 64, y / 64};
//    }

    void turn(int direction)
    {
        tankDirection = direction;
        repaint();
    }



    // Magic bellow. Do not worry about this now, you will understand everything in this course.
    // Please concentrate on your tasks only.


    public static void main(String[] args) throws Exception
    {
        CleanBattleField bf = new CleanBattleField();
        bf.runTheGame();
    }

    public CleanBattleField() throws Exception
    {
        JFrame frame = new JFrame("YOUR TANK SHOULD FIRE!!!");
        frame.setLocation(750, 150);
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
        catch (Exception ignore)
        {
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        paintBF(g);

        paintBorders(g);

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tankX, tankY, 64, 64);

        g.setColor(new Color(0, 255, 0));
        if (tankDirection == 1)
        {
            g.fillRect(tankX + 20, tankY, 24, 34);
        }
        else if (tankDirection == 3)
        {
            g.fillRect(tankX + 20, tankY + 30, 24, 34);
        }
        else if (tankDirection == 4)
        {
            g.fillRect(tankX, tankY + 20, 34, 24);
        }
        else
        {
            g.fillRect(tankX + 30, tankY + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bulletX, bulletY, 14, 14);
    }

    private void paintBorders(Graphics g)
    {
        for (int j = 0; j < battleField.length; j++)
        {
            for (int k = 0; k < battleField.length; k++)
            {
                if (battleField[j][k].equals("B"))
                {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates.substring(0, separator));
                    int x = Integer.parseInt(coordinates.substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);

                    if (IS_GRID)
                    {
                        g.setColor(new Color(0, 0, 0));
                        g.drawRect(x, y, 64, 64);
                    }
                }
            }
        }
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
                if (COLORED_MODE)
                {
                    if (i % 2 == 0)
                    {
                        cc = new Color(252, 241, 177);
                    }
                    else
                    {
                        cc = new Color(233, 243, 255);
                    }
                }
                else
                {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }
    }

    private String getQuadrantXY(int v, int h)
    {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

}
