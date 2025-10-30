/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
/**
 *
 * @author Admin
 */
public class CreateFileProperties {

    public void create() {
        Path origem = Paths.get("src/resources/config.properties");
        Path destino = Paths.get("config.properties");
       try{
           Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
       }catch(Exception e){
           e.printStackTrace();
       }
    }
}
