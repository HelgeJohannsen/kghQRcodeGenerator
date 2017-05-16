package QrCode;

/*

This file is part of the iText (R) project.
Copyright (c) 1998-2016 iText Group NV

*/

/**
* Example written by Bruno Lowagie in answer to the following question:
* http://stackoverflow.com/questions/34117589
*/
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.util.GenericArray;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Image;

import com.itextpdf.test.annotations.type.SampleTest;

import FileHandling.PdfHandler;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddImage {
 public static void manipulatePdf(String src , String dest, String img) throws Exception {
	
    File file = new File(dest);
    file.getParentFile().mkdirs();
    new AddImage();
    
	
    PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
    ImageData image = ImageDataFactory.create(img);
    Image imageModel = new Image(image);
    imageModel.setAutoScale(false);
    imageModel.setAutoScaleWidth(false);
    imageModel.setMaxHeight(1);
    System.out.println(imageModel.getHeight());
    AffineTransform at = AffineTransform.getTranslateInstance(440, 500);
    at.concatenate(AffineTransform.getScaleInstance(150.0, 150.0));
    PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());
    float[] matrix = new float[6];
    at.getMatrix(matrix);
    canvas.addImage(image, matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);
    pdfDoc.close();
    Path path = Paths.get(img);
    PdfHandler.remove(path);
}



}