package Client.controllers.partials;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
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

    public void initData(String path, String name){
        this.path = path;
        this.nameLabel.setText(name);
        loadProfilePicture();
    }
}
