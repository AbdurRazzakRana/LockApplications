
package secureapp;


import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import static secureapp.Scene1.table;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        window.setMinHeight(200);
        window.setResizable(false);
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Ok");
        closeButton.setMinWidth(100);
        closeButton.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label("\n\n\n\n\n\n"),closeButton);
        layout.setAlignment(Pos.CENTER);
        BorderPane oto = new BorderPane(label, null,  layout,null, null);
        
//        VBox layout = new VBox(10);
//        layout.getChildren().addAll(label, closeButton);
//        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(oto);
        window.setScene(scene);
        window.showAndWait();
    }

}