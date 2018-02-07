package it.bottai.view;

import it.bottai.Main;
import it.bottai.model.EsercizioImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentsOverviewController {

    @FXML
    private ListView<String> PDFList;
    @FXML
    private ListView<String> CSVList;

    private Main mainApp;

    private final FileChooser fileChooser = new FileChooser();

    private Desktop desktop = Desktop.getDesktop();

    private static ArrayList<String> fileWriterCsvList = new ArrayList<>();

    private ObservableList<EsercizioImpl> documentEsercizioList = FXCollections.observableArrayList();

    private static ObservableList<String> csvFilenameList = FXCollections.observableArrayList();

    //Il costruttore che viene chiamato prima di initialize()
    public DocumentsOverviewController() {
    }

    //Aggiunge i nomi dei file csv alla CSVList
    public static void addFilenameToCsvList(String csvNames) {
        csvFilenameList.add(csvNames);
    }

    public void setFilenameToCsvList(){
        CSVList.setItems(csvFilenameList);
    }

    //Aggiunge gli output dei csv alla lista dei FileWriter
    public static void addToFileWriterCsvList(String outputText){
        if(outputText != null) {
            fileWriterCsvList.add(outputText);
        }
    }

    @FXML
    //Questa funzione associa al bottone "Find PDF" la ricerca di più documenti
    protected void openMultipleButton(ActionEvent event) throws FileNotFoundException{
        configureFileChooser(fileChooser);
        List<File> pdfFileList = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        System.out.println(pdfFileList);
        initPdfListColumn(pdfFileList);
    }

    //Inizializza la PDFList aggiungendo i PDF da convertire
    public void initPdfListColumn(List<File> pdfFileList){
        for (File file : pdfFileList){
            EsercizioImpl doc = new EsercizioImpl(file.getName(), file);
            documentEsercizioList.add(doc);
        }
        setPdfDocumentListView();
    }

    //Questa funzione scandisce i documenti PDF e li aggiunge a PDFList
    public void setPdfDocumentListView(){
        ObservableList<String> filenameList = FXCollections.observableArrayList();
        for (EsercizioImpl esercizio: documentEsercizioList){
            filenameList.add(esercizio.getNomeFile());
            System.out.println("Elemento inserito");
        }
        PDFList.setItems(filenameList);
    }

    //Questa funzione permette di cercare all'interno delle directory i PDF da convertire
    private void configureFileChooser(final FileChooser fileChooser) throws FileNotFoundException {
        fileChooser.setTitle("View PDF");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);
    }

    //Questa funzione fa partire la conversione dei documenti, associata al bottone "Start"
    public void startConversion(){
        try {
            //Analizzo ogni file all'interno della cartella
            for (EsercizioImpl esercizio : documentEsercizioList) {
                String filename = esercizio.getNomeFile();
                System.out.println("Controllo file da analizzare: " + filename);
                if ((filename.startsWith("rom_") || filename.startsWith("power_") || filename.startsWith("sway_"))) {
                    esercizio.processConversion();
                } else {
                    System.out.println(filename + " non convertibile.");
                }
            }
            setFilenameToCsvList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    //Funzione associata al bottone "Save"
    protected void saveCsvList(ActionEvent event) throws FileNotFoundException {
        FileChooser fileSaver = new FileChooser();
        configureFileSaver(fileSaver);
        for (String outputText : fileWriterCsvList){
            File file = fileSaver.showSaveDialog(mainApp.getPrimaryStage());
            if(outputText != null && file != null){
                saveFile(file, outputText);
                System.out.println("File salvato con successo.");
            }
        }
    }

    //Questa funzione apre la schermata per decidere dove salvare i csv generati
    private void configureFileSaver(final FileChooser fileChooser) throws FileNotFoundException {
        fileChooser.setTitle("Save CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV (.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
    }

    //Funzione per il salvataggio del file
    private void saveFile(File file, String outputText){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(outputText);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(DocumentsOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    //Funzione che svuota PDFList e CSVList
    protected void clearList(ActionEvent event){
        PDFList.getItems().clear();
        CSVList.getItems().clear();
        documentEsercizioList.clear();
        fileWriterCsvList.clear();
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
