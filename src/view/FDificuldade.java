package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.Dificuldade;
import model.PropTela;

public class FDificuldade extends PropTela {

    private String nomeHeroi;
    private Dificuldade dificuldadeSelecionada = null;
    private Font fonteBotoes;

    private JButton btnFacil, btnMedio, btnDificil;
    private final Color COR_DESTAQUE = new Color(238, 150, 75);
    private final Color COR_PADRAO = new Color(73, 73, 73);
    private final Color COR_TEXTO_DESTAQUE = new Color(252, 252, 252);
    private final Color COR_TEXTO_PADRAO = new Color(238, 150, 75);

    public FDificuldade(String nomeHeroi) {
        this.nomeHeroi = nomeHeroi;
        carregarFonte(); // Carrega a fonte customizada para os botões
        initComponents();
        SetDefautProperties(this); // Aplica a fonte padrão para outros componentes (como o título)
        setExtendedState(MAXIMIZED_BOTH); // Inicia a tela maximizada
    }
    
    /**
     * Carrega o arquivo de fonte customizada para ser usado nos botões.
     */
    private void carregarFonte() {
        try {
            fonteBotoes = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf")).deriveFont(22f);
        } catch (Exception e) {
            System.err.println("Fonte 'BoldPixels.ttf' não encontrada. Usando fonte padrão.");
            fonteBotoes = new Font("SansSerif", Font.BOLD, 18);
        }
    }

    /**
     * Constrói a interface gráfica da tela.
     */
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Seleção de Dificuldade");

        // Painel principal que ocupa toda a tela para centralizar o conteúdo
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(new Color(25, 26, 31));
        
        // Painel de conteúdo com a borda laranja
        JPanel painelConteudo = new JPanel(new GridBagLayout());
        painelConteudo.setBackground(new Color(25, 26, 31));
        painelConteudo.setBorder(new LineBorder(COR_DESTAQUE, 5, true));
        painelConteudo.setPreferredSize(new Dimension(800, 500)); // Tamanho preferencial do painel
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // Título da tela
        JLabel lblTitulo = new JLabel("SELECIONE A DIFICULDADE");
        lblTitulo.setFont(fonteBotoes.deriveFont(36f)); // Aplica a fonte com tamanho maior
        lblTitulo.setForeground(Color.WHITE);
        painelConteudo.add(lblTitulo, gbc);

        // Painel para os botões de dificuldade
        JPanel painelBotoesDificuldade = new JPanel(new GridBagLayout());
        painelBotoesDificuldade.setOpaque(false);
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 25, 10, 25);
        
        btnFacil = createDifficultyButton("FACIL");
        btnMedio = createDifficultyButton("MEDIO");
        btnDificil = createDifficultyButton("DIFICIL");
        
        painelBotoesDificuldade.add(btnFacil, gbcButtons);
        painelBotoesDificuldade.add(btnMedio, gbcButtons);
        painelBotoesDificuldade.add(btnDificil, gbcButtons);

        gbc.insets = new Insets(40, 20, 40, 20); // Aumenta o espaçamento vertical
        painelConteudo.add(painelBotoesDificuldade, gbc);

        // Painel para os botões de navegação (Voltar, Continuar)
        JPanel painelNavegacao = new JPanel(new GridBagLayout());
        painelNavegacao.setOpaque(false);
        
        JButton btnVoltar = createNavButton("VOLTAR");
        JButton btnContinuar = createNavButton("CONTINUAR");

        gbcButtons.gridx = 0;
        painelNavegacao.add(btnVoltar, gbcButtons);
        gbcButtons.gridx = 1;
        painelNavegacao.add(btnContinuar, gbcButtons);

        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1.0; // Empurra o painel de navegação para o fundo
        painelConteudo.add(painelNavegacao, gbc);

        // Adiciona os listeners de ação para os botões
        btnFacil.addActionListener(e -> selecionarDificuldade(Dificuldade.FACIL, btnFacil));
        btnMedio.addActionListener(e -> selecionarDificuldade(Dificuldade.MEDIO, btnMedio));
        btnDificil.addActionListener(e -> selecionarDificuldade(Dificuldade.DIFICIL, btnDificil));
        
        btnVoltar.addActionListener(e -> voltarParaUsuario());
        btnContinuar.addActionListener(e -> continuarParaJogos());
        
        painelPrincipal.add(painelConteudo); // Adiciona o painel com borda ao painel principal
        getContentPane().add(painelPrincipal); // Adiciona o painel principal ao frame
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Cria e estiliza um botão de seleção de dificuldade.
     */
    private JButton createDifficultyButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 80));
        button.setFont(fonteBotoes); // Aplica a fonte customizada
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false); // Impede que o botão seja focado pelo teclado
        resetButtonAppearance(button);
        return button;
    }

    /**
     * Cria e estiliza um botão de navegação (Voltar/Continuar).
     */
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(220, 60));
        button.setFont(fonteBotoes.deriveFont(18f)); // Fonte um pouco menor
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusable(false); // Impede que o botão seja focado pelo teclado
        button.setBackground(COR_PADRAO);
        button.setForeground(COR_TEXTO_PADRAO);
        button.setBorder(new LineBorder(COR_DESTAQUE, 3, true));
        return button;
    }

    /**
     * Atualiza a aparência dos botões de dificuldade quando um é selecionado.
     */
    private void selecionarDificuldade(Dificuldade d, JButton clickedButton) {
        dificuldadeSelecionada = d;
        // Reseta a aparência de todos
        resetButtonAppearance(btnFacil);
        resetButtonAppearance(btnMedio);
        resetButtonAppearance(btnDificil);
        
        // Destaca o botão clicado
        clickedButton.setBackground(COR_DESTAQUE);
        clickedButton.setForeground(COR_TEXTO_DESTAQUE);
    }
    
    /**
     * Restaura a aparência padrão de um botão de dificuldade.
     */
    private void resetButtonAppearance(JButton button) {
        button.setBackground(COR_PADRAO);
        button.setForeground(COR_TEXTO_PADRAO);
        button.setBorder(new LineBorder(COR_DESTAQUE, 3, true));
    }
    
    /**
     * Ação do botão "Voltar": fecha a tela atual e abre a de usuário.
     */
    private void voltarParaUsuario() {
        FUsuario telaUsuario = new FUsuario();
        telaUsuario.setVisible(true);
        this.dispose();
    }
    
    /**
     * Ação do botão "Continuar": abre a tela de seleção de jogos.
     */
    private void continuarParaJogos() {
        if (dificuldadeSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma dificuldade para continuar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FSelectJogos telaJogos = new FSelectJogos(nomeHeroi, dificuldadeSelecionada);
        telaJogos.setVisible(true);
        this.dispose();
    }
}