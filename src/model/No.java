package model;

/**
 * Representa um nó em uma estrutura de dados encadeada, como uma lista ou fila.
 * A classe é genérica, o que significa que pode armazenar qualquer tipo de dado.
 *
 * @param <T> o tipo do dado a ser armazenado no nó.
 */
public class No<T> {

    private T data;      // O dado armazenado no nó (pode ser String, Integer, etc.)
    private No<T> proximo; // Referência para o próximo nó na sequência

    /**
     * Construtor que cria um novo nó com o dado fornecido.
     * @param data O dado a ser armazenado.
     */
    public No(T data) {
        this.data = data;
        this.proximo = null; // O próximo nó é inicialmente nulo
    }

    // --- Getters e Setters ---

    /**
     * Retorna o dado armazenado neste nó.
     * @return O dado do tipo T.
     */
    public T getData() {
        return data;
    }

    /**
     * Define ou atualiza o dado armazenado neste nó.
     * @param data O novo dado a ser armazenado.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Retorna a referência para o próximo nó na estrutura.
     * @return O próximo nó.
     */
    public No<T> getProximo() {
        return proximo;
    }

    /**
     * Define a referência para o próximo nó na estrutura.
     * @param proximo O nó que será o próximo.
     */
    public void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }
}