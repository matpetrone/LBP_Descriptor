package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        Date date = new Date();
        String pathToProject = System.getProperty("user.dir");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String csvPath = pathToProject + "/res/results/";
        FileWriter csvResult = new FileWriter(csvPath + dateFormat.format(date) + ".csv", true);
        FileWriter histResult = new FileWriter(csvPath + dateFormat.format(date) + "_histogram.csv", true);
        String[] imageNames = {"giraffe360p.jpg", "tiger480p.jpg", "leopard720p.jpg", "cheetah1080p.jpg", "deer2160p.jpg", "wolf4320p.jpg", "panorama8640p.jpg"};
        for (int i = 0; i < imageNames.length; i++){
            Test.test(imageNames[i], csvResult, histResult);
        }
        csvResult.close();
        histResult.close();
    }
}
