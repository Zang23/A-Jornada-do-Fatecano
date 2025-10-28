/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
/**
 *
 * @author Admin
 */
public class Placeholder {
    public static void setPlaceholder(String Texto, JTextComponent Campo){
        Campo.setText(Texto);
        Campo.setForeground(Color.decode("#4A4A4A"));
        Campo.addFocusListener(new FocusListener() {
            boolean isPlaceholder = true;
                        @Override
                        public void focusGained(FocusEvent e) {
                            if(isPlaceholder == true){
                                Campo.setText("");
                                Campo.setForeground(Color.decode("#FCFCFC"));
                            }
                            isPlaceholder = false;
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            if(Campo.getText().isEmpty()){
                                isPlaceholder = true;
                                Campo.setText(Texto);
                                Campo.setForeground(Color.decode("#4A4A4A"));
                            }
                        }
                    });
    }
}
