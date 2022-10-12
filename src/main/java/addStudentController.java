import helpers.DbConnect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Student;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class addStudentController implements Initializable {

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    Student student = null;

    private boolean update;
    int studentId;


    @FXML
    private TextField adressFid;

    @FXML
    private DatePicker birthFid;

    @FXML
    private TextField emailFid;

    @FXML
    private TextField nameFid;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private void clean() {
        nameFid.setText(null);
        birthFid.setValue(null);
        adressFid.setText(null);
        emailFid.setText(null);
    }

    @FXML
    private void save(MouseEvent event) {
        connection= DbConnect.getConnect();
        String name= nameFid.getText();
        String birth=String.valueOf(birthFid);
        String adress=adressFid.getText();
        String email=emailFid.getText();


        if(name.isEmpty()|| birth.isEmpty()||adress.isEmpty()||email.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please FILL ALL DATA");
            alert.showAndWait();
        }else {
            getQuery();
            insert();
            clean();
        }


    }

    @FXML
    private void close(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    private void getQuery() {
        if (update==false){
            query="INSERT INTO student( name, birth, adress, email) VALUES ( ?,?,?,?)";
        }else{
            query="UPDATE student SET name=?, birth=?, adress=?, email=? WHERE id='"+studentId+"'";
        }
}
    private void insert() {

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,nameFid.getText());
            preparedStatement.setDate(2, Date.valueOf(birthFid.getValue()));
            preparedStatement.setString(3,adressFid.getText());
            preparedStatement.setString(4,emailFid.getText());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


        public void setTextField(int id, String name, LocalDate toLocalDate, String adress, String email) {
        }

        public void setUpdate(boolean b) {
        }

}
