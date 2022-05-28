package Client.controllers;

import Client.models.Student;
import Client.components.ChatUserComponent;
import Client.utils.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UsersBoxController implements Initializable {

    private Request request = new Request();
    public static StudentController parentController;

    @FXML
    private VBox chatVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadChat();
    }

    private void loadChat(){
        Student[] activeStudents = new Student[0];
        try {
            activeStudents = request.getActiveStudents();
            chatVbox.getChildren().clear();

            for (Student s : activeStudents){
                ChatUserComponent chatUser = new ChatUserComponent();
                chatUser.picturePath = s.getPicture_path();
                chatUser.studentName = s.getFirst_name() + " " + s.getLast_name();
                chatUser.studentController = this.parentController;

                Node node = chatUser.getContent();

                chatVbox.getChildren().add(node);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
