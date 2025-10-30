package view;

import controller.EscolhaPortaController;
import model.Dificuldade;
import model.Jogo;
import model.No;
import model.minigames.EscolhaPorta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaEscolhaPorta extends JPanel implements IGamePanel {

    private static final long serialVersionUID = 1L;

    private EscolhaPortaController controller;
    private EscolhaPorta model;
    private Runnable onGameEndCallback;

    // --- PAINÉIS DA UI ---
    private JPanel pnPortas;
    private JPanel pnFilaChaves;
    private JPanel centerWrapperPanel; // --- NOVO: Painel para centralizar os cadeados ---
    private JLabel lblPontuacao, lblCoresRestantes;
    
    // --- RECURSOS E ESTILO ---
    private final Map<String, ImageIcon> cacheImagensChaves = new HashMap<>();
    private final Map<String, ImageIcon> cacheImagensPortas = new HashMap<>();
    private final Color COR_FUNDO = new Color(40, 42, 54);
    private final Color COR_PAINEL = new Color(68, 71, 90);
    private final Color COR_FONTE = new Color(248, 248, 242);
    private Font fonteJogo;

    public TelaEscolhaPorta(Dificuldade dificuldade, Runnable onGameEndCallback) {
        this.onGameEndCallback = onGameEndCallback;
        this.model = new EscolhaPorta(dificuldade);
        this.controller = new EscolhaPortaController(model);

        carregarFonte();
        carregarImagens();

        setLayout(new BorderLayout(10, 10));
        setBackground(COR_FUNDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- ALTERAÇÃO NA ESTRUTURA DO LAYOUT ---
        // 1. O painel de informações continua no NORTE.
        add(criarPainelInfo(), BorderLayout.NORTH);
        
        // 2. O painel de cadeados (`pnPortas`) agora vai DENTRO do `centerWrapperPanel`.
        this.pnPortas = criarPainelPortas(); // Cria o painel que contém os cadeados (com FlowLayout)
        
        this.centerWrapperPanel = new JPanel(new GridBagLayout()); // Cria o wrapper com GridBagLayout
        this.centerWrapperPanel.setOpaque(false);
        this.centerWrapperPanel.add(pnPortas); // Adiciona o painel dos cadeados ao centro do wrapper
        
        add(centerWrapperPanel, BorderLayout.CENTER); // Adiciona o wrapper ao centro da tela principal

        // 3. A fila de chaves continua no SUL.
        add(criarPainelFila(), BorderLayout.SOUTH);

        // Listener para redimensionar as imagens quando o tamanho da janela muda.
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                atualizarTela();
            }
        });

        setFocusable(true);
    }
    
    // ... (O resto da classe, como finalizarJogo, onTimeUp, etc., permanece igual) ...
    
    // [ COLE O RESTO DO SEU CÓDIGO DA CLASSE AQUI, EXCETO OS MÉTODOS ABAIXO QUE FORAM MODIFICADOS ]

    // --- MÉTODOS DE CONFIGURAÇÃO DA UI ---

    // ... (carregarFonte, carregarImagens, getScaledIcon, criarPainelInfo, criarPainelFila permanecem iguais) ...

    private JPanel criarPainelPortas() {
        // Este método agora só cria o painel interno, sem adicioná-lo à tela principal.
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel.setOpaque(false);
        return panel;
    }
    
    // --- MÉTODO DE ATUALIZAÇÃO DA UI (MODIFICADO) ---

    private void atualizarDesenhoPortas() {
        pnPortas.removeAll();
        
        // --- CORREÇÃO PRINCIPAL: Calcula o tamanho com base no wrapper ---
        // Se o painel wrapper ainda não tem altura, não faz nada.
        if (centerWrapperPanel.getHeight() == 0) return;

        // Calcula a altura dos cadeados como 50% da altura disponível no painel central.
        int doorHeight = (int) (centerWrapperPanel.getHeight() * 0.5);
        // Limita o tamanho para não ficar nem muito pequeno, nem absurdamente grande.
        doorHeight = Math.max(80, Math.min(doorHeight, 200));

        List<String> cores = model.getCoresDasPortas();
        for (int i = 0; i < cores.size(); i++) {
            final int index = i;
            String nomeAsset = cores.get(i);
            ImageIcon originalIcon = cacheImagensPortas.get(nomeAsset);
            
            ImageIcon scaledIcon = getScaledIconWithAspectRatio(originalIcon, doorHeight);
            
            JLabel portaLabel = new JLabel(scaledIcon);
            portaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            portaLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (controller.getGameState() == EscolhaPortaController.GameState.JOGANDO) {
                        controller.portaClicada(index);
                        
                        if (controller.getGameState() == EscolhaPortaController.GameState.VITORIA) {
                            finalizarJogo(true);
                        } else {
                            atualizarTela();
                        }
                    }
                }
            });
            pnPortas.add(portaLabel);
        }
        pnPortas.revalidate();
        pnPortas.repaint();
    }
    
    // ... (o resto da classe: atualizarDesenhoFila e atualizarTela permanecem iguais) ...
    // [ COLE O RESTO DO SEU CÓDIGO DA CLASSE AQUI ]
    
    // Deixo aqui os métodos que não mudaram para referência:
    
    @Override
    public Jogo getModel() {
        return this.model;
    }

    @Override
    public void onTimeUp() {
        controller.tempoEsgotado();
        finalizarJogo(false);
    }
    
    private void finalizarJogo(boolean vitoria) {
        for(Component comp : pnPortas.getComponents()){
            for(MouseListener ml : comp.getMouseListeners()){
                comp.removeMouseListener(ml);
            }
            comp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        
        String mensagem;
        if (vitoria) {
            mensagem = "Parabéns, você abriu todas as portas!\nPontuação Final: " + model.getPontuacao();
        } else {
            mensagem = "O tempo acabou!\nVocê não conseguiu abrir todas as portas a tempo.";
        }
        
        JOptionPane.showMessageDialog(this, mensagem, "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
        
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
    
    private void carregarFonte() {
        try {
            fonteJogo = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/assets/fonts/BoldPixels.ttf")).deriveFont(24f);
        } catch (Exception e) {
            System.err.println("Fonte não encontrada, usando SansSerif.");
            fonteJogo = new Font("SansSerif", Font.BOLD, 22);
        }
    }
    
    private void carregarImagens() {
        String[] cores = {"black", "blue", "crimson", "green", "purple", "red", "yellow"};
        
        for (String cor : cores) {
            String keyPath = "/resources/assets/sprites/keys/key-" + cor + ".png";
            try {
                cacheImagensChaves.put(cor, new ImageIcon(getClass().getResource(keyPath)));
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem: " + keyPath);
            }

            String lockName = cor.equals("purple") ? "purple-lock.png" : cor + "-lock.png";
            String lockPath = "/resources/assets/sprites/locks/" + lockName;
             try {
                cacheImagensPortas.put(cor, new ImageIcon(getClass().getResource(lockPath)));
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem: " + lockPath);
            }
        }
    }
    
    private ImageIcon getScaledIconWithAspectRatio(ImageIcon icon, int newHeight) {
        if (icon == null || icon.getIconWidth() <= 0 || newHeight <= 0) {
            return null;
        }
        double aspectRatio = (double) icon.getIconWidth() / icon.getIconHeight();
        int newWidth = (int) (newHeight * aspectRatio);
        
        Image image = icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
    private JPanel criarPainelInfo() {
        JPanel pnInfo = new JPanel(new GridLayout(1, 2, 10, 0));
        pnInfo.setOpaque(false);

        lblPontuacao = new JLabel("Pontos: 0", SwingConstants.LEFT);
        lblCoresRestantes = new JLabel("Chaves: " + model.getCoresRestantes(), SwingConstants.RIGHT);

        for (JLabel label : new JLabel[]{lblPontuacao, lblCoresRestantes}) {
            label.setFont(fonteJogo);
            label.setForeground(COR_FONTE);
            pnInfo.add(label);
        }
        return pnInfo;
    }
    
    private JPanel criarPainelFila() {
        JPanel pnFilaContainer = new JPanel(new BorderLayout());
        pnFilaContainer.setOpaque(false);
        
        pnFilaChaves = new JPanel();
        pnFilaChaves.setLayout(new BoxLayout(pnFilaChaves, BoxLayout.X_AXIS));
        pnFilaChaves.setBackground(COR_PAINEL);
        pnFilaChaves.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(pnFilaChaves);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        pnFilaContainer.setPreferredSize(new Dimension(0, 100));

        pnFilaContainer.add(scrollPane, BorderLayout.CENTER);
        return pnFilaContainer;
    }
    
    private void atualizarDesenhoFila() {
        pnFilaChaves.removeAll();
        No<String> noAtual = model.getInicioFilaCores();

        int keyHeight = 50;
        
        boolean isFirst = true;
        while (noAtual != null) {
            String nomeAsset = noAtual.getData();
            ImageIcon originalIcon = cacheImagensChaves.get(nomeAsset);

            ImageIcon scaledIcon = getScaledIconWithAspectRatio(originalIcon, keyHeight);
            
            JLabel chaveLabel = new JLabel(scaledIcon);
            
            if(isFirst) {
                chaveLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
                isFirst = false;
            } else {
                 chaveLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            }

            pnFilaChaves.add(chaveLabel);
            pnFilaChaves.add(Box.createRigidArea(new Dimension(10, 0)));
            noAtual = noAtual.getProximo();
        }
        
        pnFilaChaves.revalidate();
        pnFilaChaves.repaint();
    }
    
    private void atualizarTela() {
        SwingUtilities.invokeLater(() -> {
            atualizarDesenhoPortas();
            atualizarDesenhoFila();
            lblPontuacao.setText("Pontos: " + model.getPontuacao());
            lblCoresRestantes.setText("Chaves: " + model.getCoresRestantes());
        });
    }
}