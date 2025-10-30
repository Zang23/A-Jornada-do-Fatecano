package controller;

import model.minigames.OrdenaNumeros;

public class OrdenaNumerosController {
    private OrdenaNumeros jogo;
    private GameState gameState; // Adicionado para controlar o estado do jogo

    // Enum para definir os possíveis estados
    public enum GameState { JOGANDO, VITORIA, DERROTA }

    public OrdenaNumerosController(OrdenaNumeros jogo) {
        this.jogo = jogo;
        this.gameState = GameState.JOGANDO; // O jogo começa no estado "JOGANDO"
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
        if (jogo.getNumeroSegurado() == null) {
            jogo.puxarNumero();
        } else {
            jogo.inserirNumero();
        }
    }
    
    /**
     * Lógica executada quando o jogador vence.
     * Altera o estado do jogo e salva a pontuação.
     */
    public void processarVitoria() {
        if (gameState != GameState.JOGANDO) return; // Previne múltiplas chamadas

        this.gameState = GameState.VITORIA; // Altera o estado para VITORIA
        
        // Calcula e registra a pontuação final
        int pontuacaoFinal = jogo.calcularPontuacao(0, 0);
        NameController nameController = new NameController();
        String nomeJogador = nameController.getName();
        String nomeJogo = jogo.getNome();
        ScoreboardController.registrarPontuacao(nomeJogo, nomeJogador, pontuacaoFinal);
        
        System.out.println("Pontuação de " + pontuacaoFinal + " registrada para " + nomeJogador + " em " + nomeJogo);
    }

    /**
     * --- NOVO MÉTODO ---
     * Chamado pela View quando o timer universal se esgota.
     */
    public void tempoEsgotado() {
        this.gameState = GameState.DERROTA;
    }

    // --- NOVO GETTER ---
    public GameState getGameState() {
        return gameState;
    }

    public OrdenaNumeros getJogo() {
        return jogo;
    }
}