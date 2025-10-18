//@GabrielEsteveAqui
package model;

public class Usuario {
	private String nome;

	public Usuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

	// util pra mudar o nome dps
	public void setNome(String nome) {
		this.nome = nome;
	}
}
