package it.bottai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }


}
