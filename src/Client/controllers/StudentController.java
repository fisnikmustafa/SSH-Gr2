package Client.controllers;

import Client.models.Grade;
import Client.models.Student;
import Client.utils.SessionManager;
import Client.utils.Request;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StudentController implements Initializable {
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;

    DataInputStream inputStream;
    static DataOutputStream outStream;

    public static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
    private Request request = new Request();

    @FXML
    private PieChart pieChart;

    @FXML
    private Circle profilePicture;

    @FXML
    private VBox chatVbox;

    @FXML
    private ScrollPane activeUsersScrollPane;

    @FXML
    private Button btnProfile;

    @FXML
    private Button btnGrades;

    @FXML
    private  Button btnChat;

    @FXML
    public  static Button btnExit;

    @FXML
    private Pane paneProfile;

    @FXML
    private Pane paneGrades;

    @FXML
    private Pane paneChat;

    @FXML
    private TextField idField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField parentNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField schoolNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TableView<Grade> gradesTableView;

    @FXML
    private TableColumn<Grade, String> gradeColumn;

    @FXML
    private TableColumn<Grade, String> classColumn;

    @FXML
    private Label gpaLabel;

    //TO DO:
    @FXML
    public void onUpdateButtonClick(ActionEvent actionEvent) throws Exception {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        try{
            Student s = SessionManager.student;
            this.socket = new Socket("localhost", 8818);
            this.name = s.getFirst_name() + " " + s.getLast_name();

            inputStream = new DataInputStream(socket.getInputStream()); // create input and output stream
            outStream = new DataOutputStream(socket.getOutputStream());
            outStream.writeUTF(name); // send user name to the output stream

            read();
        } catch (IOException e){
            e.printStackTrace();
        }
        
        
        this.gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        this.classColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

        fillPieChart();
        loadProfilePicture(SessionManager.student.getPicture_path());
        renderStudent(SessionManager.student);
        renderGrades(SessionManager.student);
        loadChat();

        exec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                loadChat();
            }
        }, 0, 30, TimeUnit.SECONDS);
        
        

    }

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource() == btnProfile) {
            renderStudent(SessionManager.student);
            if (!paneProfile.isVisible()) {
                paneGrades.setVisible(false);
                paneChat.setVisible(false);
                paneProfile.setVisible(true);
                paneProfile.toFront();
            }
        }
        if (actionEvent.getSource() == btnGrades) {
            if (!paneGrades.isVisible()) {
                paneProfile.setVisible(false);
                paneChat.setVisible(false);
                paneGrades.setVisible(true);
                paneGrades.toFront();
            }
        }
        
        if (actionEvent.getSource() == btnSendMessage){
            System.out.println("Btn send clicked!");
            //String sendTo = SessionManager.selectedChatUserName;
            if(!chatMessageField.getText().isEmpty()) {
                String message = chatMessageField.getText();
                sendMessage(sendTo, message);
            }
        }


    }

    @FXML
    public void onExitButtonClick(ActionEvent e) throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Press OK if you want to log out!");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            StudentController.exec.shutdown();

            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setScene(scene);
            request.changeStatus(emailField.getText(), 0);
            SessionManager.student = null;
        } else return;
    }

    private void fillPieChart(){
        Map<String, String> gradesMap = SessionManager.student.getGrades();
        int[] gradeCounter = {0,0,0,0,0};
        double gradeSum = 0;   //shuma e notave
        double gpa; //nota mesatare

        for (String value : gradesMap.values()){
            gradeSum += Double.parseDouble(value);
            switch (value){
                case "1": gradeCounter[0]++;
                    break;
                case "2": gradeCounter[1]++;
                    break;
                case "3": gradeCounter[2]++;
                    break;
                case "4": gradeCounter[3]++;
                    break;
                case "5": gradeCounter[4]++;
                    break;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nota 1", gradeCounter[0]),
                new PieChart.Data("Nota 2", gradeCounter[1]),
                new PieChart.Data("Nota 3", gradeCounter[2]),
                new PieChart.Data("Nota 4", gradeCounter[3]),
                new PieChart.Data("Nota 5", gradeCounter[4])
        );

        pieChart.setData(pieChartData);
        gpa = gradeSum/7;

        gpaLabel.setText(String.format("%.2f", gpa));
    }

    private void loadProfilePicture(String filename){
        Image image = new Image("/Client/images/profile_pics/" + filename + ".jpg" );
        ImagePattern pattern = new ImagePattern(image);
        profilePicture.setFill(pattern);
        profilePicture.setStrokeWidth(0);
    }

    private void loadChat(){
        Runnable task = () -> {
            Platform.runLater(() -> {
                try{
                    System.out.println("Reloading chat...");
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../views/usersbox.fxml"));
                    VBox chatBox = loader.load();
                    activeUsersScrollPane.setContent(chatBox);
                } catch (Exception ex){
                    ex.printStackTrace();
                    activeUsersScrollPane.setContent(null);
                }

            });
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void renderStudent(Student s){
        idField.setText(Integer.toString(s.getId()));
        firstNameField.setText(s.getFirst_name());
        parentNameField.setText(s.getParent_name());
        lastNameField.setText(s.getLast_name());
        genderField.setText(s.getGender()+"");
        schoolNameField.setText(s.getSchool_name());
        emailField.setText(s.getEmail());
        phoneField.setText(s.getPhone_number());
    }

    private void renderGrades(Student s){
        Map<String, String> gradesMap = s.getGrades();
        ArrayList<Grade> gradeList = new ArrayList<>();

        for (Map.Entry<String, String> entry : gradesMap.entrySet()){
            gradeList.add(new Grade(entry.getKey(), entry.getValue()));
        }

        ObservableList<Grade> gradeItems = FXCollections.observableArrayList(gradeList);
        gradesTableView.setItems(gradeItems);
    }
    
    //CHAT:
    //read messages:

    public void read(){
        Runnable task = () -> {
            while (true) {
                try {
                    String m = inputStream.readUTF();  // read message from server
                    System.out.println("inside read thread : " + m); // print message for testing purpose
                    loadReceivedMessage(m);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    public void sendMessage(String name, String message) {
        if (socket.isConnected()) {
            String messageToSend = "filename" + ":" + name + ":" + message;
            try {
                outStream.writeUTF(messageToSend);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
