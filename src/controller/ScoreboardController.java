package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardController {

    private static final String BASE_FOLDER_NAME = "ScoreBoard";

    /**
     * Registra a pontuação de um jogador para um minijogo específico.
     * A pontuação só é salva se for maior que a pontuação anterior do mesmo jogador.
     *
     * @param nomeJogo O nome do minijogo (ex: "Organize a Pilha").
     * @param nomeJogador O nome do jogador.
     * @param pontuacao A pontuação obtida.
     */
    public static void registrarPontuacao(String nomeJogo, String nomeJogador, int pontuacao) {
        try {
            // Cria o caminho para o diretório base na pasta do usuário
            String userHome = System.getProperty("user.home");
            File baseDir = new File(userHome, BASE_FOLDER_NAME);
            
            // Cria o diretório específico do jogo
            File gameDir = new File(baseDir, nomeJogo.replaceAll("[^a-zA-Z0-9.-]", "_")); // Sanitiza o nome do jogo para nome de pasta
            if (!gameDir.exists()) {
                gameDir.mkdirs();
            }
            System.out.println(gameDir);

            File scoreFile = new File(gameDir, "scoreboard.txt");

            // Lê os scores existentes para um mapa
            Map<String, Integer> scores = lerScores(scoreFile);

            // Verifica se a nova pontuação é maior ou se o jogador não existe no placar
            if (!scores.containsKey(nomeJogador) || pontuacao > scores.get(nomeJogador)) {
                scores.put(nomeJogador, pontuacao);
                escreverScores(scoreFile, scores);
                System.out.println("Nova pontuação máxima para " + nomeJogador + " no jogo " + nomeJogo + ": " + pontuacao);
            }

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao registrar a pontuação.");
            e.printStackTrace();
        }
    }

    /**
     * Lê um arquivo de scoreboard e retorna um mapa com os nomes dos jogadores e suas pontuações.
     */
    private static Map<String, Integer> lerScores(File scoreFile) throws IOException {
        Map<String, Integer> scores = new HashMap<>();
        if (!scoreFile.exists()) {
            return scores; // Retorna mapa vazio se o arquivo não existe
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    scores.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        }
        return scores;
    }

    /**
     * Escreve o mapa de scores de volta para o arquivo, sobrescrevendo o conteúdo anterior.
     */
    private static void escreverScores(File scoreFile, Map<String, Integer> scores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile, false))) { // false para sobrescrever
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }
    
    public static List<Map.Entry<String, Integer>> getScoresOrdenados(String nomeJogo) {
        try {
            String userHome = System.getProperty("user.home");
            File baseDir = new File(userHome, BASE_FOLDER_NAME);
            File gameDir = new File(baseDir, nomeJogo.replaceAll("[^a-zA-Z0-9.-]", "_"));
            System.out.println(gameDir);
            File scoreFile = new File(gameDir, "scoreboard.txt");

            if (!scoreFile.exists()) {
                return new ArrayList<>(); // Retorna lista vazia se não houver scoreboard
            }

            Map<String, Integer> scores = lerScores(scoreFile);

            // Converte o mapa para uma lista para poder ordenar
            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(scores.entrySet());

            // Ordena a lista em ordem decrescente de pontuação
            sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

            return sortedList;

        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao ler o scoreboard para o jogo: " + nomeJogo);
            e.printStackTrace();
            return new ArrayList<>(); // Retorna lista vazia em caso de erro
        }
    }
}