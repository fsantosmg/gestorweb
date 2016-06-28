package net.webvalor.controller.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
//import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import net.webvalor.Services.CategoriaLancamentoRN;
import net.webvalor.index.ContextoBean;
import net.webvalor.model.financeiro.CategoriaLancamento;
import net.webvalor.util.ContextoUtil;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@ViewScoped
public class CategoriaLancamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CategoriaLancamentoRN categoriaRN;

    private TreeNode categoriasTree;

    @Inject
    private CategoriaLancamento editada;

    private List<SelectItem> categoriasSelect;
    private boolean mostraEdicao = false;

    private TreeNode selectedNode;

    private CategoriaLancamento pai;

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {

        this.selectedNode = selectedNode;
    }

    public void novo() {
        try {
            CategoriaLancamento categ = (CategoriaLancamento) selectedNode.getData();
            //Categoria pai = null;

            if (categ.getCodigo() != null) {
                // CategoriaRN categoriaRN = new CategoriaRN();
                pai = categoriaRN.carregar(categ.getCodigo());
            }
            //this.editada = new Categoria();
            //this.editada.setPai(pai);
            //this.editada.setFator(pai.getFator());
            this.mostraEdicao = true;

        } catch (NullPointerException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selecione uma categoria", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void displaySelectedSingle() {
        if (selectedNode != null) {

            CategoriaLancamento categ = (CategoriaLancamento) selectedNode.getData();
            System.out.println(categ.getDescricao());
            System.out.println(categ.getPai().getDescricao());
            // FacesMessage message = new
            // FacesMessage(FacesMessage.SEVERITY_INFO,
            // "Selected", selectedNode.getData());
            // FacesContext.getCurrentInstance().addMessage(null, message);
            selectedNode = null;
        }
    }

    public void selecionar(NodeSelectEvent event) {
        this.editada = (CategoriaLancamento) event.getTreeNode().getData();

        this.mostraEdicao = this.editada != null && pai != null && pai.getCodigo() != null;
//        if (this.editada != null && pai != null && pai.getCodigo() != null) {
//            this.mostraEdicao = true;
//        } else {
//            this.mostraEdicao = false;
//        }
    }

    public void salvar() {

        ContextoBean contextoBean = ContextoUtil.getContextoBean();

        this.editada.setEmpresa(contextoBean.getEmpresaSelecionada());
        this.editada.setPai(this.pai);
        this.editada.setFator(this.pai.getFator());

        categoriaRN.salvar(this.editada);

        this.editada = null;
        this.mostraEdicao = false;
        this.categoriasTree = null;
        this.categoriasSelect = null;
    }

    public void cancelar() {
        this.editada = null;
        this.mostraEdicao = false;
        this.categoriasTree = null;
        this.categoriasSelect = null;
    }

    public void excluir() {
        CategoriaLancamento categ = (CategoriaLancamento) selectedNode.getData();
        categoriaRN.excluir(categ);

        this.mostraEdicao = false;
        this.categoriasTree = null;
        this.categoriasSelect = null;
    }

    public TreeNode getCategoriasTree() {
        if (this.categoriasTree == null) {

            ContextoBean contextoBean = ContextoUtil.getContextoBean();

            List<CategoriaLancamento> categorias = categoriaRN.listar(contextoBean.getEmpresaSelecionada());

            this.categoriasTree = new DefaultTreeNode(null, null);
            this.montaDadosTree(this.categoriasTree, categorias);
        }
        return this.categoriasTree;
    }

    private void montaDadosTree(TreeNode pai, List<CategoriaLancamento> lista) {
        if (lista != null && lista.size() > 0) {
            TreeNode filho = null;
            for (CategoriaLancamento categoria : lista) {
                filho = new DefaultTreeNode(categoria, pai);
                this.montaDadosTree(filho, categoria.getFilhos());
            }
        }
    }

    public List<SelectItem> getCategoriasSelect() {
        List<CategoriaLancamento> categorias = null;
        if (this.categoriasSelect == null) {
            this.categoriasSelect = new ArrayList<SelectItem>();
            ContextoBean contextoBean = ContextoUtil.getContextoBean();

            // CategoriaRN categoriaRN = new CategoriaRN();
            categorias = categoriaRN.listar(contextoBean.getEmpresaSelecionada());
            this.montaDadosSelect(this.categoriasSelect, categorias, "");
        }
        return categoriasSelect;
    }

    private void montaDadosSelect(List<SelectItem> select, List<CategoriaLancamento> categorias, String prefixo) {

        SelectItem item = null;
        if (categorias != null) {
            for (CategoriaLancamento categoria : categorias) {
                item = new SelectItem(categoria, prefixo + categoria.getDescricao());
                item.setEscape(false);
                select.add(item);
                this.montaDadosSelect(select, categoria.getFilhos(), prefixo + "&nbsp;&nbsp;");
            }
        }
    }

    public CategoriaLancamento getEditada() {
        return editada;
    }

    public void setEditada(CategoriaLancamento editada) {
        this.editada = editada;
    }

    public boolean isMostraEdicao() {
        return mostraEdicao;
    }

    public void setMostraEdicao(boolean mostraEdicao) {
        this.mostraEdicao = mostraEdicao;
    }

    public void setCategoriasTree(TreeNode categoriasTree) {
        this.categoriasTree = categoriasTree;
    }

    public void setCategoriasSelect(List<SelectItem> categoriasSelect) {
        this.categoriasSelect = categoriasSelect;
    }

}
