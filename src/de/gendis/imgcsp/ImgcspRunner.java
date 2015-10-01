/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gendis.imgcsp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author gerhards
 */
public class ImgcspRunner {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        File imgFile = new File("img/original.png");
        BufferedImage img = ImageIO.read(imgFile);        
        
        Imgcsp imgscp = new Imgcsp();
        BufferedImage scaled = imgscp.cover(img, 150, 150, Imgcsp.POSITION.LEFT_UP);
        
        File outputfile = new File("img/scaled_left.png");
        ImageIO.write(scaled, "png", outputfile);
        
    }
}
