Ermittelt das Rechteck aus dem Bild, das ausgeschnitten werden soll.
      
In diesem Fall ist das Bild noch höher als gewünscht. Bei POSITION.center wird daher die volle Breite genommen sowie oben und unten die Hälfte abgeschnitten. Bei POSITION.LEFT_UP wird das crop rect
ganz oben angesetzt. Bei POSITION.RIGHT_BOTTOM ganz unten.

Ein Beispiel

```
File imgFile = new File("img/original.png");
BufferedImage img = ImageIO.read(imgFile);        

Imgcsp imgscp = new Imgcsp();
BufferedImage scaled = imgscp.cover(img, 150, 150, Imgcsp.POSITION.LEFT_UP);

File outputfile = new File("img/scaled_left.png");
ImageIO.write(scaled, "png", outputfile);
```