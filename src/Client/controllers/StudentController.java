package Client.controllers;

import Client.components.ChatUserComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Circle profilePicture;

    @FXML
    private VBox chatVbox;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnGrades;

    @FXML
    private  Button btnChat;

    @FXML
    private Pane paneProfile;

    @FXML
    private Pane paneGrades;

    @FXML
    private Pane paneChat;


    //TO DO:
    @FXML
    public void onUpdateButtonClick(ActionEvent actionEvent) throws Exception {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillPieChart();
        loadProfilePicture();
        loadChat();
    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource() == btnProfile) {
            //renderProfessor(getProfessor());
            if(!paneProfile.isVisible()){
                paneGrades.setVisible(false);
                paneChat.setVisible(false);
                paneProfile.setVisible(true);
                paneProfile.toFront();
            }
        }
        if (actionEvent.getSource() == btnGrades) {
            if(!paneGrades.isVisible()){
                paneProfile.setVisible(false);
                paneChat.setVisible(false);
                paneGrades.setVisible(true);
                paneGrades.toFront();
            }
            //fillTheTables();
        }

    }

    @FXML
    public void onExitButtonClick(ActionEvent e) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK if you want to log out!");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
        } else return;
    }

    private void fillPieChart(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nota 1", 0),
                new PieChart.Data("Nota 2", 0),
                new PieChart.Data("Nota 3", 1),
                new PieChart.Data("Nota 4", 3),
                new PieChart.Data("Nota 5", 7)
        );

        pieChart.setData(pieChartData);
        //pieChart.setTitle("Shperndarja e notave:");
        //pieChart.setLegendSide(Side.LEFT);
    }

    private void loadProfilePicture(){
        Image image = new Image("/Client/images/student3.jpg");
        ImagePattern pattern = new ImagePattern(image);
        profilePicture.setFill(pattern);
        profilePicture.setStrokeWidth(0);
    }

    private void loadChat(){
        try{
            for (int i=0; i<6; i++){
                Node user = new ChatUserComponent().getContent();
                chatVbox.getChildren().add(user);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
