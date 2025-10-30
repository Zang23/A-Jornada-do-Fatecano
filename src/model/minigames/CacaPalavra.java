package model.minigames;

import model.EstruturaDados;

import model.Jogo;
import model.Dificuldade;

import java.awt.BufferCapabilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CacaPalavra extends Jogo {

	private List<String> letras;
	private int posicaoAtual;
	private String letraSegurada;
	private String palavraCorreta;

	public CacaPalavra(Dificuldade dificuldade) {

		super("CacaPalavra", dificuldade, EstruturaDados.LISTA, "Jogo simples de Caca Palavras",
				"O usuario deve descobrir a ordem certa para encontrar a palavra");
		// Adicione aqui outros parametros iniciais necessarios

		letras = new ArrayList<>();
		String palavra = null;
		try {
			palavra = geraPalavra();
		} catch (IOException e) {
			e.printStackTrace();
		}

		palavraCorreta = palavra;
		
		palavra = espalhaPalavra(palavra);
		char[] vetPalavra = palavra.toCharArray();

		for (char c : vetPalavra) {
			letras.add(String.valueOf(c));
		}

		posicaoAtual = 0;
		letraSegurada = null;

	}
	
	@Override
	public void iniciar() {
		if (dificuldade == Dificuldade.DIFICIL) {
			System.out.println("Modo dificil...");
		}

	}

	@Override
	public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
	    // Se a palavra não estiver correta, a pontuação é zero.
	    if (!isRespostaCerta()) {
	        this.pontuacao = 0;
	        return 0;
	    }
	    
	    // Pontuação base: 100 pontos por letra na palavra correta.
	    int pontuacaoBase = getPalavraCorreta().length() * 100;
	    
	    // Adiciona um multiplicador de dificuldade
	    double multiplicador = 1.0;
	    switch (dificuldade) {
	        case MEDIO:
	            multiplicador = 1.5;
	            break;
	        case DIFICIL:
	            multiplicador = 2.0;
	            break;
	    }
	    
	    // Salva a pontuação final na variável da classe e a retorna.
	    this.pontuacao = (int) (pontuacaoBase * multiplicador);
	    return this.pontuacao;
	}

	@Override
	public EstruturaDados getEstruturaAssociada() {
		return this.estrutura;
	}
	

	private String geraPalavra() throws IOException {
	    InputStream inputStream = getClass().getResourceAsStream("/resources/palavras.txt");

	    if (inputStream == null) {
	        throw new IOException("Arquivo palavras.txt não encontrado no classpath!");
	    }

	    BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
	    String linha = null;

	    int random = (int) (Math.random() * 109);

	    for (int i = 0; i < random; i++) {
	        linha = buffer.readLine();
	        if (linha == null) break;
	    }

	    if (linha == null) {
	    	linha = "SORTUDO"; // fallback
	    }

	    return linha.toUpperCase();
	}

	private String espalhaPalavra(String palavra) {

		List<Character> letras = new ArrayList<>();
		char[] vetPalavra = palavra.toCharArray();
		
		for(char c : vetPalavra) {
			letras.add(c);
		}
		
		Collections.shuffle(letras);
		
		StringBuffer buffer = new  StringBuffer();
		for(char c : letras) {
			buffer.append(c);
		}
		
		if(buffer.toString().equals(palavra)) {
			espalhaPalavra(palavra);
		}
		
		return buffer.toString();
		
	}
	
	public String getPalavraCorreta() {
	    return palavraCorreta;
	}

	public List<String> getLetras() {
		return letras;
	}

	public void setLetras(List<String> letras) {
		this.letras = letras;
	}

	public int getPosicaoAtual() {
		return posicaoAtual;
	}

	public void setPosicaoAtual(int posicaoAtual) {
		this.posicaoAtual = posicaoAtual;
	}

	public String getLetraSegurada() {
		return letraSegurada;
	}

	public void setLetraSegurada(String letraSegurada) {
		this.letraSegurada = letraSegurada;
	}
        
	public boolean isRespostaCerta() {
	    // A linha antiga e incorreta foi removida.
	    // return letras.equals(Arrays.asList(palavraCorreta.toCharArray()));

	    // --- CORREÇÃO ---
	    // 1. Junta todos os elementos da lista 'letras' (que são Strings)
	    //    em uma única String, sem espaços ou separadores.
	    String palavraFormadaPeloJogador = String.join("", letras);
	    
	    // 2. Compara a String que o jogador formou com a palavra correta.
	    //    Esta é uma comparação de String para String, que funciona como esperado.
	    return palavraFormadaPeloJogador.equals(palavraCorreta);
	}
        
	public void moverPonteiro(int direcao) {
		if (letras.isEmpty())
			return;

		posicaoAtual += direcao;
		if (posicaoAtual < 0) {
			posicaoAtual = letras.size() - 1;
		}

		if (posicaoAtual >= letras.size()) {
			posicaoAtual = 0;
		}
	}

	public void puxarLetra() {
		if (letras.isEmpty()) {
			return;
		}

		letraSegurada = letras.remove(posicaoAtual);

		if (posicaoAtual >= letras.size() && posicaoAtual > 0) {
			posicaoAtual--;
		}
	}

	public void inserirLetra() {
		if (letraSegurada == null) {
			return;
		} else {

			int posInserir = posicaoAtual;
			if (posicaoAtual == letras.size() - 1)
				posInserir = letras.size();

			letras.add(posInserir, letraSegurada);
			letraSegurada = null;

		}
	}

}
