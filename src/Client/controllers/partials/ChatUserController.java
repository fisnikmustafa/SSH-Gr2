package Client.controllers.partials;

import Client.controllers.StudentController;
import Client.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {

    public StudentController studentController;

    @FXML
    HBox chatUserHbox;

    @FXML
    Circle pictureCircle;

    @FXML
    Label nameLabel;

    private String path;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void loadProfilePicture(){
        Image image = new Image("/Client/images/profile_pics/" + path + ".jpg");
        ImagePattern pattern = new ImagePattern(image);
        pictureCircle.setFill(pattern);
        pictureCircle.setStrokeWidth(0);
    }

    public void initData(String path, String name, StudentController studentController){
        this.path = path;
        this.nameLabel.setText(name);
        this.studentController = studentController;
        loadProfilePicture();

        chatUserHbox.setOnMouseClicked(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                SessionManager.selectedChatUserName = this.nameLabel.getText();
                System.out.println("Selected user: " + SessionManager.selectedChatUserName);

                this.studentController.openChat();
            }
        });
    }
}
