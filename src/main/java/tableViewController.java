
import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class tableViewController implements Initializable {


    @FXML
    private TableColumn<Student, String> adressCol;

    @FXML
    private TableColumn<Student, String> birthCol;


    @FXML
    private TableColumn<Student, String> c2Col;

    @FXML
    private TableColumn<Student, String> emailCol;

    @FXML
    private TableColumn<Student, String> idCol;

    @FXML
    private TableColumn<Student, String> nameCol;

    @FXML
    private TableView<Student> tableStudents;


    String query;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    Student student;

    ObservableList<Student> StudentList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
    }

    public void getAddView(javafx.scene.input.MouseEvent mouseEvent) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/addStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void close(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    private void refreshTable() {

        StudentList.clear();
        query = "select * from student";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentList.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDate("birth"),
                        resultSet.getString("adress"),
                        resultSet.getString("email")));
                tableStudents.setItems(StudentList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birth"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory = (TableColumn<Student, String> param) -> {
            // make cell containing buttons
            final TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button();
                        deleteIcon.setText("Delete");
                        Button editIcon = new Button();
                        editIcon.setText("Edit");

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                                        + "-fx-background-color:#FF0000;"


                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                                        + "-fx-background-color:#00FF00;"

                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                student = tableStudents.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM student WHERE id =" + student.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }


                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            student = tableStudents.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/addStudent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            addStudentController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(student.getId(), student.getName(),
                                    student.getBirth().toLocalDate(), student.getAdress(), student.getEmail());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();


                        });
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        c2Col.setCellFactory(cellFoctory);
        tableStudents.setItems(StudentList);


    }


}

