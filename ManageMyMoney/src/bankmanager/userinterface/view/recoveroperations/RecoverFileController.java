package bankmanager.userinterface.view.recoveroperations;

import bankmanager.GetBankTransaction;
import bankmanager.datamanagement.OperationBancaire;
import bankmanager.userinterface.view.checkoperation.CheckOperationView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecoverFileController {

    @FXML
    private Button buttonRetrieveFiles;
    @FXML
    private Button buttonProcessFiles;
    @FXML
    private Label labelFileName;
    List<File> listFile = new ArrayList<File>();
    private static final String NEWLINE = "\n";

    public void getFiles(ActionEvent actionEvent) {

        listFile.clear();
        labelFileName.setText("");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xlsx Files", "*.xlsx"));
        listFile = fileChooser.showOpenMultipleDialog(null);
        for (File file : listFile) {
            if (labelFileName.getText() != "") {
                labelFileName.setText(labelFileName.getText() + NEWLINE);
            }
            labelFileName.setText(labelFileName.getText() + file.getName());
        }
    }

    public void processFiles(ActionEvent actionEvent) {
        if (listFile.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur dans le traitement");
            alert.setContentText("Aucun fichier trouver, le traitement ne peut être effectué");

            return;
        }

        List<OperationBancaire> bankOperationList = new ArrayList<OperationBancaire>();

        for (File file : listFile) {
            GetBankTransaction getOperationController = new GetBankTransaction(file.getAbsolutePath(), file.getName());
            List<OperationBancaire> bankOperationListTemp = new ArrayList<OperationBancaire>();
            bankOperationListTemp = getOperationController.GetListOfTransaction();

            // j'ajoute toutes les opérations à ma list principal
            bankOperationList.addAll(bankOperationListTemp);
        }
        Stage currentStage = (Stage) buttonProcessFiles.getScene().getWindow();
        CheckOperationView checkOperationWindow = new CheckOperationView(bankOperationList, currentStage);
        try {
            checkOperationWindow.launchWindow();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
