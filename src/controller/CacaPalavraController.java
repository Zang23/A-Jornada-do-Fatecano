package controller;

import model.minigames.CacaPalavra;

public class CacaPalavraController {
    private CacaPalavra jogo;

    public CacaPalavraController(CacaPalavra jogo) {
        this.jogo = jogo;
    }

    public void moverEsquerda() {
        jogo.moverPonteiro(-1);
    }

    public void moverDireita() {
        jogo.moverPonteiro(1);
    }

    public void acaoEspaco() {
        if (jogo.getLetraSegurada() == null)
            jogo.puxarLetra();
        else
            jogo.inserirLetra();
    }

    public CacaPalavra getJogo() {
        return jogo;
    }
}
