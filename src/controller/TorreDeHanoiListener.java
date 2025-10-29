/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author dti
 */
import controller.TorreDeHanoiController.EstadoJogo;

public interface TorreDeHanoiListener {
    void atualizarTela();
    void mostrarMensagem(String msg);
    void jogoEncerrado(EstadoJogo estado);
}
