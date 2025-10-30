package view;

import controller.OrganizePilhaController;
import model.Dificuldade;
import model.Jogo;
import model.minigames.OrganizePilha;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class TelaOrganizePilha extends JPanel implements IGamePanel {

    private static final long serialVersionUID = 1L;

    private OrganizePilhaController controller;
    private OrganizePilha model;
    private Runnable onGameEndCallback;

    private JPanel pnMeta, pnJogador, pnInventario;
    private MouseListener mouseListener;

    private final Map<Color, ImageIcon> cacheImagens = new HashMap<>();
    private final Color COR_FUNDO = new Color(25, 26, 31);
    private final Color COR_DESTAQUE = new Color(238, 150, 75);
    private Font fonteJogo;
    private boolean vitoria;

    public TelaOrganizePilha(Dificuldade dificuldade, Runnable onGameEndCallback) {
        this.model = new OrganizePilha(dificuldade);
        this.controller = new OrganizePilhaController(model);
        this.onGameEndCallback = onGameEndCallback;

        carregarFonte();
        setLayout(new BorderLayout(10, 10));
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        add(criarPainelJogo(), BorderLayout.CENTER);
        
        carregarImagensOriginais();
        adicionarListeners();

        setFocusable(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                SwingUtilities.invokeLater(() -> atualizarTela());
            }
        });
    }

    @Override
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(() -> atualizarTela());
    }
    
    // --- MÉTODO DESENHARPILHA REALMENTE CORRIGIDO ---
    private void desenharPilha(JPanel panel, Stack<Color> pilha, boolean isJogador, int tamanhoBloco) {
        panel.removeAll();

        // 1. Adiciona um "calço" no topo para empurrar todos os blocos para a base.
        panel.add(Box.createVerticalGlue());

        // 2. Itera pela pilha de CIMA PARA BAIXO (do topo para a base).
        //    Isso garante que o bloco do topo seja adicionado primeiro ao painel e,
        //    portanto, apareça visualmente no topo.
        for (int i = pilha.size() - 1; i >= 0; i--) {
            Color cor = pilha.get(i);
            JLabel lblBloco = new JLabel(getScaledIcon(cor, tamanhoBloco));
            lblBloco.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 3. Aplica a borda de seleção no topo da pilha.
            //    Como o loop começa no topo (i == pilha.size() - 1),
            //    o primeiro bloco desenhado será o selecionado.
            if (isJogador && i == pilha.size() - 1) {
                lblBloco.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
            }

            panel.add(lblBloco);
        }

        panel.revalidate();
        panel.repaint();
    }
    
    private void atualizarTela() {
        if (getWidth() == 0 || getHeight() == 0) return;

        int tamanhoBloco = calcularTamanhoBloco();
        
        desenharPilha(pnMeta, model.getPilhaMeta(), false, tamanhoBloco);
        desenharPilha(pnJogador, model.getPilhaJogador(), true, tamanhoBloco);
        desenharFila(pnInventario, model.getInventario(), tamanhoBloco);
    }

    private void carregarFonte() {
        try {
            fonteJogo = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf"));
        } catch (Exception e) {
            System.err.println("Fonte não encontrada, usando SansSerif.");
            fonteJogo = new Font("SansSerif", Font.BOLD, 18);
        }
    }

    private JPanel criarPainelJogo() {
        JPanel pnCentral = new JPanel(new GridLayout(1, 3, 20, 0));
        pnCentral.setOpaque(false);

        pnMeta = createStyledPanel("Meta");
        pnJogador = createStyledPanel("Sua Pilha");
        pnInventario = createStyledPanel("Inventário");
        
        pnCentral.add(pnMeta);
        pnCentral.add(pnJogador);
        pnCentral.add(pnInventario);
        
        return pnCentral;
    }

    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(createTitledBorder(title));
        return panel;
    }
    
    private void carregarImagensOriginais() {
        Map<Color, String> mapaCores = new HashMap<>();
        mapaCores.put(Color.RED, "red-block.png");
        mapaCores.put(Color.BLUE, "blue-block.png");
        mapaCores.put(Color.GREEN, "green-block.png");
        mapaCores.put(Color.YELLOW, "yellow-block.png");
        mapaCores.put(Color.ORANGE, "crimson-block.png");
        mapaCores.put(Color.CYAN, "blue-block.png");
        mapaCores.put(Color.MAGENTA, "purple-block.png");

        for (Map.Entry<Color, String> entry : mapaCores.entrySet()) {
            try {
                java.net.URL imageUrl = getClass().getResource("/resources/assets/sprites/blocks/" + entry.getValue());
                if (imageUrl != null) {
                    cacheImagens.put(entry.getKey(), new ImageIcon(imageUrl));
                }
            } catch (Exception e) {
                System.err.println("Exceção ao carregar imagem: " + entry.getValue());
            }
        }
    }

    private void adicionarListeners() {
        this.mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getGameState() != OrganizePilhaController.GameState.JOGANDO) return;
                
                if (SwingUtilities.isLeftMouseButton(e)) {
                    controller.executarAcaoPilhaParaInventario();
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    controller.executarAcaoInventarioParaPilha();
                }
                
                atualizarTela();

                if (controller.getGameState() == OrganizePilhaController.GameState.VITORIA) {
                    exibirFimDeJogo();
                }
            }
        };
        this.addMouseListener(this.mouseListener);
    }

    private int calcularTamanhoBloco() {
        int alturaDisponivel = pnJogador.getHeight() - 40;
        if (alturaDisponivel <= 0) return 64;
        int tamanho = alturaDisponivel / (model.getNumeroItens() + 1);
        return Math.max(32, Math.min(tamanho, 128));
    }
    
    private void desenharFila(JPanel panel, Queue<Color> fila, int tamanhoBloco) {
        panel.removeAll();
        boolean primeiroItem = true;

        for (Color cor : fila) {
            JLabel lblBloco = new JLabel(getScaledIcon(cor, tamanhoBloco));
            lblBloco.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            if (primeiroItem) {
                lblBloco.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
                primeiroItem = false;
            }
            panel.add(lblBloco);
        }
        
        panel.add(Box.createVerticalGlue());
        panel.revalidate();
        panel.repaint();
    }

    private ImageIcon getScaledIcon(Color color, int size) {
        if (size <= 0) return null;
        ImageIcon originalIcon = cacheImagens.get(color);
        if (originalIcon == null) return null;
        Image image = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
    private void exibirFimDeJogo() {
        this.removeMouseListener(this.mouseListener);

        String mensagem;
        if (controller.getGameState() == OrganizePilhaController.GameState.VITORIA) {
            mensagem = "Parabéns! Você venceu!\nPontuação Final: " + model.getPontuacao();
        } else {
            mensagem = "O tempo acabou!\nVocê não conseguiu organizar a pilha a tempo.";
        }
        
        JOptionPane.showMessageDialog(this, mensagem, "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        
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

    private TitledBorder createTitledBorder(String title) {
        Border lineBorder = BorderFactory.createLineBorder(COR_DESTAQUE, 2);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, title);
        titledBorder.setTitleFont(fonteJogo.deriveFont(18f));
        titledBorder.setTitleColor(COR_DESTAQUE);
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        return titledBorder;
    }
    
    @Override
    public Jogo getModel() {
        return this.model;
    }

    @Override
    public void onTimeUp() {
        controller.tempoEsgotado();
        exibirFimDeJogo();
    }
}