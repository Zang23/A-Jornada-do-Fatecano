package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;


public class PropertiesModel {
    Properties props = new Properties();
    public PropertiesModel(){
       try (InputStream input = new FileInputStream("config.properties")) {
            if (input == null) {
                throw new IOException("Arquivo config.properties não encontrado!");
            }
            props.load(input);
        }catch(Exception e){
            e.printStackTrace();
        } 
    }
    
    public String GetProperty(String Campo) {
        //System.out.println("Campo: " + props.getProperty(Campo));
        return props.getProperty(Campo);
    }
    
    public void SetProperty(String Campo, String Valor) {
        props.setProperty(Campo, Valor);
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            props.store(out, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
