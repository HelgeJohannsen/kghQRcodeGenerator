package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import FileHandling.QRcontrol;
import FileHandling.MergeFiles;
import FileHandling.PdfHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UiControl extends Application {

	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<String> fileToMergeList = new ArrayList<String>();
	private ArrayList<Boolean> bar = new ArrayList();

	// Ziel des zusammengefügten PDFs der Name ist das aktuelle Datum plus die Uhrzeit damit die Datei einzigartig ist
	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	private String destPath;

	private String destPathqr = System.getProperty("user.home") + File.separator + "qrCodes" + File.separator + timeStamp + ".pdf";
	private String destPathTempStorage = System.getProperty("user.home") + File.separator + "qrCodes";
	private Desktop desktop = Desktop.getDesktop();
	List<File> list;
	Stage stage;
	final FileChooser fileChooser = new FileChooser();

	@FXML
	private TextField ErrorTextField;
	
	@FXML
	private TextField TextFieldSelectedDirectory;

	@FXML
	private ListView<?> fileListUI;

	@FXML
	private Button buttonCreateQrCode;

	@FXML
	private Button mergePDF;

	@FXML
	private Button chooseData;
	
	@FXML	
	private Button btnOpenDirectoryChooser;

	@FXML
	void handle(ActionEvent event) {
		List<File> list = fileChooser.showOpenMultipleDialog(stage);
		// textFiles.setEditable(true);
		if (list != null) {
			for (File file : list) {
				try {
					// TODO nur PDF dateien einlesen
					// CreateQR.createQR(file);
					ErrorTextField.setText("success");
					fileList.add(file.getPath());
					if (PdfHandler.checkIfATC(file.getPath())) {
						bar.add(true);
					}
					{
						bar.add(false);
					}

					// textFiles.setText("aaaaaaaaaaa");
				} catch (Exception e1) {
					ErrorTextField.setText("Fehler");
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			ObservableList row = FXCollections.observableArrayList(fileList);
			fileListUI.setItems(row);
		}
	}

	@FXML
	void mergePDF(ActionEvent event) {
		try {
			MergeFiles.mergeFiles(fileToMergeList, destPathqr);
			ErrorTextField.setText("Erfolgreich zusammenfügt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			ErrorTextField.setText("zusammenfügen der PDFs fehlgeschlagen");
			e1.printStackTrace();
		}
	}

	@FXML
	void createQrCodes(ActionEvent event) throws IOException {

		for (String inputFileString : fileList) {
			System.out.println(inputFileString);
			try {
				fileToMergeList.add(QRcontrol.createQR(inputFileString, destPathTempStorage));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ErrorTextField.setText("Qr Code konnte nicht erstellt werden");
				e.printStackTrace();
			}

			ErrorTextField.setText("Erfolgreich QR Codes erstellt");
		}
	}

	// Die Methode ist mit dem Button btnOpenDirectoryChooser verknüpft und soll den ZielPfad der zu speichernden PDFs speichern
	@FXML
    public void choosePath(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = 
                directoryChooser.showDialog(stage);
         

        destPath = selectedDirectory.getPath() + File.separator + timeStamp +".pdf";
        if(selectedDirectory == null){
        	TextFieldSelectedDirectory.setText("destPathTempStorage");
        }else{
        	destPath = selectedDirectory.getAbsolutePath();
        	TextFieldSelectedDirectory.setText(destPath);
        }
    }
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Pane page = FXMLLoader.load(UiControl.class.getResource("kgh.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Erstellt Lokal im Ordner AppData das Verzeichnis qrCodes hier werden die PDFs und QR Codes zwischengespeichert
		
		if (destPathTempStorage != null) {
		    File dirToCreate = new File(destPathTempStorage);
		    if (dirToCreate.exists() == false) {
		        boolean creationWasSuccessful = dirToCreate.mkdirs();
		        if (creationWasSuccessful == false) {
		            System.out.println("Creation was not successful");
		        }
		    }
		} else {
		    System.out.println("Unable to get programFiles path");
		}
	}

   
    
    
	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(UiControl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void updateRows(ArrayList bar) {
		// TODO fileListUI.g
	}
}
