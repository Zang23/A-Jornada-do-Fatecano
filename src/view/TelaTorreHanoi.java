package view;

import controller.TorreDeHanoiController;
import controller.TorreDeHanoiListener;
import controller.TorreDeHanoiController.EstadoJogo;
import model.Jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.util.Stack;
import model.Dificuldade; // Verifique se o import está presente

// ==========================================================
// CLASSE PRINCIPAL
// ==========================================================
public class TelaTorreHanoi extends JPanel implements TorreDeHanoiListener, IGamePanel {

    private final TorreDeHanoiController controller;
    private final Runnable onGameEndCallback;
    private HanoiDrawingPanel drawingPanel; // Esta linha causa o erro se a classe abaixo não existir
    private JLabel lblMovimentos;
    private Font fonteDoJogo;
    private MouseAdapter mouseListener;

    public TelaTorreHanoi(TorreDeHanoiController controller, Runnable onGameEndCallback) {
        this.controller = controller;
        this.onGameEndCallback = onGameEndCallback;
        
        carregarFonte();
        
        this.controller.setListener(this);
        initUI();
        atualizarTela();
    }

    private void carregarFonte() {
        try (InputStream is = getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf")) {
            if (is == null) throw new Exception("Fonte não encontrada.");
            fonteDoJogo = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(22f);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte: " + e.getMessage());
            fonteDoJogo = new Font("Arial", Font.BOLD, 18);
        }
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 26, 31));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        lblMovimentos = new JLabel("Movimentos: 0");
        lblMovimentos.setForeground(Color.WHITE);
        lblMovimentos.setFont(fonteDoJogo);
        
        infoPanel.add(lblMovimentos);
        add(infoPanel, BorderLayout.NORTH);

        drawingPanel = new HanoiDrawingPanel(controller);
        add(drawingPanel, BorderLayout.CENTER);

        this.mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int panelWidth = drawingPanel.getWidth();
                int towerClicked = (e.getX() / (panelWidth / 3)) + 1;
                controller.processarJogada(towerClicked);
            }
        };
        
        drawingPanel.addMouseListener(mouseListener);
        
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void atualizarTela() {
        lblMovimentos.setText("Movimentos: " + controller.getModel().getMovimentos());
        drawingPanel.repaint();
    }

    @Override
    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Torre de Hanoi", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void jogoEncerrado(EstadoJogo estado) {
        drawingPanel.removeMouseListener(mouseListener);

        String mensagemFinal;
        if (estado == EstadoJogo.VITORIA) {
            mensagemFinal = "Parabéns, você venceu!\nPontuação: " + controller.getModel().getPontuacao();
        } else {
            mensagemFinal = "O tempo acabou! Tente novamente.";
        }
        
        JOptionPane.showMessageDialog(this, mensagemFinal, "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        
        SwingUtilities.invokeLater(() -> {
            Window janela = SwingUtilities.getWindowAncestor(this);
            if (janela != null) {
                janela.dispose();
            }
            if (onGameEndCallback != null) {
                onGameEndCallback.run();
            }
        });
    }

    @Override
    public void atualizarTempo() {
        // Vazio, pois o timer agora é universal.
    }

    @Override
    public Jogo getModel() {
        return this.controller.getModel();
    }

    @Override
    public void onTimeUp() {
        controller.tempoEsgotado();
    }
}


// ======================================================================
// CLASSE AUXILIAR DE DESENHO (DEVE ESTAR NO MESMO ARQUIVO!)
// ======================================================================
class HanoiDrawingPanel extends JPanel {
    private final TorreDeHanoiController controller;
    private final ImageIcon[] discSprites;
    private static final Color COR_FUNDO = new Color(43, 44, 51);
    private static final Color COR_BASE = new Color(73, 73, 73);
    private static final Color COR_HASTE = new Color(120, 120, 120);
    private static final Color COR_DESTAQUE_BASE = new Color(238, 150, 75);
    private static final int ALTURA_DE_EMPILHAMENTO = 30;

    public HanoiDrawingPanel(TorreDeHanoiController controller) {
        this.controller = controller;
        this.discSprites = new ImageIcon[4];
        loadDiscSprites();
        setBackground(COR_FUNDO);
    }

    private void loadDiscSprites() {
        String[] paths = {
            "/resources/assets/sprites/Discs/SMALL-PINK.png",
            "/resources/assets/sprites/Discs/MEDIUM-GREEN.png",
            "/resources/assets/sprites/Discs/BIG-RED.png",
            "/resources/assets/sprites/Discs/GIANT-PURPLE.png"
        };
        for (int i = 0; i < paths.length; i++) {
            try {
                discSprites[i] = new ImageIcon(getClass().getResource(paths[i]));
            } catch (Exception e) {
                System.err.println("Erro ao carregar o sprite do disco: " + paths[i]);
                discSprites[i] = new ImageIcon();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int baseHeight = 25;
        int baseY = panelHeight - baseHeight - 30;
        int baseWidth = panelWidth - 60;
        int pillarWidth = 18;
        int pillarHeight = (int) (panelHeight * 0.65);
        int pillarY = baseY - pillarHeight;
        int[] pillarX = { panelWidth / 4, panelWidth / 2, (panelWidth * 3) / 4 };

        g2d.setColor(COR_BASE);
        g2d.fill(new RoundRectangle2D.Float(30, baseY, baseWidth, baseHeight, 15, 15));
        
        Stack<Integer> sourceTower = controller.getTorreSelecionada();
        if (sourceTower != null) {
            int sourceIndex = getTowerIndex(sourceTower);
            if (sourceIndex != -1) {
                g2d.setColor(COR_DESTAQUE_BASE);
                g2d.fill(new RoundRectangle2D.Float(pillarX[sourceIndex] - (baseWidth / 6f), baseY, baseWidth / 3f, baseHeight, 15, 15));
            }
        }

        g2d.setColor(COR_HASTE);
        for (int x : pillarX) {
            g2d.fill(new RoundRectangle2D.Float(x - pillarWidth / 2f, pillarY, pillarWidth, pillarHeight, 10, 10));
        }
        
        desenharTorre(g2d, controller.getModel().getTorreOrigem(), pillarX[0], baseY);
        desenharTorre(g2d, controller.getModel().getTorreAuxiliar(), pillarX[1], baseY);
        desenharTorre(g2d, controller.getModel().getTorreDestino(), pillarX[2], baseY);

        Integer heldDisc = controller.getDiscoSegurado();
        if (heldDisc != null) {
            int sourceIndex = getTowerIndex(controller.getTorreSelecionada());
            if (sourceIndex != -1) {
                ImageIcon discIcon = discSprites[heldDisc - 1];
                int discX = pillarX[sourceIndex] - (discIcon.getIconWidth() / 2);
                int discY = pillarY - discIcon.getIconHeight() - 10;
                discIcon.paintIcon(this, g2d, discX, discY);
            }
        }
    }
    
    private int getTowerIndex(Stack<Integer> tower) {
        if (tower == controller.getModel().getTorreOrigem()) return 0;
        if (tower == controller.getModel().getTorreAuxiliar()) return 1;
        if (tower == controller.getModel().getTorreDestino()) return 2;
        return -1;
    }

    private void desenharTorre(Graphics2D g2d, Stack<Integer> torre, int pillarX, int baseY) {
        if (torre == null) return; // Adiciona uma verificação de segurança
        for (int i = 0; i < torre.size(); i++) {
            int discValue = torre.get(i);
            ImageIcon discIcon = discSprites[discValue - 1];
            int discX = pillarX - (discIcon.getIconWidth() / 2);
            int discY = baseY - ((i + 1) * ALTURA_DE_EMPILHAMENTO);
            discIcon.paintIcon(this, g2d, discX, discY);
        }
    }
}