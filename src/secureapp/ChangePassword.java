
package secureapp;


import java.io.File;
import java.io.PrintWriter;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import static secureapp.Scene1.table;

public class ChangePassword {

    public static boolean display(String title) {
        try{
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(450);
        window.setMinHeight(450);
        
        
//        welcom.setStyle("-fx-font-size: 25;");
//        welcom.setTextFill(Color.GHOSTWHITE);
//        window.setResizable(false);
        Label label = new Label();
        label.setText("New Password");
          label.setStyle("-fx-font-size: 15;");
            label.setTextFill(Color.GHOSTWHITE);
        PasswordField passfield = new PasswordField();
        passfield.setStyle("-fx-font-size: 15;");
        passfield.setPromptText("type password");
        
        Label label2 = new Label();
        label2.setText("Confirm Password");
          label2.setStyle("-fx-font-size: 15;");
            label2.setTextFill(Color.GHOSTWHITE);
        PasswordField passfield2 = new PasswordField();
        passfield2.setStyle("-fx-font-size: 15;");
        passfield2.setPromptText("type password again");
       
        
        
        
        
        
        Button closeButton = new Button("Save");
        closeButton.setMinWidth(100);
        closeButton.setOnAction(e -> {
        try{
            String pass1 = passfield.getText();
        String pass2 = passfield2.getText();
        
        System.out.println(pass1 + "    "+ pass2);
        if(pass1.equals(pass2)){
            File file = new File("C:\\Users\\razza\\OneDrive\\Documents\\NetBeansProjects.Web\\SecureApp\\files\\pass.t");
             file.createNewFile();
            PrintWriter write = new PrintWriter("C:\\Users\\razza\\OneDrive\\Documents\\NetBeansProjects.Web\\SecureApp\\files\\pass.t");
            write.print(pass1);
            System.out.println(pass1);
            write.close();
            
            AlertBox.display("Confirmation", "Password Changed Successfully. You may use the new password from the next time Login.");
            window.close();
        }
        else{
        AlertBox.display("Error!!!", "Password did not match, Please try againg to set the password.");
        
        }
        }catch(Exception yp){
        yp.printStackTrace();}
        
       
        
        
        
        });
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(label,new Label("    "), passfield, new Label("\n"),label2,new Label("    "),passfield2);
        vbox.setAlignment(Pos.CENTER);
        
        
        
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"),closeButton, new Label("\n"));
        layout.setAlignment(Pos.CENTER);
        BorderPane oto = new BorderPane(vbox, null,  layout,null, null);
        oto.setStyle("-fx-background-color: #27408B");
        
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label, closeButton);
//       layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(oto);
        window.setScene(scene);
        window.showAndWait();
        return true;
        }
        catch(Exception ept){
        
        
        return false;
        }
        
    }

}