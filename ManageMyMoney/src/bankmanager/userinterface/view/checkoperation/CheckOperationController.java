package bankmanager.userinterface.view.checkoperation;

import bankmanager.datamanagement.OperationBancaire;
import bankmanager.restservice.APIFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class CheckOperationController {

    @FXML
    private Button buttonSaveOperation;
    @FXML
    private Button buttonCancel;
    @FXML
    private TableView<OperationBancaire> tableListOperation;
    @FXML
    private TableColumn<OperationBancaire, String> columLibelle;
    @FXML
    private TableColumn<OperationBancaire, Date> columDate;
    @FXML
    private TableColumn<OperationBancaire, Double> columDebit;
    @FXML
    private TableColumn<OperationBancaire, Double> columCredit;

    private List<OperationBancaire> bankOperationList;

    @FXML
    public void initialize() {

        // ATTENTION les chaine donnée en paramètre à PropertyValueFactory doivent correspondre aux variables dans OperationBancaire
        columLibelle.setCellValueFactory(new PropertyValueFactory<OperationBancaire, String>("libelle"));
        columDate.setCellValueFactory(new PropertyValueFactory<OperationBancaire, Date>("dateOperation"));
        columDebit.setCellValueFactory(new PropertyValueFactory<OperationBancaire, Double>("montantDebit"));
        columCredit.setCellValueFactory(new PropertyValueFactory<OperationBancaire, Double>("montantCredit"));
    }

    private ObservableList<OperationBancaire> getOperationList() {
        ObservableList<OperationBancaire> listOperation = FXCollections.observableArrayList(bankOperationList);

        return listOperation;
    }

    public void fillTable(List<OperationBancaire> bankOperationList) {

        this.bankOperationList = bankOperationList;

        tableListOperation.setItems(getOperationList());
    }

    public void saveOperation() {

        if (!bankOperationList.isEmpty()) {
            try {
                APIFunction.addListOfOperation(bankOperationList);
            } catch (IOException ioException) {
                ioException.printStackTrace();

                Alert alertFail = new Alert(Alert.AlertType.ERROR);
                alertFail.setTitle("Erreur");

                alertFail.setHeaderText(null);
                alertFail.setContentText("Erreur lors de la sauvegarde, veuillez réessayer.");

                alertFail.showAndWait();
            }
        }

        Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
        alertSuccess.setTitle("Sauvegarde réussi");

        alertSuccess.setHeaderText(null);
        alertSuccess.setContentText("Sauvegarde des opération en base réussi !");

        alertSuccess.showAndWait();

        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }

}
