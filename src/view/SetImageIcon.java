/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
/**
 *
 * @author Admin
 */
public class SetImageIcon {
    public void SetLabelIcon(JLabel label, String path){
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        label.setIcon(icon);
    }
}
