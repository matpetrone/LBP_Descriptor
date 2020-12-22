package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils {
    public static void displayImage(BufferedImage myPicture){
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel jPanel = new JPanel();
        jPanel.add(picLabel);
        JFrame f = new JFrame();
        f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        f.add(jPanel);
        f.setVisible(true);
    }
    public static void convertToGrayScale(BufferedImage myPicture, String filename){
        try {
            int width = myPicture.getWidth();
            int height = myPicture.getHeight();
            for(int i=0; i<height; i++) {
                for(int j=0; j<width; j++) {
                    Color c = new Color(myPicture.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,
                            red+green+blue,red+green+blue);
                    myPicture.setRGB(j,i,newColor.getRGB());
                }
            }
            File ouptut = new File(filename);
            System.out.println("Done");
            ImageIO.write(myPicture, "jpg", ouptut);
        } catch (Exception e) {}

    }
    public static int[][] convertToMatrix(BufferedImage myPicture){
        int pix[][]= new int[myPicture.getWidth()][myPicture.getHeight()];
        for (int i = 0; i<myPicture.getWidth();i++){
            for (int j=0;j< myPicture.getHeight();j++){ //W and H could be wrong assigned
                pix[i][j]= myPicture.getRGB(i,j);
            }
        }
        return pix;

    }
}
