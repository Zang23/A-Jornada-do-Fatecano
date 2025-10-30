package view;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
public class ScorePanel {

    // Método que recebe o nome do jogo e devolve um JPanel com os 10 primeiros scores
    public JPanel criarPainelScores(String nomeJogo) {
        // Cria o painel com BoxLayout (vertical)
        JPanel painel = new JPanel();
        InputStream is = getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf");
        Font fontBase = new Font("Arial", Font.PLAIN, 16);
        try{
            fontBase = Font.createFont(Font.TRUETYPE_FONT, is);
        }catch(Exception e){
            e.printStackTrace();
        }
        Font fontePersonalizada = fontBase.deriveFont(Font.PLAIN, 18f);
        painel.setBackground(new Color(73, 73, 73));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        // Obtém a lista de pontuações ordenadas
        List<Map.Entry<String, Integer>> scores = controller.ScoreboardController.getScoresOrdenados(nomeJogo);

        // Contador para limitar a 10 itens
        int contador = 0;

        // Usa for-each para percorrer as entries
        for (Map.Entry<String, Integer> entry : scores) {
            System.out.println(entry.getKey());
            JLabel label = new JLabel(entry.getKey() + " — " + entry.getValue());
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(new Color(252, 252, 252));
            label.setFont(fontePersonalizada);
            painel.add(label);

            contador++;
            if (contador >= 10) break; // Limita aos 10 primeiros
        }
        
        return painel;
    }

    // Simulação de método original (para teste)
    
}
