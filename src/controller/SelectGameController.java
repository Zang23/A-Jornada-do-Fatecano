/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import view.TelaOrdenaNumeros;
import view.TelaCacaPalavra;
import view.FMinigameHolder;
import view.FSelectJogos;

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

    public void AddSelect(JButton SelectOption, FSelectJogos telaSelect) {
        SelectOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (selected) {
                    case 0: {
                        TelaCacaPalavra jogo = new TelaCacaPalavra();
                        FMinigameHolder tela = new FMinigameHolder(jogo);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 1: {
                    	telaSelect.setVisible(false);	
                        break;
                    }
                    case 2: {
                        TelaOrdenaNumeros jogo = new TelaOrdenaNumeros();
                        FMinigameHolder tela = new FMinigameHolder(jogo);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 3: {
                    	telaSelect.setVisible(false);
                        break;
                    }
                    case 4: {
                    	telaSelect.setVisible(false);
                        break;
                    }
                    default: {
                    	telaSelect.setVisible(false);
                        break;
                    }
                }
            }
        });
    }
}
