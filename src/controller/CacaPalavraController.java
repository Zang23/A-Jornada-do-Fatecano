package controller;

import model.minigames.CacaPalavra;

public class CacaPalavraController {
    
    private CacaPalavra jogo;
    private GameState gameState; // Adicionado para controlar o estado

    // Enum para definir os estados possíveis do jogo
    public enum GameState { JOGANDO, VITORIA, DERROTA }

    public CacaPalavraController(CacaPalavra jogo) {
        this.jogo = jogo;
        this.gameState = GameState.JOGANDO; // O jogo sempre começa jogando
    }

    public void moverEsquerda() {
        if (gameState != GameState.JOGANDO) return; // Só permite mover se estiver jogando
        jogo.moverPonteiro(-1);
    }

    public void moverDireita() {
        if (gameState != GameState.JOGANDO) return; // Só permite mover se estiver jogando
        jogo.moverPonteiro(1);
    }

    public void acaoEspaco() {
        if (gameState != GameState.JOGANDO) return; // Só permite ação se estiver jogando
        if (jogo.getLetraSegurada() == null) {
            jogo.puxarLetra();
        } else {
            jogo.inserirLetra();
        }
    }
    
    /**
     * Centraliza a lógica a ser executada quando o jogador vence.
     * Define o estado para VITORIA e registra a pontuação.
     */
    public void processarVitoria() {
        if (gameState != GameState.JOGANDO) return; // Evita chamadas múltiplas
        
        this.gameState = GameState.VITORIA; // Muda o estado
        
        jogo.calcularPontuacao(0, 0); // Calcula a pontuação final no modelo

        // Registra a pontuação usando o controller padrão
        NameController nameController = new NameController();
        String nomeJogador = nameController.getName();
        String nomeJogo = jogo.getNome();
        int pontuacaoFinal = jogo.getPontuacao();
        ScoreboardController.registrarPontuacao(nomeJogo, nomeJogador, pontuacaoFinal);
    }

    /**
     * --- NOVO MÉTODO ---
     * Chamado pela View quando o timer universal se esgota.
     */
    public void tempoEsgotado() {
        this.gameState = GameState.DERROTA;
    }

    public CacaPalavra getJogo() {
        return jogo;
    }

    /**
     * --- NOVO GETTER ---
     * Retorna o estado atual do jogo.
     */
    public GameState getGameState() {
        return gameState;
    }
}