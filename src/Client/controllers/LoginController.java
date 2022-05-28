package Client.controllers;

import Client.utils.Request;
import Client.utils.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.net.http.HttpClient;

import Client.config.IPconfig;

public class LoginController implements Initializable {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    private Request request = new Request();

    @FXML
    Button btnLogin;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onLoginButtonClick(javafx.event.ActionEvent actionEvent) throws IOException, InterruptedException {
        if (!emailField.getText().isBlank() && !passwordField.getText().isBlank()) {
            int result = requestLogin(emailField.getText(), passwordField.getText());

            if (result == 200) {
                request.changeStatus(emailField.getText(), 1);
                SessionManager.student = request.getStudent(emailField.getText());
                loadStudentView(actionEvent);

                System.out.println(SessionManager.student.toString());

            } else {
                loginMessage.setText("Incorrect email/password!");
            }

        } else {
            loginMessage.setText("Please enter your credentials!");
        }


    }

        private void loadStudentView(javafx.event.ActionEvent actionEvent){
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("../views/student.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = new Stage();
                primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                primaryStage.setOnCloseRequest(e -> {
                    try {
                        request.changeStatus(SessionManager.student.getEmail(), 0);
                        SessionManager.student = null;
                        StudentController.exec.shutdown();
                        StudentController.endConnection();
                        Platform.exit();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });


                primaryStage.setScene(scene);
                primaryStage.show();


            } catch (Exception ex){
                ex.printStackTrace();
            }
        }

        private int requestLogin(String email, String password) throws IOException, InterruptedException {
            String url = "http://" + IPconfig.getAddress() + "/api/v1/students/";
            String[] keys = {"email", "password"};
            String[] values = {email, password};
            return request.POST(url, keys, values);
        }


    }


