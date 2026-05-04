/*----------------------------------------------------------------------------------------
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for license information.
 *---------------------------------------------------------------------------------------*/
package main;

import assets.actors.Enemy;
import assets.actors.Tower;
import gamelogic.Ticks;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;

public class Main {

    static int Tickcount = 0;
    static int Tickloop = 1;
    public static int wave = 1;
    public static double Healthbase = 4000.000;
    public static Tiles[][] lilM = new Tiles[35][25];
    public static ArrayList<Enemy> allenemies = new ArrayList<>();
    public static lilC c = new lilC();

    public static void main(String[] args) {
        // Vorbereitung Map Erstlleung
        // TODO code application logic here
         Ticks m = new Ticks(c);
         gamelogic.Mouse mouse = new gamelogic.Mouse();
         c.addMouseListener(mouse);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setSize(1920, 1080);

        // Erzeugt Ebenen
        JLayeredPane lP = new JLayeredPane();
        lP.setSize(new Dimension(1920, 1080));
        lP.setLayout(null);

    

        // 5 -> je kleiner die Zahl, desto weiter im Hintergrund
        lP.add(c, (Object) (5));

        // Label
        JPanel hilfP1 = new JPanel();
        hilfP1.setSize(200, 100);
        hilfP1.setLayout(null);
        hilfP1.setBounds(1460, 350, 300, 295);
        hilfP1.setOpaque(false);

        JLabel text1 = new JLabel();
        text1.setBounds(0, 0, hilfP1.getWidth(), hilfP1.getHeight());
        Font font1 = new Font("Calibri", Font.BOLD, 16);
        text1.setFont(font1);
        text1.setForeground(Color.black);
        text1.setText("Range");
        text1.setVisible(true);

        JPanel hilfP2 = new JPanel();
        hilfP2.setSize(200, 100);
        hilfP2.setLayout(null);
        hilfP2.setBounds(1630, 350, 300, 295);
        hilfP2.setOpaque(false);

        JLabel text2 = new JLabel();
        text2.setBounds(0, 0, hilfP2.getWidth(), hilfP2.getHeight());
        Font font2 = new Font("Calibri", Font.BOLD, 16);
        text2.setFont(font2);
        text2.setForeground(Color.black);
        text2.setText("Splash");
        text2.setVisible(true);

        JPanel hilfP3 = new JPanel();
        hilfP3.setSize(200, 100);
        hilfP3.setLayout(null);
        hilfP3.setBounds(1802, 350, 300, 295);
        hilfP3.setOpaque(false);

        JLabel text3 = new JLabel();
        text3.setBounds(0, 0, hilfP3.getWidth(), hilfP3.getHeight());
        Font font3 = new Font("Calibri", Font.BOLD, 16);
        text3.setFont(font3);
        text3.setForeground(Color.black);
        text3.setText("Melee");
        text3.setVisible(true);

        JPanel hilfP4 = new JPanel();
        hilfP4.setSize(200, 100);
        hilfP4.setLayout(null);
        hilfP4.setBounds(1640, 425, 300, 295);
        hilfP4.setOpaque(false);

        JLabel text4 = new JLabel();
        text4.setBounds(0, 0, hilfP4.getWidth(), hilfP4.getHeight());
        Font font4 = new Font("Calibri", Font.BOLD, 12);
        text4.setFont(font4);
        text4.setForeground(Color.black);
        text4.setText("LVL1");
        text4.setVisible(true);

        JPanel hilfP5 = new JPanel();
        hilfP5.setSize(200, 100);
        hilfP5.setLayout(null);
        hilfP5.setBounds(1450, 450, 300, 295);
        hilfP5.setOpaque(false);

        JLabel text5 = new JLabel();
        text5.setBounds(0, 0, hilfP5.getWidth(), hilfP5.getHeight());
        Font font5 = new Font("Calibri", Font.BOLD, 12);
        text5.setFont(font5);
        text5.setForeground(Color.black);
        text5.setText("Bambusspeer");
        text5.setVisible(true);

        JPanel hilfP6 = new JPanel();
        hilfP6.setSize(200, 100);
        hilfP6.setLayout(null);
        hilfP6.setBounds(1450, 550, 300, 295);
        hilfP6.setOpaque(false);

        JLabel text6 = new JLabel();
        text6.setBounds(0, 0, hilfP6.getWidth(), hilfP6.getHeight());
        Font font6 = new Font("Calibri", Font.BOLD, 12);
        text6.setFont(font6);
        text6.setForeground(Color.black);
        text6.setText("Doppelbambuspeer");
        text6.setVisible(true);

        JPanel hilfP7 = new JPanel();
        hilfP7.setSize(200, 100);
        hilfP7.setLayout(null);
        hilfP7.setBounds(1450, 650, 300, 295);
        hilfP7.setOpaque(false);

        JLabel text7 = new JLabel();
        text7.setBounds(0, 0, hilfP7.getWidth(), hilfP7.getHeight());
        Font font7 = new Font("Calibri", Font.BOLD, 12);
        text7.setFont(font7);
        text7.setForeground(Color.black);
        text7.setText("Blasrohr");
        text7.setVisible(true);

        JPanel hilfP8 = new JPanel();
        hilfP8.setSize(200, 100);
        hilfP8.setLayout(null);
        hilfP8.setBounds(1450, 668, 300, 295);
        hilfP8.setOpaque(false);

        JLabel text8 = new JLabel();
        text8.setBounds(0, 0, hilfP8.getWidth(), hilfP8.getHeight());
        Font font8 = new Font("Calibri", Font.BOLD, 12);
        text8.setFont(font8);
        text8.setForeground(Color.black);
        text8.setText("(schnelle SChüssse)");
        text8.setVisible(true);

        JPanel hilfP9 = new JPanel();
        hilfP9.setSize(200, 100);
        hilfP9.setLayout(null);
        hilfP9.setBounds(1620, 450, 300, 295);
        hilfP9.setOpaque(false);

        JLabel text9 = new JLabel();
        text9.setBounds(0, 0, hilfP9.getWidth(), hilfP9.getHeight());
        Font font9 = new Font("Calibri", Font.BOLD, 12);
        text9.setFont(font9);
        text9.setForeground(Color.black);
        text9.setText("Smash");
        text9.setVisible(true);

        JPanel hilfP10 = new JPanel();
        hilfP10.setSize(200, 100);
        hilfP10.setLayout(null);
        hilfP10.setBounds(1620, 550, 300, 295);
        hilfP10.setOpaque(false);

        JLabel text10 = new JLabel();
        text10.setBounds(0, 0, hilfP10.getWidth(), hilfP10.getHeight());
        Font font10 = new Font("Calibri", Font.BOLD, 12);
        text10.setFont(font10);
        text10.setForeground(Color.black);
        text10.setText("Dynamit");
        text10.setVisible(true);

        JPanel hilfP11 = new JPanel();
        hilfP11.setSize(200, 100);
        hilfP11.setLayout(null);
        hilfP11.setBounds(1620, 650, 300, 295);
        hilfP11.setOpaque(false);

        JLabel text11 = new JLabel();
        text11.setBounds(0, 0, hilfP11.getWidth(), hilfP11.getHeight());
        Font font11 = new Font("Calibri", Font.BOLD, 12);
        text11.setFont(font11);
        text11.setForeground(Color.black);
        text11.setText("Bombe");
        text11.setVisible(true);

        JPanel hilfP12 = new JPanel();
        hilfP12.setSize(200, 100);
        hilfP12.setLayout(null);
        hilfP12.setBounds(1780, 450, 300, 295);
        hilfP12.setOpaque(false);

        JLabel text12 = new JLabel();
        text12.setBounds(0, 0, hilfP12.getWidth(), hilfP12.getHeight());
        Font font12 = new Font("Calibri", Font.BOLD, 12);
        text12.setFont(font12);
        text12.setForeground(Color.black);
        text12.setText("Punch");
        text12.setVisible(true);

        JPanel hilfP13 = new JPanel();
        hilfP13.setSize(200, 100);
        hilfP13.setLayout(null);
        hilfP13.setBounds(1780, 550, 300, 295);
        hilfP13.setOpaque(false);

        JLabel text13 = new JLabel();
        text13.setBounds(0, 0, hilfP13.getWidth(), hilfP13.getHeight());
        Font font13 = new Font("Calibri", Font.BOLD, 12);
        text13.setFont(font13);
        text13.setForeground(Color.black);
        text13.setText("Schwert");
        text13.setVisible(true);

        JPanel hilfP14 = new JPanel();
        hilfP14.setSize(200, 100);
        hilfP14.setLayout(null);
        hilfP14.setBounds(1770, 650, 300, 295);
        hilfP14.setOpaque(false);

        JLabel text14 = new JLabel();
        text14.setBounds(0, 0, hilfP14.getWidth(), hilfP14.getHeight());
        Font font14 = new Font("Calibri", Font.BOLD, 12);
        text14.setFont(font14);
        text14.setForeground(Color.black);
        text14.setText("Schwert&Axt");
        text14.setVisible(true);

        JPanel hilfP15 = new JPanel();
        hilfP15.setSize(200, 100);
        hilfP15.setLayout(null);
        hilfP15.setBounds(1640, 520, 300, 295);
        hilfP15.setOpaque(false);

        JLabel text15 = new JLabel();
        text15.setBounds(0, 0, hilfP15.getWidth(), hilfP15.getHeight());
        Font font15 = new Font("Calibri", Font.BOLD, 12);
        text15.setFont(font15);
        text15.setForeground(Color.black);
        text15.setText("LVL2");
        text15.setVisible(true);

        JPanel hilfP16 = new JPanel();
        hilfP16.setSize(200, 100);
        hilfP16.setLayout(null);
        hilfP16.setBounds(1640, 620, 300, 295);
        hilfP16.setOpaque(false);

        JLabel text16 = new JLabel();
        text16.setBounds(0, 0, hilfP16.getWidth(), hilfP16.getHeight());
        Font font16 = new Font("Calibri", Font.BOLD, 12);
        text16.setFont(font16);
        text16.setForeground(Color.black);
        text16.setText("LVL3");
        text16.setVisible(true);

        JPanel hilfP17 = new JPanel();
        hilfP17.setSize(200, 100);
        hilfP17.setLayout(null);
        hilfP17.setBounds(1480, 805, 300, 295);
        hilfP17.setOpaque(false);

        JLabel text17 = new JLabel();
        text17.setBounds(0, 0, hilfP17.getWidth(), hilfP17.getHeight());
        Font font17 = new Font("Calibri", Font.BOLD, 20);
        text17.setFont(font17);
        text17.setForeground(Color.cyan);
        text17.setText("FREEZE");
        text17.setVisible(true);

        JPanel hilfP18 = new JPanel();
        hilfP18.setSize(200, 100);
        hilfP18.setLayout(null);
        hilfP18.setBounds(1730, 805, 300, 295);
        hilfP18.setOpaque(false);

        JLabel text18 = new JLabel();
        text18.setBounds(0, 0, hilfP18.getWidth(), hilfP18.getHeight());
        Font font18 = new Font("Calibri", Font.BOLD, 20);
        text18.setFont(font18);
        text18.setForeground(Color.green);
        text18.setText("UPGRADE");
        text18.setVisible(true);

        // Text aufs hilfspanel
        hilfP1.add(text1);
        hilfP1.setVisible(true);
        hilfP2.add(text2);
        hilfP2.setVisible(true);
        hilfP3.add(text3);
        hilfP3.setVisible(true);
        hilfP4.add(text4);
        hilfP4.setVisible(true);
        hilfP5.add(text5);
        hilfP5.setVisible(true);
        hilfP6.add(text6);
        hilfP6.setVisible(true);
        hilfP7.add(text7);
        hilfP7.setVisible(true);
        hilfP8.add(text8);
        hilfP8.setVisible(true);
        hilfP9.add(text9);
        hilfP9.setVisible(true);
        hilfP10.add(text10);
        hilfP10.setVisible(true);
        hilfP11.add(text11);
        hilfP11.setVisible(true);
        hilfP12.add(text12);
        hilfP12.setVisible(true);
        hilfP13.add(text13);
        hilfP13.setVisible(true);
        hilfP14.add(text14);
        hilfP14.setVisible(true);
        hilfP15.add(text15);
        hilfP15.setVisible(true);
        hilfP16.add(text16);
        hilfP16.setVisible(true);
        hilfP17.add(text17);
        hilfP17.setVisible(true);
        hilfP18.add(text18);
        hilfP18.setVisible(true);

        // hilfspanel auf die ebene 20
        lP.add(hilfP1, (Object) (20));
        lP.add(hilfP2, (Object) (20));
        lP.add(hilfP3, (Object) (20));
        lP.add(hilfP4, (Object) (20));
        lP.add(hilfP5, (Object) (20));
        lP.add(hilfP6, (Object) (20));
        lP.add(hilfP7, (Object) (20));
        lP.add(hilfP8, (Object) (20));
        lP.add(hilfP9, (Object) (20));
        lP.add(hilfP10, (Object) (20));
        lP.add(hilfP11, (Object) (20));
        lP.add(hilfP12, (Object) (20));
        lP.add(hilfP13, (Object) (20));
        lP.add(hilfP14, (Object) (20));
        lP.add(hilfP15, (Object) (20));
        lP.add(hilfP16, (Object) (20));
        lP.add(hilfP17, (Object) (20));
        lP.add(hilfP18, (Object) (20));

        lP.setVisible(true);

        f.add(lP);
        f.setVisible(true);

        int a = 40;

        for (int i = 0; i < 35; i++) {

            for (int j = 0; j < 25; j++) {
                lilM[i][j] = new Tiles(i * a, j * a, 1, true);
            }

        }
        // Weg
        lilM[0][13] = new Tiles(0 * a, 13 * a - 30, 3, false);
        lilM[1][13] = new Tiles(1 * a, 13 * a - 30, 4, false);
        lilM[2][13] = new Tiles(2 * a, 13 * a - 30, 5, false);
        lilM[3][13] = new Tiles(3 * a, 13 * a - 30, 6, false);
        lilM[4][13] = new Tiles(4 * a, 13 * a - 30, 7, false);
        lilM[4][14] = new Tiles(4 * a, 14 * a - 30, 8, false);
        lilM[4][15] = new Tiles(4 * a, 15 * a - 30, 9, false);
        lilM[4][16] = new Tiles(4 * a, 16 * a - 30, 10, false);
        lilM[4][17] = new Tiles(4 * a, 17 * a - 30, 11, false);
        lilM[5][17] = new Tiles(5 * a, 17 * a - 30, 12, false);
        lilM[6][17] = new Tiles(6 * a, 17 * a - 30, 13, false);
        lilM[7][17] = new Tiles(7 * a, 17 * a - 30, 14, false);
        lilM[8][17] = new Tiles(8 * a, 17 * a - 30, 15, false);
        lilM[8][16] = new Tiles(8 * a, 16 * a - 30, 16, false);
        lilM[8][15] = new Tiles(8 * a, 15 * a - 30, 17, false);
        lilM[8][14] = new Tiles(8 * a, 14 * a - 30, 18, false);
        lilM[8][13] = new Tiles(8 * a, 13 * a - 30, 19, false);
        lilM[8][12] = new Tiles(8 * a, 12 * a - 30, 20, false);
        lilM[8][11] = new Tiles(8 * a, 11 * a - 30, 21, false);
        lilM[8][10] = new Tiles(8 * a, 10 * a - 30, 22, false);
        lilM[8][9] = new Tiles(8 * a, 9 * a - 30, 23, false);
        lilM[8][8] = new Tiles(8 * a, 8 * a - 30, 24, false);
        lilM[8][7] = new Tiles(8 * a, 7 * a - 30, 25, false);
        lilM[8][6] = new Tiles(8 * a, 6 * a - 30, 26, false);
        lilM[9][6] = new Tiles(9 * a, 6 * a - 30, 27, false);
        lilM[10][6] = new Tiles(10 * a, 6 * a - 30, 28, false);
        lilM[11][6] = new Tiles(11 * a, 6 * a - 30, 29, false);
        lilM[12][6] = new Tiles(12 * a, 6 * a - 30, 30, false);
        lilM[12][7] = new Tiles(12 * a, 7 * a - 30, 31, false);
        lilM[12][8] = new Tiles(12 * a, 8 * a - 30, 32, false);
        lilM[12][9] = new Tiles(12 * a, 9 * a - 30, 33, false);
        lilM[12][10] = new Tiles(12 * a, 10 * a - 30, 34, false);
        lilM[12][11] = new Tiles(12 * a, 11 * a - 30, 35, false);
        lilM[12][12] = new Tiles(12 * a, 12 * a - 30, 36, false);
        lilM[12][13] = new Tiles(12 * a, 13 * a - 30, 37, false);
        lilM[12][14] = new Tiles(12 * a, 14 * a - 30, 38, false);
        lilM[12][15] = new Tiles(12 * a, 15 * a - 30, 39, false);
        lilM[12][16] = new Tiles(12 * a, 16 * a - 30, 40, false);
        lilM[12][17] = new Tiles(12 * a, 17 * a - 30, 41, false);
        lilM[12][18] = new Tiles(12 * a, 18 * a - 30, 42, false);
        lilM[12][19] = new Tiles(12 * a, 19 * a - 30, 43, false);
        lilM[12][20] = new Tiles(12 * a, 20 * a - 30, 44, false);
        lilM[12][21] = new Tiles(12 * a, 21 * a - 30, 45, false);
        lilM[13][21] = new Tiles(13 * a, 21 * a - 30, 46, false);
        lilM[14][21] = new Tiles(14 * a, 21 * a - 30, 47, false);
        lilM[15][21] = new Tiles(15 * a, 21 * a - 30, 48, false);
        lilM[16][21] = new Tiles(16 * a, 21 * a - 30, 49, false);
        lilM[17][21] = new Tiles(17 * a, 21 * a - 30, 50, false);
        lilM[18][21] = new Tiles(18 * a, 21 * a - 30, 51, false);
        lilM[19][21] = new Tiles(19 * a, 21 * a - 30, 52, false);
        lilM[20][21] = new Tiles(20 * a, 21 * a - 30, 53, false);
        lilM[21][21] = new Tiles(21 * a, 21 * a - 30, 54, false);
        lilM[22][21] = new Tiles(22 * a, 21 * a - 30, 55, false);
        lilM[23][21] = new Tiles(23 * a, 21 * a - 30, 56, false);
        lilM[23][20] = new Tiles(23 * a, 20 * a - 30, 57, false);
        lilM[23][19] = new Tiles(23 * a, 19 * a - 30, 58, false);
        lilM[23][18] = new Tiles(23 * a, 18 * a - 30, 59, false);
        lilM[23][17] = new Tiles(23 * a, 17 * a - 30, 60, false);
        lilM[23][16] = new Tiles(23 * a, 16 * a - 30, 61, false);
        lilM[23][15] = new Tiles(23 * a, 15 * a - 30, 62, false);
        lilM[23][14] = new Tiles(23 * a, 14 * a - 30, 63, false);
        lilM[23][13] = new Tiles(23 * a, 13 * a - 30, 64, false);
        lilM[23][12] = new Tiles(23 * a, 12 * a - 30, 65, false);
        lilM[23][11] = new Tiles(23 * a, 11 * a - 30, 66, false);
        lilM[23][10] = new Tiles(23 * a, 10 * a - 30, 67, false);
        lilM[23][9] = new Tiles(23 * a, 9 * a - 30, 68, false);
        lilM[23][8] = new Tiles(23 * a, 8 * a - 30, 69, false);
        lilM[23][7] = new Tiles(23 * a, 7 * a - 30, 70, false);
        lilM[23][6] = new Tiles(23 * a, 6 * a - 30, 71, false);
        lilM[24][6] = new Tiles(24 * a, 6 * a - 30, 72, false);
        lilM[25][6] = new Tiles(25 * a, 6 * a - 30, 73, false);
        lilM[26][6] = new Tiles(26 * a, 6 * a - 30, 74, false);
        lilM[27][6] = new Tiles(27 * a, 6 * a - 30, 75, false);
        lilM[27][7] = new Tiles(27 * a, 7 * a - 30, 76, false);
        lilM[27][8] = new Tiles(27 * a, 8 * a - 30, 77, false);
        lilM[27][9] = new Tiles(27 * a, 9 * a - 30, 78, false);
        lilM[27][10] = new Tiles(27 * a, 10 * a - 30, 79, false);
        lilM[27][11] = new Tiles(27 * a, 11 * a - 30, 80, false);
        lilM[27][12] = new Tiles(27 * a, 12 * a - 30, 81, false);
        lilM[27][13] = new Tiles(27 * a, 13 * a - 30, 82, false);
        lilM[28][13] = new Tiles(28 * a, 13 * a - 30, 83, false);
        lilM[29][13] = new Tiles(29 * a, 13 * a - 30, 84, false);
        lilM[30][13] = new Tiles(30 * a, 13 * a - 30, 85, false);
        lilM[31][13] = new Tiles(31 * a, 13 * a - 30, 86, false);
        lilM[32][13] = new Tiles(32 * a, 13 * a - 30, 87, false);
        lilM[33][13] = new Tiles(33 * a, 13 * a - 30, 88, false);
        lilM[34][13] = new Tiles(34 * a, 13 * a - 30, 89, false);

        // Deko
        lilM[3][6] = new Tiles(3 * a, 6 * a, 2, false);
        lilM[2][2] = new Tiles(2 * a, 2 * a, 2, false);
        lilM[2][3] = new Tiles(2 * a, 3 * a, 2, false);
        lilM[3][5] = new Tiles(3 * a, 5 * a, 2, false);
        lilM[3][2] = new Tiles(3 * a, 2 * a, 2, false);
        lilM[3][3] = new Tiles(3 * a, 3 * a, 2, false);
        lilM[4][3] = new Tiles(4 * a, 3 * a, 2, false);
        lilM[3][4] = new Tiles(3 * a, 4 * a, 2, false);
        lilM[4][4] = new Tiles(4 * a, 4 * a, 2, false);
        lilM[1][1] = new Tiles(1 * a, 1 * a, 2, false);
        lilM[2][1] = new Tiles(2 * a, 1 * a, 2, false);
        lilM[1][2] = new Tiles(1 * a, 2 * a, 2, false);
        lilM[2][2] = new Tiles(2 * a, 2 * a, 2, false);
        lilM[0][0] = new Tiles(0 * a, 0 * a, 2, false);
        lilM[1][0] = new Tiles(1 * a, 0 * a, 2, false);
        lilM[0][1] = new Tiles(0 * a, 1 * a, 2, false);
        lilM[1][1] = new Tiles(1 * a, 1 * a, 2, false);
        lilM[8][0] = new Tiles(8 * a, 0 * a, 2, false);
        lilM[9][0] = new Tiles(9 * a, 0 * a, 2, false);
        lilM[8][1] = new Tiles(8 * a, 1 * a, 2, false);
        lilM[9][1] = new Tiles(9 * a, 1 * a, 2, false);
        lilM[7][0] = new Tiles(7 * a, 0 * a, 2, false);
        lilM[8][0] = new Tiles(8 * a, 0 * a, 2, false);
        lilM[7][1] = new Tiles(7 * a, 1 * a, 2, false);
        lilM[8][1] = new Tiles(8 * a, 1 * a, 2, false);
        lilM[6][0] = new Tiles(6 * a, 0 * a, 2, false);
        lilM[7][0] = new Tiles(7 * a, 0 * a, 2, false);
        lilM[6][1] = new Tiles(6 * a, 1 * a, 2, false);
        lilM[7][1] = new Tiles(7 * a, 1 * a, 2, false);
        lilM[8][1] = new Tiles(8 * a, 1 * a, 2, false);
        lilM[9][1] = new Tiles(9 * a, 1 * a, 2, false);
        lilM[8][2] = new Tiles(8 * a, 2 * a, 2, false);
        lilM[9][2] = new Tiles(9 * a, 2 * a, 2, false);
        lilM[7][1] = new Tiles(7 * a, 1 * a, 2, false);
        lilM[8][1] = new Tiles(8 * a, 1 * a, 2, false);
        lilM[7][2] = new Tiles(7 * a, 2 * a, 2, false);
        lilM[8][2] = new Tiles(8 * a, 2 * a, 2, false);
        lilM[6][1] = new Tiles(6 * a, 1 * a, 2, false);
        lilM[7][1] = new Tiles(7 * a, 1 * a, 2, false);
        lilM[6][2] = new Tiles(6 * a, 2 * a, 2, false);
        lilM[7][2] = new Tiles(7 * a, 2 * a, 2, false);
        lilM[8][2] = new Tiles(8 * a, 2 * a, 2, false);
        lilM[9][2] = new Tiles(9 * a, 2 * a, 2, false);
        lilM[8][3] = new Tiles(8 * a, 3 * a, 2, false);
        lilM[9][3] = new Tiles(9 * a, 3 * a, 2, false);
        lilM[7][2] = new Tiles(7 * a, 2 * a, 2, false);
        lilM[8][2] = new Tiles(8 * a, 2 * a, 2, false);
        lilM[7][3] = new Tiles(7 * a, 3 * a, 2, false);
        lilM[8][3] = new Tiles(8 * a, 3 * a, 2, false);
        lilM[8][3] = new Tiles(8 * a, 3 * a, 2, false);
        lilM[9][3] = new Tiles(9 * a, 3 * a, 2, false);
        lilM[8][4] = new Tiles(8 * a, 4 * a, 2, false);
        lilM[9][4] = new Tiles(9 * a, 4 * a, 2, false);
        lilM[7][3] = new Tiles(7 * a, 3 * a, 2, false);
        lilM[8][3] = new Tiles(8 * a, 3 * a, 2, false);
        lilM[7][4] = new Tiles(7 * a, 4 * a, 2, false);
        lilM[8][4] = new Tiles(8 * a, 4 * a, 2, false);
        lilM[9][0] = new Tiles(9 * a, 0 * a, 2, false);
        lilM[10][0] = new Tiles(10 * a, 0 * a, 2, false);
        lilM[9][1] = new Tiles(9 * a, 1 * a, 2, false);
        lilM[10][1] = new Tiles(10 * a, 1 * a, 2, false);
        lilM[10][0] = new Tiles(10 * a, 0 * a, 2, false);
        lilM[11][0] = new Tiles(11 * a, 0 * a, 2, false);
        lilM[10][1] = new Tiles(10 * a, 1 * a, 2, false);
        lilM[11][1] = new Tiles(11 * a, 1 * a, 2, false);
        lilM[11][0] = new Tiles(11 * a, 0 * a, 2, false);
        lilM[12][0] = new Tiles(12 * a, 0 * a, 2, false);
        lilM[11][1] = new Tiles(11 * a, 1 * a, 2, false);
        lilM[12][1] = new Tiles(12 * a, 1 * a, 2, false);
        lilM[12][0] = new Tiles(12 * a, 0 * a, 2, false);
        lilM[13][0] = new Tiles(13 * a, 0 * a, 2, false);
        lilM[12][1] = new Tiles(12 * a, 1 * a, 2, false);
        lilM[13][1] = new Tiles(13 * a, 1 * a, 2, false);
        lilM[13][0] = new Tiles(13 * a, 0 * a, 2, false);
        lilM[14][0] = new Tiles(14 * a, 0 * a, 2, false);
        lilM[13][1] = new Tiles(13 * a, 1 * a, 2, false);
        lilM[14][1] = new Tiles(14 * a, 1 * a, 2, false);
        lilM[9][1] = new Tiles(9 * a, 1 * a, 2, false);
        lilM[10][1] = new Tiles(10 * a, 1 * a, 2, false);
        lilM[9][2] = new Tiles(9 * a, 2 * a, 2, false);
        lilM[10][2] = new Tiles(10 * a, 2 * a, 2, false);
        lilM[9][2] = new Tiles(9 * a, 2 * a, 2, false);
        lilM[10][2] = new Tiles(10 * a, 2 * a, 2, false);
        lilM[9][3] = new Tiles(9 * a, 3 * a, 2, false);
        lilM[10][3] = new Tiles(10 * a, 3 * a, 2, false);
        lilM[10][1] = new Tiles(10 * a, 1 * a, 2, false);
        lilM[11][1] = new Tiles(11 * a, 1 * a, 2, false);
        lilM[10][2] = new Tiles(10 * a, 2 * a, 2, false);
        lilM[11][2] = new Tiles(11 * a, 2 * a, 2, false);
        lilM[11][1] = new Tiles(11 * a, 1 * a, 2, false);
        lilM[12][1] = new Tiles(12 * a, 1 * a, 2, false);
        lilM[11][2] = new Tiles(11 * a, 2 * a, 2, false);
        lilM[12][2] = new Tiles(12 * a, 2 * a, 2, false);
        lilM[9][3] = new Tiles(9 * a, 3 * a, 2, false);
        lilM[10][3] = new Tiles(10 * a, 3 * a, 2, false);
        lilM[9][4] = new Tiles(9 * a, 4 * a, 2, false);
        lilM[10][4] = new Tiles(10 * a, 4 * a, 2, false);
        lilM[13][1] = new Tiles(13 * a, 1 * a, 2, false);
        lilM[14][1] = new Tiles(14 * a, 1 * a, 2, false);
        lilM[13][2] = new Tiles(13 * a, 2 * a, 2, false);
        lilM[14][2] = new Tiles(14 * a, 2 * a, 2, false);
        lilM[12][1] = new Tiles(12 * a, 1 * a, 2, false);
        lilM[13][1] = new Tiles(13 * a, 1 * a, 2, false);
        lilM[12][2] = new Tiles(12 * a, 2 * a, 2, false);
        lilM[13][2] = new Tiles(13 * a, 2 * a, 2, false);
        lilM[12][2] = new Tiles(12 * a, 2 * a, 2, false);
        lilM[13][2] = new Tiles(13 * a, 2 * a, 2, false);
        lilM[12][3] = new Tiles(12 * a, 3 * a, 2, false);
        lilM[13][3] = new Tiles(13 * a, 3 * a, 2, false);
        lilM[10][2] = new Tiles(10 * a, 2 * a, 2, false);
        lilM[11][2] = new Tiles(11 * a, 2 * a, 2, false);
        lilM[10][3] = new Tiles(10 * a, 3 * a, 2, false);
        lilM[11][3] = new Tiles(11 * a, 3 * a, 2, false);
        lilM[14][0] = new Tiles(14 * a, 0 * a, 2, false);
        lilM[15][0] = new Tiles(15 * a, 0 * a, 2, false);
        lilM[14][1] = new Tiles(14 * a, 1 * a, 2, false);
        lilM[15][1] = new Tiles(15 * a, 1 * a, 2, false);
        lilM[15][0] = new Tiles(15 * a, 0 * a, 2, false);
        lilM[16][0] = new Tiles(16 * a, 0 * a, 2, false);
        lilM[15][1] = new Tiles(15 * a, 1 * a, 2, false);
        lilM[16][1] = new Tiles(16 * a, 1 * a, 2, false);
        lilM[14][2] = new Tiles(14 * a, 2 * a, 2, false);
        lilM[15][2] = new Tiles(15 * a, 2 * a, 2, false);
        lilM[14][3] = new Tiles(14 * a, 3 * a, 2, false);
        lilM[15][3] = new Tiles(15 * a, 3 * a, 2, false);
        lilM[18][0] = new Tiles(18 * a, 0 * a, 2, false);
        lilM[19][0] = new Tiles(19 * a, 0 * a, 2, false);
        lilM[18][1] = new Tiles(18 * a, 1 * a, 2, false);
        lilM[19][1] = new Tiles(19 * a, 1 * a, 2, false);
        lilM[26][0] = new Tiles(26 * a, 0 * a, 2, false);
        lilM[27][0] = new Tiles(27 * a, 0 * a, 2, false);
        lilM[26][1] = new Tiles(26 * a, 1 * a, 2, false);
        lilM[27][1] = new Tiles(27 * a, 1 * a, 2, false);
        lilM[27][1] = new Tiles(27 * a, 1 * a, 2, false);
        lilM[28][1] = new Tiles(28 * a, 1 * a, 2, false);
        lilM[27][2] = new Tiles(27 * a, 2 * a, 2, false);
        lilM[28][2] = new Tiles(28 * a, 2 * a, 2, false);
        lilM[28][0] = new Tiles(28 * a, 0 * a, 2, false);
        lilM[29][0] = new Tiles(29 * a, 0 * a, 2, false);
        lilM[28][1] = new Tiles(28 * a, 1 * a, 2, false);
        lilM[29][1] = new Tiles(29 * a, 1 * a, 2, false);
        lilM[29][2] = new Tiles(29 * a, 2 * a, 2, false);
        lilM[30][2] = new Tiles(30 * a, 2 * a, 2, false);
        lilM[29][3] = new Tiles(29 * a, 3 * a, 2, false);
        lilM[30][3] = new Tiles(30 * a, 3 * a, 2, false);
        lilM[30][1] = new Tiles(30 * a, 1 * a, 2, false);
        lilM[31][1] = new Tiles(31 * a, 1 * a, 2, false);
        lilM[30][2] = new Tiles(30 * a, 2 * a, 2, false);
        lilM[31][2] = new Tiles(31 * a, 2 * a, 2, false);
        lilM[20][2] = new Tiles(20 * a, 2 * a, 2, false);
        lilM[21][2] = new Tiles(21 * a, 2 * a, 2, false);
        lilM[20][3] = new Tiles(20 * a, 3 * a, 2, false);
        lilM[21][3] = new Tiles(21 * a, 3 * a, 2, false);
        lilM[25][2] = new Tiles(25 * a, 2 * a, 2, false);
        lilM[26][2] = new Tiles(26 * a, 2 * a, 2, false);
        lilM[25][3] = new Tiles(25 * a, 3 * a, 2, false);
        lilM[26][3] = new Tiles(26 * a, 3 * a, 2, false);
        lilM[28][3] = new Tiles(28 * a, 3 * a, 2, false);
        lilM[29][3] = new Tiles(29 * a, 3 * a, 2, false);
        lilM[28][4] = new Tiles(28 * a, 4 * a, 2, false);
        lilM[29][4] = new Tiles(29 * a, 4 * a, 2, false);
        lilM[14][5] = new Tiles(14 * a, 5 * a, 2, false);
        lilM[15][5] = new Tiles(15 * a, 5 * a, 2, false);
        lilM[14][6] = new Tiles(14 * a, 6 * a, 2, false);
        lilM[15][6] = new Tiles(15 * a, 6 * a, 2, false);
        lilM[15][5] = new Tiles(15 * a, 5 * a, 2, false);
        lilM[16][5] = new Tiles(16 * a, 5 * a, 2, false);
        lilM[15][6] = new Tiles(15 * a, 6 * a, 2, false);
        lilM[16][6] = new Tiles(16 * a, 6 * a, 2, false);
        lilM[16][5] = new Tiles(16 * a, 5 * a, 2, false);
        lilM[17][5] = new Tiles(17 * a, 5 * a, 2, false);
        lilM[16][6] = new Tiles(16 * a, 6 * a, 2, false);
        lilM[17][6] = new Tiles(17 * a, 6 * a, 2, false);
        lilM[14][6] = new Tiles(14 * a, 6 * a, 2, false);
        lilM[15][6] = new Tiles(15 * a, 6 * a, 2, false);
        lilM[14][7] = new Tiles(14 * a, 7 * a, 2, false);
        lilM[15][7] = new Tiles(15 * a, 7 * a, 2, false);
        lilM[15][6] = new Tiles(15 * a, 6 * a, 2, false);
        lilM[16][6] = new Tiles(16 * a, 6 * a, 2, false);
        lilM[15][7] = new Tiles(15 * a, 7 * a, 2, false);
        lilM[16][7] = new Tiles(16 * a, 7 * a, 2, false);
        lilM[16][6] = new Tiles(16 * a, 6 * a, 2, false);
        lilM[17][6] = new Tiles(17 * a, 6 * a, 2, false);
        lilM[16][7] = new Tiles(16 * a, 7 * a, 2, false);
        lilM[17][7] = new Tiles(17 * a, 7 * a, 2, false);
        lilM[15][7] = new Tiles(15 * a, 7 * a, 2, false);
        lilM[16][7] = new Tiles(16 * a, 7 * a, 2, false);
        lilM[15][8] = new Tiles(15 * a, 8 * a, 2, false);
        lilM[16][8] = new Tiles(16 * a, 8 * a, 2, false);
        lilM[16][7] = new Tiles(16 * a, 7 * a, 2, false);
        lilM[17][7] = new Tiles(17 * a, 7 * a, 2, false);
        lilM[16][8] = new Tiles(16 * a, 8 * a, 2, false);
        lilM[17][8] = new Tiles(17 * a, 8 * a, 2, false);
        lilM[16][8] = new Tiles(16 * a, 8 * a, 2, false);
        lilM[17][8] = new Tiles(17 * a, 8 * a, 2, false);
        lilM[16][9] = new Tiles(16 * a, 9 * a, 2, false);
        lilM[17][9] = new Tiles(17 * a, 9 * a, 2, false);
        lilM[0][16] = new Tiles(0 * a, 16 * a, 2, false);
        lilM[1][16] = new Tiles(1 * a, 16 * a, 2, false);
        lilM[0][17] = new Tiles(0 * a, 17 * a, 2, false);
        lilM[1][17] = new Tiles(1 * a, 17 * a, 2, false);
        lilM[1][16] = new Tiles(1 * a, 16 * a, 2, false);
        lilM[2][16] = new Tiles(2 * a, 16 * a, 2, false);
        lilM[1][17] = new Tiles(1 * a, 17 * a, 2, false);
        lilM[2][17] = new Tiles(2 * a, 17 * a, 2, false);
        lilM[6][20] = new Tiles(6 * a, 20 * a, 2, false);
        lilM[7][20] = new Tiles(7 * a, 20 * a, 2, false);
        lilM[6][21] = new Tiles(6 * a, 21 * a, 2, false);
        lilM[7][21] = new Tiles(7 * a, 21 * a, 2, false);
        lilM[9][22] = new Tiles(9 * a, 22 * a, 2, false);
        lilM[10][22] = new Tiles(10 * a, 22 * a, 2, false);
        lilM[9][23] = new Tiles(9 * a, 23 * a, 2, false);
        lilM[10][23] = new Tiles(10 * a, 23 * a, 2, false);
        lilM[24][22] = new Tiles(24 * a, 22 * a, 2, false);
        lilM[25][22] = new Tiles(25 * a, 22 * a, 2, false);
        lilM[24][23] = new Tiles(24 * a, 23 * a, 2, false);
        lilM[25][23] = new Tiles(25 * a, 23 * a, 2, false);

        // Runde 1 starten
        waves(wave);

        Timer repaintT = new Timer(9, (ActionEvent e) -> {
            c.repaint();
        });
        repaintT.start();
    }

    public static void waves(int wave) {
        System.out.println("wave:" + wave);
        // Je nach wave unterschiedliche Gegner für die jeweilige Runde spawnen
        switch (wave) {
            case 1:
                for (int i = 0; i < 5; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 2; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 80, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 2; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 2; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 3:
                for (int i = 0; i < 7; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 2; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 5; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 4:
                for (int i = 0; i < 10; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 3; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 8; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 5:
                for (int i = 0; i < 15; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 1; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 20; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 6:
                for (int i = 0; i < 12; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 5; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 4; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 7:
                for (int i = 0; i < 10; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 6; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 8; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 8:
                for (int i = 0; i < 14; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 4; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 12; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 9:
                for (int i = 0; i < 15; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 5; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 15; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
            case 10:
                for (int i = 0; i < 20; i++) {
                    Enemy.Standard.add(new Enemy(0.2, 0, 50, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Anfangsgegner.png")), "Standard"));
                }
                for (int i = 0; i < 8; i++) {
                    Enemy.Tank.add(new Enemy(0.8, 0, 200, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Endgegner.png")), "Tank"));
                }
                for (int i = 0; i < 16; i++) {
                    Enemy.Fast.add(new Enemy(0.15, 0, 40, 5, 0, 490,
                            new ImageIcon(Enemy.class.getResource("/Bilder/Bomben_Gegner.png")), "Fast"));
                }
                break;
        }
    }

    // Nächstes Tile bestimmen
    public static Tiles getNextTile(int old) {
        for (int i = 0; i < lilM.length; i++) {
            for (int j = 0; j < lilM[i].length; j++) {
                if (lilM[i][j].getID() == old + 1) {
                    return lilM[i][j];
                }
            }
        }
        return null;
    }
}
