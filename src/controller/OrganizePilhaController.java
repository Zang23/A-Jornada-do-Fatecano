package controller;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import model.minigames.OrganizePilha;

public class OrganizePilhaController {

    private OrganizePilha model; //Onde ficam as funções e atributos manipulados pela nossa querida controller
    private Timer gameTimer; //Ele é um objeto que controla o timer, e ele DISPARA um evento a cada segundo
    private int tempoRestante; //própria cópia do tempo restante para ser usado, ele pega inicialmente do model com base na dificuldade e salva apenas o valor máximo, 
    //a alteração é feita aqui dentro desse valor interno
    
    private GameState gameState;
    public enum GameState { JOGANDO, VITORIA, DERROTA } //Enum é muito útil para entender bem as coisas, valeu Gabriel!
    //Aqui é só pra não usar 0, 1, 2, ai fica bonitinho, aqui a controller precisa saber o estado de jogo para ser
    //independente da view

    //O Construtor, ele recebe a instância do Model que deve controlar, e assim que o Controller é criado, ele chama na hora a função iniciarLogicaDoJogo() para dar partida em tudo
    public OrganizePilhaController(OrganizePilha model) {
        this.model = model;
        iniciarLogicaDoJogo(); //ja começa o jogo!
    }
    
    private void iniciarLogicaDoJogo() {
        this.tempoRestante = model.getTempoMaximo(); //Pega o tempo com base na dificuldade, que está lá na model, e deve ser defininida na VIEW.
        this.gameState = GameState.JOGANDO; //Muda o estado de jogo, simplesmente
        iniciarTimer(); //Inicia o timer
    }

    private void iniciarTimer() {
    	// É criada uma "ação" a ser executada. O e -> { ... } é uma forma moderna em Java (lambda) de escrever o que o timer deve fazer a cada "tick" - ChatGPT
    	//Resumindo, ele faz um loop a cada tick de tempo, e a cada tick ele dispara a ação, foi isso o que eu entendi pelo menos.
        ActionListener acaoDoTimer = e -> {
            tempoRestante--; //Diminui o tempo
            if (tempoRestante <= 0) { //Se o tempo acabar, para o jogo
                gameTimer.stop();
                this.gameState = GameState.DERROTA; //Atualiza o estado para derrota, a view deve mostrar a imagem de derrota observando o gamestate.
            }
        };
        gameTimer = new Timer(1000, acaoDoTimer); //Aqui ele cria o timer, e a cada 1000 ms (milisegundos, igual a 1 segundo, ele dispara o acaoDoTimer)
        gameTimer.start(); //Inicia o timer
    }

    // --- MÉTODOS PÚBLICOS PARA A VIEW USAR ---

    
    //Move da pilha do jogador para o inventário se a pilha do jogador não estiver vazia
    public void executarAcaoPilhaParaInventario() {
        if (gameState != GameState.JOGANDO) return;
        if (!model.getPilhaJogador().isEmpty()) {
            model.moverParaInventario();
            verificarCondicaoDeVitoria(); //verifica se ganhou
        }
    }
    
    
//Move do inventário do jogador para a pilha do jogador
    public void executarAcaoInventarioParaPilha() {
        if (gameState != GameState.JOGANDO) return;
        if (!model.getInventario().isEmpty()) {
            model.moverParaPilha();
            verificarCondicaoDeVitoria();
        }
    }
    
    //Recomeça o jogo
    public void reiniciarJogo() { //Opcional
    	
        if (gameTimer.isRunning()) { //verifica se o timer está correndo
            gameTimer.stop(); //para o timer
        }
        this.model = new OrganizePilha(model.getDificuldade());
        iniciarLogicaDoJogo();
    }

    // --- MÉTODOS DE CONSULTA (GETTERS) PARA A VIEW ---
    
    public OrganizePilha getModel() {
        return model;
    }
    
    public int getTempoRestante() {
        return tempoRestante;
    }

    public GameState getGameState() {
        return gameState;
    }

    // --- LÓGICA INTERNA ---

    private void verificarCondicaoDeVitoria() {
        if (model.verificarVitoria()) { //se for true
            gameTimer.stop();
            model.calcularPontuacao(100, tempoRestante);
            this.gameState = GameState.VITORIA;
        }
    }
}