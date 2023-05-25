package bankmanager.userinterface.view.main;

import bankmanager.userinterface.view.ViewNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane viewHolder;

    public void setView(Node node) {
        viewHolder.getChildren().setAll(node);
    }

    public void setHomeView(ActionEvent actionEvent) {
        ViewNavigator.loadView(ViewNavigator.HOME);
    }

    public void setFileView(ActionEvent actionEvent) {
        ViewNavigator.loadView(ViewNavigator.GETFILE);
    }

    public void setOperationsView(ActionEvent actionEvent) {
        ViewNavigator.loadView(ViewNavigator.GETOPERATIONS);
    }

}
