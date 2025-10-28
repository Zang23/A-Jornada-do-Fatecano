/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.Objects;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import model.PropertiesModel;
import view.SetImageIcon;

/**
 *
 * @author Admin
 */
public class PlayerIconController {

    PropertiesModel propriedades = new PropertiesModel();

    ;
    public PlayerIconController() {

    }

    public void SetIconButtonProperties(JLabel IconLabel, JLabel ButtonLabel){
    ButtonLabel.addMouseListener(new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e){
            SetImageIcon setImage = new SetImageIcon();
            // pega o ícone atual ou usa o padrão se for null
            String currentIcon = propriedades.GetProperty("user.icon");
            if(currentIcon == null){
                currentIcon = propriedades.GetProperty("default.icon");
                propriedades.SetProperty("user.icon", currentIcon);
            }

            // alterna o ícone
            if(Objects.equals(currentIcon, propriedades.GetProperty("default.icon"))){
                setImage.SetLabelIcon(IconLabel, propriedades.GetProperty("icon2"));
                propriedades.SetProperty("user.icon", propriedades.GetProperty("icon2"));
            } else {
                setImage.SetLabelIcon(IconLabel, propriedades.GetProperty("icon1"));
                propriedades.SetProperty("user.icon", propriedades.GetProperty("icon1"));
            }
        }
    });
}

}
