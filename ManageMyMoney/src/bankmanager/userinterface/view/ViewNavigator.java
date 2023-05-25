package bankmanager.userinterface.view;

import bankmanager.userinterface.view.main.MainController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ViewNavigator {

    //public static final String FILEPATH = "view/";
    public static final String MAIN = "view/main/main.fxml";
    public static final String LOGIN = "login/login.fxml";
    public static final String HOME = "home/home.fxml";
    public static final String GETFILE = "recoveroperations/recoverFile.fxml";
    public static final String GETOPERATIONS = "listoperation/listOperation.fxml";

    private static MainController mainController;

    public static void setMainController(MainController mainController) {
        ViewNavigator.mainController = mainController;
    }

    public static void loadView(String fxmlFile) {
        try {
            // Fonctionne pas car on est déjà dans le dossier view
            //FILEPATH + fxmlFile
            mainController.setView(
                    FXMLLoader.load(
                            ViewNavigator.class.getResource(
                                    fxmlFile
                            )
                    )
            );
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
