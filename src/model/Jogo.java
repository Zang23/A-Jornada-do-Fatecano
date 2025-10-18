// @GabrielEsteveAqui
package model;

public abstract class Jogo {
	protected String nome; // É o nome do jogo uai
	protected Dificuldade dificuldade; // É um enum
	protected EstruturaDados estrutura; // qual estrutura usa
	protected String descricao; // descricao do jogo
	protected String instrucoes; // instrucoes de como jogar
	protected int pontuacao; // pontos dos jogos

	protected Jogo(String nome, Dificuldade dificuldade,
			EstruturaDados estrutura, String descricao,
			String instrucoes) {
		this.nome = nome;
		this.dificuldade = dificuldade;
		this.estrutura = estrutura;
		this.descricao = descricao;
		this.instrucoes = instrucoes;
		this.pontuacao = 0;
	}

	// Getters
	public String getNome() {
		return nome;
	}

	public Dificuldade getDificuldade() {
		return dificuldade;
	}

	public EstruturaDados getEstrutura() {
		return estrutura;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	// metodos que os jogos TEM QUE IMPLEMENTAR

	// aqui vai ficar a logica do jogo pra glr que vai programar e tudo mais
	public abstract void iniciar();

	// aqui os pontos serao calculados baseado em quanto o usuario marcou e
	// em quanto tempo ainda sobra no relógio (além de que a dificuldade vai
	// multiplicar esse valor por uma porcentagem específica)
	public abstract int calcularPontuacao(int pontosMarcados, int tempoSobrando);

	// retorna a estrutura utilizada no jogo
	public abstract EstruturaDados getEstruturaAssociada();
}
