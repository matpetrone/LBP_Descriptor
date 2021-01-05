package com.company;

import java.util.Arrays;
import java.lang.Math;


public class Lbp {

    public static int[][] lbp(int[][] greyImage){
        int newImage[][] = new int[greyImage.length][greyImage[0].length];
        int neighboor = 3;
        int chunks = neighboor*neighboor;

        for (int i = 1; i<greyImage.length-1;i++){
            for (int j = 1; j<greyImage[0].length-1;j++){
                int subImage[][] = Utils.getSubMatrix(greyImage,i,j, neighboor);
                int center = subImage[1][1];
                for (int h=0; h<neighboor;h++){
                    for(int k = 0; k < neighboor;k++){
                        if (h!=1 && k!=1){
                            if(subImage[h][k]>=center)
                                subImage[h][k] = 1;
                            else
                                subImage[h][k] = 0;
                        }
                    }
                }
                int flatArray[] = Utils.flatten(subImage);
                System.out.println(Arrays.toString(flatArray));
                int flatArray_clean[] = Utils.deleteCenter(flatArray, 4);
                Integer[] flatArray_index = Utils.validIndex(flatArray_clean);
                int num=0;
                if(flatArray_index.length>0){
                    int sum = 0;
                    for (int l=0; l<flatArray_index.length;l++)
                        sum += Math.pow(2,flatArray_index[l]);
                    num = sum;
                }
                else
                    num = 0;
                newImage[i][j] = num;
            }
        }

        return  newImage;
    }
}
