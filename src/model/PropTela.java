/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Font;
import java.awt.Component;
import java.io.File;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Container;

/**
 *
 * @author Admin
 */
public class PropTela extends JFrame {

    public void SetDefautProperties(JFrame tela) {
        SetFont(tela);
    }

    public void SetFont(JFrame tela) {
        Font fonte;
        try {
            fonte = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/assets/fonts/BoldPixels.ttf"));
            fonte = fonte.deriveFont(Font.PLAIN, 18f);

            SetFont(tela, fonte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetFont(Component tela, Font fonte) {
        tela.setFont(fonte);
        for (Component c : ((Container) tela).getComponents()) {
            SetFont((Component) c, fonte);
        }
    }
}
