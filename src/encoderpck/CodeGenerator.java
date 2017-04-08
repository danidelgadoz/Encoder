/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encoderpck;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.pdf417.encoder.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sourceforge.jbarcodebean.*;
import net.sourceforge.jbarcodebean.model.*;


/**
 *
 * @author Daniel Delgado Diaz
 */
public class CodeGenerator {
    private String codigo;
    private Integer height;
    private Integer width;
    
    public CodeGenerator(String codigo){
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
    
    public BufferedImage getQR() throws FileNotFoundException, IOException {
        BitMatrix matrix;
        Writer writer = new QRCodeWriter();

        try {
            matrix = writer.encode(codigo, BarcodeFormat.QR_CODE, 0, 0);
        } catch (WriterException e) {
            e.printStackTrace(System.err);
            return null;
        }

        int codeWidth = matrix.getWidth();
        int codeHeight = matrix.getHeight();

        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < codeHeight; y++) {
            for (int x = 0; x < codeWidth; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        
        if (width != null && height != null) {// Resizing ...
            float widthIncreasePercent = Float.parseFloat(String.valueOf(width)) / codeWidth;
            float heightIncreasePercent = Float.parseFloat(String.valueOf(height)) / codeHeight;
            
            BufferedImage imageCustomSize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imageCustomSize.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(widthIncreasePercent, heightIncreasePercent);
            g.drawRenderedImage(image, at);
            image =  imageCustomSize; // Required size of the code image ...
        }
        
        return image;
    }
    
    public BufferedImage getBC(int tipoCode) throws FileNotFoundException, IOException {
        JBarcodeBean fox = new JBarcodeBean();
        switch (tipoCode) {
            case 2:
                fox.setCodeType(new Code128());
                break;
            case 3:
                fox.setCodeType(new Code39());
                break;
            case 4:
                fox.setCodeType(new Codabar());
                break;
            case 5:
                fox.setCodeType(new Ean13());
                break;
            case 6:
                fox.setCodeType(new Interleaved25());
                break;
        }
        fox.setCode(codigo);
        fox.setCheckDigit(true);
        //fox.setAngleDegrees(270);//horientacion

        Dimension x = new Dimension();
        x = fox.getMinimumSize();

        int codeWidth = (int) x.getWidth();
        int codeHeight = fox.getBarcodeHeight() + 35;

        BufferedImage image = fox.draw(new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB));// Default code image ....
        
        if (width != null && height != null) {// Resizing ...
            float widthIncreasePercent = Float.parseFloat(String.valueOf(width)) / codeWidth;
            float heightIncreasePercent = Float.parseFloat(String.valueOf(height)) / codeHeight;
                        
            BufferedImage imageCustomSize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imageCustomSize.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(widthIncreasePercent, heightIncreasePercent);
            g.drawRenderedImage(image, at);
            image =  imageCustomSize; // Required size of the code image ...
        }
        
        return image;
    }
    
    public BufferedImage getPDF417() throws FileNotFoundException, IOException {
        BitMatrix matrix;
        Writer writer = new PDF417Writer();

        try {
            matrix = writer.encode(codigo, BarcodeFormat.PDF_417, 1, 2);
        } catch (WriterException e) {
            e.printStackTrace(System.err);
            return null;
        }

        int codeWidth = matrix.getWidth();
        int codeHeight = matrix.getHeight();

        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < codeHeight; y++) {
            for (int x = 0; x < codeWidth; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }
        
        // Cutting edges and generating a new image ...
        codeWidth = codeWidth - 40;
        codeHeight = codeHeight - 50;        
        image = image.getSubimage(20, 25, codeWidth, codeHeight);
        // End - Cutting edges and generating a new image ...

        if (width != null && height != null) {// Resizing ...
            float widthIncreasePercent = Float.parseFloat(String.valueOf(width)) / codeWidth;
            float heightIncreasePercent = Float.parseFloat(String.valueOf(height)) / codeHeight;
                        
            BufferedImage imageCustomSize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imageCustomSize.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(widthIncreasePercent, heightIncreasePercent);
            g.drawRenderedImage(image, at);
            image =  imageCustomSize; // Required size of the code image ...
        }

        return image;
    }
    
    public BufferedImage getCODE128() throws FileNotFoundException, IOException {        
        BitMatrix matrix;
        Writer writer = new Code128Writer();

        try {
            matrix = writer.encode(codigo, BarcodeFormat.CODE_128, 0, 51);
        } catch (WriterException e) {
            e.printStackTrace(System.err);
            return null;
        }

        int codeWidth = matrix.getWidth();
        int codeHeight = matrix.getHeight();

        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < codeHeight; y++) {
            for (int x = 0; x < codeWidth; x++) {
                int grayValue = (matrix.get(x, y) ? 0 : 1) & 0xff;
                image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
            }
        }

        if (width != null && height != null) {// Resizing ...
            float widthIncreasePercent = Float.parseFloat(String.valueOf(width)) / codeWidth;
            float heightIncreasePercent = Float.parseFloat(String.valueOf(height)) / codeHeight;

            BufferedImage imageCustomSize = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imageCustomSize.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(widthIncreasePercent, heightIncreasePercent);
            g.drawRenderedImage(image, at);
            image = imageCustomSize;
        }
        return image;
    }
}
