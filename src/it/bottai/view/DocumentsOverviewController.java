package it.bottai.view;

import it.bottai.Main;
import it.bottai.model.Esercizio;
import it.bottai.model.EsercizioImpl;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentsOverviewController {

    @FXML
    private TableView<Esercizio> documentTable;
    @FXML
    private ListView<String> PDFList;
    @FXML
    private ListView<String> CSVList;

    private Main mainApp;

    private final FileChooser fileChooser = new FileChooser();

    private Desktop desktop = Desktop.getDesktop();

    private ObservableList<Esercizio> documentEsercizioList = FXCollections.observableArrayList();

    //Questa funzione restituisce i documenti sotto forma di lista
    public ObservableList<Esercizio> getDocumentEsercizioList() {
        return documentEsercizioList;
    }

    //Il costruttore che viene chiamato prima di initialize()
    public DocumentsOverviewController() {
    }

    @FXML
    protected void openMultipleButton(ActionEvent event) throws FileNotFoundException{
        configureFileChooser(fileChooser);
        List<File> pdfFileList = fileChooser.showOpenMultipleDialog(mainApp.getPrimaryStage());
        System.out.println(pdfFileList);
    }

    //Questa funzione si posiziona nella cartella contenente i PDF da analizzare
    private void configureFileChooser(final FileChooser fileChooser) throws FileNotFoundException {
        fileChooser.setTitle("View PDF");
        //al posto di user.home dovrei cercare di mettere il percorso per la cartella con i pdf
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF (.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);

    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void setDocumentTable(){
        // Add observable list data to the table
        documentTable.setItems(getDocumentEsercizioList());
    }

    public void setPdfColumn(){

    }

    public void addPdfToColumn(Esercizio doc, String docName){


    }

    public void initPdfListColumn(List<File> pdfFileList){
        for (File file : pdfFileList){
            Esercizio doc = new EsercizioImpl(file.getName(), file);
            documentEsercizioList.add(doc);
        }
        setDocumentTable();
    }
}
