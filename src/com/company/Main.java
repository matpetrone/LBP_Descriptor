package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        try {
            String imagePath = "images/leopard.jpg";
            BufferedImage myPicture = ImageIO.read(Main.class.getClassLoader().getResourceAsStream(imagePath));
            float speedUp;
            Utils.convertToGrayScale(myPicture,"images/grey.jpg");
            int matrix[][] = Utils.imageToMatrix(myPicture);
            //int padd_matrix[][] = Utils.padding(matrix);
            Instant start1 = Instant.now();
            int[][] texture = Lbp.lbp(matrix);
            Instant end1 = Instant.now();
            float delta_seq = Duration.between(start1, end1).toMillis();
            System.out.format("Sequential Time Execution:%f\n", delta_seq);
            BufferedImage text_image = Utils.matrixToImage(texture);
            //Utils.displayImage(text_image);

            //Parallel program
            int n_threads = Runtime.getRuntime().availableProcessors();
            ArrayList<int[][]> splits = Utils.split_matrix(matrix, n_threads);
            ArrayList<int[][]> lbp_splits = new ArrayList<int[][]>();
            ArrayList<Thread_lbp> threads = new ArrayList<Thread_lbp>();
            Instant start2 = Instant.now();
            for (int i = 0; i<n_threads; i++){
                threads.add(new Thread_lbp(splits.get(i),i));
                threads.get(i).start();
            }
            for (int i = 0;i<n_threads;i++){
                threads.get(i).join();
                lbp_splits.add(threads.get(i).getResult_matrix());
            }
            Instant end2 = Instant.now();
            float delta_par = Duration.between(start2, end2).toMillis();
            System.out.format("Parallel Time Execution:%f\n", delta_par);
            speedUp = delta_seq/delta_par;
            System.out.format("Speedup:%f", speedUp);
            int[][] mat = Utils.merge_matrix(lbp_splits);
            BufferedImage test_image = Utils.matrixToImage(mat);
            Utils.displayImage(test_image);





        }catch (IOException | InterruptedException err){
            System.out.println(err.getMessage());
        }



    }
}
