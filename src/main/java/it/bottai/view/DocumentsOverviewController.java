package it.bottai.view;

import it.bottai.Main;
import it.bottai.model.EsercizioImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    //Questa funzione restituisce i documenti sotto forma di lista
    public ObservableList<EsercizioImpl> getDocumentEsercizioList() {
        return documentEsercizioList;
    }

    //Il costruttore che viene chiamato prima di initialize()
    public DocumentsOverviewController() {
    }

    public static void addFilenameToCsvList(String csvNames) {
        csvFilenameList.add(csvNames);
    }

    public void setFilenameToCsvList(){
        CSVList.setItems(csvFilenameList);
    }

    public static void addToFileWriterCsvList(String outputText){
        if(outputText != null) {
            fileWriterCsvList.add(outputText);
        }
    }

    @FXML
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

    @FXML
    //Questa funzione associa al bottone "Find PDF" la ricerca di più documenti
    protected void openMultipleButton(ActionEvent event) throws FileNotFoundException{
        configureFileChooser(fileChooser);
        List<File> pdfFileList = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        System.out.println(pdfFileList);
        initPdfListColumn(pdfFileList);
    }

    //Questa funzione si posiziona nella cartella contenente i PDF da analizzare
    private void configureFileChooser(final FileChooser fileChooser) throws FileNotFoundException {
        fileChooser.setTitle("View PDF");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);
    }

    private void configureFileSaver(final FileChooser fileChooser) throws FileNotFoundException {
        fileChooser.setTitle("Save CSV");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("CSV (.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(filter);
    }

    private void saveFile(File file, String outputText){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(outputText);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(DocumentsOverviewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    //Questa funzione scandisce i documenti PDF e li aggiunge a PDFList
    public void setPdfDocumentListView(){
        // Add observable list data to the table
        ObservableList<String> filenameList = FXCollections.observableArrayList();
        for (EsercizioImpl esercizio: documentEsercizioList){
            filenameList.add(esercizio.getNomeFile());
            System.out.println("Elemento inserito");
        }
        PDFList.setItems(filenameList);
    }

    //Inizializza la PDFList aggiungendo i PDF da convertire
    public void initPdfListColumn(List<File> pdfFileList){
        for (File file : pdfFileList){
            EsercizioImpl doc = new EsercizioImpl(file.getName(), file);
            documentEsercizioList.add(doc);
        }
        setPdfDocumentListView();
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
                            /*
                String filename = elencoFile[i];
                System.out.println("Controllo file da analizzare: " +filename);
                String outputDir = lettoreTabella.getOutputDirectory();
                if ((filename.startsWith("rom_") || filename.startsWith("power_") || filename.startsWith("sway_")) &&  filename.endsWith(".pdf")){
                    lettoreTabella.setFilename(filename);
                    EsercizioDefault esercizio = new EsercizioDefault();
                    ArrayList<String> righeTabella = esercizio.estraiRigheTabella(lettoreTabella.parsePdf());
                    righeTabella = esercizio.parsaRigheTabella(righeTabella);
                    esercizio.scriviRigheTabella(righeTabella, outputDir, filename);
                }
                else {
                    System.out.println(elencoFile[i] + " non convertibile.");
                }*/
            }
            setFilenameToCsvList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
