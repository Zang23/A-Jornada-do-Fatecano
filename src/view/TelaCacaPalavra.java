package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.CacaPalavraController;
import model.minigames.CacaPalavra;
import model.Dificuldade;

public class TelaCacaPalavra extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel pnLetras;
    private JLabel lblMao;
    private JLabel lblPalavraAlvo;


    private CacaPalavra jogo;
    private CacaPalavraController controller;

    public TelaCacaPalavra() {
        // Configurações da janela
        setTitle("Caça Palavras");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);

        // Cria o jogo (model) e o controller
        jogo = new CacaPalavra(Dificuldade.FACIL); // ou DIFICIL, se quiser testar
        controller = new CacaPalavraController(jogo);

        // Painel principal de letras
        pnLetras = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(pnLetras, BorderLayout.CENTER);

        // Label que mostra o que o jogador está segurando
        lblMao = new JLabel("Mão: (vazia)", SwingConstants.CENTER);
        lblMao.setFont(new Font("Arial", Font.BOLD, 18));
        lblMao.setForeground(Color.DARK_GRAY);
        
        lblPalavraAlvo = new JLabel("Monte a palavra: " + jogo.getPalavraCorreta(), SwingConstants.CENTER);
        lblPalavraAlvo.setFont(new Font("Arial", Font.BOLD, 22));
        lblPalavraAlvo.setForeground(new Color(0, 100, 0));
        add(lblPalavraAlvo, BorderLayout.NORTH);

        
        add(lblMao, BorderLayout.SOUTH);

        // Atualiza o estado inicial da tela
        atualizarTela();

        // Listener de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        controller.moverEsquerda();
                        break;
                    case KeyEvent.VK_RIGHT:
                        controller.moverDireita();
                        break;
                    case KeyEvent.VK_SPACE:
                        controller.acaoEspaco();
                        break;
                }
                atualizarTela();
            }
        });

        setFocusable(true);
    }

    // Atualiza a exibição da tela de acordo com o estado atual do jogo
    private void atualizarTela() {
        pnLetras.removeAll();

        // Exibe cada letra do model como um JLabel
        for (int i = 0; i < jogo.getLetras().size(); i++) {
            JLabel lbl = new JLabel(jogo.getLetras().get(i), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(60, 60));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(0, 0, 160));
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.BOLD, 20));

            // Destaque o quadrado atual com amarelo
            Color corBorda = (i == jogo.getPosicaoAtual()) ? Color.YELLOW : Color.BLACK;
            lbl.setBorder(new LineBorder(corBorda, 2));

            pnLetras.add(lbl);
        }

        // Atualiza o texto da "mão"
        if (jogo.getLetraSegurada() == null) {
            lblMao.setText("Mão: (vazia)");
            lblMao.setForeground(Color.DARK_GRAY);
        } else {
            lblMao.setText("Mão: " + jogo.getLetraSegurada());
            lblMao.setForeground(Color.BLUE);
        }

        pnLetras.revalidate();
        pnLetras.repaint();
    }

    // Método principal para testar a tela separadamente
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TelaCacaPalavra tela = new TelaCacaPalavra();
            tela.setVisible(true);
        });
    }
}
