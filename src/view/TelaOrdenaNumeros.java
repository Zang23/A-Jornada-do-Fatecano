package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.OrdenaNumerosController;
import model.Dificuldade;
import model.Jogo;
import model.minigames.OrdenaNumeros;

// A classe agora implementa a interface IGamePanel
public class TelaOrdenaNumeros extends JPanel implements IGamePanel {

    private static final long serialVersionUID = 1L;

    private JPanel pnNumeros;
    private OrdenaNumeros jogo;
    private OrdenaNumerosController controller;
    private Runnable onGameEndCallback;
    private KeyListener keyListener; // Referência para o listener para poder desativá-lo

    public TelaOrdenaNumeros(Dificuldade dificuldade, Runnable onGameEndCallback) {
        this.onGameEndCallback = onGameEndCallback;
        
        setLayout(new BorderLayout());
        setBackground(new Color(25, 26, 31));

        jogo = new OrdenaNumeros(dificuldade);
        controller = new OrdenaNumerosController(jogo);
        
        jogo.iniciar();

        pnNumeros = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
        pnNumeros.setOpaque(false);
        add(pnNumeros, BorderLayout.CENTER);

        atualizarTela();

        // Listener de teclado
        this.keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Bloqueia ações se o jogo não estiver no estado "JOGANDO"
                if (controller.getGameState() != OrdenaNumerosController.GameState.JOGANDO) {
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

                // Verifica a condição de vitória após a ação
                if (jogo.estaOrdenado()) {
                    controller.processarVitoria();
                    exibirFimDeJogo(true); // Chama o método de finalização com status de vitória
                }
            }
        };

        addKeyListener(this.keyListener);
        setFocusable(true);
    }
    
    /**
     * --- NOVO MÉTODO ---
     * Centraliza a lógica de exibição do fim de jogo.
     * @param vitoria True se o jogador ganhou, false se perdeu por tempo.
     */
    private void exibirFimDeJogo(boolean vitoria) {
        removeKeyListener(this.keyListener); // Impede que o jogador continue jogando

        String mensagem;
        if (vitoria) {
            mensagem = "Parabéns! Você ordenou os números!\nPontuação: " + jogo.getPontuacao();
        } else {
            mensagem = "O tempo acabou!\nVocê não conseguiu ordenar a tempo.";
        }
        
        JOptionPane.showMessageDialog(
            this, 
            mensagem, 
            "Fim de Jogo", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
        // Fecha a janela e executa o callback de retorno ao menu
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
        pnNumeros.removeAll();

        for (int i = 0; i < jogo.getNumeros().size(); i++) {
            Integer n = jogo.getNumeros().get(i);
            JLabel lbl = new JLabel(String.valueOf(n), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(50, 50));
            lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            lbl.setOpaque(true);
            lbl.setForeground(Color.WHITE);
            lbl.setBackground(new Color(73, 73, 73));
            
            // Define a cor da borda baseada na posição do ponteiro
            Color corBorda = (i == jogo.getPosicaoAtual()) ? new Color(238, 150, 75) : new Color(120, 120, 120);
            lbl.setBorder(new LineBorder(corBorda, 3));

            pnNumeros.add(lbl);
        }

        pnNumeros.revalidate();
        pnNumeros.repaint();
    }
    
    // --- MÉTODOS DA INTERFACE IGamePanel ---

    @Override
    public Jogo getModel() {
        return this.jogo;
    }

    @Override
    public void onTimeUp() {
        controller.tempoEsgotado(); // Notifica o controller que o tempo acabou
        exibirFimDeJogo(false);    // Chama o método de finalização com status de derrota
    }
}