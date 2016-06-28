/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.webvalor.dao.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.webvalor.model.financeiro.CategoriaLancamento;
import net.webvalor.model.financeiro.LancamentoFinanceiro;

/**
 *
 * @author Felipe Santos
 */
@Named
@ViewScoped
public class LancFinanceiroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaLancamentoDao categDao;

    private CategoriaLancamento categoriaPai;

    private List<CategoriaLancamento> categoriasRaizes;
    private List<CategoriaLancamento> subcategorias = new ArrayList<CategoriaLancamento>();

    private LancamentoFinanceiro lancamento;

    public LancFinanceiroBean() {

        categoriasRaizes = new ArrayList<CategoriaLancamento>();
        subcategorias = new ArrayList<CategoriaLancamento>();
        lancamento = new LancamentoFinanceiro();

    }

//    public void inicializar() {
//        if (FacesUtil.isNotPostback()) {
//            categoriasRaizes = categDao.raizes();
//        }
//    }

    public void salvar() {
        System.out.println("Salvo: " + categoriaPai.getDescricao());

    }

    public CategoriaLancamento getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(CategoriaLancamento categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

//    public List<CategoriaLancamento> getCategoriasRaizes() {
//        categoriasRaizes = categDao.raizes();
//        return categoriasRaizes;
//    }

//    public List<CategoriaLancamento> getSubcategorias() {
//        //if (categoriaPai != null) {
//            subcategorias = categDao.subcategoriasDe(categoriaPai);
//        //}
//        return subcategorias;
//    }

    public LancamentoFinanceiro getLancamento() {

        return lancamento;
    }

    public void setLancamento(LancamentoFinanceiro lancamento) {
        this.lancamento = lancamento;
    }

}
