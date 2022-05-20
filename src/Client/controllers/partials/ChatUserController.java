package Client.controllers.partials;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable {
    @FXML
    Circle pictureCircle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadProfilePicture();
    }

    private void loadProfilePicture(){
        Image image = new Image("/Client/images/student2.jpg");
        ImagePattern pattern = new ImagePattern(image);
        pictureCircle.setFill(pattern);
        pictureCircle.setStrokeWidth(0);
    }
}
