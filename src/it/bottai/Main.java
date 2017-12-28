package it.bottai;

import it.bottai.model.Esercizio;
import it.bottai.view.DocumentsOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Desktop desktop = Desktop.getDesktop();
    private ObservableList<Esercizio> documentEsercizioList = FXCollections.observableArrayList();

    //Questa funziona restituisce uno Stage, un contenitore principale
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    //Questa funzione restituisce i documenti sotto forma di lista
    public ObservableList<Esercizio> getDocumentEsercizioList() {
        return documentEsercizioList;
    }

    //Questa funzione fa partire l'esecuzione del programma
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("GykoPdfConverter");

        initRootLayout();
        showDocumentsOverview();

    }

    //Questa funzione inizializza il rootLayout
    public void initRootLayout() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Questa funzione inizializza l'interfaccia dell'applicazione
    public void showDocumentsOverview() {
        try {
            // Carico l'interfaccia dei documenti
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/DocumentsOverview.fxml"));
            AnchorPane documentsOverview = (AnchorPane) loader.load();

            // Posiziona al centro l'interfaccia
            rootLayout.setCenter(documentsOverview);

            // Give the controller access to the main app.
            DocumentsOverviewController controller = loader.getController();
            controller.setMainApp(this);

            final FileChooser fileChooser = new FileChooser();

            final Button openButton = new Button("Find PDF");
            final Button openMultipleButton = new Button("Cancel");

            openButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            configureFileChooser(fileChooser);
                            File file = fileChooser.showOpenDialog(getPrimaryStage());
                            if (file != null) {
                                openFile(file);
                            }
                        }
                    });

            openMultipleButton.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent e) {
                            configureFileChooser(fileChooser);
                            List<File> list =
                                    fileChooser.showOpenMultipleDialog(getPrimaryStage());
                            if (list != null) {
                                for (File file : list) {
                                    openFile(file);
                                }
                            }
                        }
                    });

            final GridPane inputGridPane = new GridPane();

            GridPane.setConstraints(openButton, 0, 0);
            GridPane.setConstraints(openMultipleButton, 1, 0);
            inputGridPane.setHgap(6);
            inputGridPane.setVgap(6);
            inputGridPane.getChildren().addAll(openButton, openMultipleButton);

            //qui ci sono le dimensioni della piccola finestra con i due bottoni
            final Pane rootGroup = new VBox(12);
            rootGroup.getChildren().addAll(inputGridPane);
            rootGroup.setPadding(new Insets(300, 12, 20, 700));

            primaryStage.setScene(new Scene(rootGroup));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Questa funzione si posiziona nella cartella contenente i PDF da analizzare
    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View PDF");
        //al posto di user.home dovrei cercare di mettere il percorso per la cartella con i pdf
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("PDF (.pdf)", "*.pdf"),
                new FileChooser.ExtensionFilter("CSV (.csv)", "*.csv")
        );
    }

    /*
    final Button browseButton = new Button("...");
        browseButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(final ActionEvent e) {
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            final File selectedDirectory = directoryChooser.showDialog(primaryStage);
            if (selectedDirectory != null) {
                selectedDirectory.getAbsolutePath();
            }
        }
    }*/


    //Questa funzione apre l'interfaccia del File
    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
