package it.bottai;

import it.bottai.view.DocumentsOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;


    //Questa funziona restituisce uno Stage, un contenitore principale
    public Stage getPrimaryStage() {
        return primaryStage;
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
            loader.setLocation(Main.class.getResource("/views/RootLayout.fxml"));
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
            loader.setLocation(Main.class.getResource("/views/DocumentsOverview.fxml"));
            AnchorPane documentsOverview = (AnchorPane) loader.load();

            // Posiziona al centro l'interfaccia
            rootLayout.setCenter(documentsOverview);

            // Da l'accesso al controller per la Main App
            DocumentsOverviewController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

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




    public static void main(String[] args) {
        launch(args);
    }


}
