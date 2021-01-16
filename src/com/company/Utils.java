package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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
    public static void convertToGrayScale(BufferedImage myPicture, String filename, boolean save){
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
            if (save) {
                File ouptut = new File(filename);
                ImageIO.write(myPicture, "jpg", ouptut);
            }
        } catch (Exception e) {}

    }
    public static int[][] imageToMatrix(BufferedImage myPicture){
        int pix[][]= new int[myPicture.getHeight()][myPicture.getWidth()];
        for (int i = 0; i<myPicture.getHeight();i++){
            for (int j=0;j< myPicture.getWidth();j++){
                pix[i][j]= myPicture.getRGB(j,i);
            }
        }
        return pix;
    }

    public static BufferedImage matrixToImage(int[][] matrix){
        BufferedImage bufferedImage = new BufferedImage(matrix[0].length, matrix.length, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int pixel=matrix[i][j];
                Color newColor = new Color(pixel, pixel, pixel);
                bufferedImage.setRGB(j, i, newColor.getRGB());
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

    public static int[][] padding(int[][] matrix){ //padding for given matrix
        int m = matrix.length+2;
        int n = matrix[0].length+2;
        int[][] padd_matrix = new int[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(i==0 || j==0 || i==m-1 || j==n-1){
                    padd_matrix[i][j] = 128;
                }
            }
        }
        for (int i = 1; i < m-1; i++) {
            for (int j = 1; j < n-1; j++) {
                try {
                    padd_matrix[i][j] = matrix[i-1][j-1];

                } catch (java.util.NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
        }

        return padd_matrix;
    }

    public static ArrayList<int[][]> split_matrix(int[][] matrix, int n_split){
        int width = matrix[0].length; //split matrix vertically
        ArrayList<int[][]> matrices = new ArrayList<int[][]>();
        int inf = Math.floorDiv(width,n_split);
        int start_split_idx;
        for (int k = 0; k<n_split;k++){
            int[][] new_matrix;
            if (k!=n_split-1){
                new_matrix = new int[matrix.length][inf];
            }
            else{
                new_matrix = new int[matrix.length][width-k*inf];
            }
            for(int i =0; i<new_matrix.length; i++){
                start_split_idx = k*inf;
                for(int j=0;j< new_matrix[0].length; j++){
                    new_matrix[i][j]=matrix[i][start_split_idx+j];
                }
            }
            matrices.add(new_matrix);
        }
        return matrices;
    }

    public static ArrayList<int[][]> split_matrix_horiz(int[][] matrix, int n_split){
        int height = matrix.length; //split matrix horizontally
        ArrayList<int[][]> matrices = new ArrayList<int[][]>();
        int inf = Math.floorDiv(height,n_split);
        int start_split_idx;
        for (int k = 0; k<n_split;k++){
            int[][] new_matrix;
            if (k != n_split-1){
                new_matrix = new int[inf][matrix[0].length];
            }
            else{
                new_matrix = new int[height-k*inf][matrix[0].length];
            }
            for(int i =0; i<new_matrix.length; i++){
                start_split_idx = k*inf;
                for(int j=0;j< new_matrix[0].length; j++){
                    new_matrix[i][j]=matrix[start_split_idx + i][j];
                }
            }
            matrices.add(new_matrix);
        }
        return matrices;
    }

    public static int[][] merge_matrix(ArrayList<int[][]> matrices){  //merge splits
        int original_width = 0;
        int original_height = matrices.get(0).length;
        for (int i = 0;i<matrices.size();i++){
            original_width += matrices.get(i)[0].length;
        }
        int[][] result = new int[original_height][original_width];
        for (int m = 0;m<matrices.size();m++){
            int start_split_idx = m*matrices.get(0)[0].length;
            for (int i = 0;i<original_height;i++){
                for (int j = 0;j<matrices.get(m)[0].length;j++){
                    result[i][start_split_idx+j] = matrices.get(m)[i][j];
                }
            }
        }
        return result;
    }

    public static int[][] merge_matrix_horiz(ArrayList<int[][]> matrices){  //merge horizontally splits
        int original_width = matrices.get(0)[0].length;
        int original_height = 0;
        for (int i = 0;i<matrices.size();i++){
            original_height += matrices.get(i).length;
        }
        int[][] result = new int[original_height][original_width];
        for (int m = 0; m < matrices.size(); m++){
            int start_split_idx = m*matrices.get(0).length;
            for (int i = 0;i < matrices.get(m).length; i++){
                for (int j = 0;j < original_width;j++){
                    result[start_split_idx + i][j] = matrices.get(m)[i][j];
                }
            }
        }
        return result;
    }

    public static int[] merge_histogram(ArrayList<int[]> histograms){
        int[] new_hist = new int[256];
        for (int i = 0; i < new_hist.length; i++){
            for (int j = 0; j < histograms.size(); j++)
                new_hist[i] += histograms.get(j)[i];
        }
        return new_hist;
    }



}
