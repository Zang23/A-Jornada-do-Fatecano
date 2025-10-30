package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.CacaPalavraController;
import model.Dificuldade;
import model.Jogo;
import model.minigames.CacaPalavra;

// A classe agora implementa a interface IGamePanel
public class TelaCacaPalavra extends JPanel implements IGamePanel {

    private static final long serialVersionUID = 1L;

    private JPanel pnLetras;
    private JLabel lblMao;
    private JLabel lblPalavraAlvo;

    private CacaPalavra jogo;
    private CacaPalavraController controller;
    private Runnable onGameEndCallback;
    private KeyListener keyListener; // Referência para o listener para poder removê-lo

    public TelaCacaPalavra(Dificuldade dificuldade, Runnable onGameEndCallback) {
        this.onGameEndCallback = onGameEndCallback;

        // Configurações da janela
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(25, 26, 31));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cria o jogo e o novo controller
        jogo = new CacaPalavra(dificuldade);
        controller = new CacaPalavraController(jogo);

        // Painéis e Labels
        pnLetras = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnLetras.setOpaque(false);
        add(pnLetras, BorderLayout.CENTER);

        lblMao = new JLabel("Mão: (vazia)", SwingConstants.CENTER);
        lblMao.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblMao.setForeground(Color.WHITE);
        
        lblPalavraAlvo = new JLabel("Monte a palavra: " + jogo.getPalavraCorreta(), SwingConstants.CENTER);
        lblPalavraAlvo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblPalavraAlvo.setForeground(new Color(238, 150, 75));
        add(lblPalavraAlvo, BorderLayout.NORTH);

        add(lblMao, BorderLayout.SOUTH);

        atualizarTela();

        // Listener de teclado foi movido para uma variável
        this.keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Bloqueia qualquer ação se o jogo não estiver no estado "JOGANDO"
                if (controller.getGameState() != CacaPalavraController.GameState.JOGANDO) {
                    return;
                }

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

                // Verifica a condição de vitória após cada ação
                if (jogo.isRespostaCerta()) {
                    controller.processarVitoria(); // Controller processa a vitória
                    exibirFimDeJogo(true);     // A tela exibe o resultado
                }
            }
        };
        
        addKeyListener(this.keyListener);
        setFocusable(true);
    }
    
    /**
     * --- NOVO MÉTODO ---
     * Centraliza a lógica de finalização do jogo (vitória ou derrota).
     * @param vitoria True se o jogador venceu, false caso contrário.
     */
    private void exibirFimDeJogo(boolean vitoria) {
        removeKeyListener(this.keyListener); // Desativa a entrada do teclado

        String mensagem;
        if (vitoria) {
            mensagem = "Parabéns, você acertou a palavra!\nPontuação: " + jogo.getPontuacao();
        } else {
            mensagem = "O tempo acabou!\nVocê não completou a palavra a tempo.";
        }
        
        JOptionPane.showMessageDialog(
            this,
            mensagem,
            "Fim de Jogo",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Usa SwingUtilities para garantir que o fechamento da janela ocorra na thread de UI
        SwingUtilities.invokeLater(() -> {
            Window janelaAtual = SwingUtilities.getWindowAncestor(this);
            if (janelaAtual != null) {
                janelaAtual.dispose();
            }

            if (onGameEndCallback != null) {
                onGameEndCallback.run();
            }
        });
    }

    private void atualizarTela() {
        pnLetras.removeAll();

        for (int i = 0; i < jogo.getLetras().size(); i++) {
            JLabel lbl = new JLabel(jogo.getLetras().get(i), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(60, 60));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(73, 73, 73));
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 24));

            Color corBorda = (i == jogo.getPosicaoAtual()) ? new Color(238, 150, 75) : new Color(120, 120, 120);
            lbl.setBorder(new LineBorder(corBorda, 3));

            pnLetras.add(lbl);
        }

        if (jogo.getLetraSegurada() == null) {
            lblMao.setText("Mão: (vazia)");
        } else {
            lblMao.setText("Mão: " + jogo.getLetraSegurada());
        }

        pnLetras.revalidate();
        pnLetras.repaint();
    }

    // --- MÉTODOS DA INTERFACE IGamePanel ---

    @Override
    public Jogo getModel() {
        return this.jogo;
    }

    @Override
    public void onTimeUp() {
        controller.tempoEsgotado(); // Notifica o controller
        exibirFimDeJogo(false);    // Exibe a tela de derrota
    }
}