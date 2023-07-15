package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;


public class ChatRoomController extends Thread {
    public TextField txtField;
    public Text nameTxt;
    public AnchorPane EmojiPane;
    public VBox vBoxMsg;

    public BufferedReader reader;
    public PrintWriter writer;
    public Socket socket;
    public JFXButton sendBtn;

    private FileChooser fileChooser;
    private File filePath;

    public void initialize() {
        String userName = LoginFormController.name;
        this.nameTxt.setText(userName);
        try {
            socket = new Socket("localhost", 3000);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);

            this.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];


                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }


                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }


                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);

                }
                if (firstChars.equalsIgnoreCase("img")) {
                    //for the Images

                    st = st.substring(3, st.length() - 1);


                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);


                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);


                    if (!cmd.equalsIgnoreCase(nameTxt.getText())) {

                        vBoxMsg.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);


                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);


                    }

                    Platform.runLater(() -> vBoxMsg.getChildren().addAll(hBox));


                } else {

                    TextFlow tempFlow = new TextFlow();


                    if (!cmd.equalsIgnoreCase(nameTxt.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);

                        tempFlow.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(15,125,242);" +
                                " -fx-background-radius: 10px");
                        tempFlow.setPadding(new Insets(3,10,3,10));
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);




                    if (!cmd.equalsIgnoreCase(nameTxt.getText() + ":")) {


                        vBoxMsg.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);
                        hBox.setPadding(new Insets(2,5,2,10));

                    } else {

                        Text text2 = new Text(fullMsg + ": Me");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                        hBox.setPadding(new Insets(2,5,2,10));

                        flow2.setStyle("-fx-color: rgb(239,242,255);" +
                                "-fx-background-color: rgb(191,241,9);" +
                                "-fx-background-radius: 10px");
                        flow2.setPadding(new Insets(3,10,3,10));

                    }
                    Platform.runLater(() -> vBoxMsg.getChildren().addAll(hBox));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void txtFieldOnAction(ActionEvent actionEvent) {
        sendBtn.fire();
    }

    public void sendBtnOnAction(ActionEvent actionEvent) {
        String msg = txtField.getText();
        writer.println(nameTxt.getText() + ": "+ msg);
        txtField.clear();

        if (msg.equalsIgnoreCase("!Bye") || (msg.equalsIgnoreCase("logout"))){
            System.exit(0);
        }
    }

    public void emojiIconOnAction(MouseEvent mouseEvent) {
        EmojiPane.setVisible(true);
    }

    public void sendFileOnAction(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(nameTxt.getText() + " " + "img" + filePath.getPath());
    }
    public void Heart(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128525));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void sadMood(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128546));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void normalMood(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars( 128522));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void Hehe(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128513));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void ToungOut(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128539));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void sick(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128560));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void Hiks(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128540));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void soSad(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128554));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void haha(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128514));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void Emotional(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128578));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void bad(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128543));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void money(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(129297));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void satisfied(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128519));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void ohh(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128550));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }

    public void wow(MouseEvent mouseEvent) {
        String emoji = new String(Character.toChars(128559));
        txtField.setText(emoji);
        EmojiPane.setVisible(false);
    }
}
