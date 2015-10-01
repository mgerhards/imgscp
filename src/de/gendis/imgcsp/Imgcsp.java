/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gendis.imgcsp;

import com.sun.javafx.geom.BaseBounds;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author gerhards
 */
public class Imgcsp {

    
    
    public static enum POSITION { CENTER, LEFT_UP, RIGHT_BOTTOM };

    /**
     * Skaliert ein Bild zu den Abmessungen  bIn.getWidth()*scaleX und bIn.getHeight()*scaleY
     * 
     * @param biIn
     * @param scaleX
     * @param scaleY
     * @return 
     */
    public BufferedImage scale(BufferedImage biIn, Double scaleX, Double scaleY){
        int sw = (int) Math.ceil(biIn.getWidth()*scaleX);
        int sh = (int) Math.ceil(biIn.getHeight()*scaleY);
        BufferedImage biOut = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
        
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        
        AffineTransformOp scale = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        biOut = scale.filter(biIn, biOut);
        
        return biOut;
    }
    
    /**
     * Schneidet das gegebene Rechteckaus aus bIn und gibt es zurück
     * @param biIn
     * @param rect
     * @return 
     */
    public BufferedImage crop(BufferedImage biIn, Rectangle rect) {
        BufferedImage biOut = biIn.getSubimage(rect.x, rect.y, rect.width, rect.height);        
        return biOut;
    }

    public BufferedImage cover(BufferedImage img, int widthThumb, int heightThumb, POSITION position) throws IOException {        
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        
        double scaleX = ((double)widthThumb)/(double)w;
        double scaleY = ((double)heightThumb)/(double)h;
        
        BufferedImage biOut;
        Rectangle rect;
                
        if(scaleX >= scaleY){
            biOut = scale(img, scaleX, scaleX);
            rect = getHeightCrop(position, biOut, heightThumb, widthThumb);
        }else{
            biOut = scale(img, scaleY, scaleY);
            rect = getWidthCrop(position, biOut, heightThumb, widthThumb);
        }        	
         
        biOut = crop(biOut, rect);
        return biOut;
    }

    /**
     * Ermittelt das Rechteck aus dem Bild, das ausgeschnitten werden soll.
     * 
     * In diesem Fall ist das Bild noch höher als gewünscht. Bei POSITION.center wird daher die volle
     * Breite genommen sowie oben und unten die Hälfte abgeschnitten. Bei POSITION.LEFT_UP wird das crop rect
     * ganz oben angesetzt. Bei POSITION.RIGHT_BOTTOM ganz unten.
     * 
     * @param position
     * @param biOut
     * @param heightThumb
     * @param widthThumb
     * @return 
     */
    private Rectangle getHeightCrop(POSITION position, BufferedImage biOut, int heightThumb, int widthThumb) {
        Rectangle rect;
        switch(position){
            case LEFT_UP:
                rect = new Rectangle(0, 0, widthThumb, heightThumb);
                break;
            case RIGHT_BOTTOM:
                rect = new Rectangle(0, biOut.getHeight() - heightThumb, widthThumb, heightThumb);
                break;
            default:
            case CENTER:
                rect = new Rectangle(0, (biOut.getHeight() - heightThumb)/2, widthThumb, heightThumb);
            
        }
        return rect;
    }
    
    /**
     * Ermittelt das Rechteck aus dem Bild, das ausgeschnitten werden soll.
     * 
     * In diesem Fall ist das Bild noch breiter als gewünscht. Bei POSITION.center wird daher die volle
     * Höhe genommen sowie links und rechts die Hälfte abgeschnitten. Bei POSITION.LEFT_UP wird das crop rect
     * ganz links angesetzt. Bei POSITION.RIGHT_BOTTOM ganz rechts.
     * 
     * @param position
     * @param biOut
     * @param heightThumb
     * @param widthThumb
     * @return 
     */
    private Rectangle getWidthCrop(POSITION position, BufferedImage biOut, int heightThumb, int widthThumb) {
        Rectangle rect;
        switch(position){
            case LEFT_UP:
                rect = new Rectangle(0, 0, widthThumb, heightThumb);
                break;
            case RIGHT_BOTTOM:
                rect = new Rectangle(biOut.getWidth()- widthThumb, 0, widthThumb, heightThumb);
                break;
            default:
            case CENTER:
                rect = new Rectangle((biOut.getWidth()- widthThumb)/2, 0, widthThumb, heightThumb);
        }
        return rect;
    }

    
}
