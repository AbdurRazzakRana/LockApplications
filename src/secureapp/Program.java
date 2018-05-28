/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package secureapp;

public class Program {

    private String name;

    public Program(){
        this.name = "";

    }

    public Program(String name){
        this.name = name;
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}