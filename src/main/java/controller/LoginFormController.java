package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public static String name;
    public TextField txtName;
    public JFXButton joinChatBtn;


    public void txtNameOnAction(ActionEvent actionEvent) {
        joinChatBtn.fire();
    }

    public void joinChatBtnOnAction(ActionEvent actionEvent) throws IOException {
        name = txtName.getText();

        Parent root = FXMLLoader.load(getClass().getResource("/view/chatRoomForm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Yooo lets chat");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        txtName.clear();
    }
}
