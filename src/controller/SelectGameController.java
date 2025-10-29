/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import view.TelaOrdenaNumeros;
import view.TelaCacaPalavra;
import view.FMinigameHolder;

/**
 *
 * @author Admin
 */
public class SelectGameController {

    static private int selected = 6;
    private String[] NomeJogos = {"Caça palavras", "Escolha a porta", "Ordena números", "Organiza torre", "Torre de Hanoi"};

    public void AddOption(JPanel OptionPanel, JLabel NomeLabel, int OptionInt) {
        OptionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = OptionInt;
                NomeLabel.setText(NomeJogos[OptionInt]);
            }
        });
    }

    public void AddSelect(JButton SelectOption, JComboBox CompDif) {
        SelectOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (selected) {
                    case 0: {
                        TelaCacaPalavra jogo = new TelaCacaPalavra();
                        FMinigameHolder tela = new FMinigameHolder(jogo, 180);
                        tela.setVisible(true);
                        break;
                    }
                    case 1: {
                        
                        break;
                    }
                    case 2: {
                        TelaOrdenaNumeros jogo = new TelaOrdenaNumeros();
                        FMinigameHolder tela = new FMinigameHolder(jogo, 180);
                        tela.setVisible(true);
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 4: {
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        });
    }
}
