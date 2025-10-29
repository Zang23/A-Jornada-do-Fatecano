package controller;

import model.minigames.OrdenaNumeros;

public class OrdenaNumerosController {
    private OrdenaNumeros jogo;

    public OrdenaNumerosController(OrdenaNumeros jogo) {
        this.jogo = jogo;
    }

    public void moverEsquerda() {
        jogo.moverPonteiro(-1);
    }

    public void moverDireita() {
        jogo.moverPonteiro(1);
    }

    public void acaoEspaco() {
        if (jogo.getNumeroSegurado() == null)
            jogo.puxarNumero();
        else
            jogo.inserirNumero();
    }

    public boolean verificarOrdenado() {
        return jogo.estaOrdenado();
    }

    public OrdenaNumeros getJogo() {
        return jogo;
    }
}
