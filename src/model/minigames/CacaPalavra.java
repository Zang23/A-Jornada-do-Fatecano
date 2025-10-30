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

import controller.GameTimer;

public class CacaPalavra extends Jogo {

	private List<String> letras;
	private int posicaoAtual;
	private String letraSegurada;
	private String palavraCorreta;
	private GameTimer timer;
	
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
		
		timer = new GameTimer();
	    timer.iniciar();

	}
	
	@Override
	public void iniciar() {
		if (dificuldade == Dificuldade.DIFICIL) {
			System.out.println("Modo dificil...");
		}

	}
	
	public double getTempoDecorrido() {
        return timer.getTime();
    }

    public void resetarTimer() {
        timer.iniciar();
    }

	@Override
	public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
	    int acertos = 0;

	    // Conta letras corretas na posição certa
	    for (int i = 0; i < letras.size(); i++) {
	        char letraAtual = letras.get(i).charAt(0);
	        char letraCorreta = palavraCorreta.charAt(i);
	        if (letraAtual == letraCorreta) {
	            acertos += 10;
	        }
	    }

	    // Se a palavra estiver completamente correta
	    if (isRespostaCerta()) {
	        double tempo = getTempoDecorrido(); // herdado de Jogo
	        int resultado = (int) (tempo * acertos);

	        // multiplicador por dificuldade
	        if (dificuldade.equals(Dificuldade.FACIL)) {
	            resultado *= 1.02;
	        } else if (dificuldade.equals(Dificuldade.MEDIO)) {
	            resultado *= 1.05;
	        } else if (dificuldade.equals(Dificuldade.DIFICIL)) {
	            resultado *= 1.10;
	        }

	        return resultado;
	    }

	    return acertos;
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
	    StringBuilder palavraAtual = new StringBuilder();
	    for (String letra : letras) {
	        palavraAtual.append(letra);
	    }
	    return palavraAtual.toString().equalsIgnoreCase(palavraCorreta);
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
