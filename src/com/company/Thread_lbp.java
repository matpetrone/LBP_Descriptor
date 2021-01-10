package com.company;

public class Thread_lbp extends Thread {

    private int idx;
    private int[][] grey_matrix;
    private int[][] result_matrix;


    public Thread_lbp(int[][] matrix, int idx){
        this.idx = idx;
        grey_matrix = matrix;
    }

    public void run(){
        result_matrix = Lbp.lbp(grey_matrix);
    }

    public int[][] getResult_matrix() {
        return result_matrix;
    }

    public int getIdx() {
        return idx;
    }
}
