package model.minigames;

import model.Jogo;
import model.Dificuldade;
import model.EstruturaDados;

public class TorreHanoi extends Jogo {
	public TorreHanoi(Dificuldade dificuldade) {
		super("TorreHanoi", dificuldade, EstruturaDados.PILHA,
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
