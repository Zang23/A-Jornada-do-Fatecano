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
import view.TelaEscolhaPorta;
import view.TelaOrganizePilha;
import view.FMinigameHolder;
import view.FSelectJogos;
import view.TelaTorreHanoi;
import model.Dificuldade;
import controller.TorreDeHanoiController;
import javax.swing.JComboBox;
import view.ScorePanel;
import java.awt.BorderLayout;
import java.text.Normalizer;

/**
 *
 * @author Admin
 */
public class SelectGameController {

    static private int selected = 6;
    private String[] NomeJogos = {"Caça palavras", "Escolha a porta", "Ordena números", "Organiza torre", "Torre de Hanoi"};
    private String[]NomeJogosFile = {"CacaPalavra", "Escolha_a_Porta", "OrdenaNumeros", "OrganizePilha", "TorreDeHanoi"};
    public void AddOption(JPanel OptionPanel, JLabel NomeLabel, int OptionInt, JPanel painelScore) {
        OptionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = OptionInt;
                NomeLabel.setText(NomeJogos[OptionInt]);
                ScorePanel PScoreView = new ScorePanel();
                painelScore.add(PScoreView.criarPainelScores(NomeJogosFile[OptionInt]), BorderLayout.CENTER);
            }
        });
    }

    public void AddSelect(JButton SelectOption, FSelectJogos telaSelect, JComboBox selectBox) {
        Runnable acaoAposJogo = () -> telaSelect.setVisible(true);

        SelectOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int dif = selectBox.getSelectedIndex() + 1;
                int tempoDefault = 60;
                Dificuldade dificuldade = null;
                switch (dif) {
                    case 1:
                        dificuldade = dificuldade.FACIL;
                    case 2:
                        dificuldade = dificuldade.MEDIO;
                    case 3:
                        dificuldade = dificuldade.DIFICIL;
                }

                TorreDeHanoiController TorreController = new TorreDeHanoiController(dificuldade);

                switch (selected) {
                    case 0: {
                        TelaCacaPalavra jogo = new TelaCacaPalavra(dificuldade, acaoAposJogo);
                        FMinigameHolder tela = new FMinigameHolder(jogo, tempoDefault, dif);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 1: {
                        TelaEscolhaPorta jogo = new TelaEscolhaPorta(dificuldade, acaoAposJogo);
                        FMinigameHolder tela = new FMinigameHolder(jogo, tempoDefault, dif);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 2: {
                        TelaOrdenaNumeros jogo = new TelaOrdenaNumeros(dificuldade, acaoAposJogo);
                        FMinigameHolder tela = new FMinigameHolder(jogo, tempoDefault, dif);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 3: {
                        TelaOrganizePilha jogo = new TelaOrganizePilha(dificuldade, acaoAposJogo);
                        FMinigameHolder tela = new FMinigameHolder(jogo, tempoDefault, dif);
                        tela.setVisible(true);
                        telaSelect.setVisible(false);
                        break;
                    }
                    case 4: {
                        TelaTorreHanoi jogo = new TelaTorreHanoi(TorreController, acaoAposJogo);
                        FMinigameHolder tela = new FMinigameHolder(jogo, tempoDefault, dif);
                        tela.setVisible(true);
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
