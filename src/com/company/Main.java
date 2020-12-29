package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {
            String imagePath = "images/leopard.jpg";
            BufferedImage myPicture = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(imagePath));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            JPanel jPanel = new JPanel();
            jPanel.add(picLabel);
            JFrame f = new JFrame();
            f.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
            f.add(jPanel);
            f.setVisible(true);
            Utils.convertToGrayScale(myPicture,"src/com/company/images/gray.jpg");
            int matrix[][] = Utils.convertToMatrix(myPicture);
            System.out.println(matrix);

        }catch (IOException err){
            System.out.println(err.getMessage());
        }



    }
}
