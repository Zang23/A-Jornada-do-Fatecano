package controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.print.attribute.standard.PrinterInfo;

import model.Jogo;
import model.RegistroPontuacao;
import model.minigames.OrdenaNumeros;
import view.FSelectJogos;
import view.TelaOrdenaNumeros;

public class OrdenaNumerosController {
    private OrdenaNumeros jogo;

    public OrdenaNumerosController(OrdenaNumeros jogo) {
        this.jogo = jogo;
    }

    public void moverEsquerda() {
        jogo.moverPonteiro(-1);
    }

    public void moverDireita() {
        jogo.moverPonteiro(1);
    }

    public void acaoEspaco() {
        if (jogo.getNumeroSegurado() == null)
            jogo.puxarNumero();
        else
            jogo.inserirNumero();
    }

    public boolean verificarOrdenado() {
        return jogo.estaOrdenado();
    }

    public OrdenaNumeros getJogo() {
        return jogo;
    }
    
    public void registraScore(RegistroPontuacao registro)throws IOException{
    
    	String caminho = "C:\\Users\\dti\\Documents\\ScoreBoards";
    	Jogo jogo = registro.getJogo();
    	String nomeJogo = jogo.getNome();
    	
    	File folderGeral = new File(caminho);
    	if(!folderGeral.exists()) {
    		folderGeral.mkdir();
    	}
    	
    	File folderJogo = new File(folderGeral + nomeJogo);
    	
    	if(!folderJogo.exists()) {
    		folderJogo.mkdir();
    		
    	}
    	
    	File scoreJogo = new File(folderJogo, "scoreboard.txt");
    	
    	if(!scoreJogo.exists()) {
    		FileWriter fileWriter = new FileWriter(scoreJogo);
    		PrintWriter printWriter = new PrintWriter(fileWriter);
    		
    		StringBuffer sBuffer = new StringBuffer();
    		sBuffer.append(registro.getUsuario() + ", ").append(registro.getJogo() + ", ").append(registro.getPontuacao() + ", ");
    		printWriter.println(sBuffer.toString());
    		
    		
    		printWriter.close();
    		fileWriter.close();
    	}else {
    		
    		FileInputStream fluxo = new FileInputStream(scoreJogo);
    		InputStreamReader leitor =  new InputStreamReader(fluxo);
    		BufferedReader buffer = new BufferedReader(leitor);
    		String linha = buffer.readLine();
    		
    		while(linha != null) {
    			linha = buffer.readLine();
    		}
    		
    		FileWriter fileWriter = new FileWriter(scoreJogo);
    		PrintWriter printWriter = new PrintWriter(fileWriter);
    		
    		StringBuffer sBuffer = new StringBuffer();
    		sBuffer.append(registro.getUsuario() + ", ").append(registro.getJogo() + ", ").append(registro.getPontuacao() + ", ");
    		printWriter.println(sBuffer.toString());
    		
    		printWriter.close();
    		fileWriter.close();
    		buffer.close();
    		leitor.close();
    		fluxo.close();
    		
    	}
    	
    	
    	
    	
    	
    }

	public void retornaSelectJogo() {

		NameController nome = new NameController();
		FSelectJogos telaSelect = new FSelectJogos(nome.getName());
		TelaOrdenaNumeros tela = new TelaOrdenaNumeros();
		
		tela.setVisible(false);
		
		telaSelect.SetDefautProperties(telaSelect);
		telaSelect.setVisible(true);
		
	}
    
    
    
}
