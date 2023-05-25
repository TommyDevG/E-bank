package bankmanager.userinterface.view.checkoperation;

import bankmanager.datamanagement.OperationBancaire;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckOperationView {

    private List<OperationBancaire> bankOperationList;
    private Stage parentStage;
    public CheckOperationView(List<OperationBancaire> bankOperationList, Stage parentStage) {
        this.bankOperationList = bankOperationList;
        this.parentStage = parentStage;
    }

    public void launchWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("checkOperation.fxml"));
        Parent root = loader.load();
        CheckOperationController checkOperationController = loader.getController();
        checkOperationController.fillTable(bankOperationList);

        Stage stage = new Stage();
        stage.setTitle("Vérification des opérations");
        stage.setScene(new Scene(root));
        // oblige l'utilisateur à rester sur cette fenêtre tant qu'il ne l'a pas fermé ou effectuer les actions voulues
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(parentStage);

        stage.setX(parentStage.getX());
        stage.setY(parentStage.getY());

        stage.show();
    }

}
