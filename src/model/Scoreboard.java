// @Gabriel esteve aqui
package model;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
	private List<RegistroPontuacao> registros = new ArrayList<>();

	// adiciona registros com o nome do usuario
	// e quantos pontos ele fez em uma lista de registros
	public void adicionarRegistro(RegistroPontuacao registro) {
		registros.add(registro);
	}

	// pega todos os registros associados a um usuario especifico
	// Joaozinho maconheiro 10 pontos em tal, 20 pontos em y, etc
	public List<RegistroPontuacao> getRegistrosUsuario(Usuario usuario) {
		List<RegistroPontuacao> resultado = new ArrayList<>();
		for (RegistroPontuacao registro : registros) {
			if (registro.getUsuario().equals(usuario)) {
				resultado.add(registro);
			}
		}
		return resultado;
	}

	// pega todos os registros associados a um jogo especifico
	// Torre de Hanoi:
	// Joazinho maconheiro 10 pontos
	// Aninha Safadinha 20 pontos
	
	public List<RegistroPontuacao> getRegistrosJogo(Jogo jogo) {
		List<RegistroPontuacao> resultado = new ArrayList<>();
		for (RegistroPontuacao registro : registros) {
			if (registro.getJogo().equals(jogo)) {
				resultado.add(registro);
			}
		}
		return resultado;
	}

	// pega TODOS os registros sem distincao
	// util pro score final?? Nao sei ainda
	public List<RegistroPontuacao> getRegistros() {
		return registros;
	}
}
