package model.minigames;

import model.EstruturaDados;
import model.Jogo;
import model.Dificuldade;

public class CacaPalavra extends Jogo {

	public CacaPalavra(Dificuldade dificuldade) {
		super("CacaPalavra", dificuldade, EstruturaDados.LISTA,
		"Jogo simples de Caca Palavras",
		"O usuario deve descobrir a ordem certa para encontrar a palavra");
		// Adicione aqui outros parametros iniciais necessarios
	}

	@Override
	public void iniciar() {
		if (dificuldade == Dificuldade.DIFICIL) {
			System.out.println("Modo dificil...");
		}

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
