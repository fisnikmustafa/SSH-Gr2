package Client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onLoginButtonClick(javafx.event.ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../views/student.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = new Stage();
            primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
