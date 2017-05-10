package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import FileHandling.CreateQR;
import FileHandling.MergeFiles;
import FileHandling.ReadPDF;
import FileHandling.RenamePDFs;
import QrCode.QRCode;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UiControl extends Application {

	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<String> fileToMergeList = new ArrayList<String>();
	private ArrayList<Boolean> bar = new ArrayList();
	private String dest = "./src/OutputPDFs/MergedPDF.pdf";
	private Desktop desktop = Desktop.getDesktop();
	List<File> list;
	Stage stage;
	final FileChooser fileChooser = new FileChooser();

	@FXML
	private TextField ErrorTextField;

	@FXML
	private ListView<?> fileListUI;

	@FXML
	private Button buttonCreateQrCode;

	@FXML
	private Button mergePDF;

	@FXML
	private Button chooseData;

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
					if (ReadPDF.checkIfATC(file.getPath())) {
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
			MergeFiles.mergeFiles(fileToMergeList, dest);
			ErrorTextField.setText("Erfolgreich gemerged");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			ErrorTextField.setText("Mergen fehlgeschlagen");
			e1.printStackTrace();
		}
	}

	@FXML
	void createQrCodes(ActionEvent event) throws IOException {
		for (String inputFileString : fileList) {
			System.out.println(inputFileString);
			try {
				fileToMergeList.add(CreateQR.createQR(inputFileString));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ErrorTextField.setText("Qr Code konnte nicht erstellt werden");
				e.printStackTrace();
			}

			ErrorTextField.setText("Erfolgreich QR Codes erstellt");
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
