package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        try {
            String imagePath = "images/leopard.jpg";
            BufferedImage myPicture = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(imagePath));
            /*JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);
            JFrame f = new JFrame();
            f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
            f.add(jPanel);
            f.setVisible(true);*/
            Utils.convertToGrayScale(myPicture,"images/grey.jpg");
            int matrix[][] = Utils.imageToMatrix(myPicture);
            ArrayList<int[][]> split = Utils.split_matrix(matrix, 3);
            //System.out.println(matrix);
            int padd_matrix[][] = Utils.padding(matrix);
            int[][] texture = Lbp.lbp(padd_matrix);
            BufferedImage text_image = Utils.matrixToImage(texture);
            Utils.displayImage(text_image);

        }catch (IOException err){
            System.out.println(err.getMessage());
        }



    }
}
