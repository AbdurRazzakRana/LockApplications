/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static secureapp.SecureApp.win;

public class ScenePassCollection {

    static boolean a = false;


    public static void sn(){

        Stage stg = new Stage();
         stg.initModality(Modality.APPLICATION_MODAL);
        Label labelPass = new Label("Password");
        labelPass.setStyle("-fx-font-size: 15;");
        labelPass.setTextFill(Color.GHOSTWHITE);

        PasswordField pf = new PasswordField();


        Button okey = new Button("Ok");
        okey.setOnAction(action->{
        String temp = pf.getText();
        if(pf.equals(SecureApp.pw)){
         a= true;
        
        
        }
        else{
        AlertBox.display("Error!!!", "Password did not match.");
        
        
        }
        stg.close();
        
        
        });



        VBox pathTaker = new VBox(5);
        pathTaker.setPadding(new Insets(5, 5, 5, 5));
        pathTaker.getChildren().addAll(new Label("\n"), labelPass, new Label("\n"), pf, new Label("\n"), okey);
        pathTaker.setAlignment(Pos.TOP_CENTER);


        BorderPane ot = new BorderPane(pathTaker);
        ot.autosize();
        ot.setStyle("-fx-background-color: #27408B");
        Scene scene2nd = new Scene(ot, 300, 200);
        win.setScene(scene2nd);
        win.showAndWait();

    }


}
