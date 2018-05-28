package secureapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import static secureapp.SecureApp.stg;

public class BackGroundThread implements Runnable {

    Thread t;

    //public static Map map;
    Scene sn;
    public static Map<String, Integer> map = new HashMap<String, Integer>();

    public BackGroundThread() {

        t = new Thread(this, "Background Thread");
        System.out.println("Child Thread");
        t.start();
    }

    public static boolean isRunning(String process) {
        boolean found = false;
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n Set locator = CreateObject(\"WbemScripting.SWbemLocator\")\n Set service = locator.ConnectServer()\n Set processes = service.ExecQuery _\n "
                    + "(\"select * from Win32_Process where name='" + process + "'\")\n For Each process in processes\n wscript.echo process.Name \n Next\n Set WSHShell = Nothing\n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            line = input.readLine();
            if (line != null) {
                if (line.equals(process)) {
                    found = true;
                }
            }
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return found;
    }

    @Override
    public void run() {
        try {
            //refresshing the values
            try {
                Connection conn = null;
                Statement stm = null;
                ResultSet rs = null;
                System.out.println("line 1");
                String connectionURL = "jdbc:mysql://localhost:3306/applocker";

                Class.forName("com.mysql.jdbc.Driver").newInstance();

                conn = DriverManager.getConnection(connectionURL, "root", "123456");

                stm = conn.createStatement();
                //System.out.println("line 2: "+name+"    "+filedirectory);
                String Q;
                Q = "SELECT task_id, porgram FROM task_list";

                rs = stm.executeQuery(Q);
                ArrayList<Integer> num = new ArrayList();
                ArrayList<String> name2 = new ArrayList();

                while (rs.next()) {
                    String name = rs.getString(2);
                    boolean ans = ProgramStatusChecker.isRunning(name);
                    if (ans) {
                        System.out.println(rs.getInt(1));
                        num.add(rs.getInt(1));

                    } else {
                        name2.add(name);

                    }

                }
                rs.close();
                stm.close();
                stm = conn.createStatement();
                stm.executeUpdate("UPDATE task_list SET session= '0', is_active = '0';");
                stm.close();
                for (int i = 0; i < num.size(); i++) {
                    stm = conn.createStatement();
                    String p = "UPDATE task_list SET session = '1', is_active = '1' WHERE task_id= '" + num.get(i) + "'";
                    stm.executeUpdate(p);
                    stm.close();
                }
                num.clear();

            } catch (Exception op) {
                op.printStackTrace();

            }
            String connectionURL = "jdbc:mysql://localhost:3306/applocker";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = null;
            conn = DriverManager.getConnection(connectionURL, "root", "123456");
            while (!Thread.currentThread().isInterrupted()) {

                try {
                    ArrayList<String> names = new ArrayList();

                    Statement stm = null;
                    ResultSet rs = null;

                    stm = conn.createStatement();
                    //System.out.println("line 2: "+name+"    "+filedirectory);
                    String Q;
                    Q = "SELECT porgram FROM program_list";
                    rs = stm.executeQuery(Q);

                    while (rs.next()) {
                        names.add(rs.getString(1));
                    }
                    rs.close();
                    stm.close();
                    for (int i = 0; i < names.size(); i++) {

                        String line = names.get(i);

                        //map.put(line, i);
                        boolean result = ProgramStatusChecker.isRunning(line);
                        try {
                            if (result) {
                                System.out.println(i + "  " + line + " is Running");

                                stm = conn.createStatement();
                                rs = stm.executeQuery("SELECT pass FROM task_list WHERE porgram = '" + line + "';");
                                int pass = 0;
                                while (rs.next()) {
                                    pass = rs.getInt(1);
                                }
                                rs.close();
                                stm.close();
                                if (pass == 0) {
                                    ProcessKiller psk = new ProcessKiller(line);
                                    psk.ExecuteStopping();
                                }
                                stm = conn.createStatement();
                                stm.executeUpdate("UPDATE task_list SET session = '1', is_active= '1' WHERE porgram = '" + line + "'");
                                stm.close();
                                stm = conn.createStatement();

                                stm = conn.createStatement();
                                Q = "SELECT * FROM request WHERE porgram = '" + line + "';";
                                rs = stm.executeQuery(Q);
                                int gh = 0;
                                while (rs.next()) {
                                    gh++;
                                    System.out.println("secureapp.BackGroundThread.run(): " + rs.getInt(1));
                                }
                                rs.close();
                                stm.close();

                                if (gh == 0) {
                                    stm = conn.createStatement();
                                    stm.executeUpdate("INSERT INTO request VALUES(null, '" + line + "')");
                                    stm.close();

                                }

                            } else {

                                try {
                                    int x = 1;
                                    int y = 1;
                                    int z = 1;
                                    stm = conn.createStatement();

                                    rs = stm.executeQuery("SELECT pass, session, is_active FROM task_list WHERE porgram = '" + line + "'");
                                    while (rs.next()) {
                                        z = rs.getInt(1);
                                        x = rs.getInt(2);
                                        y = rs.getInt(3);

                                    }
                                    rs.close();
                                    stm.close();
                                    if (x == 1 && y == 1 && z == 1) {
                                        stm = conn.createStatement();
                                        stm.executeUpdate("UPDATE task_list SET pass = '0', session='0', is_active='0' WHERE porgram = '" + line + "'");
                                        stm.close();
                                        stm = conn.createStatement();
                                        stm.executeUpdate("DELETE FROM request WHERE porgram = '" + line + "';");
                                        stm.close();
                                    } else if (x == 0 && y == 0) {

                                        stm = conn.createStatement();
                                        stm.executeUpdate("UPDATE task_list SET pass = '0' WHERE porgram = '" + line + "'");
                                        stm.close();
                                        stm = conn.createStatement();
                                        stm.executeUpdate("DELETE FROM request WHERE porgram = '" + line + "';");
                                        stm.close();
                                    }

                                } catch (SQLException ex) {
                                    Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    names.clear();
                } catch (SQLException ex) {
                    Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BackGroundThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
