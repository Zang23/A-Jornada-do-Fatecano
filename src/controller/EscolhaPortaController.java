package controller;

// javax.swing.Timer não é mais necessário
import model.minigames.EscolhaPorta;

public class EscolhaPortaController {

    private EscolhaPorta model;
    
    private GameState gameState;
    // O estado DERROTA foi removido, pois não há mais como perder pelo tempo
    public enum GameState { JOGANDO, VITORIA, DERROTA}

    public EscolhaPortaController(EscolhaPorta model) {
        this.model = model;
        iniciarLogicaDoJogo();
    }
    
    private void iniciarLogicaDoJogo() {
        // A lógica do timer foi removida
        this.gameState = GameState.JOGANDO;
    }

    // O método iniciarTimer() foi completamente removido.

    // --- MÉTODOS PÚBLICOS PARA A VIEW USAR ---
    
    public void tempoEsgotado() {
        this.gameState = GameState.DERROTA;
    }

    public void portaClicada(int indicePorta) {
        if (gameState != GameState.JOGANDO) return;

        model.verificarClique(indicePorta);
        model.avancarFila();
        
        verificarCondicaoDeVitoria();

        if (gameState == GameState.JOGANDO) {
            model.gerarCoresDasPortas();
        }
    }
    
    public void reiniciarJogo() {
        // A verificação do timer foi removida
        this.model = new EscolhaPorta(model.getDificuldade());
        iniciarLogicaDoJogo();
    }
    
    // --- LÓGICA INTERNA E GETTERS ---

    private void verificarCondicaoDeVitoria() {
        if (model.isFilaVazia()) {
            // A chamada para parar o timer foi removida
            // O parâmetro de tempo restante foi removido do cálculo de pontuação
            model.calcularPontuacao(model.getPontuacao(), 0); 
            this.gameState = GameState.VITORIA;
            registrarPontuacaoNoScoreboard();
        }
    }
    
    private void registrarPontuacaoNoScoreboard() {
        NameController nameController = new NameController();
        String nomeJogador = nameController.getName();
        String nomeJogo = model.getNome();
        int pontuacaoFinal = model.getPontuacao();
        ScoreboardController.registrarPontuacao(nomeJogo, nomeJogador, pontuacaoFinal);
    }

    public EscolhaPorta getModel() {
        return model;
    }
    
    // O método getTempoRestante() foi removido.

    public GameState getGameState() {
        return gameState;
    }
}