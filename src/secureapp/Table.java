/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.stream.Stream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Table {

    Stage window;
    TableView<Program> table;
    TextField nameInput, priceInput, quantityInput;
    String nametxt = "C:\\Users\\razza\\OneDrive\\Documents\\NetBeansProjects.Web\\SecureApp\\files\\directorys.txt";
    File nameProg = new File(nametxt);

    public TableView makingTable() throws Exception {

        //Name column
        TableColumn<Program, String> nameColumn = new TableColumn<>("Programs to Secure");
        nameColumn.setMinWidth(750);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

//        //Name input
//        nameInput = new TextField();
//        nameInput.setPromptText("Name");
//        nameInput.setMinWidth(100);
//
//        //Price input
//        priceInput = new TextField();
//        priceInput.setPromptText("Price");
//
//        //Quantity input
//        quantityInput = new TextField();
//        quantityInput.setPromptText("Quantity");
        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());
//
//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(10,10,10,10));
//        hBox.setSpacing(10);
//        hBox.getChildren().addAll(nameInput, priceInput, quantityInput, addButton, deleteButton);

        table = new TableView<>();
        table.setItems(getProgram());
        table.getColumns().addAll(nameColumn);

//        VBox vBox = new VBox();
//        vBox.getChildren().addAll(table, hBox);
        return table;
    }

    //Add button clicked
    public void addButtonClicked() {
        Program product = new Program();
        product.setName(nameInput.getText());

        table.getItems().add(product);

    }

    //Delete button clicked
    public void deleteButtonClicked() {
        ObservableList<Program> productSelected, allPrograms;
        allPrograms = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

        productSelected.forEach(allPrograms::remove);
    }

    //Get all of the products
    public ObservableList<Program> getProgram() {

        ObservableList<Program> products = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(nametxt))) {

            try{
        Connection conn;
        Statement stm;
        String connectionURL = "jdbc:mysql://localhost:3306/applocker";

                                System.out.println("line 1");
                                Class.forName("com.mysql.jdbc.Driver").newInstance();

                                System.out.println("line 2");
                                conn = DriverManager.getConnection(connectionURL, "root", "123456");
                                System.out.println("line 3");
                                stm = conn.createStatement();
                                ResultSet rs = stm.executeQuery("SELECT porgram FROM program_list");
                                while(rs.next()){
                                 products.add(new Program(rs.getString(1)));
                                
                                }
    
    
        }
        catch(Exception sdf){
            System.out.println("Hello error");
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        products.add(new Program("Laptop"));
//        products.add(new Program("Bouncy Ball"));
//        products.add(new Program("Toilet"));
//        products.add(new Program("The Notebook DVD"));
//        products.add(new Program("Corn"));
        return products;
    }

}
