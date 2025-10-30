package model.minigames;

import model.EstruturaDados;
import model.Jogo;
import model.Dificuldade;

import controller.GameTimer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class OrdenaNumeros extends Jogo {

    private List<Integer> numeros;         // lista que o jogador manipula
    private List<Integer> numerosCorretos; // lista ordenada (resposta correta)
    private int posicaoAtual;
    private Integer numeroSegurado;
    private GameTimer timer;
    
    public OrdenaNumeros(Dificuldade dificuldade) {
        super("OrdenaNumeros", dificuldade, EstruturaDados.LISTA, 
              "Jogo de Ordenação de Números", 
              "O jogador deve organizar os números em ordem crescente.");

        numeros = new ArrayList<>();
        numerosCorretos = new ArrayList<>();
        posicaoAtual = 0;
        numeroSegurado = null;
        timer = new GameTimer();
        
        gerarNumeros();
    }

    private void gerarNumeros() {
        int quantidade = 0;
        
        switch (dificuldade) {
            case FACIL: quantidade = 5;
            case MEDIO: quantidade = 8;
            case DIFICIL: quantidade = 12;
            default: quantidade = 5;
        };

        Random random = new Random();
        List<Integer> base = new ArrayList<>();

        // gera números aleatórios sem repetir
        while (base.size() < quantidade) {
            int n = random.nextInt(99) + 1; // entre 1 e 99
            if (!base.contains(n)) {
                base.add(n);
            }
        }

        // copia a versão ordenada (resposta certa)
        numerosCorretos = new ArrayList<>(base);
        Collections.sort(numerosCorretos);

        // embaralha para criar o desafio
        numeros = new ArrayList<>(base);
        Collections.shuffle(numeros);
    }

    @Override
    public void iniciar() {
        System.out.println("=== Ordene os números em ordem crescente ===");
        System.out.println("Use as setas para mover e a tecla espaço para pegar/soltar o número.");
        timer.iniciar();
        exibirNumeros();
    }
    
    public double getTempoDecorrido() {
        return timer.getTime();
    }

    public void resetarTimer() {
        timer.iniciar();
    }

    public void exibirNumeros() {
        for (int i = 0; i < numeros.size(); i++) {
            if (i == posicaoAtual) {
                System.out.print("[" + numeros.get(i) + "] ");
            } else {
                System.out.print(numeros.get(i) + " ");
            }
        }

        if (numeroSegurado != null) {
            System.out.print("   (Segurando: " + numeroSegurado + ")");
        }
        System.out.println();
    }

    @Override
    public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
        // Se o jogo não estiver ordenado, a pontuação é 0.
        if (!estaOrdenado()) {
            this.pontuacao = 0;
            return 0;
        }

        // --- FIX 1: O tempo não estava sendo medido corretamente ---
        // A pontuação base será inversamente proporcional ao tempo.
        // Menos tempo = mais pontos. Usamos 10000 como um valor base.
        double tempo = getTempoDecorrido();
        int pontuacaoBase = Math.max(0, 10000 - ((int) tempo * 100)); // Penaliza 100 pontos por segundo

        // Adiciona um multiplicador de dificuldade
        double multiplicador = 1.0;
        switch (dificuldade) {
            case MEDIO:
                multiplicador = 1.5;
                break;
            case DIFICIL:
                multiplicador = 2.0;
                break;
            case FACIL:
            default:
                multiplicador = 1.0;
                break;
        }

        // --- FIX 2: A pontuação final deve ser armazenada na variável da classe ---
        this.pontuacao = (int) (pontuacaoBase * multiplicador);
        
        return this.pontuacao;
    }

    @Override
    public EstruturaDados getEstruturaAssociada() {
        return this.estrutura;
    }

    public boolean estaOrdenado() {
        return numeros.equals(numerosCorretos);
    }

    // --- Ações do jogador (iguais ao CacaPalavra) ---

    public void moverPonteiro(int direcao) {
        if (numeros.isEmpty())
            return;

        posicaoAtual += direcao;
        if (posicaoAtual < 0) {
            posicaoAtual = numeros.size() - 1;
        }

        if (posicaoAtual >= numeros.size()) {
            posicaoAtual = 0;
        }
    }

    public void puxarNumero() {
        if (numeros.isEmpty())
            return;

        numeroSegurado = numeros.remove(posicaoAtual);

        if (posicaoAtual >= numeros.size() && posicaoAtual > 0) {
            posicaoAtual--;
        }
    }

    public void inserirNumero() {
        if (numeroSegurado == null)
            return;

        int posInserir = posicaoAtual;
        if (posicaoAtual == numeros.size() - 1)
            posInserir = numeros.size();

        numeros.add(posInserir, numeroSegurado);
        numeroSegurado = null;
    }

    // getters e setters
    public List<Integer> getNumeros() { return numeros; }
    public List<Integer> getNumerosCorretos() { return numerosCorretos; }
    public int getPosicaoAtual() { return posicaoAtual; }
    public void setPosicaoAtual(int posicaoAtual) { this.posicaoAtual = posicaoAtual; }
    public Integer getNumeroSegurado() { return numeroSegurado; }
    public void setNumeroSegurado(Integer numeroSegurado) { this.numeroSegurado = numeroSegurado; }

}
