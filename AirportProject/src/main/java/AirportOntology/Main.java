package AirportOntology;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/window.fxml"));

        fxmlLoader.setController(new WindowController());

        Parent root = (Parent) fxmlLoader.load();

        primaryStage.setTitle("Airport Ontology Manager");

        primaryStage.setScene(new Scene(root, 640, 440));

        primaryStage.setResizable(false);

        primaryStage.setOnCloseRequest(e -> {
            Optional<File> optional = Optional.ofNullable(DAO.tempData);
            if (optional.isPresent()) DAO.tempData.delete();
            System.exit(0);
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
