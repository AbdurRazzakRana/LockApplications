/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessKiller{
    

    private static final String TASKLIST = "tasklist";
    private static final String KILL = "taskkill /IM ";
    private static String processName ="";
    public ProcessKiller(String process){
    processName = process;
    }
    public static boolean isProcessRunging(String serviceName) throws Exception {

        Process p = Runtime.getRuntime().exec(TASKLIST);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                p.getInputStream()));
        
        String line;

        while ((line = reader.readLine()) != null) {

            System.out.println(line);
            if (line.contains(serviceName)) {
                return true;
            }
        }
        return false;
    }

    public static void killProcess(String serviceName) throws IOException{

        Runtime.getRuntime().exec(KILL + serviceName);

    }

    public static boolean ExecuteStopping() throws Exception {
        

        System.out.print(isProcessRunging(processName));

        if (isProcessRunging(processName)) {

            killProcess(processName);
            
            return true;
        }
        return false;
    }
}