package FileHandling;

import java.io.File;

import QrCode.AddImage;
import QrCode.QRCode;

public class QRcontrol {

	public static String createQR(String inputFile, String path) throws Exception {
		
		// Diese Klasse greift auf die Klassen AddImage und QRCode zu und erstellt mit ihnen einen QRCode und fügt ihn dem Bild hinzu
		// Die QR png Dateien werden kurzfristig in OutputPDFs erstellt und wieder gelöscht

		String extractedString = PdfHandler.getString(inputFile);

//		RenamePDFs.rename(extractedString, inputFile);
		String dest = path + File.separator + extractedString +".pdf";
	
		String img =  path + File.separator + extractedString + ".png";
		
		QRCode.create(extractedString,img );
		AddImage.manipulatePdf(inputFile, dest, img);
		//returnt einen Pfad der in einem Array durch MergeFiles konkateniert wird
		return dest;
	

	}
}
