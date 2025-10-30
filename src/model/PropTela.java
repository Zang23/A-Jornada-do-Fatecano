/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.awt.Font;
import java.awt.Component;
import java.io.File;
import javax.swing.JFrame;
import java.awt.Container;

/**
 *
 * @author Admin
 */
public class PropTela extends JFrame {

    // O construtor foi removido. A janela agora usará o comportamento padrão do JFrame.

    public void SetDefautProperties(JFrame tela) {
        // Apenas a configuração da fonte permanece.
        SetFont(tela);
    }

    public void SetFont(JFrame tela) {
        Font fonte;
        try {
            // Recomendo usar getResourceAsStream para compatibilidade com arquivos .jar
            fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf"));
            fonte = fonte.deriveFont(Font.PLAIN, 18f);

            SetFont((Component) tela, fonte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetFont(Component tela, Font fonte) {
        tela.setFont(fonte);
        
        // Verifica se o componente é um container antes de iterar
        if (tela instanceof Container) {
            for (Component c : ((Container) tela).getComponents()) {
                SetFont(c, fonte);
            }
        }
    }
}