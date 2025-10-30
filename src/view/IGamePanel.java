package view;

import model.Jogo;

/**
 * Interface que define um contrato para todos os painéis de minigame.
 * Garante que eles possam ser notificados sobre o fim do tempo e que possam
 * fornecer seu modelo de dados.
 */
public interface IGamePanel {

    /**
     * Método chamado pelo container do jogo quando o tempo se esgota.
     */
    void onTimeUp();

    /**
     * Retorna a instância do modelo (lógica) do jogo associado a este painel.
     * @return O objeto Jogo.
     */
    Jogo getModel();
}