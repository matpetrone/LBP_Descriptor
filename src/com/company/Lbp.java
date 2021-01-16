package com.company;

import java.util.Arrays;
import java.lang.Math;


public class Lbp {
    public static int[][] lbp(int[][] greyImage){
        int newImage[][] = new int[greyImage.length-2][greyImage[0].length-2]; //returned image don't have borders from padding
        int neighboor = 3;
        int radius = neighboor / 2;

        for (int i = 1; i<greyImage.length-1;i++){
            for (int j = 1; j<greyImage[0].length-1;j++){
                int[] lbp_code = new int[neighboor*neighboor -1];
                int lbp_idx = 0;
                for (int h = -radius; h<radius;h++){
                    for(int k = - radius; k < radius;k++){
                        if(h==1 && k==1){}
                        else{
                            if(greyImage[i+h][j+k]>= greyImage[i][j])
                                lbp_code[lbp_idx++] = 1;
                            else 
                                lbp_code[lbp_idx++] = 0;
                        }

                    }
                }
                // compute LBP value for the i, j pixel    
                int sum = 0;
                for(int c_idx=0; c_idx<lbp_code.length; c_idx++){
                    sum +=  Math.pow(2,c_idx) * lbp_code[c_idx];
                }
                newImage[i-1][j-1] = sum; //returned image has been traslated on top-left respect to original one
            }
        }
        return  newImage;
    }


    public static int[] computeHistogram(int[][] matrix){
        int[] histogram = new int[256];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++)
                histogram[matrix[i][j]]++;
        }
        return histogram;
    }

    public static boolean verifyHistogram(int[] hist, int[][] matrix){
        int counth = 0;
        int countm = matrix.length * matrix[0].length;
        for (int i = 0; i < hist.length; i++)
            counth += hist[i];
        if (counth == countm)
            return true;
        else
            return false;
    }
}
