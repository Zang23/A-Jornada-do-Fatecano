/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
/**
 *
 * @author dti
 */
public class SetActiveBorder {
    public void setPanelActive(JPanel painel){
        painel.setBorder(new LineBorder(new Color(255, 100, 0), 5));
    }
    public void unsetPanelActive(JPanel painel){
        painel.setBorder(null);
    }
}
