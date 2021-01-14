package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Test {
    public static void test(String imageName, FileWriter csvResults){
        try{
            String pathToProject = System.getProperty("user.dir");
            String imagePath = pathToProject + "/res/images/" + imageName;
            String imageSize = imageName.replaceAll("[^0-9]", "");
            BufferedImage myPicture = ImageIO.read(new File(imagePath));
            String grayImagePath = pathToProject + "/res/images/" + imageName.substring(0,imageName.indexOf("."))+"_gray.jpg";
            String csvLine = "0,1" + "," + imageSize;
            int n_iter = 15;
            float delta_seq = 0;
            int[][] text_matrix = new int[myPicture.getHeight()][myPicture.getWidth()];
            Utils.convertToGrayScale(myPicture, grayImagePath, false);
            int matrix[][] = Utils.imageToMatrix(myPicture);

            //Sequential part
            int padd_matrix[][] = Utils.padding(matrix);
            Instant start1;
            Instant end1;
            System.out.printf("%n## Starting Sequential test on %dp image ## %n%n", Integer.parseInt(imageSize));
            for (int i = 0; i < n_iter; i++){
                start1 = Instant.now();
                text_matrix = Lbp.lbp(padd_matrix);
                end1 = Instant.now();
                delta_seq += Duration.between(start1, end1).toMillis();
            }
            delta_seq /= n_iter;
            System.out.format("Sequential Time Execution:%f%n", delta_seq);
            csvLine = csvLine + "," + Float.toString(delta_seq) + "\n";
            csvResults.append(csvLine);
            //BufferedImage text_image = Utils.matrixToImage(text_matrix);
            //File ouptut = new File(grayImagePath);
            //ImageIO.write(text_image, "jpg", ouptut);
            //Utils.displayImage(text_image);

            //Parallel part
            int max_threads = Runtime.getRuntime().availableProcessors();
            System.out.printf("%n## Starting Parallel test on %dp image ## %n%n", Integer.parseInt(imageSize));
            for (int i = 2; i <= max_threads; i++){
                float delta_par = 0;
                csvLine = "1," + Integer.toString(i)+ "," + imageSize;
                ArrayList<int[][]> lbp_splits = new ArrayList<int[][]>();
                ArrayList<Thread_lbp> threads;
                ArrayList<int[][]> splits = Utils.split_matrix_horiz(matrix, i);
                for (int l = 0; l < splits.size(); l++)
                    splits.set(l, Utils.padding(splits.get(l)));
                Instant start2;
                Instant end2;
                float speedUp;
                for (int k = 0; k < n_iter; k++){
                    threads = new ArrayList<Thread_lbp>();
                    lbp_splits = new ArrayList<int[][]>();
                    start2 = Instant.now();
                    for (int j = 0; j < i; j++){
                        threads.add(new Thread_lbp(splits.get(j),j));
                        threads.get(j).start();
                    }
                    for (int j = 0; j < i; j++){
                        threads.get(j).join();
                        lbp_splits.add(threads.get(j).getResult_matrix());
                    }
                    end2 = Instant.now();
                    delta_par += Duration.between(start2, end2).toMillis();
                }
                delta_par /= n_iter;
                System.out.format("Parallel Time Execution with %d threads:%f%n", i, delta_par);
                speedUp = delta_seq/delta_par;
                csvLine = csvLine + "," + Float.toString(delta_par) + "," + Float.toString(speedUp) + "\n";
                csvResults.append(csvLine);
                System.out.format("SpeedUp with %d threads:%f%n", i, speedUp);
                /*if (i == 4){
                    String[] str = new String[i];
                    for (int n = 0; n < lbp_splits.size(); n++){
                        str[n] = Integer.toString(n);
                        BufferedImage test_image = Utils.matrixToImage(lbp_splits.get(n));
                        File ouptut = new File(pathToProject + "/res/images/" + str[n] + ".jpg");
                        ImageIO.write(test_image, "jpg", ouptut);
                    }
                    int[][] mat = Utils.merge_matrix_horiz(lbp_splits);
                    BufferedImage test_image = Utils.matrixToImage(mat);
                    File ouptut = new File(pathToProject + "/res/images/gray.jpg");
                    ImageIO.write(test_image, "jpg", ouptut);
                    //Utils.displayImage(test_image);
                }*/

            }
        }catch(IOException | InterruptedException err){
            System.out.println(err.getMessage());
        }

    }

}
