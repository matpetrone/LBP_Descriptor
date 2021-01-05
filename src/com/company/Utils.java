package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;


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
    public static int[][] imageToMatrix(BufferedImage myPicture){
        int pix[][]= new int[myPicture.getWidth()][myPicture.getHeight()];
        for (int i = 0; i<myPicture.getWidth();i++){
            for (int j=0;j< myPicture.getHeight();j++){ //W and H could be wrong assigned
                pix[i][j]= myPicture.getRGB(i,j);
            }
        }
        return pix;
    }

    public static BufferedImage matrixToImage(int[][] matrix){
        BufferedImage bufferedImage = new BufferedImage(matrix.length, matrix[0].length, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int pixel=matrix[i][j];
                //System.out.println("The pixel in Matrix: "+pixel);
                bufferedImage.setRGB(i, j, pixel);
                //System.out.println("The pixel in BufferedImage: "+bufferedImage.getRGB(i, j));
            }
        }
        return bufferedImage;
    }

    public static int[][] getSubMatrix(int[][] image, int i, int j, int neighboor){
        int newMat[][]= new int[neighboor][neighboor];
        for(int h =0;h<neighboor;h++){
            for(int k =0;k<neighboor;k++){
                newMat[h][k] = image[i-1+h][j-1+k];
            }
        }
        return newMat;
    }

    public static int[] flatten(int[][] arr) {
        int result[] = new int[arr.length * arr[0].length];
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                result[index++] = arr[i][j];
            }
        }
        return result;
    }

    public static int[] deleteCenter(int[] array, int index){
        int[] copy = new int[array.length - 1];
        for (int i = 0, j = 0; i < array.length; i++) {
            if (i != index)
                copy[j++] = array[i];
        }
        return copy;
    }

    public static Integer[] validIndex(int[] array){
        ArrayList<Integer> copy = new ArrayList<Integer>();
        for(int i = 0;i<array.length;i++) {
            if (array[i] > 0)
                copy.add(i);
        }
        Integer[] arr = copy.toArray(new Integer[0]);
        return arr;

    }




}
