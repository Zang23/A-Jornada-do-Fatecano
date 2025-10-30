package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Dificuldade; // Importa o ENUM de Dificuldade
import model.Jogo;
import view.FMinigameHolder;
import view.FSelectJogos;
import view.TelaCacaPalavra;
import view.TelaEscolhaPorta;
import view.TelaOrdenaNumeros;
import view.TelaOrganizePilha;
import view.TelaTorreHanoi;

public class SelectGameController {

    // Mantém o estado de qual jogo está selecionado
    static private int selected = -1; // Valor inicial seguro
    private final String[] NomeJogos = {"Caça Palavras", "Escolha a Porta", "Ordena Números", "Organize a Pilha", "Torre de Hanoi"};

    /**
     * Adiciona um listener a um painel de opção de jogo. Ao clicar, atualiza o
     * título na tela de seleção e armazena o índice do jogo selecionado.
     * (Este método não precisou de alterações).
     */
    public void AddOption(JPanel OptionPanel, JLabel NomeLabel, int OptionInt) {
        OptionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selected = OptionInt;
                NomeLabel.setText(NomeJogos[OptionInt]);
            }
        });
    }

    /**
     * Adiciona o listener ao botão "GO!!!" para iniciar o minigame selecionado.
     * Este método foi modificado para receber a dificuldade e passá-la para
     * a criação dos minijogos.
     *
     * @param SelectOption O botão "GO!!!".
     * @param telaSelect A tela de seleção de jogos, para poder voltar a ela.
     * @param dificuldade A dificuldade (FACIL, MEDIO, DIFICIL) escolhida.
     */
    public void AddSelect(JButton SelectOption, FSelectJogos telaSelect, Dificuldade dificuldade) {
        SelectOption.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                // Se nenhum jogo for selecionado, não faz nada.
                if (selected < 0 || selected >= NomeJogos.length) {
                    System.out.println("Nenhum jogo selecionado!");
                    return;
                }

                // Ação a ser executada quando o minigame fechar (seja por vitória,
                // derrota ou clicando em "Voltar").
                Runnable acaoAposJogo = () -> telaSelect.setVisible(true);

                // Variáveis para guardar o painel do jogo e seu modelo correspondente.
                JPanel painelJogo = null;
                Jogo modeloJogo = null;

                // Cria a view e o model do jogo selecionado, passando a dificuldade escolhida
                switch (selected) {
                    case 0: {
                        TelaCacaPalavra jogoView = new TelaCacaPalavra(dificuldade, acaoAposJogo);
                        painelJogo = jogoView;
                        modeloJogo = jogoView.getModel();
                        break;
                    }
                    case 1: {
                        TelaEscolhaPorta jogoView = new TelaEscolhaPorta(dificuldade, acaoAposJogo);
                        painelJogo = jogoView;
                        modeloJogo = jogoView.getModel();
                        break;
                    }
                    case 2: {
                        TelaOrdenaNumeros jogoView = new TelaOrdenaNumeros(dificuldade, acaoAposJogo);
                        painelJogo = jogoView;
                        modeloJogo = jogoView.getModel();
                        break;
                    }
                    case 3: {
                        TelaOrganizePilha jogoView = new TelaOrganizePilha(dificuldade, acaoAposJogo);
                        painelJogo = jogoView;
                        modeloJogo = jogoView.getModel();
                        break;
                    }
                    case 4: {
                        TorreDeHanoiController controller = new TorreDeHanoiController(dificuldade);
                        TelaTorreHanoi jogoView = new TelaTorreHanoi(controller, acaoAposJogo);
                        painelJogo = jogoView;
                        modeloJogo = jogoView.getModel();
                        break;
                    }
                }

                // Se um painel e um modelo foram criados com sucesso, abre a janela do jogo.
                if (painelJogo != null && modeloJogo != null) {
                    // Passa a ação de retorno para o FMinigameHolder, para ser usada no botão "Voltar"
                    FMinigameHolder tela = new FMinigameHolder(painelJogo, modeloJogo, acaoAposJogo);
                    tela.setTitle(modeloJogo.getNome());
                    tela.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela
                    
                    tela.setVisible(true);
                    telaSelect.setVisible(false); // Esconde a tela de seleção
                }
            }
        });
    }
}