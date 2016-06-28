package net.webvalor.controller.comercial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import net.webvalor.Services.ProdutoServices;
import net.webvalor.dao.comercial.CategoriaDao;
import net.webvalor.model.comercial.CategoriaProduto;
import net.webvalor.model.comercial.Produto;
import net.webvalor.util.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaDao categoriaDao;

    @Inject
    private ProdutoServices cadastroProdutoService;

    private Produto produto;
    private CategoriaProduto categoriaPai;

    private List<CategoriaProduto> categoriasRaizes = new ArrayList<>();
    private List<CategoriaProduto> subcategorias;

    public CadastroProdutoBean() {
        limpar();
    }

    @PostConstruct
    public void init() {
        categoriasRaizes = categoriaDao.raizes();

    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            categoriasRaizes = categoriaDao.raizes();

            if (this.categoriaPai != null) {
                carregarSubcategorias();
            }
        }
        if (this.produto == null) {
            limpar();
        }

    }

    public void carregarSubcategorias() {
        subcategorias = categoriaDao.subcategoriasDe(categoriaPai);
    }

    private void limpar() {
        produto = new Produto();
        categoriaPai = null;
        subcategorias = new ArrayList<>();
    }

    public void salvar() {
        this.produto = cadastroProdutoService.salvar(this.produto);
        limpar();

        FacesUtil.addInfoMessage("Produto salvo com sucesso!");
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {

        this.produto = produto;
        if (this.produto != null) {
            this.categoriaPai = this.produto.getCategoria().getCategoriaPai();

        }
    }

    public List<CategoriaProduto> getCategoriasRaizes() {
        return categoriasRaizes;
    }

    @NotNull
    public CategoriaProduto getCategoriaPai() {
        return categoriaPai;
    }

    public void setCategoriaPai(CategoriaProduto categoriaPai) {
        this.categoriaPai = categoriaPai;
    }

    public List<CategoriaProduto> getSubcategorias() {
        return subcategorias;
    }

    public boolean Editando() {
        return this.produto.getId() != null;
    }

}
