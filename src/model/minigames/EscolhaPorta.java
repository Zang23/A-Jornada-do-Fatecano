package model.minigames;

import model.EstruturaDados;
import model.Jogo;
import model.Dificuldade;

public class EscolhaPorta extends Jogo {

	public EscolhaPorta(Dificuldade dificuldade) {
		super("EscolhaPorta", dificuldade, EstruturaDados.LISTA,
		"Descricao do Jogo",
		"Instrucoes do Jogo");
		// Adicione aqui outros parametros iniciais necessarios
	}

	@Override
	public void iniciar() {
	}

	@Override
	public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
		return 0;
	}

	@Override
	public EstruturaDados getEstruturaAssociada() {
		return this.estrutura;
	}

}
