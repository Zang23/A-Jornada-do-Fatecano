package controller;

import java.util.Stack;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import model.minigames.TorreDeHanoi;
import view.FSelectJogos;
import view.SetActiveBorder;
import view.TelaTorreHanoi;
import model.Dificuldade;

//Controller da Torre de Hanoi :D
//Gerencia o jogo, o tempo e as jogadas do jogador
public class TorreDeHanoiController {

    private TorreDeHanoi jogo;         //Referência para o model (a lógica do jogo)
    private Timer cronometro;          //Timer que controla o tempo
    private int segundosRestantes;     //Tempo que ainda falta
    
    private TorreDeHanoiListener listener;

    private EstadoJogo estadoAtual;    //Estado do jogo (jogando, vitória ou derrota)
    public enum EstadoJogo { JOGANDO, VITORIA, DERROTA }
    
    private Dificuldade dificuldade;

    private Stack<Integer> torreEscolhida; //Guarda a torre que o jogador selecionou

    public TorreDeHanoiController(int difInt) {
        switch (difInt) {
            case 1:
                dificuldade = Dificuldade.FACIL;
                break;
            case 2:
                dificuldade = Dificuldade.MEDIO;
                break;
            case 3:
                dificuldade = Dificuldade.DIFICIL;
                break;
            default:
                dificuldade = Dificuldade.FACIL;
        }
        this.jogo = new TorreDeHanoi(dificuldade);
        iniciarLogicaDoJogo(); //Começa o jogo
    }
    
    // Adicionei o listener baseado na interface TorreDeHanoiListener @GabrielGit10110
    public void setListener(TorreDeHanoiListener listener) {
        this.listener = listener;
    }

    //Inicia o timer e o estado do jogo
    private void iniciarLogicaDoJogo() {
        this.segundosRestantes = jogo.getTempoMaximo();
        this.estadoAtual = EstadoJogo.JOGANDO;
        this.torreEscolhida = null;
        iniciarTimer();
    }

    //Cria e inicia o cronômetro do jogo
    private void iniciarTimer() {
        ActionListener acao = e -> {
            segundosRestantes--;
            if (segundosRestantes <= 0) {
                cronometro.stop();
                this.estadoAtual = EstadoJogo.DERROTA;
            }
        };
        cronometro = new Timer(1000, acao); //Dispara a cada 1 segundo
        cronometro.start();
    }

    //MÉTODOS PÚBLICOS USADOS PELA VIEW

    //Processa a escolha e movimento de uma torre
    public void processarJogada(int torre) {
        if (estadoAtual != EstadoJogo.JOGANDO) return;

        Stack<Integer> torreAlvo = pegarTorrePorNumero(torre);
        if (torreAlvo == null) return;

        if (torreEscolhida == null) {
            if (!torreAlvo.isEmpty()) {
                torreEscolhida = torreAlvo;
            }
        } else {
            jogo.moverDisco(torreEscolhida, torreAlvo);
            if (listener != null) listener.atualizarTela();
            torreEscolhida = null; //Deseleciona depois de mover
        }
    }

    //Verifica manualmente se o jogador venceu
    public void verificarManualmenteVitoria() {
        if (estadoAtual != EstadoJogo.JOGANDO) return;

        if (jogo.verificarVitoria()) {
            cronometro.stop();
            jogo.calcularPontuacao(0, segundosRestantes);
            this.estadoAtual = EstadoJogo.VITORIA;
        } else {
            if (listener != null) listener.mostrarMensagem("Ainda não está certo, continue tentando!");
        }
        
        if (listener != null) listener.jogoEncerrado(estadoAtual);
    }

    //Reinicia completamente o jogo
    public void reiniciarJogo() {
        if (cronometro.isRunning()) {
            cronometro.stop();
        }
        this.jogo = new TorreDeHanoi(jogo.getDificuldade());
        iniciarLogicaDoJogo();
    }

    //GETTERS PARA A VIEW

    public TorreDeHanoi getModel() {
        return jogo;
    }

    public int getTempoRestante() {
        return segundosRestantes;
    }

    public EstadoJogo getGameState() {
        return estadoAtual;
    }

    public Stack<Integer> getTorreSelecionada() {
        return torreEscolhida;
    }

    //MÉTODOS INTERNOS

    private Stack<Integer> pegarTorrePorNumero(int numero) {
        switch (numero) {
            case 1: return jogo.getTorreOrigem();
            case 2: return jogo.getTorreAuxiliar();
            case 3: return jogo.getTorreDestino();
            default: return null;
        }
    }
    
    	public void retornaSelectJogo() {

		NameController nome = new NameController();
		FSelectJogos telaSelect = new FSelectJogos(nome.getName());
		telaSelect.SetDefautProperties(telaSelect);
		telaSelect.setVisible(true);
		
	}
    //Leo passou aqui
}
