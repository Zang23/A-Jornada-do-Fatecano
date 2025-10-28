package model.minigames;

import model.EstruturaDados;
import model.Jogo;
import model.Dificuldade;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;

public class EscolhaPorta extends Jogo {

	private No inicio;
    private No fim;
    private int tamanhoCores;
    private int tamanhoPorta;
    private int[] portas;
	private static Timer tempo;
    private static int tempoRestante;
	
	public EscolhaPorta(Dificuldade dificuldade) {
		super("EscolhaPorta", dificuldade, EstruturaDados.LISTA,
		"Combine as cores da fila com a porta correta",
		"A fila sai conforme o jogador faz acertos... Ou erros");
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

	// Fim Parte Gabs

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

    public void inserir(int cor) { // Insere cor á lista
    	
        No novo = new No(cor);
        if (inicio == null) {
            inicio = novo;
            fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
        tamanhoCores++;
        
    }
    
    public No getInicio() { // Pega a primeira cor da fila
        return inicio;
    }

    public int gerarCor() { // Gera a cor
    	
        Random r = new Random();
        int cor;
//      return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256)); // Para caso utilize o import
        cor = r.nextInt(50); // Os números são as cores
        return cor;
        
    }

    public void imprimir() { // Era teste pra fila
        No atual = inicio;
        while (atual != null) {
            int c = atual.getColor();
            System.out.println(c);
            atual = atual.getProximo();
        }
    }

    public int getTamanho() { // Tamanho da fila
        return tamanhoCores;
    }
    
    public void cliqueCerto() { // Pontuação padrão
    	
    	switch (dificuldade) {
        
    	case FACIL -> pontuacao += 10;
        
    	case MEDIO -> pontuacao += 15;
        
    	case DIFICIL -> pontuacao += 20;
    	
     }
    	proximaFila();
    	gerarPortasNovas();
    }
    
    public void cliqueErrado() { // Caso jogador erre a porta
		switch (dificuldade) {
			case FACIL: pontuacao -= 5;
						proximaFila();
						gerarPortasNovas();
			break;
		
			case MEDIO: pontuacao -= 10;
						proximaFila();
						gerarPortasNovas();
			break;
		
			case DIFICIL: pontuacao -= 15;
						  proximaFila();
						  gerarPortasNovas();
			break;
		}
	}
    
    public void modificadorDificuldade() { // Este método cria modificadores de fase baseados na dificuldade
		
		switch (dificuldade) {
		
			case FACIL: tamanhoPorta = 3;
						tamanhoCores = 20;
						tempoRestante = 120;
			break;
			
			case MEDIO: tamanhoPorta = 4;
						tamanhoCores = 30;
						tempoRestante = 90;
			break;
			
			case DIFICIL: tamanhoPorta = 5;
						  tamanhoCores = 40;
						  tempoRestante = 60;
			break;
			
		}
	
	}
    
    public void gerarPortasNovas() { // Este método gera novas portas em caso de acerto
		
		Random r = new Random();
		int cor;
		portas = new int[tamanhoPorta];
		
		for(int i = 0; i < tamanhoPorta; i ++) {
			
			cor = r.nextInt(50);
			portas[i] = cor;
			
		}
			
		if (inicio != null)
			
	        portas[r.nextInt(tamanhoPorta)] = inicio.getColor();
		
	}
    
    public boolean verificarVazio() { // Verifica se a lista está vazia
		if (inicio == fim) {
			pontuacaoFinal();
			return true;
		}
		return false;
	}
	
	public boolean verificarCheio() { // Verifica se a lista está cheia
		if (fim != null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void proximaFila() { // Próxima cor da lista
	    if (inicio != null) {
	        inicio = inicio.getProximo(); 
	        tamanhoCores--;
	    }
	}
	
	public void pontuacaoFinal() { // Score total
		
		switch (dificuldade) {
		
			case FACIL: pontuacao *= 1;
						tempoRestante *= 50;
						pontuacao += tempoRestante;
			break;
		
			case MEDIO: pontuacao *= 1.25;
						tempoRestante *= 100;
						pontuacao += tempoRestante;
			break;
		
			case DIFICIL: pontuacao *= 1.5;
						  tempoRestante *= 150;
						  pontuacao += tempoRestante;
			break;
			
		}
		
	}
	
	public void clique(int portaClicada, MouseEvent e) { // Clique de porta padrão
		
		int corClicada = portas[portaClicada];
		
		if (inicio != null && corClicada == (inicio.getColor())) {
			cliqueCerto();
		} else {
			cliqueErrado();
		}
	}

	public void tempo(ActionEvent e) { // Tempo de jogo
		
		tempo = new Timer(1000, new ActionListener() {
			
		@Override
	    public void actionPerformed(ActionEvent e) {
	    
			tempoRestante--;
			
			if (tempoRestante <= 0) {
				
				tempo.stop();
				System.out.println("Game Over");
				
			}
			
		}
		
		});
		
		tempo.start();
			
	}
	
}
