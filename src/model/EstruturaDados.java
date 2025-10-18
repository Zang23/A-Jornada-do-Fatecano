// @GabrielEsteveAqui
package model;

public enum EstruturaDados {
	PILHA("Pilha"), // define o tipo de estrutura
	FILA("Fila"), // a direita fica sua descricao formatada
	LISTA("Lista"); // mais facil pra transformar em String ( ° u °)_b

	private final String descricao;

	EstruturaDados(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
