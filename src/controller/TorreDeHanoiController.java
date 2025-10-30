package controller;

import java.util.Stack;
import model.minigames.TorreDeHanoi;
import model.Dificuldade;

public class TorreDeHanoiController {

    private TorreDeHanoi jogo;
    private TorreDeHanoiListener listener;
    private EstadoJogo estadoAtual;
    public enum EstadoJogo { JOGANDO, VITORIA, DERROTA }
    private Dificuldade dificuldade; // Mantém a dificuldade
    private Stack<Integer> torreDeOrigem;
    private Integer discoSegurado;

    /**
     * --- CONSTRUTOR MODIFICADO ---
     * Agora recebe diretamente o enum Dificuldade, em vez de um inteiro.
     * Isso resolve o erro de compilação.
     */
    public TorreDeHanoiController(Dificuldade dificuldade) {
        this.dificuldade = dificuldade; // Armazena a dificuldade recebida
        this.jogo = new TorreDeHanoi(this.dificuldade); // Cria o modelo com a dificuldade correta
        iniciarLogicaDoJogo();
    }
    
    public void setListener(TorreDeHanoiListener listener) {
        this.listener = listener;
    }

    private void iniciarLogicaDoJogo() {
        this.estadoAtual = EstadoJogo.JOGANDO;
        this.torreDeOrigem = null;
        this.discoSegurado = null;
    }

    /**
     * Chamado pela View (via FMinigameHolder) quando o timer universal se esgota.
     * Altera o estado do jogo para DERROTA e notifica a View.
     */
    public void tempoEsgotado() {
        if (this.estadoAtual == EstadoJogo.JOGANDO) {
            this.estadoAtual = EstadoJogo.DERROTA;
            if (listener != null) {
                listener.jogoEncerrado(estadoAtual); // Notifica a tela para exibir a mensagem de fim de jogo
            }
        }
    }

    public void processarJogada(int torre) {
        if (estadoAtual != EstadoJogo.JOGANDO) return;

        Stack<Integer> torreAlvo = pegarTorrePorNumero(torre);
        if (torreAlvo == null) return;

        if (discoSegurado == null) { // Se não estiver segurando um disco, tenta pegar um
            if (!torreAlvo.isEmpty()) {
                discoSegurado = torreAlvo.pop();
                torreDeOrigem = torreAlvo;
            }
        } else { // Se estiver segurando um disco
            if (torreAlvo == torreDeOrigem) { // Clicou na mesma torre para devolver o disco
                torreDeOrigem.push(discoSegurado);
                discoSegurado = null;
                torreDeOrigem = null;
            } else { // Tentando mover para uma torre diferente
                if (torreAlvo.isEmpty() || torreAlvo.peek() > discoSegurado) {
                    torreAlvo.push(discoSegurado);
                    jogo.aumentarMovimentos();
                    discoSegurado = null;
                    torreDeOrigem = null;
                    verificarVitoriaAutomaticamente();
                } else {
                    if (listener != null) listener.mostrarMensagem("Movimento inválido! Não se pode colocar um disco maior sobre um menor.");
                }
            }
        }
        
        if (listener != null) listener.atualizarTela();
    }
    
    private void verificarVitoriaAutomaticamente() {
        if (jogo.verificarVitoria()) {
            this.estadoAtual = EstadoJogo.VITORIA;
            jogo.calcularPontuacao(0, 0);

            // Registra a pontuação no scoreboard
            NameController nameController = new NameController();
            String nomeJogador = nameController.getName();
            String nomeJogo = jogo.getNome();
            int pontuacaoFinal = jogo.getPontuacao();
            ScoreboardController.registrarPontuacao(nomeJogo, nomeJogador, pontuacaoFinal);

            if (listener != null) listener.jogoEncerrado(estadoAtual);
        }
    }

    public void reiniciarJogo() {
        this.jogo = new TorreDeHanoi(dificuldade);
        iniciarLogicaDoJogo();
        if (listener != null) listener.atualizarTela();
    }

    // --- GETTERS ---
    public TorreDeHanoi getModel() { return jogo; }
    public EstadoJogo getGameState() { return estadoAtual; }
    public Stack<Integer> getTorreSelecionada() { return torreDeOrigem; }
    public Integer getDiscoSegurado() { return discoSegurado; }

    private Stack<Integer> pegarTorrePorNumero(int numero) {
        switch (numero) {
            case 1: return jogo.getTorreOrigem();
            case 2: return jogo.getTorreAuxiliar();
            case 3: return jogo.getTorreDestino();
            default: return null;
        }
    }
}