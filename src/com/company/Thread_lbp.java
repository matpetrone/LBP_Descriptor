package com.company;

import java.util.concurrent.atomic.AtomicInteger;

public class Thread_lbp extends Thread {

    private int idx;
    private int[][] gray_matrix;
    private int[][] result_matrix;
    private int[] histogram;



    public Thread_lbp(int[][] matrix, int idx){
        this.idx = idx;
        gray_matrix = matrix.clone();
        histogram = new int[256];
    }

    public void run(){
        result_matrix = Lbp.lbp(gray_matrix);
        histogram = Lbp.computeHistogram(result_matrix);
    }

    public int[][] getResult_matrix() {
        return result_matrix;
    }

    public int getIdx() {
        return idx;
    }

    public int[] getHistogram() {
        return histogram;
    }
}
