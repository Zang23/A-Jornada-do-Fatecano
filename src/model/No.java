package model;

import java.awt.Color;

public class No {
	
    private int color;
    private No proximo;

    public No(int color) {
    	
        this.color = color;
        this.proximo = null;
        
    }

    public int getColor() {
    	
        return color;
        
    }

    public No getProximo() {
    	
        return proximo;
        
    }

    public void setProximo(No proximo) {
    	
        this.proximo = proximo;
        
    }
}