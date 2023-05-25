package bankmanager.userinterface;

import bankmanager.userinterface.view.ViewNavigator;
import bankmanager.userinterface.view.main.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;
    private BorderPane layout;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        stage.setTitle("E-bank");

        try {


            stage.setScene(
                    setLoginScene()
            );

        } catch(Exception exception) {
            exception.printStackTrace();
        }

        //stage.setMaximized(true);
        stage.show();

    }

    public void setMainView() throws IOException {
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );
    }

    public Scene setLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/login/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        return scene;
    }
    private Pane loadMainPane() {

        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = new Pane();
        try {
            mainPane = (Pane) loader.load(
                    getClass().getResourceAsStream(
                            ViewNavigator.MAIN
                    )
            );
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

        MainController mainController = loader.getController();

        ViewNavigator.setMainController(mainController);
        ViewNavigator.loadView(ViewNavigator.GETFILE);

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(
                mainPane
        );

        // set css
        /*
        scene.getStylesheets().setAll(
                getClass().getResource("vista.css").toExternalForm()
        );
         */

        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
