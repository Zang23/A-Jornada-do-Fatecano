/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import view.IGamePanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Admin
 */
public class TimerLabel {
    Timer timer;
    public void setTimer(JLabel labelTempo, IGamePanel painelJogo, int tempo){
        timer = new Timer(1000, new ActionListener() {
            int tempoRestante = tempo;
            @Override
            public void actionPerformed(ActionEvent e) {
                tempoRestante--;
                if (tempoRestante >= 0) {
                    labelTempo.setText("" + tempoRestante);
                }
                if (tempoRestante <= 0) {
                    timer.stop();
                    labelTempo.setText("FALHOU");
                    labelTempo.setForeground(Color.RED);
                    painelJogo.onTimeUp();
                }
            }
        });
        timer.start();
    }
    
}
