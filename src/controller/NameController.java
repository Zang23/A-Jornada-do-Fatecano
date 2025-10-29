/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PropertiesModel;

/**
 *
 * @author Admin
 */
public class NameController {
    PropertiesModel propriedades = new PropertiesModel();
    
    public String getName(){
        return propriedades.GetProperty("user.name");
    }
    public void setName(String name){
        if(!name.isEmpty()){
            propriedades.SetProperty("user.name", name);
        }else{
            propriedades.SetProperty("user.name", propriedades.GetProperty("default.name"));
        }
    }
}
