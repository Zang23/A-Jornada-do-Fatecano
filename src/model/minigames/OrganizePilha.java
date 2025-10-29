package model.minigames;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Arrays;
import java.awt.Color;

import model.Dificuldade;
import model.EstruturaDados;
import model.Jogo;

public class OrganizePilha extends Jogo {

    private Stack<Color> pilhaMeta;
    private Stack<Color> pilhaJogador;
    private Queue<Color> inventario;

    private int numeroItens;
    private int tempoMaximo; // em segundos

    public OrganizePilha(Dificuldade dificuldade) {
        super("Organize a Pilha",
              dificuldade,
              EstruturaDados.PILHA,
              "Ordene uma pilha de blocos coloridos para que fique igual à pilha de exemplo.",
              "Clique com o botão esquerdo para mover um item da sua pilha para o inventário (Fila). Clique com o botão direito para mover o primeiro item do inventário de volta para a sua pilha.");
        
        configurarDificuldade();
        iniciar();
    }

    private void configurarDificuldade() { //Observação: Para pode adicionar mais itens do que 8, mudar o vetor de cores dentro do 
    	//iniciar(), só adicionar mais cores :)
        switch (dificuldade) {
            case MEDIO:
                this.numeroItens = 6;
                this.tempoMaximo = 90;
                break;
            case DIFICIL:
                this.numeroItens = 8;
                this.tempoMaximo = 60;
                break;
            case FACIL:
            default:
                this.numeroItens = 4;
                this.tempoMaximo = 120;
                break;
        }
    }

    @Override
    public void iniciar() {
    	
    	//Limpar para não começar tudo bagunçado
    	
        pilhaMeta = new Stack<>();
        pilhaJogador = new Stack<>();
        inventario = new LinkedList<>();
        
        //Fim da limpeza
        
        //mudar aqui para permitir maior quantidade de opções :)
        List<Color> coresDisponiveis = Arrays.asList( 
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, 
            Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK
        );
        List<Color> coresSelecionadas = new LinkedList<>(coresDisponiveis.subList(0, numeroItens));
        
        //Fim de selecionar as cores do jogo, neste caso, por usar o subList, ele tira sempre os útlimos itens
        //Então por ex, se o jogo tiver 4 cores, essas cores vão ser sempre as do começo do vetor de cores
        //Disponíveis, se quiser que eu randomize só falar, mas acho que não precisa disso

        //Aqui abaixo a lógica de como funciona a montagem.
        
        // 1. Embaralha a lista UMA VEZ para definir a ordem da META

        Collections.shuffle(coresSelecionadas); //embaralha as cores
        for (Color cor : coresSelecionadas) {
            pilhaMeta.push(cor); //chama a estrutura (Pilha) do próprio Java e adiciona as cores
        }

        // 2. Embaralha a lista DE NOVO para garantir uma ordem diferente para o JOGADOR
        Collections.shuffle(coresSelecionadas); //Dessa vez embaralha a pilha que o jogador vai ter que organizar
        for (Color cor : coresSelecionadas) {
            pilhaJogador.push(cor);
        }
        
        // 3. Garante que, por uma pequena chance, as duas pilhas não comecem idênticas.
        if (verificarVitoria()) {
        	iniciar(); // Se começarem iguais, reinicia o processo para gerar novas ordens, como ao iniciar ele
        	//... limpa tudo, não há uso excessivo de memório :D
        }
    }

    
    //Essa função move o item do topo da pilha do JOGADOR, para o INVENTÁRIO DELE 
    public void moverParaInventario() {
        if (!pilhaJogador.isEmpty()) { //checar se não está vazio, claro
            inventario.add(pilhaJogador.pop()); //Ele adiciona no inventário o elemento que toma o pop, achei muito legal isso
        }
    }


    //Esse move o primeiro item do inventário (lembra que ele é uma fila) para o topo da pilha do jogador
    //A lógica é igualzinho o do de antes, e continua bem legal
    public void moverParaPilha() {
        if (!inventario.isEmpty()) {
            pilhaJogador.push(inventario.poll());
        }
    }

    //Aqui ele verifica se o jogador ganhou comparando as duas pilhas e retorna se ganhou (true) ou não (false); Bem auto-explicativo
    public boolean verificarVitoria() {
        return pilhaJogador.equals(pilhaMeta);
    }

    
    //Aqui ele calcula os pontos do jogador com base na dificuldade, esse multiplicador multiplica pelo tempo do timer estante posteriormente.
    @Override
    public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
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
                break;
        }
        this.pontuacao = (int) ((10 * tempoSobrando) * multiplicador); //Aqui é onde ele multiplica, ele multiplica por 10 também por ser mais legal,
        //... porém se precisarem que mude só avisar, é que a gente acabou não definindo como funcionaria a pontuação durante a reunião no sábado
        return this.pontuacao;
    }

    
    //Aqui o Enum que o gabriel falou, para poder mostrar qual a estrutura que é esse minigame
    @Override
    public EstruturaDados getEstruturaAssociada() {
        return this.estrutura;
    }

    // Getters para a View poder acessar os dados :)
    public Stack<Color> getPilhaMeta() {
        return pilhaMeta;
    }

    public Stack<Color> getPilhaJogador() {
        return pilhaJogador;
    }

    public Queue<Color> getInventario() {
        return inventario;
    }
    
    public int getTempoMaximo() {
    	return tempoMaximo;
    }
    
    
    //É isso, caso eu altere qualquer coisa vou deixar anotado aqui em baixo:
    //1. Nada ainda :)
}