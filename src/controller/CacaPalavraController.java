package controller;

import model.RegistroPontuacao;
import model.Scoreboard;
import model.Usuario;
import model.minigames.CacaPalavra;
import view.FSelectJogos;
import view.TelaCacaPalavra;

public class CacaPalavraController {
    
    private CacaPalavra jogo;

    public CacaPalavraController(CacaPalavra jogo) {
        this.jogo = jogo;
    }

    public void moverEsquerda() {
        jogo.moverPonteiro(-1);
    }

    public void moverDireita() {
        jogo.moverPonteiro(1);
    }

    public void acaoEspaco() {
        if (jogo.getLetraSegurada() == null)
            jogo.puxarLetra();
        else
            jogo.inserirLetra();
    }

    public CacaPalavra getJogo() {
        return jogo;
    }

    public boolean getIsCorrect() {
        return jogo.isRespostaCerta();
    }

    public void retornaSelectJogo() {
        NameController nome = new NameController();
        FSelectJogos telaSelect = new FSelectJogos(nome.getName());
        TelaCacaPalavra tela = new TelaCacaPalavra();
        tela.setVisible(false);
        telaSelect.SetDefautProperties(telaSelect);
        telaSelect.setVisible(true);
    }

    public void registrarPontuacao(int pontuacao) {
        try {
            NameController nomeController = new NameController();
            String nomeJogador = nomeController.getName();

            Usuario usuario = new Usuario(nomeJogador);
            Scoreboard scoreboard = new Scoreboard();

            RegistroPontuacao registro = new RegistroPontuacao(usuario, jogo, pontuacao);
            scoreboard.adicionarRegistro(registro);

            salvarPontuacaoEmArquivo(registro);

            System.out.println("Pontuação salva com sucesso: " + registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void salvarPontuacaoEmArquivo(RegistroPontuacao registro) {
        try {
            java.io.File pasta = new java.io.File(System.getProperty("user.home"), "Documentos/Pontuacoes");
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            java.io.File arquivo = new java.io.File(pasta, "scoreboard_cacapalavra.txt");

            try (java.io.FileWriter fw = new java.io.FileWriter(arquivo, true)) {
                fw.write(registro.toString() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
