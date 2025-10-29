package model.minigames;

import java.util.Stack;
import model.Dificuldade;
import model.EstruturaDados;
import model.Jogo;

//Jogo da Torre de Hanoi :D
//O objetivo é mover todos os discos da torre inicial para a torre final, usando a torre do meio como apoio.
public class TorreDeHanoi extends Jogo {

    private Stack<Integer> torreA;   //Torre de origem
    private Stack<Integer> torreB;   //Torre auxiliar
    private Stack<Integer> torreC;   //Torre de destino

    private final int totalDiscos = 4; //Quantidade fixa de discos
    private int qtdMovimentos;         //Contador de movimentos
    private int limiteTempo;           //Tempo máximo permitido

    public TorreDeHanoi(Dificuldade dificuldade) {
        super("Torre de Hanoi",
              dificuldade,
              EstruturaDados.PILHA,
              "Mova todos os discos da primeira torre para a última.",
              "Use os números 1, 2 e 3 para mover os discos. Regras: só pode mover um disco por vez, e nunca coloque um disco maior sobre um menor.");

        configurarDificuldade(); //Define o tempo máximo conforme a dificuldade
        iniciar(); //Inicia o jogo
    }

    //Configura o tempo limite de acordo com a dificuldade escolhida
    private void configurarDificuldade() {
        switch (dificuldade) {
            case MEDIO:
                this.limiteTempo = 75; //1 minuto e 15 segundos
                break;
            case DIFICIL:
                this.limiteTempo = 45; //45 segundos
                break;
            case FACIL:
            default:
                this.limiteTempo = 120; //2 minutos
                break;
        }
    }

    @Override
    public void iniciar() {
        torreA = new Stack<>();
        torreB = new Stack<>();
        torreC = new Stack<>();
        qtdMovimentos = 0;

        //Coloca todos os discos na torre A (do maior para o menor)
        for (int i = totalDiscos; i >= 1; i--) {
            torreA.push(i);
        }
    }

    //Faz a movimentação de um disco entre duas torres
    public boolean moverDisco(Stack<Integer> origem, Stack<Integer> destino) {
        if (origem.isEmpty()) {
            return false; //Não tem disco pra mover
        }

        if (destino.isEmpty() || destino.peek() > origem.peek()) {
            destino.push(origem.pop());
            qtdMovimentos++;
            return true;
        }

        return false; //Movimento inválido (disco maior sobre menor)
    }

    //Verifica se todos os discos estão na torre de destino
    public boolean verificarVitoria() {
        return torreC.size() == totalDiscos;
    }

    @Override
    public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
        //Número mínimo de movimentos para 4 discos = 15 (2^4 - 1)
        int movimentosIdeais = (int) Math.pow(2, totalDiscos) - 1;

        int fatorPenalidadePorMovimento; 
        switch (dificuldade) {
            case MEDIO:
                fatorPenalidadePorMovimento = 75;
                break;
            case DIFICIL:
                fatorPenalidadePorMovimento = 125;
                break;
            case FACIL:
            default:
                fatorPenalidadePorMovimento = 50;
                break;
        }
        
        int penalidade = Math.max(0, (qtdMovimentos - movimentosIdeais)) * fatorPenalidadePorMovimento;
        //Ou seja, se a pessoa fizer mais movimentos do que o necessário, ela perde pontos HUAHUAHAUHAH

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

        this.pontuacao = (int) (((5000 - penalidade) + (tempoSobrando * 10)) * multiplicador);
        return Math.max(0, this.pontuacao); //Nunca vai deixar a pontuação ficar negativa, mas seria engraçado
    }

    @Override
    public EstruturaDados getEstruturaAssociada() {
        return this.estrutura;
    }

    //Getters usados pela interface e controladores
    public Stack<Integer> getTorreOrigem() { return torreA; }
    public Stack<Integer> getTorreAuxiliar() { return torreB; }
    public Stack<Integer> getTorreDestino() { return torreC; }
    public int getMovimentos() { return qtdMovimentos; }

    //Retorna o tempo máximo configurado
    public int getTempoMaximo() {
        return limiteTempo;
    }
    //leo Passou aqui
}