package view;

import controller.PlayerIconController;
import javax.swing.JPanel;
import controller.SelectGameController;
import model.PropTela;
import controller.ScoreboardController;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import model.Dificuldade;
import java.awt.BorderLayout; // Import necessário

public class FSelectJogos extends PropTela {

    private final String[] nomesDosJogos = {
        "CacaPalavra",
        "Escolha a Porta",
        "OrdenaNumeros",
        "Organize a Pilha",
        "Torre de Hanoi"
    };

    private Font fonteScoreboard;
    private Dificuldade dificuldadeEscolhida;
    private String nomeHeroi;

    /**
     * CONSTRUTOR MODIFICADO
     * Recebe nome e dificuldade, e configura a tela com o botão "Voltar".
     */
    public FSelectJogos(String NomeHeroi, Dificuldade dificuldade) {
        this.nomeHeroi = NomeHeroi;
        this.dificuldadeEscolhida = dificuldade;

        carregarFonte();
        initComponents();
        SetDefautProperties(this); // Aplica a fonte customizada em todos os componentes
        setExtendedState(MAXIMIZED_BOTH);

        // --- Configuração do Layout Principal (escalável) ---
        jPanel2.setLayout(new BorderLayout(15, 15));
        
        // Cria um painel para a lateral direita (Scoreboard e botão GO)
        JPanel eastPanel = new JPanel(new BorderLayout(0, 10));
        eastPanel.setOpaque(false);
        eastPanel.add(jPanel3, BorderLayout.CENTER);
        eastPanel.add(jButtonGo, BorderLayout.SOUTH);
        eastPanel.setPreferredSize(new Dimension(300, 0)); // Aumentado um pouco
        
        jPanel2.add(jPanel5, BorderLayout.CENTER); // Painel dos jogos
        jPanel2.add(eastPanel, BorderLayout.EAST); // Painel da direita
        jPanel2.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- ALTERAÇÃO: Criação do botão Voltar e painel do topo ---
        JButton btnVoltar = new JButton("<< Voltar");
        btnVoltar.setBackground(new java.awt.Color(73, 73, 73));
        btnVoltar.setForeground(new java.awt.Color(238, 150, 75));
        btnVoltar.setFont(fonteScoreboard.deriveFont(18f));
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 3, true));
        btnVoltar.setFocusable(false); // Impede que o botão receba foco do teclado
        btnVoltar.addActionListener(e -> voltarParaDificuldade());
        
        // Wrapper para o painel do topo, contendo o botão e as infos do jogador
        JPanel topPanelWrapper = new JPanel(new BorderLayout(15, 0));
        topPanelWrapper.setOpaque(false);
        topPanelWrapper.add(btnVoltar, BorderLayout.WEST);
        topPanelWrapper.add(jPanel4, BorderLayout.CENTER);
        
        jPanel2.add(topPanelWrapper, BorderLayout.NORTH); // Adiciona o wrapper no topo

        // --- ALTERAÇÃO: Botão GO!!! não focável ---
        jButtonGo.setFocusable(false);

        // --- Configuração dos painéis dos jogos com GridBagLayout (escalável) ---
        jPanel5.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel[] JogosSelect = {jPanelGame1, jPanelGame2, jPanelGame3, jPanelGame4, jPanelGame5};
        
        for (int i = 0; i < 3; i++) { // Primeira linha
            gbc.gridx = i; gbc.gridy = 0;
            jPanel5.add(JogosSelect[i], gbc);
        }
        for (int i = 3; i < 5; i++) { // Segunda linha
            gbc.gridx = i - 3; gbc.gridy = 1;
            jPanel5.add(JogosSelect[i], gbc);
        }

        SelectGameController SelectController = new SelectGameController();
        LabelNome.setText(NomeHeroi);

        for (int i = 0; i < 5; i++) {
            SelectController.AddOption(JogosSelect[i], jLabelTitle, i);
            final int gameIndex = i;
            JogosSelect[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    atualizarScoreboard(nomesDosJogos[gameIndex]);
                }
            });
        }

        // --- ALTERAÇÃO: Passa a dificuldade para o controller ---
        SelectController.AddSelect(jButtonGo, this, dificuldadeEscolhida);
        
        PlayerIconController IconController = new PlayerIconController();
        IconController.setIcon(jLabelIcon);
        
        jPanel11.setBackground(new Color(73, 73, 73));
        atualizarScoreboard(nomesDosJogos[0]);
    }

    /**
     * Ação para o botão Voltar: fecha esta tela e abre a de dificuldade.
     */
    private void voltarParaDificuldade() {
        FDificuldade telaDificuldade = new FDificuldade(this.nomeHeroi);
        telaDificuldade.setVisible(true);
        this.dispose();
    }

    private void carregarFonte() {
        try {
            fonteScoreboard = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf")).deriveFont(16f);
        } catch (Exception e) {
            System.err.println("Fonte 'BoldPixels.ttf' não encontrada. Usando fonte padrão.");
            fonteScoreboard = new Font("Monospaced", Font.BOLD, 14);
        }
    }

    private void atualizarScoreboard(String nomeJogo) {
        jPanel11.removeAll();
        jPanel11.setLayout(new BoxLayout(jPanel11, BoxLayout.Y_AXIS));
        jPanel11.setBorder(new EmptyBorder(10, 15, 10, 15));

        jLabelTitle.setText(nomeJogo.toUpperCase());

        List<Map.Entry<String, Integer>> scores = ScoreboardController.getScoresOrdenados(nomeJogo);

        if (scores.isEmpty()) {
            JLabel noScoresLabel = new JLabel("Nenhuma pontuacao");
            noScoresLabel.setForeground(Color.WHITE);
            noScoresLabel.setFont(fonteScoreboard);
            jPanel11.add(noScoresLabel);
        } else {
            int rank = 1;
            for (Map.Entry<String, Integer> score : scores) {
                if (rank > 10) break;
                String texto = String.format("%-2d. %-10s %5d", rank, score.getKey(), score.getValue());
                JLabel scoreLabel = new JLabel(texto);
                scoreLabel.setForeground(Color.WHITE);
                scoreLabel.setFont(fonteScoreboard);
                jPanel11.add(scoreLabel);
                rank++;
            }
        }

        jPanel11.revalidate();
        jPanel11.repaint();
    }

    @SuppressWarnings("unchecked")
    // O código gerado pelo NetBeans permanece o mesmo, pois o layout é
    // definido programaticamente no construtor.
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelTitle = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jButtonGo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabelIcon = new javax.swing.JLabel();
        LabelNome = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanelGame1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanelGame2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanelGame3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanelGame4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanelGame5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        jPanel1.setBackground(new java.awt.Color(25, 26, 31)); // Cor de fundo principal
        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.setBorder(new EmptyBorder(35, 35, 35, 35));

        jPanel2.setBackground(new java.awt.Color(25, 26, 31));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 5, true));

        jPanel3.setBackground(new java.awt.Color(73, 73, 73));
        jPanel3.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel1.setForeground(new java.awt.Color(252, 252, 252));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scoreboard");
        jLabel1.setBorder(new javax.swing.border.EmptyBorder(5, 1, 5, 1));

        jSeparator1.setForeground(new java.awt.Color(252, 252, 252));

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabelTitle.setForeground(new java.awt.Color(252, 252, 252));
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("SELECIONE O SEU JOGO");
        jLabelTitle.setBorder(new javax.swing.border.EmptyBorder(10, 1, 1, 1));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new java.awt.Color(73, 73, 73));
        titlePanel.setLayout(new javax.swing.BoxLayout(titlePanel, javax.swing.BoxLayout.Y_AXIS));
        titlePanel.add(jLabelTitle);
        titlePanel.add(jLabel1);
        titlePanel.add(jSeparator1);
        
        jPanel3.add(titlePanel, java.awt.BorderLayout.NORTH);

        jPanel11.setBackground(new java.awt.Color(73, 73, 73));
        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.Y_AXIS));
        jPanel3.add(jPanel11, java.awt.BorderLayout.CENTER);

        jButtonGo.setBackground(new java.awt.Color(73, 73, 73));
        jButtonGo.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jButtonGo.setForeground(new java.awt.Color(238, 150, 75));
        jButtonGo.setText("GO!!!");
        jButtonGo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 3, true));
        jButtonGo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonGo.setPreferredSize(new Dimension(0, 40));

        jPanel4.setBackground(new java.awt.Color(73, 73, 73));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 90));

        jLabelIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelNome.setFont(new java.awt.Font("Segoe UI", 1, 18));
        LabelNome.setForeground(new java.awt.Color(252, 252, 252));
        LabelNome.setText("Nome do herói");
        
        // Layout para o painel de info do jogador
        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(LabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(LabelNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(25, 26, 31));
        
        // Configuração dos painéis dos jogos (Game1 a Game5)
        JPanel[] gamePanels = {jPanelGame1, jPanelGame2, jPanelGame3, jPanelGame4, jPanelGame5};
        JLabel[] gameLabels = {jLabel3, jLabel4, jLabel5, jLabel6, jLabel7};
        for(int i = 0; i < gamePanels.length; i++){
            gamePanels[i].setBackground(new java.awt.Color(25, 26, 31));
            gamePanels[i].setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 150, 75), 5, true));
            gamePanels[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            gamePanels[i].setLayout(new java.awt.BorderLayout());
            gameLabels[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            gameLabels[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/assets/sprites/UI/Generic-Game-Logo.png")));
            gamePanels[i].add(gameLabels[i], java.awt.BorderLayout.CENTER);
        }
        
        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JLabel LabelNome;
    private javax.swing.JButton jButtonGo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelGame1;
    private javax.swing.JPanel jPanelGame2;
    private javax.swing.JPanel jPanelGame3;
    private javax.swing.JPanel jPanelGame4;
    private javax.swing.JPanel jPanelGame5;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration                   
}