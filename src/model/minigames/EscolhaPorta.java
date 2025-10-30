package model.minigames;

import model.EstruturaDados;
import model.Jogo;
import model.Dificuldade;
import model.No;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EscolhaPorta extends Jogo {

    // Agora, com o No genérico, esta declaração está correta
    private No<String> inicioFilaCores;
    private No<String> fimFilaCores;
    private int tamanhoFilaInicial;
    private int coresRestantes;

    private int numeroDePortas;

    private List<String> coresDasPortas;

    private final List<String> ASSETS_DISPONIVEIS = Arrays.asList(
        "black", "blue", "crimson", "green", "purple", "red", "yellow"
    );

    public EscolhaPorta(Dificuldade dificuldade) {
        super("Escolha a Porta", dificuldade, EstruturaDados.LISTA,
              "Combine a chave da fila com a porta correta.",
              "A fila de chaves avança a cada acerto ou erro. As portas mudam de cor a cada jogada!");
        
        configurarDificuldade();
        iniciar();
    }

    private void configurarDificuldade() {
        switch (dificuldade) {
            case MEDIO:
                this.numeroDePortas = 4;
                this.tamanhoFilaInicial = 30;
                break;
            case DIFICIL:
                this.numeroDePortas = 5;
                this.tamanhoFilaInicial = 40;
                break;
            case FACIL:
            default:
                this.numeroDePortas = 3;
                this.tamanhoFilaInicial = 20;
                break;
        }
    }

    @Override
    public void iniciar() {
        inicioFilaCores = null;
        fimFilaCores = null;
        this.coresRestantes = 0;
        this.pontuacao = 0;
        
        for (int i = 0; i < tamanhoFilaInicial; i++) {
            inserirCorNaFila(gerarCorAleatoria());
        }
        
        gerarCoresDasPortas();
    }

    // --- LÓGICA DO JOGO ---

    private void inserirCorNaFila(String cor) {
        No<String> novoNo = new No<>(cor); // Cria um No que armazena uma String
        if (inicioFilaCores == null) {
            inicioFilaCores = novoNo;
            fimFilaCores = novoNo;
        } else {
            fimFilaCores.setProximo(novoNo);
            fimFilaCores = novoNo;
        }
        this.coresRestantes++;
    }

    public void avancarFila() {
        if (inicioFilaCores != null) {
            inicioFilaCores = inicioFilaCores.getProximo();
            this.coresRestantes--;
            if (inicioFilaCores == null) {
                fimFilaCores = null;
            }
        }
    }

    public void gerarCoresDasPortas() {
        coresDasPortas = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < this.numeroDePortas; i++) {
            coresDasPortas.add(gerarCorAleatoria());
        }

        if (!isFilaVazia()) {
            int portaCorretaIndex = random.nextInt(this.numeroDePortas);
            coresDasPortas.set(portaCorretaIndex, getCorAtualDaFila());
        }
    }
    
    public boolean verificarClique(int indicePortaClicada) {
        if (isFilaVazia()) return false;

        String corPortaClicada = coresDasPortas.get(indicePortaClicada);
        if (corPortaClicada.equals(getCorAtualDaFila())) {
            int pontosGanhos = (dificuldade == Dificuldade.DIFICIL) ? 20 : (dificuldade == Dificuldade.MEDIO ? 15 : 10);
            this.pontuacao += pontosGanhos;
            return true;
        } else {
            int pontosPerdidos = (dificuldade == Dificuldade.DIFICIL) ? 15 : (dificuldade == Dificuldade.MEDIO ? 10 : 5);
            this.pontuacao = Math.max(0, this.pontuacao - pontosPerdidos);
            return false;
        }
    }

    public boolean isFilaVazia() {
        return inicioFilaCores == null;
    }
    
    private String gerarCorAleatoria() {
        Random random = new Random();
        return ASSETS_DISPONIVEIS.get(random.nextInt(ASSETS_DISPONIVEIS.size()));
    }

    @Override
    public int calcularPontuacao(int pontosMarcados, int tempoSobrando) {
        // A pontuação já é calculada em verificarClique.
        // O bônus de tempo foi removido.
        return this.pontuacao;
    }

    // --- GETTERS PARA VIEW E CONTROLLER ---

    @Override
    public EstruturaDados getEstruturaAssociada() {
        return this.estrutura;
    }

    public String getCorAtualDaFila() {
        return isFilaVazia() ? null : inicioFilaCores.getData();
    }

    public No<String> getInicioFilaCores() {
        return inicioFilaCores;
    }

    public List<String> getCoresDasPortas() {
        return coresDasPortas;
    }
    
    public int getNumeroDePortas() {
        return numeroDePortas;
    }
    
    public int getCoresRestantes() {
        return coresRestantes;
    }
}