/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
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

public class Scene1 {

    Scene scene;
    Stage stg;

    static File defultDir = null;
    static File newDir;
    public static Stage window;
    public static String too = null;
    ComboBox selectcombo;
    static TextArea consol;
    static String fullText = null;
    public static int[] ar = new int[20];
    public static String[] br = new String[20];
    public static File copyToDir;
    public static TableView table;


    public Scene sn() {
        Label welcom = new Label("Secure Your Application");
        welcom.setStyle("-fx-font-size: 25;");
        welcom.setTextFill(Color.GHOSTWHITE);

        final Button choose = new Button("Add Program        ");
        choose.setAlignment(Pos.TOP_LEFT);
        choose.setOnAction(e -> {

            File f = null;
            boolean t = true;
            while (t) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select Executable File");
//            fileChooser.showOpenDialog(win);
                    System.out.println("Hello");
                    f = fileChooser.showOpenDialog(win);
                    System.out.println("Hello 2");
                    if (f.getName().contains(".exe")) {
                        System.out.println("Hello 3");
                        t = false;
                        break;
                    } else {
                        AlertBox.display("Error!!", "You can not select files except .exe");
                    }
                } catch (NullPointerException np) {
                    System.out.println("From the catch");
                    f = null;
                    break;
                }
            }
            if (f != null) {

                String name = f.getName();

                String filedirectory = f.getPath();
                System.out.println(filedirectory);
                File file = new File("C:\\Users\\razza\\OneDrive\\Documents\\NetBeansProjects.Web\\SecureApp\\files\\path.txt");
                
                //File file =new File("E:\\G.txt");    

        //if file doesnt exists, then create it    
        if(!f.exists()){    
                    try {
                        file.createNewFile();    
                        System.out.println("New File Created Now");
                    } catch (IOException ex) {
                        Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }    

        //true = append file    
            FileWriter fileWritter;        
                try {
                    fileWritter = new FileWriter(file,true);
                    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(System.getProperty("line.separator")+filedirectory);
            bufferWritter.close();
            fileWritter.close();
                } catch (IOException ex) {
                    Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
                }
            

        System.out.println("Done");
                
                
                
                try {
                    Connection conn = null;
                    Statement stm = null;
                   ResultSet rs= null;
                    System.out.println("line 1");
                    String connectionURL = "jdbc:mysql://localhost:3306/applocker";

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    conn = DriverManager.getConnection(connectionURL, "root", "123456");
                    
                    stm = conn.createStatement();
                    System.out.println("line 2: "+name+"    "+filedirectory);
                    String Q;
                    Q = "SELECT * FROM program_list WHERE porgram = '"+name+"'";
                    rs = stm.executeQuery(Q);
                    int p=0;
                    while(rs.next()){
                    p++;
                    }
                    stm.close();
                    System.out.println("line 3: "+p);
                    if(p==0){
                     stm = conn.createStatement();
                     Q = "INSERT INTO program_list VALUES (NULL, '"+name+"','"+filedirectory+"');";
                    stm.executeUpdate(Q);
                    boolean boo = ProgramStatusChecker.isRunning(name);
                    int session=0;
                    if(boo)session=1;
                    stm.close();
                    Q = "INSERT INTO task_list VALUES(NULL, '"+name+"','"+0+"','"+session+"','"+session+"');";
                    
                    stm = conn.createStatement();
                    stm.executeUpdate(Q);
                    System.out.println("line 3");
                    }
                     

                } catch (Exception sdf) {
                    System.out.println("Exception taken");
                }

                addButtonClicked(name);

            } else {
                System.out.println("Getting Null");
            }
            //addButtonClicked(name);
        });

        Button go = new Button("Change Password  ");
        go.setAlignment(Pos.BOTTOM_LEFT);

        go.setOnAction(er -> {
            changePasswordClicked();
        });

        Button okey = new Button("Ok");
        TextArea ta = new TextArea();
        consol = ta;

        Table tableView = new Table();
        try {
            table = tableView.makingTable();
        } catch (Exception ex) {
            Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
        }
        Button deleteButton = new Button("Delete Program     ");
        deleteButton.setAlignment(Pos.CENTER_LEFT);
        deleteButton.setOnAction(e ->{
            
            try{
            
            
            deleteButtonClicked();}
        
            catch(Exception po){
                System.out.println("Hello Error from delete button. ");
            }
        }
        );

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(deleteButton);

        VBox pathTaker = new VBox(5);
        pathTaker.setPadding(new Insets(5, 5, 5, 5));
        pathTaker.getChildren().addAll(new Label("\n"), choose, new Label("\n"), deleteButton, new Label("\n"), go);
        pathTaker.setAlignment(Pos.TOP_CENTER);

        HBox topline = new HBox(5);
        topline.setPadding(new Insets(5, 5, 5, 5));
        topline.getChildren().addAll(welcom);
        topline.setAlignment(Pos.TOP_CENTER);

        BorderPane ot = new BorderPane(table, topline, null, null, pathTaker);
        ot.autosize();
        ot.setStyle("-fx-background-color: #27408B");
        Scene scene2nd = new Scene(ot, 900, 550);
        scene = scene2nd;
        return scene;
    }

    public void deleteButtonClicked() {

        TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

// Item here is the table view type:
        Program item = (Program) table.getItems().get(row);

        TableColumn col = pos.getTableColumn();

// this gives the value in the selected cell:
        String data = (String) col.getCellObservableValue(item).getValue();
        System.out.println("Data: = " + data);

        try {
             Connection conn;
                    Statement stm;
                    String connectionURL = "jdbc:mysql://localhost:3306/applocker";

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    conn = DriverManager.getConnection(connectionURL, "root", "123456");

                    stm = conn.createStatement();
                    String Q = "DELETE FROM program_list WHERE porgram='"+data+"';";
                    stm.executeUpdate(Q);
                    stm.close();
                    stm = conn.createStatement();
                    Q = "DELETE FROM task_list WHERE porgram='"+data+"';";
                    stm.executeUpdate(Q);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Scene1.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<Program> productSelected, allPrograms;
        allPrograms = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        int poss = table.getSelectionModel().getSelectedCells().indexOf(productSelected);
        System.out.println("pos value :" + poss);
        String removeFile = table.getSelectionModel().getSelectedCells().toString();
        System.out.println("File Removed: " + removeFile);

        productSelected.forEach(allPrograms::remove);

    }

    public void addButtonClicked(String name) {
        Program product = new Program();
        product.setName(name);
        System.out.println(name+"   inside Add button clicked");
        table.getItems().add(product);

    }

    public void changePasswordClicked() {
        boolean chk = ChangePassword.display("Changing Password");
        System.out.println(chk);
    }

}
