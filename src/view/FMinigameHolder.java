package view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout; // Import que estava faltando
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import model.Jogo;
import controller.NameController;
import controller.PlayerIconController;

public class FMinigameHolder extends JFrame {

    private Timer gameTimer;
    private int tempoRestante;
    private final JPanel painelMinigame;
    private final Runnable onGameEndCallback; // Armazena a ação de retorno
    private Font fonteJogo;

    public FMinigameHolder(JPanel painelMinigame, Jogo jogoModel, Runnable onGameEndCallback) {
        this.painelMinigame = painelMinigame;
        this.onGameEndCallback = onGameEndCallback;
        
        carregarFonte();
        initComponents();
        
        // Adiciona o painel do jogo específico no centro da tela
        jPanel3.add(painelMinigame, BorderLayout.CENTER);

        atualizarInfoJogador();
        
        this.tempoRestante = jogoModel.getTempoMaximo();
        iniciarTimer();

        // Garante que o menu volte a aparecer se o usuário fechar a janela pelo "X"
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarParaMenu();
            }
        });
    }
    
    /**
     * Carrega a fonte customizada dos recursos do projeto.
     */
    private void carregarFonte() {
        try (InputStream is = getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf")) {
            if (is == null) throw new Exception("Arquivo de fonte não encontrado!");
            fonteJogo = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte: " + e.getMessage());
            fonteJogo = new Font("SansSerif", Font.BOLD, 20);
        }
    }
    
    /**
     * Atualiza o nome e o ícone do jogador no cabeçalho.
     */
    private void atualizarInfoJogador() {
        NameController nameController = new NameController();
        LabelNome.setText(nameController.getName());
        
        PlayerIconController iconController = new PlayerIconController();
        iconController.setIcon(jLabelIcon);
    }
    
    /**
     * Inicia o timer universal do jogo.
     */
    private void iniciarTimer() {
        LabelTempo.setText("Tempo: " + tempoRestante);
        
        gameTimer = new Timer(1000, e -> {
            tempoRestante--;
            if (tempoRestante >= 0) {
                LabelTempo.setText("Tempo: " + tempoRestante);
            }
            
            if (tempoRestante < 0) {
                gameTimer.stop();
                LabelTempo.setText("Tempo Esgotado!");
                LabelTempo.setForeground(Color.RED);
                
                // Notifica o painel do minigame que o tempo acabou
                if (painelMinigame instanceof IGamePanel) {
                    ((IGamePanel) painelMinigame).onTimeUp();
                }
            }
        });
        gameTimer.start();
    }

    /**
     * Para o timer, fecha a janela atual e executa o callback para voltar ao menu.
     */
    private void voltarParaMenu() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        this.dispose(); // Fecha esta janela
        if (onGameEndCallback != null) {
            onGameEndCallback.run(); // Executa a ação (mostrar a tela anterior)
        }
    }

    /**
     * Inicializa os componentes da UI. O código foi reescrito para usar layouts
     * mais flexíveis e aplicar as correções de fonte e foco.
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        LabelNome = new javax.swing.JLabel();
        LabelTempo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        JButton btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        
        jPanel1.setBackground(new java.awt.Color(40, 42, 54));
        jPanel1.setLayout(new BorderLayout());
        jPanel1.setBorder(new EmptyBorder(35, 35, 35, 35));

        jPanel2.setBackground(new java.awt.Color(25, 26, 31));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 5, true));
        jPanel2.setLayout(new BorderLayout());

        // --- PAINEL DO CABEÇALHO (jPanel4) ---
        jPanel4.setBackground(new java.awt.Color(73, 73, 73));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 90));
        jPanel4.setLayout(new BorderLayout(15, 0));
        jPanel4.setBorder(new EmptyBorder(5, 15, 5, 15));

        // --- BOTÃO VOLTAR (COM CORREÇÕES) ---
        btnVoltar.setText("<< Voltar");
        btnVoltar.setBackground(new java.awt.Color(73, 73, 73));
        btnVoltar.setForeground(new java.awt.Color(238, 150, 75));
        btnVoltar.setFont(fonteJogo.deriveFont(18f)); // Fonte customizada
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 2, true));
        btnVoltar.setFocusable(false); // Impede que o botão seja focado pelo teclado
        btnVoltar.addActionListener(e -> voltarParaMenu());
        jPanel4.add(btnVoltar, BorderLayout.WEST);

        // --- Painel para Ícone e Nome ---
        JPanel playerInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        playerInfoPanel.setOpaque(false);
        jLabelIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerInfoPanel.add(jLabelIcon);
        
        LabelNome.setForeground(new java.awt.Color(252, 252, 252));
        LabelNome.setFont(fonteJogo); // Fonte customizada
        playerInfoPanel.add(LabelNome);
        jPanel4.add(playerInfoPanel, BorderLayout.CENTER);

        // --- Label do Tempo ---
        LabelTempo.setForeground(new java.awt.Color(252, 252, 252));
        LabelTempo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LabelTempo.setFont(fonteJogo); // Fonte customizada
        jPanel4.add(LabelTempo, BorderLayout.EAST);

        jPanel2.add(jPanel4, BorderLayout.NORTH);

        // --- Painel do Conteúdo do Jogo ---
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.setBorder(new EmptyBorder(15, 15, 15, 15));
        jPanel2.add(jPanel3, BorderLayout.CENTER);

        jPanel1.add(jPanel2, BorderLayout.CENTER);

        getContentPane().add(jPanel1, BorderLayout.CENTER);

        pack();
    }

    // Declaração das variáveis dos componentes
    private javax.swing.JLabel LabelNome;
    private javax.swing.JLabel LabelTempo;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
}