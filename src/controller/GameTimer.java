/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Admin
 */
public class GameTimer{
    private long inicio;
    
    public GameTimer(){
        super();
    }
    public void iniciar(){
        this.inicio = System.currentTimeMillis();
    }
    public double getTime(){
        return (System.currentTimeMillis() - this.inicio) /1000;
    }
}
