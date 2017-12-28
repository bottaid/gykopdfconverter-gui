package it.bottai.view;

import it.bottai.Main;
import it.bottai.model.Esercizio;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class DocumentsOverviewController {

    @FXML
    private TableView<Esercizio> documentTable;
    @FXML
    private TableColumn<Esercizio, String> PDF;
    @FXML
    private TableColumn<Esercizio, String> CSV;

    private Main mainApp;

    //Il costruttore che viene chiamato prima di initialize()
    public DocumentsOverviewController() {
    }

    //Inizializza la tabella con le due colonne
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        PDF.setCellValueFactory(cellData -> cellData.getValue().primaColonnaProperty());
        CSV.setCellValueFactory(cellData -> cellData.getValue().secondaColonnaProperty());
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        documentTable.setItems(mainApp.getDocumentEsercizioList());
    }
}
