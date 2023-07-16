package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public static String name;
    public TextField txtName;
    public JFXButton joinChatBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        joinChatBtn.setDisable(true);
    }
    public void txtNameOnAction(ActionEvent actionEvent) {
        joinChatBtn.fire();
    }

    public void joinChatBtnOnAction(ActionEvent actionEvent) throws IOException {
        name = txtName.getText();

        Parent root = FXMLLoader.load(getClass().getResource("/view/chatRoomForm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("TelChat");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        txtName.clear();
    }

    public void KeyPressed(KeyEvent keyEvent) {
        if (txtName.getText().isEmpty()){
            joinChatBtn.setDisable(false);
        }
    }
}
