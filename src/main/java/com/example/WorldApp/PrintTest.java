package com.example.WorldApp;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PrintTest {

    public String getData(String type , int nr , HashMap<String , String> map , HashSet<String> hashSet  , HashSet<String> doi){

        RandomQuestion worldAppApplication = new RandomQuestion();
        StringBuilder sb = new StringBuilder();
        sb.append("Complete the gaps\n\n\n");
      //  System.out.println("______________"+worldAppApplication.key(map , hashSet));
      //  System.out.println("____________________ " + type);
        int a =0;
        while (nr > 0){

            switch (type){
                case "key":

                    sb.append("_____________________ -> ");
                    sb.append(worldAppApplication.key(map,doi)).append("\n\n");
                    break;

                case "value":

                    sb.append(worldAppApplication.value(map, hashSet));
                    sb.append(" -> _____________________ ").append("\n\n");
                    break;

                case "random":

                    if(a ==0){
                        sb.append("_____________________ -> ");
                        sb.append(worldAppApplication.key(map,doi)).append("\n\n");
                        a = 1;
                    }

                    else{
                        sb.append(worldAppApplication.value(map , hashSet ));
                        sb.append(" -> _____________________ ").append("\n\n");
                        a= 0;
                    }

                    break;
            }
            nr--;
        }

        sb.append("\n\n\nMemoryLord");
        System.out.println(sb.toString());
        return sb.toString();

    }

    public  void printFile(String filePath)  {

        try {
            System.out.println(filePath);
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setJobName("PrintJob");
            printerJob.setCopies(1);
            printerJob.setPrintable(new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    String[] lines = filePath.split("\n");
                    int y = 75; // Starting y position for the first line
                    for (String line : lines) {
                        g2d.drawString(line, 75, y);
                        y += 15; // Adjust the y position for the next line
                    }


                    return Printable.PAGE_EXISTS;
                }
            });

            if (!printerJob.printDialog()) {
                return;
            }
            printerJob.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }

    }


}
