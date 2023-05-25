package bankmanager.userinterface.view.login;

import bankmanager.datamanagement.User;
import bankmanager.restservice.APIFunction;
import bankmanager.userinterface.Main;
import bankmanager.userinterface.view.ViewNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginController {

    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;
    @FXML
    private Label labelInfoIdentifiantIncorrect;

    public void signIn() {

        if (textFieldUsername.getText() == "" || passwordFieldPassword.getText() == "") {
            Alert alertFail = new Alert(Alert.AlertType.ERROR);
            alertFail.setTitle("Erreur de saisie");

            alertFail.setHeaderText(null);
            alertFail.setContentText("Veuillez remplir tous les champs.");

            alertFail.showAndWait();

            return;
        }

        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();

        // je crée un Salage random
        String salt = "$v1fG9M1!6p";

        String passwordAndSalt = salt + password;

        String hashPassword = DigestUtils.sha256Hex(passwordAndSalt);

        User currentUser = new User();

        try {
            currentUser = APIFunction.signIn(username, hashPassword);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if (currentUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur API");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de la requête API");

            alert.showAndWait();
        } else {
            if (currentUser.getIduser() == 0) {
                labelInfoIdentifiantIncorrect.setVisible(true);
            } else {
                Main main = new Main();
                try {
                    main.setMainView();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        }

/*
        if (loginWithSuccess) {
            Main main = new Main();
            try {
                main.setMainView();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Identifiants incorrects");

            alert.showAndWait();
        }
*/
    }

    public void signUp() {

    }

}
