import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socket.Server;

import java.io.IOException;

public class Launcher extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        new Thread(() -> {
            try {
                Server.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Parent root = FXMLLoader.load(this.getClass().getResource("/view/loginForm.fxml"));
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.setTitle("Login For Chat");
        stage.centerOnScreen();
        stage.show();
    }
}
