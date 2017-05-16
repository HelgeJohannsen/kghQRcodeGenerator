package FileHandling;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;



public class PdfHandler {

	public static String read(String src) {
		Rectangle rect = new Rectangle(300, 760, 20, 20);
		PdfReader pdfReader = null;
		try {
			pdfReader = new PdfReader(src);
		} catch (IOException e) {
			//TODO
			System.out.println("Fehler beim einlesen des DateiPfades");
			e.printStackTrace();
		}
		PdfDocument pdfDoc = new PdfDocument(pdfReader);
		PageSize mediabox = pdfDoc.getDefaultPageSize();
		System.out.println(mediabox);
		TextRegionEventFilter regionFilter = new TextRegionEventFilter(rect);
		ITextExtractionStrategy strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(),
				regionFilter);

		String resultString = PdfTextExtractor.getTextFromPage(pdfDoc.getPage(1), strategy) + "\n";

		pdfDoc.close();
		return resultString;

	}

	public static String getString(String source) {
		String extractedString = read(source);
		String regexATC = "AT-?C-?[0-9]*-?[0-9]*-?[0-9]*-?[0-9]*-?[0-9]*-?";
		String regexKennung = "-([0-9]{10})-";
		String atc = null, kennung = null;

		Pattern patternATC = Pattern.compile(regexATC);
		Pattern patternKennung = Pattern.compile(regexKennung);
		Matcher matcher = patternATC.matcher(extractedString);
		{
			if (matcher.find()) {
				atc = matcher.group().replace("-", "");
			}
			System.out.println(extractedString);
			Matcher matcherKennung = patternKennung.matcher(extractedString);

			if (matcherKennung.find()) {
				kennung = matcherKennung.group(1);
			}

			System.out.print("ATC: " + atc + "\n" + "Kennung:" + kennung + "\n");

		}
		String newFilename = atc + "-" + kennung;
		return newFilename;
	}
	

	public static Boolean checkIfATC(String source) {
		String extractedString = read(source);
		String regexATC = "AT-?C-?[0-9]*-?[0-9]*-?[0-9]*-?[0-9]*-?[0-9]*-?";
		String regexKennung = "-([0-9]{10})-";
		String atc = null, kennung = null;
		Boolean isATC = false;

		Pattern patternATC = Pattern.compile(regexATC);
		Pattern patternKennung = Pattern.compile(regexKennung);
		Matcher matcher = patternATC.matcher(extractedString);
		{
			if (matcher.find()) {
				atc = matcher.group().replace("-", "");
			}
			System.out.println(extractedString);
			Matcher matcherKennung = patternKennung.matcher(extractedString);

			if (matcherKennung.find()) {
				isATC = true;
				
			}
		}
		
		return isATC;
	}
	public static void rename(String newName, String src){
		Path source = Paths.get(src);
		String newPath =  newName + ".pdf";
			
		try {
		
			Files.move(source, source.resolveSibling(newPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void remove(Path path){
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
}