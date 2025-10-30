package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import controller.CacaPalavraController;
import model.minigames.CacaPalavra;
import model.Dificuldade;

public class TelaCacaPalavra extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel pnLetras;
    private JLabel lblMao;
    private JLabel lblPalavraAlvo;

    private CacaPalavra jogo;
    private CacaPalavraController controller;

    public TelaCacaPalavra() {
        setLayout(new BorderLayout());

        // Inicializa jogo e controller
        jogo = new CacaPalavra(Dificuldade.FACIL);
        controller = new CacaPalavraController(jogo);

        // Painel principal
        pnLetras = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnLetras.setBackground(new Color(245, 245, 245));
        add(pnLetras, BorderLayout.CENTER);

        // Label superior com a palavra alvo
        lblPalavraAlvo = new JLabel("Monte a palavra: " + jogo.getPalavraCorreta(), SwingConstants.CENTER);
        lblPalavraAlvo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblPalavraAlvo.setForeground(new Color(0, 100, 0));
        add(lblPalavraAlvo, BorderLayout.NORTH);

        // Label inferior da "mão"
        lblMao = new JLabel("Mão: (vazia)", SwingConstants.CENTER);
        lblMao.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblMao.setForeground(Color.DARK_GRAY);
        add(lblMao, BorderLayout.SOUTH);

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

                // ✅ Verifica se o jogador terminou o jogo
                if (jogo.isRespostaCerta()) {
                	
                	int pontuacao = jogo.calcularPontuacao(0, 0);
                	controller.registrarPontuacao(pontuacao);

                    JOptionPane pane = new JOptionPane(
                        "Parabéns! Você montou a palavra corretamente!\nPressione ENTER para voltar à seleção de jogos.",
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION
                    );

                    JDialog dialog = pane.createDialog(null, "Você venceu!");
                    dialog.setModal(true);

                    JRootPane rootPane = dialog.getRootPane();
                    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER_pressed");

                    rootPane.getActionMap().put("ENTER_pressed", new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                            controller.retornaSelectJogo();

                            // Fecha a janela que contém esta tela
                            Window janelaAtual = SwingUtilities.getWindowAncestor(TelaCacaPalavra.this);
                            if (janelaAtual != null) {
                                janelaAtual.dispose();
                            }
                        }
                    });

                    dialog.setVisible(true);
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    private void atualizarTela() {
        pnLetras.removeAll();

        for (int i = 0; i < jogo.getLetras().size(); i++) {
            JLabel lbl = new JLabel(jogo.getLetras().get(i), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(60, 60));
            lbl.setOpaque(true);
            lbl.setBackground(new Color(0, 0, 160));
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 20));

            Color corBorda = (i == jogo.getPosicaoAtual()) ? Color.YELLOW : Color.BLACK;
            lbl.setBorder(new LineBorder(corBorda, 2));

            pnLetras.add(lbl);
        }

        // Atualiza a “mão”
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
}
