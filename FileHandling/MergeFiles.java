package FileHandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

public class MergeFiles {
    public static void mergeFiles( ArrayList<String> fileList, String dest) throws IOException {
    	//TODO try catch 
    	
        File file = new File(dest);
        file.getParentFile().mkdirs();
     	PdfDocument targetPDF = new PdfDocument(new PdfWriter(dest));
    	for (String pathToPDF : fileList) {
			PdfDocument pdfToMerge = new PdfDocument(new PdfReader(pathToPDF));
        	for (int i = 1; i <= pdfToMerge.getNumberOfPages() ; i++) {
				pdfToMerge.copyPagesTo(i, i, targetPDF);
		    	System.out.println(pdfToMerge.toString() + i);
			}
        	pdfToMerge.close();
   		}

    	targetPDF.close();
    
    for(String PathToDeleteFile : fileList){
    	Path fileToDelete = Paths.get(PathToDeleteFile);
    	PdfHandler.remove(fileToDelete);
    }
    }
}
