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

    public void SetIconButtonProperties(JLabel IconLabel, JLabel ButtonLabel) {
        String IconString = propriedades.GetProperty("user.icon");
                if (IconString == null) {
                    IconString = propriedades.GetProperty("default.icon");
                    propriedades.SetProperty("user.icon", IconString);
                }
        ButtonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SetImageIcon setImage = new SetImageIcon();
                // pega o ícone atual
                String currentIcon = propriedades.GetProperty("user.icon");

                // alterna o ícone
                if (currentIcon.equals(propriedades.GetProperty("default.icon"))) {
                    setImage.SetLabelIcon(IconLabel, propriedades.GetProperty("icon2"));
                    propriedades.SetProperty("user.icon", propriedades.GetProperty("icon2"));
                } else {
                    setImage.SetLabelIcon(IconLabel, propriedades.GetProperty("icon1"));
                    propriedades.SetProperty("user.icon", propriedades.GetProperty("icon1"));
                }
            }
        });
    }
    public void setIcon(JLabel IconLabel){
        SetImageIcon setImage = new SetImageIcon();
        String PathIcon = propriedades.GetProperty("user.icon");
                if (PathIcon == null) {
                    PathIcon = propriedades.GetProperty("default.icon");
                    propriedades.SetProperty("user.icon", PathIcon);
                }
        setImage.SetLabelIcon(IconLabel, PathIcon);
    }

}
