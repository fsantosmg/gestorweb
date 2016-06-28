/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.webvalor.model.financeiro;

import java.io.Serializable;

/**
 *
 * @author Felipe Santos
 */
public class LancamentoFinanceiro implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private CategoriaLancamento categoria;

    public CategoriaLancamento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaLancamento categoria) {
        this.categoria = categoria;
    }
    
    
    
}
