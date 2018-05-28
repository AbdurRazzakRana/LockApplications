/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @web http://zoranpavlovic.blogspot.com/
 */
public class SecureApp extends Application {

    String user = System.getProperty("user.name");
    public static String pw = "";
    String checkUser, checkPw;
    Scene second;
    public static Stage win;
    public static Stage stg;
    static Group group;

    public static void main(String[] args) {
        new BackGroundThread();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        stg = new Stage();
        win = primaryStage;

        primaryStage.setTitle("Login Page");

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("    Enter    ");
        Button btnChangePwd = new Button("Change Passward");
        final Label lblMessage = new Label();

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);

        gridPane.add(lblMessage, 1, 2);

        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Login Page");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");

        //Action for btnLogin
        btnLogin.setOnAction((ActionEvent event) -> {

            System.out.println(System.getProperty("user.name"));

            try {
                String currentLine;

                BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\razza\\OneDrive\\Documents\\NetBeansProjects.Web\\SecureApp\\files\\pass.t"));
                while ((currentLine = reader.readLine()) != null) {

                    //pw = currentLine;
                    String trimmedLine = currentLine.trim();
                    System.out.println(trimmedLine);
                    pw = trimmedLine;
                }

            } catch (Exception ui) {
                System.out.println("Jhamela hoice");

            }

            checkUser = txtUserName.getText().toString();
            checkPw = pf.getText().toString();
            System.out.println(checkPw + "  " + pw + " " + "hj");
            if (checkUser.equals(user) && checkPw.equals(pw)) {
                Scene1 createSn = new Scene1();
                second = createSn.sn();
                win.setTitle("SecureApp");
                win.setScene(second);

            } else {
                lblMessage.setText("Incorrect user or pw.");
                lblMessage.setTextFill(Color.RED);
            }
            txtUserName.setText("");
            pf.setText("");
        });
        btnLogin.setDefaultButton(true);
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("SecureApp");

        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
}
