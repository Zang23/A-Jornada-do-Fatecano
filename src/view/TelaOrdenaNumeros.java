package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import controller.OrdenaNumerosController;
import model.minigames.OrdenaNumeros;
import model.Dificuldade;

public class TelaOrdenaNumeros extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel pnNumeros;
    private JLabel lblPonteiro;
    private OrdenaNumeros jogo;
    private OrdenaNumerosController controller;

    public TelaOrdenaNumeros() {
        setLayout(new BorderLayout());

        // Inicializa o jogo e o controller
        jogo = new OrdenaNumeros(Dificuldade.FACIL);
        controller = new OrdenaNumerosController(jogo);

        // Painel principal
        pnNumeros = new JPanel();
        pnNumeros.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        pnNumeros.setBackground(new Color(240, 240, 240));
        add(pnNumeros, BorderLayout.CENTER);

        // Ponteiro visual
        lblPonteiro = new JLabel("⬆");
        lblPonteiro.setHorizontalAlignment(SwingConstants.CENTER);
        lblPonteiro.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(lblPonteiro, BorderLayout.SOUTH);

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

                if (jogo.estaOrdenado()) {
                    JOptionPane.showMessageDialog(null, "✅ Parabéns! Você ordenou todos os números!");
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    private void atualizarTela() {
        pnNumeros.removeAll();

        for (int i = 0; i < jogo.getNumeros().size(); i++) {
            Integer n = jogo.getNumeros().get(i);

            JLabel lbl = new JLabel(String.valueOf(n), SwingConstants.CENTER);
            lbl.setPreferredSize(new Dimension(50, 50));
            lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            lbl.setOpaque(true);
            lbl.setBackground(Color.WHITE);
            lbl.setBorder(new LineBorder(Color.BLACK, 2));

            if (i == jogo.getPosicaoAtual()) {
                lbl.setBackground(new Color(173, 216, 230)); // azul claro no número selecionado
            }

            pnNumeros.add(lbl);
        }

        pnNumeros.revalidate();
        pnNumeros.repaint();
    }
}
