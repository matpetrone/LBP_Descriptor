package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread_lbp implements Runnable {

    private int idx;
    private int[][] gray_matrix;
    private int[][] result_matrix;
    AtomicInteger[] histogram;



    public Thread_lbp(int[][] matrix, int idx){
        this.idx = idx;
        gray_matrix = matrix;
        histogram = new AtomicInteger[256];
        result_matrix = new int[gray_matrix.length - 2][gray_matrix[0].length - 2];
    }

    public void run(){
        Lbp.lbp_threads(gray_matrix, result_matrix);
    }

    public int[][] getResult_matrix() {
        return result_matrix;
    }

    public int getIdx() {
        return idx;
    }
}
