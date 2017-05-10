package FileHandling;

import QrCode.AddImage;
import QrCode.QRCode;

public class CreateQR {

	public static String createQR(String inputFile) throws Exception {
		
		// Diese Klasse greift auf die Klassen AddImage und QRCode zu und erstellt mit ihnen einen QRCode und fügt ihn dem Bild hinzu
		// Die QR png Dateien werden kurzfristig in OutputPDFs erstellt und wieder gelöscht
		long start = System.currentTimeMillis();
		String extractedString = ReadPDF.getString(inputFile);

		RenamePDFs.rename(extractedString, inputFile);
		String dest = "./src/OutputPDFs/" + extractedString +".pdf";
		
		String img = "./src/OutputPDFs/" + extractedString + ".png";
		
		long time = System.currentTimeMillis() - start;
		QRCode.create(extractedString,img );
		System.out.println("Time: " + time);
		AddImage.manipulatePdf(inputFile, dest, img);
		//returnt einen Pfad der in einem Array durch MergeFiles konkateniert wird
		return dest;
	

	}
}
